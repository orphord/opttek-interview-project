package com.opttek.orford.logistics;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.Node;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.SwapResponse;
import com.opttek.orford.logistics.service.NodeService;

public class LogisticsControllerTest {
	private static final Logger log = LoggerFactory.getLogger(LogisticsControllerTest.class);

	private LogisticsController logController;
	private NodeService nodeService;
	@Before
	public void setUp() throws Exception {
		logController = new LogisticsController();
		nodeService = NodeService.getInstance();
	}

	@Test
	public void testControllerExists() {
		assertNotNull("Controller should exist at this point.", logController);
	}

	@Test
	public void testDoOptimizationBCE() throws InterruptedException, ExecutionException {
		SwapResponse resp = logController.doOptimization("B,C,E");
		Node E = nodeService.getNodeByName("E");
		Node C = nodeService.getNodeByName("C");
		Node B = nodeService.getNodeByName("B");
		NodeSequence seqToCompare = new NodeSequence();
		seqToCompare.addNode(E);
		seqToCompare.addNode(C);
		seqToCompare.addNode(B);

		assertEquals("The optimal response should be ECB.", seqToCompare, resp.getSwappedSequence());
	}

	@Test
	public void testDoOptimizationABCD() throws InterruptedException, ExecutionException {
		SwapResponse resp = logController.doOptimization("A,B,C,D");
		Node A = nodeService.getNodeByName("A");
		Node B = nodeService.getNodeByName("B");
		Node C = nodeService.getNodeByName("C");
		Node D = nodeService.getNodeByName("D");
		NodeSequence seqToCompare = new NodeSequence();
		seqToCompare.addNode(C);
		seqToCompare.addNode(B);
		seqToCompare.addNode(A);
		seqToCompare.addNode(D);

		assertEquals("The optimal response should be CBAD.", seqToCompare, resp.getSwappedSequence());
	}
}
