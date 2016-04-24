package com.cmdi.runi.model.message;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1660306898250279230L;
	private int id;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}