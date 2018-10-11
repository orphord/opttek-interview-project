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
import com.opttek.orford.logistics.model.NodeSequence;


public class LogisticsController {
	private static final Logger log = LoggerFactory.getLogger(LogisticsController.class);
	private NodeSequence initialSequence;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		LogisticsController controller = new LogisticsController();
		Integer sum = controller.sumOfCallables(5); 
		log.info("The sum of all callables is: " + sum.intValue());

	}

	private Integer sumOfCallables(int numTasks) throws InterruptedException, ExecutionException {
		log.info("SumOfCallables method called with number : " + numTasks);
		this.loadData();

		ExecutorService executor = Executors.newFixedThreadPool(numTasks);
		List<SwapAndCompareCallable> callableList= new ArrayList<SwapAndCompareCallable>(numTasks);
		for(int i = 0; i < numTasks; i++) {
			callableList.add(new SwapAndCompareCallable(i, i + 1));
		}
		
		List<Future<Integer>> answers = new ArrayList<Future<Integer>>();
		for(SwapAndCompareCallable task : callableList) {
			answers.add(executor.submit(task));
		}
		
		// End executor
		executor.shutdown();

		int sum = 0;
		for(Future<Integer> ans : answers) {
			sum += ans.get().intValue();
		}

		return Integer.valueOf(sum);
	}

	private void loadData() {
		FileDataAccessor dataAccessor = FileDataAccessor.getInstance();
		dataAccessor.getNodeData();
		dataAccessor.getTransitionMatrix();
		initialSequence = dataAccessor.getInitialSequenceData();
		
	}
	
}
