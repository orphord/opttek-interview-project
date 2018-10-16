package com.opttek.orford.logistics.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwapResponse implements Comparable<SwapResponse> {
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


	@Override
	public int compareTo(SwapResponse _toCompare) {
		return this.netChangeFromBaseLine.compareTo(_toCompare.getNetChangeFromBaseLine());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getSwappedSequence().hashCode();
		result = prime * result + this.getNetChangeFromBaseLine().hashCode();
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
		if (!(o instanceof SwapResponse)) {
			return false;
		}

		// typecast o to Complex so that we can compare data members
		SwapResponse c = (SwapResponse) o;

		// Compare the data members and return accordingly
		return this.getSwappedSequence().equals(c.getSwappedSequence()) && this.getNetChangeFromBaseLine().equals(c.getNetChangeFromBaseLine());

	}

	
}
