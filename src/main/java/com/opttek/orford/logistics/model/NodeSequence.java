package com.opttek.orford.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.service.NodeTransitionService;

public class NodeSequence {
	private Logger log = LoggerFactory.getLogger(NodeSequence.class);

	private List<Node> nodeSequence;
	private List<NodeTransition> transitionSequence;
	private NodeTransitionService transitionService;



	public NodeSequence() {
		nodeSequence = new ArrayList<Node>();
		transitionSequence = new ArrayList<NodeTransition>();
		transitionService = NodeTransitionService.getInstance();
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
			NodeTransition transition = transitionService.getNodeTransition(prev, justAdded);
			transitionSequence.add(transition);
		}

	}


	/**
	 * Method to return the number of sequence elements there are in this NodeSequence.
	 * 
	 * @return int representing the size of the transitionSequence list
	 */
	public int getNumTransitions() {
		return transitionSequence.size();
	}

	public Integer totalTransitionCost() {
		int totalSeqCost = 0;
		for(NodeTransition trans : this.transitionSequence) {
			totalSeqCost += trans.getTransitionCost().intValue();
		}

		return Integer.valueOf(totalSeqCost);
	}



	public void doSwap(Integer _indexToChange) {
		int idxToSwap = _indexToChange.intValue();
		
		// Swap transistion
		NodeTransition transitionOfInterest = transitionSequence.get(idxToSwap);
	
		Node fromNode = transitionOfInterest.getFromNode();
		Node toNode   = transitionOfInterest.getToNode();

		transitionOfInterest = transitionService.getNodeTransition(toNode, fromNode);
		transitionSequence.set(idxToSwap, transitionOfInterest);

		// Swap nodes
		int origFromIdx = idxToSwap;
		int origToIdx = (idxToSwap + 1) < nodeSequence.size() ? idxToSwap + 1 : idxToSwap;

		Collections.swap(nodeSequence, origFromIdx, origToIdx);

	}


	/**
	 * Method to create an independent clone of this NodeSequence.  Each Node and
	 * NodeTransition are immutable, so we can add references to each List without cloning
	 * those as well.
	 * @return cloned NodeSequence object
	 */
	public NodeSequence cloneNodeSequence() {
		NodeSequence outSeq = new NodeSequence();
		for(Node nodeToClone : this.nodeSequence) {
			outSeq.addNode(nodeToClone);
		}

		return outSeq;
	}


	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("NodeSequence {\n");
		buf.append("\tnodes: [\n");
		for(Node node: nodeSequence) {
			buf.append(node.toString());
			buf.append(",\n");
		}
		buf.append("],\n}");
/*		buf.append("\tnodeTranstions: [\n");
		for(NodeTransition nt:transitionSequence) {
			buf.append(nt.toString());
			buf.append(",\n");
		}
		buf.append("],\n}");*/

		return buf.toString();
	}

}
