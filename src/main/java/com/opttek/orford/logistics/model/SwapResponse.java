package com.opttek.orford.logistics.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwapResponse {
	private static final Logger log = LoggerFactory.getLogger(SwapResponse.class);
	
	private NodeSequence swappedSequence;
	private Integer netChangeFromBaseLine;
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

	
}
