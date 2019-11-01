package com.forezp.bingtest;

public class Father {

	private String name;
	private String age1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge1() {
		return age1;
	}

	public void setAge1(String age1) {
		this.age1 = age1;
	}

	public Father() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Father(String name, String age1) {
		super();
		this.name = name;
		this.age1 = age1;
	}

	@Override
	public String toString() {
		return "Father [name=" + name + ", age1=" + age1 + "]";
	}

}
