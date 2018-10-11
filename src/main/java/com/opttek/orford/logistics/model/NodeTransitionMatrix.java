package com.opttek.orford.logistics.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTransitionMatrix {
	private static final Logger log = LoggerFactory.getLogger(NodeTransitionMatrix.class);

	private Map<String, Map<String, NodeTransition>> transitionsByFromNameMap;
	private static NodeTransitionMatrix instance;

	public static NodeTransitionMatrix getInstance() {
		if(instance == null) {
			instance = new NodeTransitionMatrix();
		}
		return instance;
	}
	
	private NodeTransitionMatrix() {
		transitionsByFromNameMap = new HashMap<String, Map<String, NodeTransition>>();
	}


	public void addNodeTransition(NodeTransition _toAdd) {
		String fromName = _toAdd.getFromNode().getName();
		String toName   = _toAdd.getToNode().getName();
		Map<String, NodeTransition> toMap = transitionsByFromNameMap.get(fromName);
		
		// Check if this from name has a Map for toNames yet, if not create one
		if(toMap == null) {
			toMap = new HashMap<String, NodeTransition>();
			transitionsByFromNameMap.put(fromName, toMap);
		}

		// Add NodeTransition to this toMap for quick access
		toMap.put(toName, _toAdd);
	}

	public NodeTransition getNodeTransitionByFromNameAndToName(String _fromName, String _toName) {
		Map<String, NodeTransition> toMap = transitionsByFromNameMap.get(_fromName);
		NodeTransition outTrans = null;
		if(toMap != null) {
			outTrans = toMap.get(_toName);
		}

		return outTrans;
	}

}
