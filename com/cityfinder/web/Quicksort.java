package com.cityfinder.web;

import java.io.*;
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
		// System.out.println(sortWellBeingCSV(1, "Canada well being.csv"));

		// MAKE SURE YOU STORE THE UNSORTED DATA SETS FROM THE GOOGLE DRIVE IN
		// THE ROOT FOLDER.
		// THE SORT METHOD CREATES A NEW SORTED FILE OF THE SAME NAME IN THE
		// COM.EXAMPLE.WEB DIRECTORY

		 sortWellBeingCSV(1);
		 sortCrimeIndexCSV(1);
		
	}

	// sort method has 2 parameters
	// - first is the sort type (look at main method above for legend)
	// - second is the file type (i.e. "well being" or "crime index")
	// currently configured to only work for "Canada well being.csv"
	public static ArrayList<String[]> sortWellBeingCSV(int sortType)
			throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<String[]> data = loadData("Canada well being.csv"); // read
																		// file
		String filetype = "well being"; // differentiating token
		quick(data, 0, data.size() - 1, sortType, filetype); // sort
		assert isSorted(data, 0, data.size() - 1, sortType, filetype); // check
																		// if
																		// sorted
		writeData(data, "Canada well being.csv", 1); // write sorted list to a
														// file
		return data;

	}

	public static ArrayList<String[]> sortCrimeIndexCSV(int sortType)
			throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<String[]> crimeData = new ArrayList<String[]>();
		crimeData = readCrimeData(crimeData, "Crime Index (Unaltered).csv"); // read
																				// crime
																				// index
																				// values
																				// for
																				// provinces
		crimeData = readCrimeData(crimeData, "Territories-Crime-Index.csv"); // for
																				// territories

		String filetype = "crime index"; // differentiating token

		quick(crimeData, 0, crimeData.size() - 1, sortType, filetype); // sort
		writeData(crimeData, "Crime Index.csv", 2); // write sorted list to a
													// file
		assert isSorted(crimeData, 0, crimeData.size() - 1, sortType, filetype); // makes
																					// sure
																					// file
																					// is
																					// sorted

		return crimeData;
	}

	private static void quick(ArrayList<String[]> cities, int lo, int hi, int sortType, String filetype) {
		if (hi <= lo)
			return;
		int j = partition(cities, lo, hi, sortType, filetype);
		quick(cities, lo, j - 1, sortType, filetype);
		quick(cities, j + 1, hi, sortType, filetype);
		assert isSorted(cities, 0, cities.size() - 1, sortType, filetype);
	}

	private static int partition(ArrayList<String[]> cities, int lo, int hi, int sortType, String filetype) {
		int i = lo;
		int j = hi + 1;
		String[] v = cities.get(lo);
		while (true) {

			// find item on lo to swap
			while (less(cities.get(++i), v, sortType, filetype))
				if (i == hi)
					break;

			// find item on hi to swap
			while (less(v, cities.get(--j), sortType, filetype))
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

	public static boolean isSorted(ArrayList<String[]> cities, int lo, int hi, int sortType, String filetype) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(cities.get(i), cities.get(i - 1), sortType, filetype))
				return false;
		return true;
	}

	private static boolean less(String[] object, String[] object2, int i, String filetype) {
		if (filetype.equals("well being")) {
			if (i == 1 || i == 8 || i == 9)
				return object[i].compareTo(object2[i]) < 0;
			else if (i == 7)
				return compareTo(object[i].replace("%", ""), object2[i].replace("%", "")) < 0;
			else
				return compareTo((object[i]), (object2[i])) < 0;
		}
		// else if (filetype.equals("crime index"))
		else {
			if (i == 0) {
				return Integer.parseInt(object[i]) < Integer.parseInt(object2[i]);
			} else if ((i >= 1 && i <= 4) || (i == 6)) {
				return object[i].compareTo(object2[i]) < 0;
			} else if (i == 5) {
				return Integer.parseInt(object[i]) < Integer.parseInt(object2[i]);
			} else if (i == 7) {
				return compareTo(object[i].replace("v", ""), object2[i].replace("v", "")) < 0;
			} else if (i == 8) {
				return Double.parseDouble(object[i]) < Double.parseDouble(object2[i]);
			} else if (i == 9) {
				return compareTo(object[i].replace("..", ""), object2[i].replace("..", "")) < 0;
			}
			else {
				return compareTo((object[i]), (object2[i])) < 0;
			}
			// {
			// if (object[i].substring(0, 1).equals(" "))
			// object[i].replaceFirst(" ", "");
			// if (object2[i].substring(0, 1).equals(" "))
			// object2[i].replaceFirst(" ", "");
			// return compareTo((object[i]), (object2[i])) < 0;
			// }
		}

	}

	public static int compareTo(String a, String b) {
		if (a.equals("") || b.equals(""))
			return a.compareTo(b);
		else {
			if (a.contains(".") || b.contains(".")) {
				Double x = Double.parseDouble(a);
				// System.out.println(b);
				Double y = Double.parseDouble(b);
				if (x < y)
					return -1;
				else if (x == y)
					return 0;
				else
					return 1;
			} else {
				// System.out.println(a);
				int x = Integer.parseInt(a);
				// System.out.println(b);
				int y = Integer.parseInt(b);
				if (x < y)
					return -1;
				else if (x == y)
					return 0;
				else
					return 1;
			}
		}
	}

	private static void exch(ArrayList<String[]> cities, int x, int y) {
		Collections.swap(cities, x, y);
	}

	private static ArrayList<String[]> loadData(String filename) {
		ArrayList<String[]> cities = new ArrayList<String[]>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {

				if (line.contains("\"")) {
					int first = line.indexOf("\"");
					int last = line.lastIndexOf("\"");
					String substring = line.substring(first, last);
					substring = substring.replace(",", "");
					line = line.substring(0, first) + substring + line.substring(last, line.length());
					line = line.replace("\"", "");
					line = line.replace("*", "");
				}
				cities.add(line.split(","));
				// cityCount++;
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

	// Reads 2013 Crime severity index for each city in the csv file. The year
	// 2013 was chosen because it had more data available than other years.
	private static ArrayList<String[]> readCrimeData(ArrayList<String[]> crimeData, String filename) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				line = line.replace("\"", "");
				String[] info = line.split(",");
				if (info[0].equals("2013")) { // picks 2013 data because this
												// year has the most data
					if (info[3].equals(" municipal")) { // checks for alternate
														// format rows
						if (info[5].equals("Crime severity index")) {
							String[] info2 = new String[info.length + 1];
							for (int i = 0; i < info2.length; i++) {
								if (i < 3)
									info2[i] = info[i];
								else if (i == 3)
									info2[i] = "";
								else
									info2[i] = info[i - 1];
							}
							// for (int i = 0; i < info2.length; i++) {
							// System.out.print(info2[i] + " ");
							// }
							// System.out.println();
							crimeData.add(info2);
						}
					} else { // row format is regular
						if (info[6].equals("Crime severity index")) {
							// for (int i = 0; i < info.length; i++) {
							// System.out.print(info[i] + " ");
							// }
							// System.out.println();
							crimeData.add(info);
						}
					}
				}
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
		return crimeData;
	}

	private static void toString(ArrayList<String[]> data) {
		// System.out.println(data.size());
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

	private static void writeData(ArrayList<String[]> data, String filename, int a)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintStream p = new PrintStream(new File(filename), "UTF-8");
		if (a == 1) {
			p.println(
					"CSD Code,Census subdivision (CSD) name,2011 Income Score,2011 Education Score,2011 Housing Score,2011 Labour Force Activity Score,2011 CWB Score,2011 Global Non-Response,Province,Type of Collectivity,2011 NHS Population");
		} else if (a == 2) {
			p.println(
					"Ref_Date,City,Province,Police Dept.,Type,Geographical classification,STATISTICS,Vector,Coordinate,Value");
		}
		// System.out.println(data.size());
		for (int i = 0; i < data.size(); i++) {
			String[] city = data.get(i);
			p.print(city[0]);
			for (int j = 1; j < city.length; j++) {
				p.print("," + city[j]);
			}
			p.println();

			// p.println(i);
		}
		p.close();
		// return "src/com/example/web/" + filename;
	}

}
