package com.hhoss.mold.edge;

import java.io.Serializable;

import com.hhoss.util.HMap;

/**
 * @author kejun
 *
 */
public class Tank implements Serializable {
	private static final long serialVersionUID = 5537771237616518281L;
	private HMap<Serializable> auth = new HMap<Serializable>();
	private HMap<Serializable> info = new HMap<Serializable>();
	private HMap<Serializable> data = new HMap<Serializable>();
	
	public HMap<Serializable> getAuth() {
		return auth;
	}

	public HMap<Serializable> getInfo() {
		return info;
	}

	public HMap<Serializable> getData() {
		return data;
	}
	
	
	public Tank setAuth(String key, Serializable val) {
		auth.let(key, val);
		return this;
	};


	public Tank setInfo(String key, Serializable val) {
		info.let(key, val);
		return this;
	};

	public Tank setData(String key, Serializable val) {
		data.let(key, val);
		return this;
	};
	
	
}
