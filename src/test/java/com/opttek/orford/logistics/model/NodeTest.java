package com.opttek.orford.logistics.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTest {
	private static final Logger log = LoggerFactory.getLogger(NodeTest.class); 

	private Node testNode;
	private String initialName = "Hank Hill";
	private Integer initialProductionTime = Integer.valueOf(8);

	@Before
	public void setUp() throws Exception {
		testNode = new Node();
		testNode.setName(initialName);
		testNode.setProductionTime(initialProductionTime);
	}


	@Test
	public void testGetName() {
		assertEquals("TestNode name should be initial Name: ", initialName, testNode.getName());
	}

	@Test
	public void testSetName() {
		String newName = "Crazy Larry";
		testNode.setName(newName);
		assertNotEquals("TestNode name should NOT be initial Name: ", initialName, testNode.getName());
		assertEquals("TestNode name should be new name: ", newName, testNode.getName());
	}

	@Test
	public void testGetProductionTime() {
		assertEquals("TestNode production time should be initial production time: ", initialProductionTime, testNode.getProductionTime());
	}

	@Test
	public void testSetProductionTime() {
		Integer newProductionTime = Integer.valueOf(90);
		testNode.setProductionTime(newProductionTime);
		assertNotEquals("TestNode production time should NOT be initial production time: ", initialProductionTime, testNode.getProductionTime());
		assertEquals("TestNode production time should be new production time: ", newProductionTime, testNode.getProductionTime());
	}

}
