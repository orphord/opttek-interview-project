package com.opttek.orford.logistics.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.Node;
import com.opttek.orford.logistics.model.NodeTransition;

public class NodeTransitionService {
	private static final Logger log = LoggerFactory.getLogger(NodeTransitionService.class);

	private Map<String, NodeTransition> transitionsByFromNameMap;
	private Map<String, NodeTransition> transitionsByToNameMap;
	private static NodeTransitionService instance;

	public static NodeTransitionService getInstance() {
		if(instance == null) {
			instance = new NodeTransitionService();
		}
		return instance;
	}
	
	private NodeTransitionService() {
		transitionsByFromNameMap = new HashMap<String, NodeTransition>();
		transitionsByToNameMap = new HashMap<String, NodeTransition>();
	}


	public void addNodeTransition(NodeTransition _toAdd) {
		String fromName = _toAdd.getFromNode().getName();
		String toName   = _toAdd.getToNode().getName();
		
		transitionsByFromNameMap.put(fromName, _toAdd);
		transitionsByToNameMap.put(toName, _toAdd);
		
	}
	

	public NodeTransition getNodeTransitionByFromName(String _fromName) {
		return transitionsByFromNameMap.get(_fromName);
	}
	
	public NodeTransition getNodeTransitionByToName(String _toName) {
		return transitionsByFromNameMap.get(_toName);
	}
}
