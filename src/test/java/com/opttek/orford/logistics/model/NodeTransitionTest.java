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
	private Integer initialAtoBTransitionCost;

	@Before
	public void setUp() throws Exception {
		a = new Node();
		a.setName("A");
		b = new Node();
		b.setName("B");

		initialAtoBTransitionCost = Integer.valueOf(10);
		aToB = new NodeTransition();
		aToB.setFromNode(a);
		aToB.setToNode(b);
		aToB.setTransitionCost(initialAtoBTransitionCost);

		bToA = new NodeTransition();
		bToA.setFromNode(b);
		bToA.setToNode(a);
		bToA.setTransitionCost(Integer.valueOf(8));
	}


	@Test
	public void testCompareTo() {
		assertTrue( 0 < aToB.compareTo(bToA) );
	}

	@Test
	public void testGetFromNode() {
		assertEquals("A to B fromNode should be equal to A: ", a, aToB.getFromNode());
	}

	@Test
	public void testSetFromNode() {
		aToB.setFromNode(b); // this is setting teh from node of aToB to B for this test
		assertNotEquals("A to B fromNode should NOT be equal to A: ", a, aToB.getFromNode());
		assertEquals("A to B fromNode should be equal to B: ", b, aToB.getFromNode());
	}

	@Test
	public void testGetToNode() {
		assertEquals("B to A toNode should be equal to A: ", a, bToA.getToNode());
	}

	@Test
	public void testSetToNode() {
		aToB.setToNode(a); // this is setting the to node of aToB to A for this test
		assertNotEquals("A to B toNode should NOT be equal to B: ", b, aToB.getToNode());
		assertEquals("A to B toNode should be equal to A: ", a, aToB.getToNode());
	}

	@Test
	public void testGetTransitionCost() {
		assertEquals("A to B transition cost should be " + this.initialAtoBTransitionCost, this.initialAtoBTransitionCost, aToB.getTransitionCost());
	}

	@Test
	public void testSetTransitionCost() {
		Integer newTransitionCost = Integer.valueOf(12);
		aToB.setTransitionCost(newTransitionCost);
		assertNotEquals("New transition cost should NOT be old one", this.initialAtoBTransitionCost, aToB.getTransitionCost());
		assertEquals("New transition cost should be set in aToB", newTransitionCost, aToB.getTransitionCost());
	}

}
