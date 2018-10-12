package com.opttek.orford.logistics.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTransitionMatrixTest {
	private static final Logger log = LoggerFactory.getLogger(NodeTransitionMatrixTest.class);

	private static NodeTransitionMatrix matrix;

	private String node1Name = "Hal";
	private String node2Name = "Pal";
	private Node testNode1 = new Node(node1Name, Integer.valueOf(5));
	private Node testNode2 = new Node(node2Name, Integer.valueOf(8));
	private NodeTransition trans1 = new NodeTransition(testNode1, testNode2, Integer.valueOf(3));
	
	@Before
	public void setUp() throws Exception {
		matrix = NodeTransitionMatrix.getInstance();
		matrix.addNodeTransition(trans1);
	}

	@Test
	public void testGetInstance() {
		assert(matrix != null);
	}


	@Test
	public void testGetNodeTransitionByFromNameAndToName() {
		NodeTransition testTrans = matrix.getNodeTransitionByFromNameAndToName(node1Name, node2Name);
		assert(testTrans.equals(trans1));
	}


	@Test
	public void testAddNodeTransition() {
		NodeTransition trans2 = new NodeTransition(testNode2, testNode1, Integer.valueOf(21));	
		matrix.addNodeTransition(trans2);

		NodeTransition testTrans = matrix.getNodeTransitionByFromNameAndToName(node2Name, node1Name);
		assert(trans2.equals(testTrans));
		assert(trans2 == testTrans); // test that these are exactly the same object
	}


}
