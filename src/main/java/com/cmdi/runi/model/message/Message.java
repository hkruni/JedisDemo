package com.cmdi.runi.model.message;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1660306898250279230L;
	private String id;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}