package com.m.moviememoir.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.m.moviememoir.Bean.DataParser;

import java.util.HashMap;
import java.util.List;


public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData = "";
    String url;
    private TokenListeners tokenListener = null;

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            url = (String) params[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(result);
        String Token = "";
        Token = dataParser.getToken(result);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        if (tokenListener != null) {
            tokenListener.Success(nearbyPlacesList, Token);
        }
    }

    public void setTokenListeners(TokenListeners tokenListener) {
        this.tokenListener = tokenListener;
    }

    public interface TokenListeners {
        void Success(List<HashMap<String, String>> nearbyPlacesList, String token);
    }

}
