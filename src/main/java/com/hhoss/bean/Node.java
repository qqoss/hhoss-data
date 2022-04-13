package com.hhoss.bean;

import com.hhoss.jour.Logger;
import com.hhoss.ksid.CapNum;
import com.hhoss.lang.Beans;

public interface Node<T extends Node<T>> extends java.io.Serializable{	
	
	Long getSid();
	void setSid(Long sid);

	Long getFid();
	void setFid(Long fid);
	
	String getCode() ;
	void setCode(String code);

	String getName();
	void setName(String name) ;

	String getText() ;
	void setText(String text);	

	/**记录状态标记，对应flag中idx(9A-Z3-7)，idx0奇偶@,idx31有效_*/
	String getMark();
	void setMark(String mark);	

	Integer getFlag();
	void setFlag(Integer flag);
	
	/**记录状态标记，32位，可用30位（1-30）（（1-26：A-Z）27-30建议保留），末位[idx31]正负位，用于标记记录是否有效；开始位[idx0]奇偶位，用于标记业务有效*/
	Integer getStat();
	void setStat(Integer stat);

	/** 等级，排名，排位，层次，枚举 */
	Integer getRank() ;
	void setRank(Integer rank);
	
	/** meta,mode,sort,model,prototype  */
	Integer getMold();
	void setMold(Integer mold);

	/** using mold
	Integer getSort();
	void setSort(Integer sort);
	*/

	/**数据版本，用于事务，版本历史*/
	Integer getVers();
	void setVers(Integer vers);

	Long getTims();
	void setTims(Long tims);

	Long getUnid();
	void setUnid(Long unid);
	

	/**
	 * set the bit[idx] to one|true or 0|flase;
	 * @param idx should between [0,31)
	 * @param bv true to 1, false to 0;
	 */
	default void setFlag(int idx, boolean bv) {
		if(idx<0||idx>0x1F){return;}
		int flag=(getFlag()==null)?0:getFlag();
		flag=bv?(flag|(1<<idx)):(flag&~(1<<idx));
		setFlag(flag);
		setMark(CapNum.mask(flag));
		/*		
		String mark=getMark();
		if(mark==null){mark="";}
		char c= CapNum.at(idx);
		int U =  mark.indexOf(idx|0x40);//64='@' upper letter 
		if( U>=0 && !bv ){//remove upper letter;
			mark=mark.substring(0, U).concat(mark.substring(U+1));
		}
		int L =  mark.indexOf(idx|0x60);//96='`' lower letter
		if( L>=0 && !bv ){//remove lower letter;
			mark=mark.substring(0, L).concat(mark.substring(L+1));
		}
		if( bv && U<0 && L<0 ){//append upper letter;
			mark+=(char)(idx|0x40);
		}		
		setMark(mark);
		*/
	}
	
	/**
	 * merge the flag with special flag value.
	 * @param flag  val to merge 
	 */
	default void addFlag(int val){
		Integer flag=getFlag();
		flag=flag==null?val:flag|val;
		setFlag(flag);
		setMark(CapNum.mask(flag));
	}	
	/**
	 * merge the flag with special flag value.
	 * @param int should be 9A-Z3-7, 
	 */
	default void ridFlag(int val){
		Integer flag=getFlag();
		flag=flag==null?0:flag&~val;
		setFlag(flag);
		setMark(CapNum.mask(flag));
	}	
	
	/**
	 * @param whether contain the flag val
	 * @return true if contain all flag
	 */
	default boolean hasFlag(int val) {
		Integer flag=getFlag();
		return flag==null?false:val==(val&flag);
	}
	
	/**
	 * @param idx bit idx of the flag
	 * @return true if the bit is 1
	 */
	default boolean bitFlag(int idx) {
		if(idx<0||idx>0x1F){return false;}
		return hasFlag(1<<idx);
	}

	/**
	 * add the special flag/mark.
	 * @param mask chars should be 9A-Z3-7, 
	 */
	default void addMark(String str){
		addFlag(CapNum.mask(str));
	}
	/**
	 * remove the special masks flag/mark.
	 * @param mask chars should be in 9A-Z3-7, 
	 */
	default void ridMark(String mask){
		ridFlag(CapNum.mask(mask));
	}

	/**
	 * whether the flag/mark contains all chars in mask.
	 * @param mask chars should be in 9A-Z3-7, 
	 */
	default boolean hasMark(String mask) {
		return hasFlag(CapNum.mask(mask));
	}	
	
	/**
	 * set the object field value
	 * @param field name case-insensitive
	 * @param value may nul
	 * @return T
	 * @throws RuntimeException 
	 */
	@SuppressWarnings("unchecked")
	default T let(String field, Object value) throws RuntimeException{
		Beans.setFieldValue(this, field, value);
		//Bean.let(this, field, value);
		return (T)this;
	}	
	
	
	/**
	 * please care the field names should match the class fields
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default T load(Object obj) {		
		return (T)Bean.copy(obj, this);
	}

		
	default String str(){
		return Bean.str(this,3);		 		
	}
	
	default String xml(){
		return Bean.xml(this);
    }
	
	default void log(){
		logger.info(str());		 		
	}	
	
	static final Logger logger = Logger.get();

}
