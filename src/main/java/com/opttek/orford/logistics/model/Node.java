package com.opttek.orford.logistics.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Node {
	private static final Logger log = LoggerFactory.getLogger(Node.class);

	private String name;
	private Integer productionTime;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param _name the name to set
	 */
	public void setName(String _name) {
		this.name = _name;
	}

	/**
	 * @return the productionTime
	 */
	public Integer getProductionTime() {
		return productionTime;
	}
	/**
	 * @param _productionTime the productionTime to set
	 */
	public void setProductionTime(Integer _productionTime) {
		this.productionTime = _productionTime;
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
		if (!(o instanceof Node)) {
			return false;
		}

		// typecast o to Complex so that we can compare data members
		Node c = (Node) o;

		// Compare the data members and return accordingly
		return this.getName().equals(c.getName()) && this.getProductionTime().equals(c.getProductionTime());

	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("Node {\n");
		buf.append("/tName: " + this.getName() + ",\n");
		buf.append("/tproductionTime: " + this.getProductionTime() + "\n");
		buf.append("}\n");

		return buf.toString();
	}
}
