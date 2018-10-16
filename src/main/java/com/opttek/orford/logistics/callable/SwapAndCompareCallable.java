package com.opttek.orford.logistics.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;
import com.opttek.orford.logistics.service.OptimizerService;

public class SwapAndCompareCallable implements Callable<SwapResponse> {
	private static final Logger log = LoggerFactory.getLogger(SwapAndCompareCallable.class);

	private NodeSequence operationSequence;
	private NodeSequence baselineSequence;
	private Integer indexOfInterest;


	public SwapAndCompareCallable(NodeSequence _seq, Integer _indexToChange) {
		log.debug("Constructing SwapAndCompareCallable for index: " + _indexToChange);
		baselineSequence = _seq;
		operationSequence = baselineSequence.cloneNodeSequence();
		indexOfInterest = _indexToChange;

	}

	public SwapResponse call() throws Exception {
		SwapResponse resp = null;

		operationSequence.doSwap(indexOfInterest);

		Integer swappedTransitionCost = operationSequence.totalTransitionCost();
		Integer baselineTransitionCost = baselineSequence.totalTransitionCost();
		int netTransitionCostChange = baselineTransitionCost.intValue() - swappedTransitionCost.intValue();

		//Now, if swap was a net positive (ie. better than break even) recursively call OptimizerService.doOptimization
		if(netTransitionCostChange > 0) {
			OptimizerService opService = new OptimizerService();
			resp = opService.optimize(operationSequence);
			// Need to set transition cost for response to be relative to the baseline of this request
			Integer respSeqTransitionCost = resp.getSwappedSequence().totalTransitionCost();
			resp.setNetChangeFromBaseLine(Integer.valueOf(baselineTransitionCost - respSeqTransitionCost.intValue()));
		} else {
			resp = new SwapResponse();
			resp.setSwappedSequence(baselineSequence);
			resp.setNetChangeFromBaseLine(Integer.valueOf(0));
		}

		return resp;
	}

}
