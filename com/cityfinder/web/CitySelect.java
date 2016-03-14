package com.cityfinder.web;

        import javax.servlet.RequestDispatcher;
        import javax.servlet.ServletException;
        import javax.servlet.http.*;
        import java.io.*;
        import java.util.*;

        import com.cityfinder.model.ListModule;

//requestType: string, gets requestType
//city: when request is cityName put in city
//getCityList: make new instance ListModule
//listCity: store list of cities here
//sortCity: new instance of QuickSort
public class CitySelect extends HttpServlet{

    /**
     * Code which actually handles the request.
     * @param request The request to be handled.
     * @param response The response used to write to the client.
     * @throws IOException
     * @throws ServletException
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        /* Get parameters */
        String requestType = request.getParameter("reqType"); //gets which type of request
        //search (cityName) or similarity(to a city, cityName), List(no parameter)
        String city = request.getParameter("cityName"); //type cityName

		/* Set up the request */
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter(); // Get the writer to write the response to

		/* Determine what we need to return */
        switch (requestType) // Determine type of request to respond to
        {
            case "list": {
                ListModule getCityList = new ListModule();//for the drop down
                ArrayList<String> cityList = getCityList.getCity(city); // List of cities

                // Print matching list to response - print names

                for (int i = 0; i < cityList.size(); i++) // List is sorted, go through it in order
                {
                    writer.write(cityList.get(i)); // Write the city's name to output
                }

                System.exit(0);
                break;
            }

            case "search":
            {
                QuickSort sortCity = new QuickSort();
                sortCity.sort(1);//1 means sort alphabetically

                /**
                 * NOTE: THE BACK-END ASSUMES THAT THE FRONT-END WILL PRE-FILTER THE LIST OF CITIES SUCH THAT ONLY 1 CITY WILL BE FOUND.
                 */
                SearchAlgo searchCity = new SearchAlgo();
                String[] citysInfo = searchCity.search(1, city, response); // An array of strings containing the city's data
                request.setAttribute("citysInfo", citysInfo); // Store array for JSP
                RequestDispatcher view = request.getRequestDispatcher("result.jsp");
                view.forward(request, response);

		/* Print a JSON object */
                //writer.write("{\"csdCode\":" + Integer.parseInt(citysInfo[0]) + ", \"csdName\":" + citysInfo[1] + ", \"income\": " + Integer.parseInt(citysInfo[2]) + ", \"education\": " + Integer.parseInt(citysInfo[3]) + ", \"housing\": " + Integer.parseInt(citysInfo[4]) + ", \"labourForceActivity\":" + Integer.parseInt(citysInfo[5]) + ", \"cwb\": " + Integer.parseInt(citysInfo[6]) + ", \"globalNonResponse\": " + Integer.parseInt(citysInfo[7]) + ", \"province\": " + citysInfo[8] + ", \"collectivityType\": " + citysInfo[9] + ", \"population\": " + Integer.parseInt(citysInfo[10]) + "}"); // ASSUMPTION: THE DATA FORMAT WILL BE EXACTLY AS IN THE DATASET
                /*writer.write("Search output:");

                for (String s : citysInfo) // DEBUGGING: Print search return val
                {
                    writer.write("\"" + s + "\"");
                    writer.println();
                }
                break;*/
            }
        }

        writer.write("{\"status\": \"test\", \"requestType\": \"" + requestType + ", \", \"cityName\": \"" + city + "\"}\r\n\r\n"); // Print a JSON response
    }

    public void handleRequest2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
         /* Get parameters */
        String requestType = request.getParameter("reqType"); //gets which type of request
        //search (cityName) or similarity(to a city, cityName), List(no parameter)
        String city = request.getParameter("cityName"); //type cityName

        /* Set up the request */
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter(); // Get the writer to write the response to
        writer.write("{\"status\": \"test\", \"requestType\": \"" + requestType + "\", \"cityName\": \"" + city + "\"\r\n\r\n"); // Print a JSON response
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response); // Call the request handler function
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		handleRequest(request, response); // Handle the request
        /*PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.write("{\"test\": \"test\"}");*/
    }
}
