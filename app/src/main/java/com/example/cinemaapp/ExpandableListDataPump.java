package com.example.cinemaapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("1");
        cricket.add("2");
        cricket.add("3");
        cricket.add("4");
        cricket.add("5");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("LOGIN OR SIGN-UP", cricket);
        expandableListDetail.put("CHOOSE TICKETS", football);
        expandableListDetail.put("CHOOSE SEATS", basketball);
        return expandableListDetail;
    }
}
