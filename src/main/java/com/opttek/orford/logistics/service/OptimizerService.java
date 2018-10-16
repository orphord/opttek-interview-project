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

import com.opttek.orford.logistics.callable.SwapAndCompare;
import com.opttek.orford.logistics.exception.LogisticsException;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;

public class OptimizerService {
	private Logger log = LoggerFactory.getLogger(OptimizerService.class);
	
	public SwapResponse optimize(NodeSequence _baselineSeq) throws LogisticsException {
		log.debug("optimize function called on Sequence: " + _baselineSeq.toString());
		// Initialize bestSeq to the baseline (to start the best *is* the baseline)
		NodeSequence bestSeq = _baselineSeq;
		int numTasks = _baselineSeq.getNumTransitions();

		// Loop numTasks times and make recursive call to find optimal sequence for each path
		Set<SwapResponse> respSetFromThisRound = new HashSet<SwapResponse>();
		for(int j = 0; j < numTasks; j++) {
			SwapAndCompare operator = new SwapAndCompare();
			respSetFromThisRound.add(operator.checkOptimal(bestSeq, j));

		}

		// 
		List<SwapResponse> respList = new ArrayList<SwapResponse>(respSetFromThisRound);

		// Sort responses in reverse order so largest net savings from baseline is at the top 
		Collections.sort(respList, Collections.reverseOrder());

		return respList.get(0);

	}
}
