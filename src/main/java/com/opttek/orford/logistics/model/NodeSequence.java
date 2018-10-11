package com.opttek.orford.logistics.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.service.NodeTransitionService;

public class NodeSequence {
	private Logger log = LoggerFactory.getLogger(NodeSequence.class);

	private List<Node> nodeSequence;
	private List<NodeTransition> transitionSequence;
	
	public NodeSequence() {
		nodeSequence = new ArrayList<Node>();
		transitionSequence = new ArrayList<NodeTransition>();
	}

	/**
	 * Method to add a Node object to the sequence list as well as creating the NodeTransition
	 * (when it is not the first added) to the transitionSequence list.
	 * 
	 * @param _toAdd
	 */
	public void addNode(Node _toAdd) {
		nodeSequence.add(_toAdd);
		
		// A new node has been added to the list
		if(nodeSequence.size() > 1) {
			Node justAdded = nodeSequence.get(nodeSequence.size() - 1);
			Node prev = nodeSequence.get(nodeSequence.size() - 2);
			NodeTransition transition = NodeTransitionService.getInstance().getNodeTransition(prev, justAdded);
			transitionSequence.add(transition);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("NodeSequence {\n");
		buf.append("\t[\n");
		for(Node node: nodeSequence) {
			buf.append(node.toString());
			buf.append(",\n");
		}
		buf.append("]\n}");
		
		return buf.toString();
	}
}
