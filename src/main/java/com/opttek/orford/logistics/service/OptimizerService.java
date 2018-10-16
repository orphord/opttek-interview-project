package com.opttek.orford.logistics.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	
	public SwapResponse optimize(NodeSequence _baselineSeq) throws LogisticsException {
		// Initialize bestSeq to the baseline (to start the best *is* the baseline)
		NodeSequence bestSeq = _baselineSeq;
		int numTasks = _baselineSeq.getNumTransitions();
//TESTCODE-------------
log.info(java.lang.Thread.currentThread().getName() + ": =============================");
log.info(java.lang.Thread.currentThread().getName() + ": BASELINESEQ numTransitions: " + numTasks);
log.info(java.lang.Thread.currentThread().getName() + _baselineSeq.toString());
//---------------------

		ExecutorService executor = Executors.newFixedThreadPool(numTasks);

		// Loop numTasks and create callable objects each with a different index to test
		List<SwapAndCompareCallable> callableList= new ArrayList<SwapAndCompareCallable>(numTasks);
		for(int j = 0; j < numTasks; j++) {
			callableList.add(new SwapAndCompareCallable(bestSeq, j));
		}

		// Create an array list of futures to be populated by just created callables and assign to answers list
		List<Future<SwapResponse>> answers = new ArrayList<Future<SwapResponse>>();
		for(SwapAndCompareCallable task : callableList) {
			answers.add(executor.submit(task));
		}

		// End executor
		executor.shutdown();

		// Loop thru answers list of future objects and get actual responses
		Set<SwapResponse> respSetFromThisRound = new HashSet<SwapResponse>();
		for(Future<SwapResponse> futResp : answers) {
			try {
				respSetFromThisRound.add(futResp.get());
			} catch (InterruptedException | ExecutionException ex) {
				throw new LogisticsException(ex);
			}
		}
		List<SwapResponse> respList = new ArrayList<SwapResponse>(respSetFromThisRound);

		// Sort responses in reverse order so largest net savings from baseline is at the top 
		Collections.sort(respList, Collections.reverseOrder());


//TESTCODE---------
log.info(java.lang.Thread.currentThread().getName() + ": #########################");
for(SwapResponse resp : respList) {
	log.info(java.lang.Thread.currentThread().getName() + ": Sequence: " + resp.getSwappedSequence().toString());
	log.info(java.lang.Thread.currentThread().getName() + ": Net savings over baseline: " + resp.getNetChangeFromBaseLine());
}
log.info(java.lang.Thread.currentThread().getName() + ": *************************");
//-----------------

		return respList.get(0);

	}
}
