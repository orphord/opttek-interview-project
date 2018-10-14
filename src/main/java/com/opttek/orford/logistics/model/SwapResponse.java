package com.opttek.orford.logistics.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwapResponse implements Comparable<SwapResponse> {
	private static final Logger log = LoggerFactory.getLogger(SwapResponse.class);
	
	private NodeSequence swappedSequence;
	private Integer netChangeFromBaseLine;
	private Integer indexOfInterest;


	/**
	 * @return the swappedSequence
	 */
	public NodeSequence getSwappedSequence() {
		return this.swappedSequence;
	}

	/**
	 * @param _swappedSequence the swappedSequence to set
	 */
	public void setSwappedSequence(NodeSequence _swappedSequence) {
		this.swappedSequence = _swappedSequence;
	}


	/**
	 * @return the netChangeFromBaseLine
	 */
	public Integer getNetChangeFromBaseLine() {
		return this.netChangeFromBaseLine;
	}

	/**
	 * @param _netChangeFromBaseLine the netChangeFromBaseLine to set
	 */
	public void setNetChangeFromBaseLine(Integer _netChangeFromBaseLine) {
		this.netChangeFromBaseLine = _netChangeFromBaseLine;
	}


	/**
	 * @return the indexOfInterest
	 */
	public Integer getIndexOfInterest() {
		return indexOfInterest;
	}

	/**
	 * @param _indexOfInterest the indexOfInterest to set
	 */
	public void setIndexOfInterest(Integer _indexOfInterest) {
		this.indexOfInterest = _indexOfInterest;
	}


	@Override
	public int compareTo(SwapResponse _toCompare) {
		return this.netChangeFromBaseLine.compareTo(_toCompare.getNetChangeFromBaseLine());
	}

	
}
