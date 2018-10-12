package com.opttek.orford.logistics.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.Node;

public class NodeServiceTest {
	private static final Logger log = LoggerFactory.getLogger(NodeServiceTest.class);

	private NodeService svc;
	String nodeOneName = "One";
	Integer nodeOneProdTime = Integer.valueOf(10);

	@Before
	public void setUp() throws Exception {
		svc = NodeService.getInstance();
		Node one = new Node(nodeOneName, nodeOneProdTime);
		svc.addNode(one);
	}

	@Test
	public void testGetInstance() {
		assert(svc != null);
	}

	@Test
	public void testGetNodeByName() {
		Node testNode = svc.getNodeByName(nodeOneName);
		assert(testNode.getProductionTime().equals(nodeOneProdTime));
	}

	@Test
	public void testAddNode() {
		String node2Name = "Larry";
		Integer node2ProdTime = Integer.valueOf(8);

		Node testNode = new Node(node2Name, node2ProdTime);
		svc.addNode(testNode);

		Node added = svc.getNodeByName(node2Name);
		assert(testNode.equals(added));
		assert(testNode == added); // test that these are exactly the same object
	}


}
