package com.hhoss.mold.base;

/**
 * @author kejun 
 * Trend for order by 
 * <s>old</s> : <del>System Object reference type</del>
 */
public enum Sort {
	ASC("asc", 1),   DESC("desc", -1), 
	RISE("rise", 1), DOWN("down", -1);

	private String name;
	private int code;

	// 构造方法
	private Sort(String name, int code) {
		this.name = name;
		this.code = code;
	}

	// 普通方法
	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}
}
