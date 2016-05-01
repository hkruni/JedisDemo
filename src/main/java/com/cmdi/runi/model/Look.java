package com.cmdi.runi.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Look implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4865524523520634357L;
	@Setter @Getter private String houseId;
	@Setter @Getter private String boker;
	@Setter @Getter private String time;
	
	
	@Override
	public String toString() {
		return "Look [houseId=" + houseId + ", boker=" + boker + ", time="
				+ time + "]";
	} 
	
	
	

}
