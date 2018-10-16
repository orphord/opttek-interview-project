package com.opttek.orford.logistics.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.exception.LogisticsException;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;
import com.opttek.orford.logistics.service.OptimizerService;

public class SwapAndCompare {
	private static final Logger log = LoggerFactory.getLogger(SwapAndCompare.class);

	public SwapAndCompare() {}

	public SwapResponse checkOptimal(NodeSequence _seq, Integer _indexToChange) throws LogisticsException {
		SwapResponse resp = null;
		NodeSequence baselineSequence = _seq;
		NodeSequence operationSequence = baselineSequence.cloneNodeSequence();
		Integer indexOfInterest = _indexToChange;

		operationSequence.doSwap(indexOfInterest);

		Integer swappedTransitionCost = operationSequence.totalTransitionTime();
		Integer baselineTransitionCost = baselineSequence.totalTransitionTime();
		int netTransitionCostChange = baselineTransitionCost.intValue() - swappedTransitionCost.intValue();

		//Now, if swap was a net positive (ie. better than break even) recursively call OptimizerService.doOptimization
		if(netTransitionCostChange > 0) {
			OptimizerService opService = new OptimizerService();
			resp = opService.optimize(operationSequence);
			// Need to set transition cost for response to be relative to the baseline of this request
			Integer respSeqTransitionCost = resp.getSwappedSequence().totalTransitionTime();
			resp.setNetChangeFromBaseLine(Integer.valueOf(baselineTransitionCost - respSeqTransitionCost.intValue()));
		} else {
			resp = new SwapResponse();
			resp.setSwappedSequence(baselineSequence);
			resp.setNetChangeFromBaseLine(Integer.valueOf(0));
		}

		return resp;
	}

}
