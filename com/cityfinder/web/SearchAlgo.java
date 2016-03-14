package com.cityfinder.web;

        import java.io.*;
        import java.util.ArrayList;
import javax.servlet.*;

public class SearchAlgo {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // 1 = alphabetically by city name
        // 2 = income
        // 3 = education
        // 4 = housing
        // 5 = labour force activity
        // 6 = well-being
        // 7 = province
        search(1,args[0]);
        //System.out.println(search(1));
    }

    public static String [] search(int sortType, String cityName)  {
        int count =0;
        ArrayList<String[]>  data = loadData("Canada well being.csv");
        PrintWriter debugWriter = null;
        int searchRes = 0;
        try {
            debugWriter = new PrintWriter(new File("dbgOutput.txt"));
            /*for (String[] info : data) {

                for (String s : info) {
                    System.out.println(s);
                    count++;
                }
            }*/
            searchRes = binarySearch(data, cityName); // Search for the city
            debugWriter.write("Search result (index): " + searchRes);
            System.out.println("Search result (index): " + searchRes + "\nCity name: " + data.get(searchRes)[1]);
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println("Couldn't open debug file!!");
        }

        catch (ArrayIndexOutOfBoundsException aioobe)
        {
            System.err.println("Couldn't find city!");
            return null;
        }

        finally {
            debugWriter.close();
            return data.get(searchRes);
        }
    }

    private static int binarySearch(ArrayList<String[]> data, String cityName) {
        int lo = 0;
        int hi = data.size() - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (cityName.compareTo(data.get(mid)[1]) < 0) hi = mid - 1;
            else if (cityName.compareTo(data.get(mid)[1]) > 0 )  lo = mid + 1;
            else return mid;
        }
        return -1;
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
}

//search by cityname
//search by criteria
//return information
//jacen

