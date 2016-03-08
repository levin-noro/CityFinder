package com.example.model;

import java.util.*;

public class BeerExpert {
	public List getBrands(String color) {
		List brands = new ArrayList();
		if (color.equals("amber")) {
			brands.add("Jack Amber");
			brands.add("Red Moose");
		} else {
			brands.add("Jail Pale Ale");
			brands.add("Gout Stout");
		}
		return brands;
	}
	
	public static void main(String []args){
		String color = "amber";
		List brand = new BeerExpert().getBrands(color);
		System.out.println(brand.get(0));
		}
}