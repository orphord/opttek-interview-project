package com.opttek.orford.logistics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.Node;
import com.opttek.orford.logistics.model.NodeTransition;
import com.opttek.orford.logistics.model.NodeTransitionMatrix;

public class NodeTransitionService {
	private static final Logger log = LoggerFactory.getLogger(NodeTransitionService.class);

	private NodeTransitionMatrix matrix;
	private static NodeTransitionService instance;

	public static NodeTransitionService getInstance() {
		if(instance == null) {
			instance = new NodeTransitionService();
		}

		return instance;
	}
	
	private NodeTransitionService() {
		matrix = NodeTransitionMatrix.getInstance();
	}

	public NodeTransition getNodeTransition(Node _fromNode, Node _toNode) {
		return matrix.getNodeTransitionByFromNameAndToName(_fromNode.getName(), _toNode.getName());
	}
}
