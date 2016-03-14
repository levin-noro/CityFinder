package com.cityfinder.model;

import java.util.ArrayList;
import java.util.List;

public class ListModule {
    public ArrayList<String> getCity(String city){
        ArrayList<String> cities = new ArrayList<String>();
        cities.add("Toronto");
        return cities;
    }

//	public List getBrands(String color) {
//		List brands = new ArrayList();
//		if (color.equals("amber")) {
//			brands.add("Jack Amber");
//			brands.add("Red Moose");
//		} else {
//			brands.add("Jail Pale Ale");
//			brands.add("Gout Stout");
//		}
//		return brands;
//	}

}