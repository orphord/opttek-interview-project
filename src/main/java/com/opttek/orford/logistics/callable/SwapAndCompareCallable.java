package com.opttek.orford.logistics.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;
import com.opttek.orford.logistics.service.NodeTransitionService;

public class SwapAndCompareCallable implements Callable<SwapResponse> {
	private static final Logger log = LoggerFactory.getLogger(SwapAndCompareCallable.class);
	private NodeTransitionService transitionService = NodeTransitionService.getInstance();
	private Integer baselineTransitionCost;
	private NodeSequence operationSequence;
	private Integer indexOfInterest;


	public SwapAndCompareCallable(NodeSequence _seq, Integer _indexToChange) {
		log.info("Constructing SwapAndCompareCallable for index: " + _indexToChange);
		baselineTransitionCost = _seq.totalTransitionCost();
		operationSequence = _seq.cloneNodeSequence();
		indexOfInterest = _indexToChange;

	}

	public SwapResponse call() throws Exception {
		SwapResponse resp = new SwapResponse();

		operationSequence.doSwap(indexOfInterest);
		resp.setSwappedSequence(operationSequence);
		int baselineTransition = baselineTransitionCost.intValue();
		int swappedTransition  = operationSequence.totalTransitionCost().intValue();
		Integer netTransitionChange = Integer.valueOf(baselineTransition - swappedTransition);
		resp.setNetChangeFromBaseLine(netTransitionChange);

		return resp;

	}

}
