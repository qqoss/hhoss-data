package com.hhoss.mold.edge;

import java.io.Serializable;

/**
 * @author kejun
 * pagination info VO
 */
public class Find implements Serializable {
	private static final long serialVersionUID = 3970944615258617670L;
	/** current page index, from 0  */
	private int index=0; 
	/** first record offset of current page  */
	private int start=0; 
	/** maxi record every page  */
	private int limit=20;
	/** size of current page, should less than limit  */
	private int cache=0;
	/** total records in whole result */
	private int total=0;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getCache() {
		return cache;
	}
	public void setCache(int cache) {
		this.cache = cache;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
