package com.hhoss.mold.core;

import com.hhoss.mold.Item;

/**
 * 
 * @author kejun
 *
 *obj:object:target:resource:moudule:
 *fid:function:method:operate:action:
 *uid:user:unit:roule:team:dept:corp:
 *act:action:value:bitmap
 */
public class Race extends Item<Race> {
	private static final long serialVersionUID = 1448553818185279344L;
	/**  sid of the function,module,sort,object; which the mace belong to  **/
	private long fid; 
	
	/** sid of user,role,team,unit,corp,dept,club,band,gang; which the mace belong to  **/
	private long uid;
	
	/** vale as bitmap for permission set, bit is operation/action **/
	private int act;
	
	/** how about active expire time?*/
}
