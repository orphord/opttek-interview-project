package com.opttek.orford.logistics.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.NodeTransitionMatrix;
import com.opttek.orford.logistics.service.NodeService;

public class FileDataAccessorTest {
	private static final Logger log = LoggerFactory.getLogger(FileDataAccessorTest.class);

	private FileDataAccessor fileAccessor;

	@Before
	public void setUp() throws Exception {
		fileAccessor = FileDataAccessor.getInstance();
	}

	@Test
	public void testGetNodeData() {
		log.info("Test Node data retrieval.");
		fileAccessor.getNodeData();
		NodeService nodeService = NodeService.getInstance();
		assertTrue(nodeService.getNodeByName("A") != null);
	}


	@Test
	public void testGetTransitionMatrix() {
		log.info("Test Node Transition data retrieval.");
		fileAccessor.getTransitionMatrix();
		NodeTransitionMatrix txnService = NodeTransitionMatrix.getInstance();
		assertTrue(txnService.getNodeTransitionByFromNameAndToName("A", "B") != null);
	}

}
