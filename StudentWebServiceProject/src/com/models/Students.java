package com.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Students {
	public String getName();
	public void setName(String name);
	public String getAddr();
	public void setAddr(String addr);
	public int getId();
	public void setId(int id);
	
}
