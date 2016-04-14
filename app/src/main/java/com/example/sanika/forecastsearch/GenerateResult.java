package com.example.sanika.forecastsearch;

/**
 * Created by sanika on 24-11-2015.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.lang.String;
import java.io.BufferedInputStream;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;



public class GenerateResult extends AsyncTask<String, Void, String> {

    private Context context;
    String urlmain;
    public GenerateResult(Context context){

        this.context=context;

    }

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.

        try {
            String getData = downloadUrl(urls[0]);
            return getData;
        } catch (IOException e) {

            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        String DEBUG_TAG="Forecast";
        InputStream is = null;

        try {
            urlmain=myurl;
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG,"The response is: " + response);
             is = new BufferedInputStream(conn.getInputStream());

            // Convert the InputStream into a string
            String contentAsString = getStringFromInputStream(is);
            Log.d(DEBUG_TAG,contentAsString);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }


        }


    }

    private static String getStringFromInputStream(InputStream is) {

        String DEBUG="JSON";
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

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
        Log.d(DEBUG,sb.toString());
        return sb.toString();

    }


    @Override
    protected void onPostExecute(String result){


        Intent intent=new Intent(context, ResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("JSONDATA",result);
        intent.putExtra("url",urlmain);
        context.startActivity(intent);




    }

}

