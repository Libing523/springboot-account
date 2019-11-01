package com.forezp.bingtest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class CopyTest {

	public static void main(String[] args) {
	/*
		List<Phone> list1 = new ArrayList<Phone>();
		Phone p1 = new Phone();
		p1.setNumber("1234567890");
		Phone p2 = new Phone();
		p2.setNumber("1234567891");
		Phone p3 = new Phone();
		p3.setNumber("1234567892");
		list1.add(p1);
		list1.add(p2);
		list1.add(p3);
		System.out.println(list1);
		for(int i=0;i<list1.size();i++) {
			String a = list1.get(i).getNumber();
			String bb = a.replaceAll("\\d{2}(?=[\\d\\D]{2}$)", "**");
			list1.get(i).setNumber(bb);
		}*/
		//Date date = new Date();
		//System.out.println(date);
	
	/*	String t1 = "12.34";
		String t2 = "56.26";
		System.out.println(Double.sum(Double.parseDouble(t1), Double.parseDouble(t2)));*/
		
		/*String t1 = "file.xml";
		System.out.println(t1.substring(0, t1.lastIndexOf(".")));
		System.out.println(new SimpleDateFormat("HHmmss").format(new Date()));
		
		int i1 = 1;
		System.out.println(++i1);*/
		
		 /* 父类list转换成子类list，参数不会丢失，可以通过子类对象调用父类方法获取 */
		List<Father> parents = new ArrayList<Father>();
		Father fa = new Father();
		fa.setName("11");
		fa.setAge1("1");
		parents.add(fa);
		
        Object[] object = parents.toArray();
        //使用Arrays工具类，将数组转换成list
        List<Object> objects = Arrays.asList(object);
        //将objects强转成childlist；这里强转时，不能定义后面括号内的List类型，如果定义会报编译错误
        //及时child没有继承Parent，这里也不会报编译错误，但是按照Child对象循环输出时会报错
        List<Child> childs2 = (List)objects;
        System.out.println("这是childs2："+childs2.toString());
		
		
	}

}
