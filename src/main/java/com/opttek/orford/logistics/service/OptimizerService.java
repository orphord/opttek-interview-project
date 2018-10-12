package com.opttek.orford.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.callable.SwapAndCompareCallable;
import com.opttek.orford.logistics.model.NodeSequence;

public class OptimizerService {
	private Logger log = LoggerFactory.getLogger(OptimizerService.class);
	
	public NodeSequence doOptimization(NodeSequence _baselineSeq) {
		NodeSequence bestSeq = null;
		int numTasks = 1;//_baselineSeq.getNumTransitions();

		ExecutorService executor = Executors.newFixedThreadPool(numTasks);
		List<SwapAndCompareCallable> callableList= new ArrayList<SwapAndCompareCallable>(numTasks);
		for(int i = 0; i < numTasks; i++) {
			callableList.add(new SwapAndCompareCallable(_baselineSeq, i));
		}

		List<Future<NodeSequence>> answers = new ArrayList<Future<NodeSequence>>();
		for(SwapAndCompareCallable task : callableList) {
			answers.add(executor.submit(task));
		}

		// End executor
		executor.shutdown();

		return bestSeq;
		
	}
}