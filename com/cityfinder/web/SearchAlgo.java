package com.cityfinder.web;

        import java.io.*;
        import java.util.ArrayList;
import javax.servlet.*;
        import javax.servlet.http.HttpServletResponse;

public class SearchAlgo {

    private static PrintWriter debugWriter = null;
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

    public static String [] search(int sortType, String cityName) {
        int count = 0;
        ArrayList<String[]> data = loadData("Canada well being.csv");
        int searchRes = 0;
        try {
            debugWriter = new PrintWriter(new File("dbgOutput.txt"));
            debugWriter.println("search input: sortType = " + sortType + ", cityName = \"" + cityName + "\"");
            System.out.println("search input: sortType = " + sortType + ", cityName = \"" + cityName + "\"");
            for (String[] info : data) {

                for (String s : info) {
                    debugWriter.write(s);
                    debugWriter.println();
                    count++;
                }
            }
            searchRes = binarySearch(data, cityName); // Search for the city
            debugWriter.write("Search result (index): " + searchRes);
            debugWriter.println();
            System.out.println("Search result (index): " + searchRes);
        } catch (FileNotFoundException fnfe) {
            System.err.println("Couldn't open debug file!!");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("Couldn't find city!");
            return null;
        } finally {
            debugWriter.close();

            if (searchRes != -1) // Found result
            {
                return data.get(searchRes); // Return the result
            } else {
                return new String[]{"nomatch"}; // Let caller know that nothing was found
            }
            //String[] test = {"a", "b", "c", cityName, Integer.toString(searchRes)};
            //return test;
        }
    }

    private static int binarySearch(ArrayList<String[]> data, String cityName) {
        int lo = 0;
        int hi = data.size() - 1;
        debugWriter.println("binarySearch: init: lo = " + lo + ", hi = " + hi + ", cityName = \"" + cityName + "\"");
        System.out.println("binarySearch: init: lo = " + lo + ", hi = " + hi + ", cityName = \"" + cityName + "\"");
        while (lo <= hi) {
            System.out.println("binarySearch: in while");
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            System.out.println("mid = " + mid + "\ndata[" + mid + "] = " + data.get(mid));
            if      (cityName.compareTo(data.get(mid)[1]) < 0) {hi = mid - 1; System.out.println("new hi = " + hi);}
            else if (cityName.compareTo(data.get(mid)[1]) > 0 )  {lo = mid + 1; System.out.println("new lo = " + lo);}
            else {System.out.println("Done: returning " + mid);return mid;}
        }
        System.out.println("binarySearch: couldn't find anything");
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
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("IOException: " + e.getMessage());
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

