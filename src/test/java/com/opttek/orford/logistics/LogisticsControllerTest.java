package com.opttek.orford.logistics;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogisticsControllerTest {
	private static final Logger log = LoggerFactory.getLogger(LogisticsControllerTest.class);

	private LogisticsController logController;

	@Before
	public void setUp() throws Exception {
		logController = new LogisticsController();
	}

	@Test
	public void testDoOptimization() throws InterruptedException, ExecutionException {
		logController.doOptimization("B,C,E");
	}

}
