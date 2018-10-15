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
		// Initialize bestSeq to the baseline (to start the best *is* the baseline)
		NodeSequence bestSeq = _baselineSeq;
		int numTasks = _baselineSeq.getNumTransitions();
//TESTCODE-------------
log.info("=============================");
log.info("BASELINESEQ: ");
log.info(_baselineSeq.toString());
//---------------------
		boolean stillOptimizeable = true;
		while(stillOptimizeable) {
			ExecutorService executor = Executors.newFixedThreadPool(numTasks);

			// Loop numTasks and create callable objects each with a different index to test
			List<SwapAndCompareCallable> callableList= new ArrayList<SwapAndCompareCallable>(numTasks);
			for(int j = 0; j <= numTasks; j++) {
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
			List<SwapResponse> respsFromThisRound = new ArrayList<SwapResponse>(answers.size());
			for(Future<SwapResponse> futResp : answers) {
				try {
					respsFromThisRound.add(futResp.get());
				} catch (InterruptedException | ExecutionException ex) {
					throw new LogisticsException(ex);
				}
			}

			// Sort responses in reverse order so largest net savings from baseline is at the top 
			Collections.sort(respsFromThisRound, Collections.reverseOrder());
			
			// Check if the best (zero-th) resp has a better than zero net change
			if(respsFromThisRound.get(0).getNetChangeFromBaseLine().intValue() > 0) {
				bestSeq = respsFromThisRound.get(0).getSwappedSequence();
			} else {
				stillOptimizeable = false; 
			}
//TESTCODE---------
log.info("#########################");
for(SwapResponse resp : respsFromThisRound) {
	log.info("Net savings over baseline: " + resp.getNetChangeFromBaseLine());
	log.info("Which index was that??: " + resp.getIndexOfInterest());
}
log.info("#########################");
//-----------------
			
//TESTCODE----------------
log.info("++++++++++++++++++++++");
log.info("BEST SEQUENCE: " + bestSeq.toString());
//------------------------
		}

		return bestSeq;
		
	}
}
