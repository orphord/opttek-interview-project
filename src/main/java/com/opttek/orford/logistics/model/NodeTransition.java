package com.opttek.orford.logistics.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTransition implements Comparable<NodeTransition>{
	private static final Logger log = LoggerFactory.getLogger(NodeTransition.class);
	
	private Node fromNode;
	private Node toNode;
	private Integer transitionCost;

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
	 * @param _fromNode the fromNode to set
	 */
	public void setFromNode(Node _fromNode) {
		this.fromNode = _fromNode;
	}

	/**
	 * @return the toNode
	 */
	public Node getToNode() {
		return this.toNode;
	}

	/**
	 * @param _toNode the toNode to set
	 */
	public void setToNode(Node _toNode) {
		this.toNode = _toNode;
	}

	/**
	 * @return the transitionCost
	 */
	public Integer getTransitionCost() {
		return this.transitionCost;
	}

	/**
	 * @param _transitionCost the transitionCost to set
	 */
	public void setTransitionCost(Integer _transitionCost) {
		this.transitionCost = _transitionCost;
	}

}
