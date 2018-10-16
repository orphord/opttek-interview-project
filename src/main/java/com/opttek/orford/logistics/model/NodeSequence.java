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



	/**
	 * Method to swap Nodes and NodeTransitions in this NodeSequence based on an index
	 * parameter.
	 * 
	 * @param _indexToChange
	 */
	public void doSwap(Integer _indexToChange) {
		int idxToSwap = _indexToChange.intValue();

		// Swap nodes
		int origFromIdx = idxToSwap;
		int origToIdx = idxToSwap + 1;
		if(origFromIdx > -1 && origToIdx < nodeSequence.size()) {
			Collections.swap(nodeSequence, origFromIdx, origToIdx);
		}

		// Swap transition referred to by _indextoChange parameter
		NodeTransition transitionOfInterest = this.swapTransition(idxToSwap);
		if(transitionOfInterest != null) {
			transitionSequence.set(idxToSwap, transitionOfInterest);
		}

		// Swap transition before (if it exists)
		int prevTransitionIdx = idxToSwap - 1;
		NodeTransition prevTrans = this.swapTransition(prevTransitionIdx);
		if( prevTrans != null ) {
			transitionSequence.set(prevTransitionIdx, prevTrans);
		}

		// Swap transition after (if it exists)
		int postTransitionIdx = idxToSwap + 1;
		NodeTransition postTrans = this.swapTransition(postTransitionIdx);
		if( postTrans != null ) {
			transitionSequence.set(postTransitionIdx, postTrans);
		}

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

	private NodeTransition swapTransition(int _nodeIdxToSwap) {
		NodeTransition trans = null;

		int fromNodeIdx = _nodeIdxToSwap;
		int toNodeIdx   = _nodeIdxToSwap + 1;
		if(fromNodeIdx > -1 && toNodeIdx < nodeSequence.size()) {
			// Swap transistion
			Node fromNode = nodeSequence.get(fromNodeIdx);
			Node toNode   = nodeSequence.get(toNodeIdx);

			trans = transitionService.getNodeTransition(fromNode, toNode);
		}

		return trans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for(Node node : this.nodeSequence) {
			result = prime * result + node.hashCode();
		}

		for(NodeTransition nt : this.transitionSequence) {
			result = prime * result + nt.hashCode();
		}

		
		return result;
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
		if (!(o instanceof NodeSequence)) {
			return false;
		}

		// typecast o to Complex so that we can compare data members
		NodeSequence c = (NodeSequence) o;

		// Compare the data members and return accordingly
		boolean equal = true;
		for(int i = 0; i < this.nodeSequence.size(); i++) {
			equal = (this.nodeSequence.get(i).equals(c.nodeSequence.get(i)));
			if(!equal)
				break;
		}
		return equal;

	}


	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("\nNodeSequence {\n");
		buf.append("[");
		for(Node node : nodeSequence) {
			buf.append(node.getName());
		}
		buf.append("],\n");
		buf.append("totalTransitionCost: " + this.totalTransitionCost() + "\n}");
//		buf.append("\tnodes: [\n");
//		for(Node node: nodeSequence) {
//			buf.append(node.toString());
//			buf.append(",\n");
//		}
//		buf.append("],\n}");
//		buf.append("\tnodeTranstions: [\n");
//		for(NodeTransition nt:transitionSequence) {
//			buf.append(nt.toString());
//			buf.append(",\n");
//		}
//		buf.append("],\n}");

		return buf.toString();
	}

}
