package com.hhoss.data.boss;

import java.util.Date;

import com.hhoss.data.Item;


/**
 * @author kejun
 * 数字证书信息
 */
public class Cert extends Item<Cert> {
	private static final long serialVersionUID = 127456467584625L;
	private String detail;//认证详细内容        body
	private String master;//主体，被证人，持证者，common-name
	private String issuer;//认证提供者 provider
	private Date beginTime;//认证内容标注的开始时间
	private Date ceaseTime;//认证内容标注的结束时间
	private Date activeTime;//认证启用时间
	private Date expireTime;//认证过期时间
	
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}	
	public Date getCeaseTime() {
		return ceaseTime;
	}
	public void setCeaseTime(Date ceaseTime) {
		this.ceaseTime = ceaseTime;
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
