package com.opttek.orford.logistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.callable.SwapAndCompareCallable;
import com.opttek.orford.logistics.dao.FileDataAccessor;
import com.opttek.orford.logistics.exception.LogisticsException;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.service.OptimizerService;


public class LogisticsController {
	private static final Logger log = LoggerFactory.getLogger(LogisticsController.class);
	private NodeSequence baselineSequence;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		LogisticsController controller = new LogisticsController();
		controller.doOptimization();

	}

	private void doOptimization() throws InterruptedException, ExecutionException {

		this.loadData();
		OptimizerService optService = new OptimizerService();
		try {
			NodeSequence optimal = optService.doOptimization(baselineSequence);
			log.info("The OPTIMAL NodeSequence is: " + optimal.toString());
		} catch(LogisticsException ex) {
			log.error("A LogisticsException was thrown with the following stack trace: ", ex);
			ex.printStackTrace();
		}

	}

	private void loadData() {
		FileDataAccessor dataAccessor = FileDataAccessor.getInstance();
		dataAccessor.getNodeData();
		dataAccessor.getTransitionMatrix();
		baselineSequence = dataAccessor.getInitialSequenceData();
		
	}
	
}
