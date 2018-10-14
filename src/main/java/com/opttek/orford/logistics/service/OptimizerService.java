package com.opttek.orford.logistics.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.callable.SwapAndCompareCallable;
import com.opttek.orford.logistics.exception.LogisticsException;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;

public class OptimizerService {
	private Logger log = LoggerFactory.getLogger(OptimizerService.class);
	
	public NodeSequence doOptimization(NodeSequence _baselineSeq) throws LogisticsException {
		NodeSequence bestSeq = null;
		int numTasks = _baselineSeq.getNumTransitions();

		ExecutorService executor = Executors.newFixedThreadPool(numTasks);
		List<SwapAndCompareCallable> callableList= new ArrayList<SwapAndCompareCallable>(numTasks);
		for(int i = 0; i <= numTasks; i++) {
			callableList.add(new SwapAndCompareCallable(_baselineSeq, i));
		}

		List<Future<SwapResponse>> answers = new ArrayList<Future<SwapResponse>>();
		for(SwapAndCompareCallable task : callableList) {
			answers.add(executor.submit(task));
		}

		// End executor
		executor.shutdown();

		List<SwapResponse> respsFromThisRound = new ArrayList<SwapResponse>(answers.size());
		for(Future<SwapResponse> futResp : answers) {
			try {
				respsFromThisRound.add(futResp.get());
			} catch (InterruptedException | ExecutionException ex) {
				throw new LogisticsException(ex);
			}
		}

		Collections.sort(respsFromThisRound);


		return respsFromThisRound.get(0).getSwappedSequence();
		
	}
}
