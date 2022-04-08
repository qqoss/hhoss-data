package com.hhoss.data.hold;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hhoss.data.edge.Find;

public interface Rest extends Serializable {
	/**
	 * some http control information. 
	 */
	Head getHead();
	
	/**
	 * the data for some encrypt/bin data
	 * @return Map
	 */
	Body getBody();
		
	/***
	 * the paginate information
	 * @return Find
	 */
	Find getFind();	

	/**
	 * the main resonse infomation, eg:Error Code, error text:
	 * @return Map
	 */
	Map getMain();
	
	/**
	 * the resultset ObjectList
	 * @return List
	 */
	List getList();
}
