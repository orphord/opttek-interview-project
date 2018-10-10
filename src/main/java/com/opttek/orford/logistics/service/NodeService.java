package com.opttek.orford.logistics.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.Node;

public class NodeService {
	private static final Logger log = LoggerFactory.getLogger(NodeService.class);

	private Map<String, Node> nodesByNameMap;
	private static NodeService instance;

	public static NodeService getInstance() {
		if(instance == null) {
			instance = new NodeService();
		}
		return instance;
	}
	
	private NodeService() {
		nodesByNameMap = new HashMap<String, Node>();
	}


	public void addNode(Node _toAdd) {
		String name = _toAdd.getName();
		nodesByNameMap.put(name, _toAdd);
	}
	

	public Node getNodeByName(String _name) {
		return nodesByNameMap.get(_name);
	}
}
