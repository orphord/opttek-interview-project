package com.opttek.orford.logistics.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.service.NodeTransitionService;

public class SwapAndCompareCallable implements Callable<NodeSequence> {
	private static final Logger log = LoggerFactory.getLogger(SwapAndCompareCallable.class);
	private NodeTransitionService transitionService = NodeTransitionService.getInstance();
	private NodeSequence operationSequence;
	private Integer indexOfInterest;


	public SwapAndCompareCallable(NodeSequence _seq, Integer _indexToChange) {
		log.info("Constructing SwapAndCompareCallable for index: " + _indexToChange);
		operationSequence = _seq.cloneNodeSequence();
		indexOfInterest = _indexToChange;

	}

	public NodeSequence call() throws Exception {
		operationSequence.doSwap(indexOfInterest);
		return operationSequence;

	}

}
