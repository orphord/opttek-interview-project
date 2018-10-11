package com.opttek.orford.logistics.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opttek.orford.logistics.model.Node;
import com.opttek.orford.logistics.model.NodeSequence;
import com.opttek.orford.logistics.model.NodeTransition;
import com.opttek.orford.logistics.model.NodeTransitionMatrix;
import com.opttek.orford.logistics.service.NodeService;

public class FileDataAccessor {
	private static final Logger log = LoggerFactory.getLogger(FileDataAccessor.class);
	private final String NODE_DATA_FILE_NAME = "/Node.data";
	private final String TRANSITION_DATA_FILE_NAME = "/NodeTransitionMatrix.data";
	private final String SEQUENCE_DATA_FILE_NAME = "/Sequence.data";
	private final String COST_SPLIT_DELIM = ":";
	private final String TRANSITION_SPLIT_DELIM = "->";

	private static FileDataAccessor instance;
	
	public static FileDataAccessor getInstance() {
		if(instance == null) {
			instance = new FileDataAccessor();
		}
		
		return instance;
	}
	
	private FileDataAccessor() {}
	/**
	 * Method to get the node data from a data file.  This is not going to be doing any data validation
	 * or error handling.  Normally I do that, but for this purpose it seems like overkill.  The format
	 * of the Node information in the data file is to be <name>:<production cost>, where the name is a
	 * String and the production cost is an integer value.
	 */
	public void getNodeData() {
		log.info("getNodeData() function called.");

		try {
			InputStream dataFileStream = getClass().getResourceAsStream(NODE_DATA_FILE_NAME);

			BufferedReader buf = new BufferedReader(new InputStreamReader(dataFileStream));
			String nextLine;
			while( (nextLine = buf.readLine()) != null) {
				String[] nodeDataArr = getNodeValues(nextLine);

				NodeService nodeService = NodeService.getInstance();
				Node tempNode = new Node();
				tempNode.setName(nodeDataArr[0]);
				Integer prodTime = Integer.valueOf(nodeDataArr[1]);
				tempNode.setProductionTime(prodTime);

				nodeService.addNode(tempNode);
			}
		} catch (FileNotFoundException ex) {
			log.error("File " + NODE_DATA_FILE_NAME + " not found.");
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("Error getting next line from file.");
			ex.printStackTrace();
		}


	}


	/**
	 * Method to get the NodeTransition data from a data file.  This is not going to be doing any data validation
	 * or error handling.  Normally I do that, but for this purpose it seems like overkill.  The format
	 * of the NodeTransition information in the data file is to be <fromNode name>-><toNode name>:<transition cost>,
	 * where the node names are strings and the transition cost is an integer value.
	 * 
	 * IMPORTANT NOTE: This function depends on the nodes referenced herein having already been created (likely by getNodeData()
	 * method above. 
	 */
	public void getTransitionMatrix() {
		log.info("getTransitionMatrix() function called.");

		try {
			InputStream dataFileStream = getClass().getResourceAsStream(TRANSITION_DATA_FILE_NAME);

			BufferedReader buf = new BufferedReader(new InputStreamReader(dataFileStream));
			String nextLine;
			NodeService nodeService = NodeService.getInstance();
			NodeTransitionMatrix transitionService = NodeTransitionMatrix.getInstance();
			
			while( (nextLine = buf.readLine()) != null) {
				// Get data from file line and parse into NodeTransition information
				String[] transitionAndCostArr = getTransitionValues(nextLine);
				String fromNodeName = transitionAndCostArr[0];
				String toNodeName   = transitionAndCostArr[1];
				Integer transitionCost = Integer.valueOf(transitionAndCostArr[2]);

				// Get the appropriate Nodes from NodeService
				Node fromNode = nodeService.getNodeByName(fromNodeName);
				Node toNode   = nodeService.getNodeByName(toNodeName);

				// Create NodeTransition object and populate it 
				NodeTransition nodeTrans = new NodeTransition();
				nodeTrans.setFromNode(fromNode);
				nodeTrans.setToNode(toNode);
				nodeTrans.setTransitionCost(transitionCost);

				transitionService.addNodeTransition(nodeTrans);
			}
		} catch (FileNotFoundException ex) {
			log.error("File " + TRANSITION_DATA_FILE_NAME + " not found.");
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("Error getting next line from file.");
			ex.printStackTrace();
		}

	}


	/**
	 * Method to get the Node Sequence data from a data file.  This is not going to be doing any data validation
	 * or error handling.  Normally I do that, but for this purpose it seems like overkill.  The format
	 * of the NodeTransition information in the data file is to be each line with a node name equivalent to the node names in the
	 * Node.data and NodeTransitionMatrix.data files.  The **key** thing for this file is the order the values are
	 * presented in.
	 * 
	 * IMPORTANT NOTE: This function depends on the nodes referenced herein having already been created as well as the NodeTransitionMatrix 
	 */
	public NodeSequence getInitialSequenceData() {
		log.info("getSequenceData() function called.");
		NodeSequence initialSequence = new NodeSequence();
		NodeService nodeService = NodeService.getInstance();

		try {
			InputStream dataFileStream = getClass().getResourceAsStream(SEQUENCE_DATA_FILE_NAME);

			BufferedReader buf = new BufferedReader(new InputStreamReader(dataFileStream));
			String nextLine;
			while( (nextLine = buf.readLine()) != null) {
				Node tempNode = nodeService.getNodeByName(nextLine);
				initialSequence.addNode(tempNode);
			}
		} catch (FileNotFoundException ex) {
			log.error("File " + SEQUENCE_DATA_FILE_NAME + " not found.");
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("Error getting next line from file.");
			ex.printStackTrace();
		}
		
		return initialSequence;

	}

	private String[] getNodeValues(String _nodeFileLine) {
		String[] outVals = _nodeFileLine.split(COST_SPLIT_DELIM);
		return outVals;
	}


	private String[] getTransitionValues(String _transitionFileLine) {
		String[] outVals = new String[3];
		
		String[] transitionAndCost = _transitionFileLine.split(COST_SPLIT_DELIM);
		outVals[2] = transitionAndCost[1];
		String[] transition = transitionAndCost[0].split(TRANSITION_SPLIT_DELIM);
		outVals[0] = transition[0];
		outVals[1] = transition[1];
		
		return outVals;
		
	}
}
