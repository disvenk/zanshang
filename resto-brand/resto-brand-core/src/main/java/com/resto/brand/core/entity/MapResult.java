package com.resto.brand.core.entity;

import java.util.HashMap;
import java.util.Map;

public class MapResult extends JSONResult<Map<String,Object>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6301208735538438613L;
	
	public MapResult() {
		super();
		this.setData(new HashMap<String,Object>());
	}
	
	public void put(String key,Object value){
		this.getData().put(key,value);
	}
}
