package com.sro.poc.jaxb_test.xml;

import javax.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) 
public class Person {
	
	@XmlElement
	private String name;
	@XmlElement
	private String id;
	@XmlElement
	private Integer age;
	
    @XmlElementWrapper(name = "hobbies")
    @XmlElement(name = "hobby")
    private List<Hobby> hobbies = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public List<Hobby> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<Hobby> hobbies) {
		this.hobbies = hobbies;
	}
	
	
}
