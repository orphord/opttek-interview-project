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
	private Node testNode1 = new Node();
	Node testNode2 = new Node();
	NodeTransition trans1 = new NodeTransition();
	String node1Name = "Hal";
	String node2Name = "Pal";

	@Before
	public void setUp() throws Exception {
		matrix = NodeTransitionMatrix.getInstance();
		testNode1.setName(node1Name);
		testNode1.setProductionTime(Integer.valueOf(5));
		testNode2.setName(node2Name);
		testNode2.setProductionTime(Integer.valueOf(8));
		trans1.setFromNode(testNode1);
		trans1.setToNode(testNode2);
		trans1.setTransitionCost(Integer.valueOf(3));
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
		NodeTransition trans2 = new NodeTransition();
		trans2.setFromNode(testNode2);
		trans2.setToNode(testNode1);
		trans2.setTransitionCost(Integer.valueOf(21));
	
		matrix.addNodeTransition(trans2);

		NodeTransition testTrans = matrix.getNodeTransitionByFromNameAndToName(node2Name, node1Name);
		assert(trans2.equals(testTrans));
		assert(trans2 == testTrans); // test that these are exactly the same object
	}


}
