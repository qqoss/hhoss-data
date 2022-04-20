package com.hhoss.mold.hold;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hhoss.mold.edge.Find;

/**
 * @author kejun
 *
 */
public interface Root extends Serializable {
	/**
	 * some http control information. 
	 */
	Head getHead();
	
	/**
	 * the data for some encrypt/bin data
	 * @return Map
	 */
	Body getBody();
	
	/**
	 * the record meta info, eg:the fields.
	 * @return Meta
	 */
	Meta getMeta();
	
}
