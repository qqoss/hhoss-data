package com.hhoss.mold.able;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hhoss.mold.base.Sort;

/**
 * @author kejun
 * sort info vo
 *  ascending and a descending
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 4826916715540541724L;
	private Map<String, Sort> fields = new LinkedHashMap<>();
	public Map<String, Sort> getSorts() {
		return fields;
	}

	public void setFields(Map<String, Sort> fields) {
		this.fields = fields;
	}

	public void set(String fldName, Sort dir) {
		this.fields.put(fldName, dir);
	}

	public void set(String fldName,boolean asc) {
		set(fldName, asc?Sort.ASC:Sort.DESC);
	}

	public void asc(String fldName) {
		set(fldName, Sort.ASC);
	}

	public void desc(String fldName) {
		set(fldName, Sort.DESC);
	}
	
	public boolean isAsc(String fldName){
		return Sort.DESC.equals(fields.get(fldName));
	}
}
