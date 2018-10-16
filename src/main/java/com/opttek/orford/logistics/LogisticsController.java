package com.opttek.orford.logistics;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.dao.FileDataAccessor;
import com.opttek.orford.logistics.exception.LogisticsException;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;
import com.opttek.orford.logistics.service.OptimizerService;


public class LogisticsController {
	private static final Logger log = LoggerFactory.getLogger(LogisticsController.class);
	private NodeSequence baselineSequence;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		LogisticsController controller = new LogisticsController();
		String commaDelimitedSequence = null;
		if(args.length >  0) {
			commaDelimitedSequence = args[0];
		}

		controller.doOptimization(commaDelimitedSequence);

	}

	public SwapResponse doOptimization(String _commaSeq) throws InterruptedException, ExecutionException {
		log.debug("Optimizing sequence: " + _commaSeq);
		
		SwapResponse finalResponse = null;
		this.loadData(_commaSeq);
		OptimizerService optService = new OptimizerService();
		try {
			finalResponse = optService.optimize(baselineSequence);
			log.info("********************");
			log.info("The OPTIMAL NodeSequence is: " + finalResponse.getSwappedSequence().toString());
			log.info("********************");
		} catch(LogisticsException ex) {
			log.error("A LogisticsException was thrown with the following stack trace: ", ex);
			ex.printStackTrace();
		}

		return finalResponse;
	}

	private void loadData(String _commaDelimitedSequence) {
		FileDataAccessor dataAccessor = FileDataAccessor.getInstance();
		dataAccessor.getNodeData();
		dataAccessor.getTransitionMatrix();
		baselineSequence = dataAccessor.getInitialSequenceData(_commaDelimitedSequence);
		
	}
	
}
