package com.opttek.orford.logistics.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeSequenceTest {
	private static final Logger log = LoggerFactory.getLogger(NodeSequenceTest.class);

	private String aName = "A";
	private String bName = "B";
	private String cName = "C";
	private Integer aProdTime = Integer.valueOf(5);
	private Integer bProdTime = Integer.valueOf(9);
	private Integer cProdTime = Integer.valueOf(12);

	private Node a;
	private Node b;
	private Node c;

	NodeSequence nodeSeq;

	@Before
	public void setUp() throws Exception {
		a = new Node(aName, aProdTime);
		b = new Node(bName, bProdTime);
		c = new Node(cName, cProdTime);

		nodeSeq = new NodeSequence();

		nodeSeq.addNode(a);
		nodeSeq.addNode(b);
		nodeSeq.addNode(c);

	}

	@Test
	public void testNodeSequence() {
		assertNotNull("NodeSequence should exist at this point.", nodeSeq);
	}


	@Test
	public void testAddNode() {
		assertEquals("Before adding a node there should be TWO transitions.", Integer.valueOf(2), Integer.valueOf(nodeSeq.getNumTransitions()) );
		Node d = new Node("D", Integer.valueOf(13));
		nodeSeq.addNode(d);
		assertEquals("After adding a node there should be THREE transitions.", Integer.valueOf(3), Integer.valueOf(nodeSeq.getNumTransitions()) );
	}

	@Test
	public void testGetNumTransitions() {
		assertEquals("There should be a TWO transitions.", Integer.valueOf(2), Integer.valueOf(nodeSeq.getNumTransitions()));
	}


}
