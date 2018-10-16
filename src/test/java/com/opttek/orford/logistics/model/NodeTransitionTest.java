package com.opttek.orford.logistics.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTransitionTest {
	private static final Logger log = LoggerFactory.getLogger(NodeTransitionTest.class);

	private NodeTransition aToB;
	private NodeTransition bToA;
	private Node a;
	private Node b;
	private Integer initialAtoBTransitionCost = Integer.valueOf(10);
	private String aName = "A";
	private String bName = "B";
	private Integer aProdTime = Integer.valueOf(19);
	private Integer bProdTime = Integer.valueOf(8);

	@Before
	public void setUp() throws Exception {
		a = new Node(aName, aProdTime);
		b = new Node(bName, bProdTime);

		aToB = new NodeTransition(a, b, initialAtoBTransitionCost);
		bToA = new NodeTransition(b, a, Integer.valueOf(7));

	}


	@Test
	public void testGetFromNode() {
		assertEquals("A to B fromNode should be equal to A: ", a, aToB.getFromNode());
	}


	@Test
	public void testGetToNode() {
		assertEquals("B to A toNode should be equal to A: ", a, bToA.getToNode());
	}


	@Test
	public void testGetTransitionCost() {
		assertEquals("A to B transition cost should be " + this.initialAtoBTransitionCost, this.initialAtoBTransitionCost, aToB.getTransitionTime());
	}


}
