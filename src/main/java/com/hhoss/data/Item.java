package com.hhoss.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * generic data base bean node 
 * @author kejun
 *
 */

public class Item<T extends Item<T>> extends com.hhoss.bean.Item<T> {
	private static final long serialVersionUID = 127456467584625L;
	private long id;
	private String keys;
	private Date fromTime;
	private Date snapTime;
	private Date stopTime;
	
	private Date createTime;//业务创建时间
	private Date updateTime;//业务修改时间
	private Date deleteTime;//业务删除时间
	private Date activeTime;//业务启用时间
	private Date expireTime;//业务过期时间

	private List list;
	private Map map;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getKeys() {
		return keys;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}

	public Date getSnapTime() {
		return snapTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}