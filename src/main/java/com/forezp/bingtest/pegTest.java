package com.forezp.bingtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

public class pegTest {

	public static void main(String[] args) {
		Peg peg = new Peg();
		peg.setName("liii");
		peg.setAge(3);

		Peg peg1 = new Peg();
		peg1.setName("lii");
		peg1.setAge(4);

		List<Peg> list = new ArrayList<Peg>();
		list.add(peg);
		list.add(peg1);
		
		System.out.println(list);
		sort1(list);
		System.out.println(list);
		sort2(list);
		System.out.println(list);
	}

	public static void sort1(List<Peg> list) {
		Collections.sort(list,new Comparator<Peg>() {

			@Override
			public int compare(Peg o1, Peg o2) {
				   return o1.getName().hashCode() - o2.getName().hashCode();
			}
			
		});
	}

	public static void sort2(List<Peg> list) {
		Collections.sort(list,new Comparator<Peg>() {

			@Override
			public int compare(Peg o1, Peg o2) {
				   return o1.getAge() - o2.getAge();
			}
			
		});
	}

}
