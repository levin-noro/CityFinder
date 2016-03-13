package com.example.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class QuickSort {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		// 1 = alphabetically by city name
		// 2 = income
		// 3 = education
		// 4 = housing
		// 5 = labour force activity
		// 6 = well-being
		// 7 = province
		System.out.println(sort(1,"Canada well being.csv"));
		
		// MAKE SURE YOU STORE THE UNSORTED DATA SETS FROM THE GOOGLE DRIVE IN THE ROOT FOLDER.
		// THE SORT METHOD CREATES A NEW SORTED FILE OF THE SAME NAME IN THE COM.EXAMPLE.WEB DIRECTORY
		
	}
	
	// sort method has 2 parameters - first is the sort type (look at main method above for legend), second is the file name
	// currently configured to only work for "Canada well being.csv"
	public static ArrayList<String[]> sort(int sortType, String filename) throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<String[]> data = loadData(filename);
		quick(data, 0, data.size()-1, sortType);
		assert isSorted(data, 0, data.size() - 1, sortType);
		//toString(data);
		writeData(data, filename);
		return data;
		
	}

	
	
	

	private static void quick(ArrayList<String[]> cities, int lo, int hi, int sortType) {
		if (hi <= lo)
			return;
		int j = partition(cities, lo, hi, sortType);
		quick(cities, lo, j - 1, sortType);
		quick(cities, j + 1, hi, sortType);
		assert isSorted(cities, 0, cities.size() - 1, sortType);
	}

	private static int partition(ArrayList<String[]> cities, int lo, int hi, int sortType) {
		int i = lo;
		int j = hi + 1;
		String[] v = cities.get(lo);
		while (true) {

			// find item on lo to swap
			while (less(cities.get(++i), v, sortType))
				if (i == hi)
					break;

			// find item on hi to swap
			while (less(v, cities.get(--j), sortType))
				if (j == lo)
					break; // redundant since a[lo] acts as sentinel

			// check if pointers cross
			if (i >= j)
				break;

			exch(cities, i, j);
		}

		// put partitioning item v at a[j]
		exch(cities, lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}
	
	private static boolean isSorted(ArrayList<String[]> cities, int lo, int hi, int sortType) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(cities.get(i), cities.get(i - 1), sortType))
				return false;
		return true;
	}

	private static boolean less(String[] object, String[] object2, int i) {
		if (i == 1 || i == 8 || i == 9) return object[i].compareTo(object2[i]) < 0;
		else if (i == 7) return compareTo(object[i].replace("%", ""),object2[i].replace("%", "")) < 0;
		else return compareTo((object[i]), (object2[i])) < 0;
	}

	public static int compareTo(String a, String b) {
		if (a .equals("") || b .equals(""))  return a.compareTo(b);
		else {
			//System.out.println(a);
			int x = Integer.parseInt(a);
			//System.out.println(b);
			int y = Integer.parseInt(b);
			if (x < y) return -1;
			else if ( x == y) return 0;
			else return 1;
		}
	}
	
	private static void exch(ArrayList<String[]> cities, int x, int y) {
		Collections.swap(cities, x, y);
	}
	
	private static ArrayList<String[]> loadData(String filename) {
		ArrayList<String[]> cities = new ArrayList<String[]>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename), "UTF8"));
			 String line;
			 br.readLine();
	         while((line = br.readLine()) != null) {
	        	 
	 			if (line.contains("\"")) {
	 				int first = line.indexOf("\"");
	 				int last = line.lastIndexOf("\"");
	 				String  substring = line.substring(first, last);
	 				substring = substring.replace(",", "");
	 				line = line.substring(0,first) + substring + line.substring(last,line.length());
	 				line = line.replace("\"", "");
	 				line = line.replace("*", "");
	 			}
	 			cities.add(line.split(","));
	 			//cityCount++;
	         }
		} catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } finally {
	         if (br != null) {
	            try {
	               br.close();
	            } catch (IOException e) {
	               e.printStackTrace();
	            }
	         }
	      }
	      return cities;
		
	}
	
	private static void toString(ArrayList<String[]> data) {
		//System.out.println(data.size());
		for (int i = 0; i < data.size(); i++) {
			String[] city = data.get(i);
			System.out.print(city[0]);
			for (int j = 1; j < city.length; j++) {
				System.out.print("," + city[j]);
			}
			System.out.println();
			
			System.out.println(i);
		}	
	}

	private static String writeData(ArrayList<String[]> data, String filename) throws FileNotFoundException, UnsupportedEncodingException {
		PrintStream p  = new PrintStream(new File("src/com/example/web/" + filename),"UTF-8");
		p.println("CSD Code,Census subdivision (CSD) name,2011 Income Score,2011 Education Score,2011 Housing Score,2011 Labour Force Activity Score,2011 CWB Score,2011 Global Non-Response,Province,Type of Collectivity,2011 NHS Population");
		//System.out.println(data.size());
		for (int i = 0; i < data.size(); i++) {
			String[] city = data.get(i);
			p.print(city[0]);
			for (int j = 1; j < city.length; j++) {
				p.print("," + city[j]);
			}
			p.println();
			
			//p.println(i);
		}	
		p.close();
		return "src/com/example/web/" + filename;
	}
	
//	private static ArrayList<String[]> loadData(String filename) throws FileNotFoundException {
//		Scanner csvFile = new Scanner(new File(filename));
//		
//		ArrayList<String[]> cities = new ArrayList<String[]>();
//		
//		csvFile.nextLine();
//		int cityCount = 0;
//		
//		while (csvFile.hasNextLine()) {
//			String line = csvFile.nextLine();
//			if (line.contains("\"")) {
//				int first = line.indexOf("\"");
//				int last = line.lastIndexOf("\"");
//				String  substring = line.substring(first, last);
//				substring = substring.replace(",", "");
//				line = line.substring(0,first) + substring + line.substring(last,line.length());
//				line = line.replace("\"", "");
//			}
//			cities.add(line.split(","));
//			cityCount++;
//		}
//		//System.out.println(cityCount);
//		csvFile.close();
//		return cities;
//	}
}
