package com.opttek.orford.logistics.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTransition implements Comparable<NodeTransition>{
	private static final Logger log = LoggerFactory.getLogger(NodeTransition.class);
	
	private Node fromNode;
	private Node toNode;
	private Integer transitionCost;

	public NodeTransition(Node _from, Node _to, Integer _transCost) {
		fromNode = _from;
		toNode = _to;
		transitionCost = _transCost;
	}


	@Override
	public int compareTo(NodeTransition _toCompare) {
		return this.getTransitionCost().compareTo(_toCompare.getTransitionCost());
	}

	/**
	 * @return the fromNode
	 */
	public Node getFromNode() {
		return this.fromNode;
	}


	/**
	 * @return the toNode
	 */
	public Node getToNode() {
		return this.toNode;
	}


	/**
	 * @return the transitionCost
	 */
	public Integer getTransitionCost() {
		return this.transitionCost;
	}


	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true
		if (o == this) {
			return true;
		}

		/*
		 * Check if o is an instance of Node or not "null instanceof [type]" also
		 * returns false
		 */
		if (!(o instanceof NodeTransition)) {
			return false;
		}

		// typecast o to Complex so that we can compare data members
		NodeTransition c = (NodeTransition) o;

		// Compare the data members and return accordingly
		return this.getToNode().equals(c.getToNode()) && this.getTransitionCost().equals(c.getTransitionCost());

	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("NodeTransition {\n");
		buf.append("\tFromNode: " + this.getFromNode().toString() + ",\n");
		buf.append("\ttoNode: " + this.getToNode().toString() + ",\n");
		buf.append("\ttransitionCost: " + this.getTransitionCost() + "\n");
		buf.append("}\n");

		return buf.toString();
	}
}
