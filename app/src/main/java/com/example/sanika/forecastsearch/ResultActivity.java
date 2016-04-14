package com.example.sanika.forecastsearch;

import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.widget.ShareDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.AccessToken;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Arrays;
import java.lang.Float;
import android.util.Log;


public class ResultActivity extends AppCompatActivity {

    String forecastDataToBeSent;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_result);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        Intent intent=getIntent();
        String forecastData=intent.getStringExtra("JSONDATA");
        TextView resultText=(TextView)findViewById(R.id.result);
        TextView summaryText=(TextView)findViewById(R.id.summary);
        TextView precip=(TextView)findViewById(R.id.precip);
        TextView cor=(TextView)findViewById(R.id.cor);
        TextView ws=(TextView)findViewById(R.id.ws);
        TextView humid=(TextView)findViewById(R.id.humid);
        TextView dpScene=(TextView)findViewById(R.id.dp);
        TextView visibilityScene=(TextView)findViewById(R.id.visible);
        TextView sunriseScene=(TextView)findViewById(R.id.sunrise);
        TextView sunsetScene=(TextView)findViewById(R.id.sunset);
        ImageView icon=(ImageView)findViewById(R.id.iconImage);
        Button moreDetails=(Button)findViewById(R.id.MoreDetails);
        JSONObject jsonData=null;
        final String cityName=SearchFormActivity.citySend;
        final String stateName=SearchFormActivity.stateSend;
        final String units_value=SearchFormActivity.units_value;
        forecastDataToBeSent=intent.getStringExtra("JSONDATA");
        String getURL=intent.getStringExtra("url");
        forecastData=forecastData.replace("\\","");

        try{
            jsonData=new JSONObject(forecastData.substring(1,forecastData.length()-1));
        }catch(JSONException e){

            throw new RuntimeException(e);
        }

        String summary=null;
        Float precipIntensity;
        Float precipProbability;
        String chanceOfRain;
        Float windspeed;
        String win;
        Float dewPoint;
        String dp;
        Float humidity;
        String hum;
        Float visibility;
        String vi;
        String timezone;
        Integer sunrise;
        Integer sunset;
        String imageSource;
        Float tempmin=null;
        Float tempmax=null;
        String tempeminMain;
        String tempemaxMain;


        String wns="";
        String dpunit="";
        String visibilityUnit="";
        Float temperature=null;
        String tempe="";

        if(units_value.equals("us")){

            wns="mph";
            dpunit="F";
            visibilityUnit="mi";
        }else if(units_value.equals("si")){

            wns="m/s";
            dpunit="C";
            visibilityUnit="km";

        }

        try {
            summary=jsonData.getJSONObject("currently").getString("summary");
            temperature=Float.parseFloat(jsonData.getJSONObject("currently").getString("temperature"));
            tempe=String.format("%.0f", temperature);
            tempmin=Float.parseFloat(jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMin"));
            tempeminMain=String.format("%.0f", tempmin);
            tempmax=Float.parseFloat(jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMax"));
            tempemaxMain=String.format("%.0f",tempmax);
            imageSource=jsonData.getJSONObject("currently").getString("icon");
            precipIntensity=Float.parseFloat(jsonData.getJSONObject("currently").getString("precipIntensity"));
            precipProbability=Float.parseFloat(jsonData.getJSONObject("currently").getString("precipProbability")) * 100;
            chanceOfRain=String.format("%.0f", precipProbability) + "%";
            windspeed=Float.parseFloat(jsonData.getJSONObject("currently").getString("windSpeed"));
            win=String.format("%.2f", windspeed);
            dewPoint=Float.parseFloat(jsonData.getJSONObject("currently").getString("dewPoint"));
            dp=String.format("%.0f", dewPoint);
            humidity=(Float.parseFloat(jsonData.getJSONObject("currently").getString("humidity")))*100;
            hum=String.format("%.0f",humidity) + "%";
            visibility=Float.parseFloat(jsonData.getJSONObject("currently").getString("visibility"));
            vi=String.format("%.2f", visibility);
            timezone=jsonData.getString("timezone");
            sunrise=Integer.parseInt(jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("sunriseTime"));
            sunset=Integer.parseInt(jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("sunsetTime"));


        }catch(JSONException e){

            throw new RuntimeException(e);
        }


        Date time=new Date((long)sunrise*1000);
        SimpleDateFormat formatTime=new SimpleDateFormat("hh:mm a");
        formatTime.setTimeZone(TimeZone.getTimeZone(timezone));
        String timeSunrise=formatTime.format(time);

        Date timenew=new Date((long)sunset*1000);
        String timeSUnset=formatTime.format(timenew);

        final String imageURL=imageSource;

        switch(imageSource){
            case "clear-day":
                icon.setBackgroundResource(R.drawable.clear);
                break;
            case "clear-night":
                icon.setBackgroundResource(R.drawable.clear_night);
                break;
            case "partly-cloudy-day":
                icon.setBackgroundResource(R.drawable.cloud_day);
                break;
            case "partly-cloudy-night":
                icon.setBackgroundResource(R.drawable.cloud_night);
                break;
            case "rain":
                icon.setBackgroundResource(R.drawable.rain);
                break;
            case "snow":
                icon.setBackgroundResource(R.drawable.snow);
                break;
            case "sleet":
                icon.setBackgroundResource(R.drawable.sleet);
                break;
            case "wind":
                icon.setBackgroundResource(R.drawable.wind);
                break;
            case "fog":
                icon.setBackgroundResource(R.drawable.fog);
                break;
            case "cloudy":
                icon.setBackgroundResource(R.drawable.cloudy);
                break;
        }

        if(summary!=null){

            summaryText.setText(summary+ " "+ "in"+ " "+ cityName +  "," + stateName);

        }
        else{summaryText.setText("N.A");}

            TextView temperatureMain=(TextView)findViewById(R.id.temperatureMain);
            temperatureMain.setTextSize(TypedValue.COMPLEX_UNIT_PX,50);
            temperatureMain.setText(Html.fromHtml(tempe + "<sup><small>" + (char) 0x00B0 + dpunit + "</small></sup>"));

            TextView minMaxTemperatureView=(TextView)findViewById(R.id.minmaxmain);
            minMaxTemperatureView.setText("L:" + tempeminMain + (char) 0x00B0 + " " + "|" + " " + "H:" + tempemaxMain + (char) 0x00B0);

            Float val1=0.002f;
            Float val2=0.017f;
            Float val3=0.1f;
            Float val4=0.4f;

            if(units_value=="si"){

                Float val=25.4f;
                precipIntensity=(precipIntensity/val);
            }

            if(((Float.compare(precipIntensity,0)>0) ||  (Float.compare(precipIntensity,0)==0)) && Float.compare(val1,precipIntensity)>0 ){

                Log.d("compare",precipIntensity.toString());
                precip.setText("None");
            }else if(((Float.compare(precipIntensity,val1)>0) ||  (Float.compare(precipIntensity,val1)==0)) && Float.compare(val2,precipIntensity)>0 ){

                Log.d("compare",precipIntensity.toString());
                precip.setText("Very Light");
            }else if(((Float.compare(precipIntensity,val2)>0) ||  (Float.compare(precipIntensity,val2)==0)) && Float.compare(val3,precipIntensity)>0 ){

                Log.d("compare",precipIntensity.toString());
                precip.setText("Light");
            }else if(((Float.compare(precipIntensity,val3)>0) ||  (Float.compare(precipIntensity,val3)==0)) && Float.compare(val4,precipIntensity)>0 ){

                Log.d("compare",precipIntensity.toString());
                precip.setText("Moderate");
            }else if((Float.compare(precipIntensity,val4)>0) || (Float.compare(precipIntensity,val4)==0)){

                Log.d("compare",precipIntensity.toString());
                precip.setText("Heavy");
            }


            final String summaryValue=summary;
            final String temp1=tempe;
            final String inputDeg=dpunit;
           cor.setText(chanceOfRain);
           ws.setText(win + " "+ wns);
           dpScene.setText(dp + " " + (char) 0x00B0 + dpunit);
           humid.setText(hum);
           visibilityScene.setText(vi + " " + visibilityUnit);
           sunriseScene.setText(timeSunrise);
           sunsetScene.setText(timeSUnset);



        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                intent.putExtra("JSONdata", forecastDataToBeSent);
                intent.putExtra("units",units_value);
                startActivity(intent);


            }
        });


        callbackManager=CallbackManager.Factory.create();
        ImageButton fbLogin=(ImageButton)findViewById(R.id.fbLoginButton);
        shareDialog = new ShareDialog(this);


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                String src="";
                switch(imageURL){
                    case "clear-day":
                        src="clear.png";
                        break;
                    case "clear-night":
                        src="clear_night.png";
                        break;
                    case "partly-cloudy-day":
                        src="cloud_day.png";
                        break;
                    case "partly-cloudy-night":
                        src="cloud_night.png";
                        break;
                    default:
                        src=src+".png";
                }

                String imgsrc="http://cs-server.usc.edu:45678/hw/hw8/images/"+src;
                Uri myUri = Uri.parse(imgsrc);


                 if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current weather in" + " "+ cityName + ", " + " " + stateName)
                            .setContentDescription(
                                    summaryValue + "," + " " + temp1 + (char) 0x00B0 + inputDeg )
                            .setContentUrl(Uri.parse("http://forecast.io/"))
                            .setImageUrl(myUri)
                            .build();

                    shareDialog.show(linkContent);
                }

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }


        });



        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            public void onSuccess(Sharer.Result shareResult) {
                // App code
                Toast.makeText(getApplicationContext(), "Facebook Post Succesful", Toast.LENGTH_LONG).show();



            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();

            }


        });

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accessToken=AccessToken.getCurrentAccessToken();
                if (accessToken != null) {

                    LoginManager.getInstance().logInWithReadPermissions(ResultActivity.this, Arrays.asList("public_profile", "user_friends"));


                }

            }
        });



        Button mapView=(Button)findViewById(R.id.ViewMap);

        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity.this, MapActivity.class);
                intent.putExtra("coords",forecastDataToBeSent);
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}


