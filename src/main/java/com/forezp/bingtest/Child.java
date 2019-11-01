package com.forezp.bingtest;

public class Child extends Father {

	private String name;
	private String age2;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge2() {
		return age2;
	}

	public void setAge2(String age2) {
		this.age2 = age2;
	}

	@Override
	public String toString() {
		return "Child [name=" + name + ", age2=" + age2 + "]";
	}

	public Child() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Child(String name, String age2) {
		super();
		this.name = name;
		this.age2 = age2;
	}

}
