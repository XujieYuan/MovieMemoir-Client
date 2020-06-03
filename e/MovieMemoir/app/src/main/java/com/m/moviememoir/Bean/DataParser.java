package com.m.moviememoir.Bean;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataParser {
    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        List<HashMap<String, String>> hashMapList = new ArrayList<>();
        try {
            Log.d("Places", "parse");
            jsonObject = new JSONObject((String) jsonData);
            jsonArray = jsonObject.getJSONArray("results");
            hashMapList = getPlaces(jsonArray);
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }
        return hashMapList;
    }

    public String getToken(String jsonData) {
        JSONObject jsonObject;
        String token = "";
        try {
            jsonObject = new JSONObject((String) jsonData);
            if (!jsonObject.isNull("next_page_token")) {
                token = jsonObject.getString("next_page_token");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        Log.d("Places", "getPlaces");

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        String photo_reference = "";
        String rating = "0.0";
        Log.d("getPlace", "Entered");

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            if (!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getString("rating");
            }
            if (!googlePlaceJson.isNull("photos")) {
                JSONArray jsonArray = googlePlaceJson.getJSONArray("photos");
                if (jsonArray.length() >= 1) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    photo_reference = jsonObject.getString("photo_reference");
                }
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("id", googlePlaceJson.getString("id"));
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
            googlePlaceMap.put("rating", rating);
            googlePlaceMap.put("photo_reference", photo_reference);
            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return googlePlaceMap;
    }


}
