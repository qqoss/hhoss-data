package com.hhoss.bean;

/**
 * generic data base bean node 
 * @author kejun
 *
 */

public abstract class Item<T extends Item<T>> implements Node<T> {
	private static final long serialVersionUID = 8285336812370060929L;
	
	/*
	 * key.color.red
	 * code:#FF0000
	 * name:red
	 * text:红色
	 * 
	 * key.data.item.sid
	 * 数据字段名 code:SID  
	 * 程序字段名 name:sid 
	 * 标签展示名 text:序号   
	 * 注解性描述 note:数据唯一主键
	 */
	/** 实例编码  code of data encode */
	private String code;
	/** 实例名称 name and main entry */
	private String name;
	/** 描述性文本,可由I18N替换*/
	private String text;	
	/** multi attrs readable keys 功能状态属性标记集 9A-Z3-7 */
	private String mark;
	
	/** range area & numeric key 范围，区间，枚举，等级，级别,子序*/
	private Integer rank;
	//** system object reference type 系统对象分类，pojo合表*/
	//private Integer sort;	
	/** model of list data, model entity type apply 模型实例元数据应用，链引用*/
	private Integer mold;
	/** function list attribute grid 功能状态属性标记集*/
	private Integer flag;	
	/** data status, define by business*/
	private Integer stat;
	/** version each release signal 版本化每次变更 */
	private Integer vers;			
	
	/** self sequece id 唯一主键*/
	private Long sid;	
	/** father id 相同sort*/
	private Long fid;	
	/** unit native isolate data， unit id 数据隔离，多租户区分*/
	private Long unid;		
	/** database persist update timestamp*/
	private Long tims;
	

	
	
	/**
	 * @return the code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the text
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	@Override
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the mark
	 */
	@Override
	public String getMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 * only using in ORM, use addFlag in business code
	 */
	@Override
	public void setMark(String mark) {
		this.mark = mark;
	}


	/**
	 * @return the mold
	 */
	@Override
	public Integer getMold() {
		return mold;
	}

	/**
	 * @param mold model of list data
	 * the mold to set
	 */
	@Override
	public void setMold(Integer mold) {
		this.mold = mold;
	}

	/**
	 * @return the rank
	 */
	@Override
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	@Override
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * @return the flag
	 */
	@Override
	public Integer getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	@Override
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * @return the stat
	 */
	@Override
	public Integer getStat() {
		return stat;
	}

	/**
	 * @param stat the stat to set
	 */
	@Override
	public void setStat(Integer stat) {
		this.stat = stat;
	}

	/**
	 * @return the vers
	 */
	@Override
	public Integer getVers() {
		return vers;
	}

	/**
	 * @param vers the vers to set
	 */
	@Override
	public void setVers(Integer vers) {
		this.vers = vers;
	}

	/**
	 * @return the sid
	 */
	@Override
	public Long getSid() {
		return sid;
	}

	/**
	 * @param sid the sid to set
	 */
	@Override
	public void setSid(Long sid) {
		this.sid = sid;
	}

	/**
	 * @return the fid
	 */
	@Override
	public Long getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	@Override
	public void setFid(Long fid) {
		this.fid = fid;
	}

	/**
	 * @return the unid
	 */
	@Override
	public Long getUnid() {
		return unid;
	}

	/**
	 * @param unid the unid to set
	 */
	@Override
	public void setUnid(Long unid) {
		this.unid = unid;
	}

	/**
	 * @return the tims
	 */
	@Override
	public Long getTims() {
		return tims;
	}

	/**
	 * @param tims the tims to set
	 */
	@Override
	public void setTims(Long tims) {
		this.tims = tims;
	}

	@Override
	public String toString(){
		return str();
    }

}
