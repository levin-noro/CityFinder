package com.cityfinder.web.graph.datastructures.graph;

public class City {
    /* See constructor for details */
    private int myCSD;
    private String myCSDName;
    private int myIncome;
    private int myHousingScore;
    private int myEdScore;
    private int myLabourActivityScore;
    private int myCWBScore;
    private int myGlobNonRespPerc;
    private String myProv;
    private String myCollectivityType;
    private int myPop; // com.cityfinder.web.graph.datastructures.graph.City's population

    /**
     * Fetches one of the integer values associated with the city.
     * @param valNum The value's ID (detailed in the method body).
     * @return The value, if it exists, or -2 otherwise.
     */
    public int getIntValue(int valNum)
    {
        int toReturn; // Value to return

        switch (valNum) // Determine what to return based on the value
        {
            case 0: // CSD
            {
                toReturn = myCSD;
                break;
            }

            case 1: // Income
            {
                toReturn = myIncome;
                break;
            }

            case 2: // Housing score
            {
                toReturn = myHousingScore;
                break;
            }

            case 3: // Education score
            {
                toReturn = myEdScore;
                break;
            }

            case 4: // Labour activity score
            {
                toReturn = myLabourActivityScore;
                break;
            }

            case 5: // CWB score
            {
                toReturn = myCWBScore;
                break;
            }

            case 6: // Non-response percentage
            {
                toReturn = myGlobNonRespPerc;
                break;
            }

            case 7: // Population
            {
                toReturn =  myPop;
                break;
            }

            default: // Unknown variable
            {
                toReturn = -2;
                break;
            }
        }

        return toReturn; // Return the value
    }

    /**
     * Fetches the string value associated with the value number.
     * @param valNum The number of the value to return.
     * @return The string value, or an empty string if it doesn't exist.
     */
    public String getStrVal(int valNum)
    {
        String toReturn;

        switch (valNum)
        {
            case 0: // Name
            {
                toReturn = myCSDName;
                break;
            }

            case 1: // Province
            {
                toReturn = myProv;
                break;
            }

            case 2: // Collectivity type
            {
                toReturn = myCollectivityType;
                break;
            }

            default: // Error
            {
                toReturn = "";
                break;
            }
        }

        return toReturn;
    }

    /**
     * Creates a city object from the well-being index.
     * @param csdCode The CSD (census subdivision) code of the city.
     * @param csdName The city's census subdivision name.
     * @param income The avg. income in the city.
     * @param edScore The city's education score.
     * @param housScore The city's housing score.
     * @param labAct The city's labour activity score.
     * @param cwbScore The city's CWB score.
     * @param gnr The city's global non-response percentage.
     * @param prov The city's province.
     * @param collec The city's collectivity type.
     * @param pop The city's population.
     */
    public City(int csdCode, String csdName, int income, int edScore, int housScore, int labAct, int cwbScore, int gnr, String prov, String collec, int pop)
    {
            /* Store constructor vars */
        myCSD = csdCode;
        myCSDName = csdName != null ? csdName : ""; // Prevent null str values - check if it is null and assign conditionally
        myIncome = income;
        myEdScore = edScore;
        myHousingScore = housScore;
        myLabourActivityScore = labAct;
        myCWBScore = cwbScore;
        myGlobNonRespPerc = gnr;
        myProv = prov != null ? prov : "";
        myCollectivityType = collec != null ? collec : "";
        myPop = pop;
    }

    /**
     * Returns either a string or an integer value, depending on what value index is given.
     * @param valInd The index of the value to return. Mapped to either a string or a number value.
     * @return An object containing the requested value. The caller MUST cast it to the appropriate type.
     */
    public Object getValue(int valInd)
    {
        /* Determine if category is integer or string type */
        if (valInd < 8) // Integer type
        {
            return new Integer(getIntValue(valInd)); // Just get the value
        }

        else // String type
        {
            return getStrVal(valInd % 3); // Convert the index to be one in the range [0, 2] and return the string value with that index
        }
    }

    /**
     * For debugging. Represents a city as a string.
     * @return A string representation of the city.
     */
    public String toString()
    {
        return "{\"csdCode\": " + Integer.toString(myCSD) + ", \"csdName\": \"" + myCSDName + "\", \"income\": " + Integer.toString(myIncome) + ", \"edScore\": " + Integer.toString(myEdScore) + ", \"houScore\": " + Integer.toString(myHousingScore) + ", \"labActScore\": " + Integer.toString(myLabourActivityScore) + ", \"cwbScore\": " + Integer.toString(myCWBScore) + ", \"globNonRespPerc\": " + Integer.toString(myGlobNonRespPerc) + ", \"prov\": \"" + myProv + "\", \"pop\": " + Integer.toString(myPop) + "}";
    }
}