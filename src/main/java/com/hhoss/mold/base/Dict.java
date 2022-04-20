package com.hhoss.mold.base;

import com.hhoss.mold.Item;

public class Dict extends Item<Dict> {
	private static final long serialVersionUID = 4067440878153902097L;

	/**
	 *  unique string key 
	 * key can be used in I18N, resource, attr， default is sid ....
	 * */
	private String key;

	/**
	 * 
	 * @return key if not null or SID.${sid} if null
	 */
	public String getKey() {
		return key==null?"SID_"+getSid():key;
	}

	public void setKey(String key) {
		this.key = key;
	}	

	

}
