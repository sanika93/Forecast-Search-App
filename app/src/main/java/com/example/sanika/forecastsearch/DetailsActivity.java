package com.example.sanika.forecastsearch;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.AbsListView.LayoutParams;
import android.util.Log;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent=getIntent();
        String forecast=intent.getStringExtra("JSONdata");

        TextView heading=(TextView)findViewById(R.id.heading);
        String city=SearchFormActivity.citySend;
        String state=SearchFormActivity.stateSend;

        heading.setText("More details for "+ city + "," + " "+ state);

        TextView tempHeading=(TextView)findViewById(R.id.tempDeg);
        String unitsVal=intent.getStringExtra("units");
        String t="";
        Log.d("units",unitsVal);
        if(unitsVal.equals("us")) {
            tempHeading.setText("Temp(" +(char) 0x00B0 +"F"+")");
            t="F";
        }else if(unitsVal.equals("si")){

            tempHeading.setText("Temp(" +(char) 0x00B0 +"C"+")");
            t="C";
        }

        forecast=forecast.replace("\\", "");
        final JSONObject jsonParse;
        try{
            jsonParse=new JSONObject(forecast.substring(1,forecast.length()-1));
        }catch(JSONException e){

            throw new RuntimeException(e);
        }


        final TableLayout tableDetails=(TableLayout)findViewById(R.id.next24details);
        Button more=(Button)findViewById(R.id.next24);
        more.setBackgroundColor(Color.BLUE);
        Button more7=(Button)findViewById(R.id.next7);




        for(int i=0;i<24;i+=1){

            TableRow newRow=new TableRow(this);
            newRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newRow.setPadding(0,10,0,10);
            TextView first=new TextView(this);
            switch(i){
                case 0: first.setId(R.id.time0);
                        break;
                case 1: first.setId(R.id.time1);
                    break;
                case 2: first.setId(R.id.time2);
                    break;
                case 3: first.setId(R.id.time3);
                    break;
                case 4: first.setId(R.id.time4);
                    break;
                case 5: first.setId(R.id.time5);
                    break;
                case 6: first.setId(R.id.time6);
                    break;
                case 7: first.setId(R.id.time7);
                    break;
                case 8: first.setId(R.id.time8);
                    break;
                case 9: first.setId(R.id.time9);
                    break;
                case 10: first.setId(R.id.time10);
                    break;
                case 11: first.setId(R.id.time11);
                    break;
                case 12: first.setId(R.id.time12);
                    break;
                case 13: first.setId(R.id.time13);
                    break;
                case 14: first.setId(R.id.time14);
                    break;
                case 15: first.setId(R.id.time15);
                    break;
                case 16: first.setId(R.id.time16);
                    break;
                case 17: first.setId(R.id.time17);
                    break;
                case 18: first.setId(R.id.time18);
                    break;
                case 19: first.setId(R.id.time19);
                    break;
                case 20: first.setId(R.id.time20);
                    break;
                case 21: first.setId(R.id.time21);
                    break;
                case 22: first.setId(R.id.time22);
                    break;
                case 23: first.setId(R.id.time23);
                    break;
            }
            Integer time=0;
            String timezone="";
            String iconImage="";
            Float temperature=null;
            String temp="";
            try{
                time=Integer.parseInt(jsonParse.getJSONObject("hourly").getJSONArray("data").getJSONObject(i).getString("time"));
                timezone=jsonParse.getString("timezone");
                iconImage=jsonParse.getJSONObject("hourly").getJSONArray("data").getJSONObject(i).getString("icon");
                temperature=Float.parseFloat(jsonParse.getJSONObject("hourly").getJSONArray("data").getJSONObject(i).getString("temperature"));
            }catch(JSONException e){

                throw new RuntimeException(e);
            }
            Date timeStamp=new Date((long)time*1000);
            SimpleDateFormat formatTime=new SimpleDateFormat("hh:mm a");
            formatTime.setTimeZone(TimeZone.getTimeZone(timezone));
            String timeSunrise=formatTime.format(timeStamp);
            first.setText(timeSunrise);
            first.setTextColor(Color.rgb(128,128,128));
            first.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            if(i%2==0){
            newRow.setBackgroundColor(Color.rgb(192,192,192));}
            else{
                newRow.setBackgroundColor(Color.WHITE);
            }
            first.setGravity(Gravity.CENTER);
            first.setTextSize(30);
            ImageView second=new ImageView(this);
            second.setLayoutParams(new TableRow.LayoutParams(0,40,1.0f));
            switch(iconImage){
                case "clear-day":
                    second.setBackgroundResource(R.drawable.clear);
                    break;
                case "clear-night":
                    second.setBackgroundResource(R.drawable.clear_night);
                    break;
                case "partly-cloudy-day":
                    second.setBackgroundResource(R.drawable.cloud_day);
                    break;
                case "partly-cloudy-night":
                    second.setBackgroundResource(R.drawable.cloud_night);
                    break;
                case "rain":
                    second.setBackgroundResource(R.drawable.rain);
                    break;
                case "snow":
                    second.setBackgroundResource(R.drawable.snow);
                    break;
                case "sleet":
                    second.setBackgroundResource(R.drawable.sleet);
                    break;
                case "wind":
                    second.setBackgroundResource(R.drawable.wind);
                    break;
                case "fog":
                    second.setBackgroundResource(R.drawable.fog);
                    break;
                case "cloudy":
                    second.setBackgroundResource(R.drawable.cloudy);
                    break;
            }
            TextView third=new TextView(this);
            temp=String.format("%.0f",temperature);
            third.setText(temp);
            third.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            third.setGravity(Gravity.CENTER);
            third.setTextSize(30);
            third.setTextColor(Color.rgb(128,128,128));
            newRow.addView(first);
            newRow.addView(second);
            newRow.addView(third);
            tableDetails.addView(newRow);
        }

        for(int p=0;p<1;p++) {
            TableRow newRow=new TableRow(this);
            newRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Button first=new Button(this);
            first.setText("+");
            first.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            first.setGravity(Gravity.CENTER_HORIZONTAL);
            first.setBackgroundColor(Color.rgb(192,192,192));
            first.setTextSize(30);
            first.setId(R.id.plusButton);
            newRow.setBackgroundColor(Color.GRAY);
            newRow.addView(first);
            newRow.setId(R.id.extraRow);
            tableDetails.addView(newRow);
        }

            Button plus=(Button)findViewById(R.id.plusButton);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button plus=(Button)findViewById(R.id.plusButton);
                    TableRow firstRow=(TableRow)findViewById(R.id.extraRow);
                    plus.setVisibility(View.GONE);
                    firstRow.setVisibility(View.GONE);
                    for(int i=24;i<48;i+=1){
                        Context context=getApplicationContext();
                        TableRow newRow=new TableRow(context);
                        newRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        newRow.setPadding(0,10,0,10);
                        TextView one=new TextView(context);
                        one.setTextColor(Color.rgb(128,128,128));
                        switch(i){
                            case 24: one.setId(R.id.time24);
                                break;
                            case 25: one.setId(R.id.time25);
                                break;
                            case 26: one.setId(R.id.time26);
                                break;
                            case 27: one.setId(R.id.time27);
                                break;
                            case 28: one.setId(R.id.time28);
                                break;
                            case 29: one.setId(R.id.time29);
                                break;
                            case 30: one.setId(R.id.time30);
                                break;
                            case 31: one.setId(R.id.time31);
                                break;
                            case 32: one.setId(R.id.time32);
                                break;
                            case 33: one.setId(R.id.time33);
                                break;
                            case 34: one.setId(R.id.time34);
                                break;
                            case 35: one.setId(R.id.time35);
                                break;
                            case 36: one.setId(R.id.time36);
                                break;
                            case 37: one.setId(R.id.time37);
                                break;
                            case 38: one.setId(R.id.time38);
                                break;
                            case 39: one.setId(R.id.time39);
                                break;
                            case 40: one.setId(R.id.time40);
                                break;
                            case 41: one.setId(R.id.time41);
                                break;
                            case 42: one.setId(R.id.time42);
                                break;
                            case 43: one.setId(R.id.time43);
                                break;
                            case 44: one.setId(R.id.time44);
                                break;
                            case 45: one.setId(R.id.time45);
                                break;
                            case 46: one.setId(R.id.time46);
                                break;
                            case 47: one.setId(R.id.time47);
                                break;
                        }
                        Integer time=0;
                        String timezone="";
                        String imageSource="";
                        Float temperature=null;
                        String temp="";
                        try{
                            time=Integer.parseInt(jsonParse.getJSONObject("hourly").getJSONArray("data").getJSONObject(i).getString("time"));
                            timezone=jsonParse.getString("timezone");
                            imageSource=jsonParse.getJSONObject("hourly").getJSONArray("data").getJSONObject(i).getString("icon");
                            temperature=Float.parseFloat(jsonParse.getJSONObject("hourly").getJSONArray("data").getJSONObject(i).getString("temperature"));
                        }catch(JSONException e){

                            throw new RuntimeException(e);
                        }
                        Date timeStamp=new Date((long)time*1000);
                        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
                        formatTime.setTimeZone(TimeZone.getTimeZone(timezone));
                        String timeSunrise=formatTime.format(timeStamp);
                        one.setText(timeSunrise);
                        one.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                        if(i%2==0){
                            newRow.setBackgroundColor(Color.rgb(192,192,192));}
                        else{
                            newRow.setBackgroundColor(Color.WHITE);
                        }
                        one.setGravity(Gravity.CENTER);
                        one.setTextSize(30);
                        ImageView two = new ImageView(context);
                        two.setLayoutParams(new TableRow.LayoutParams(0, 40, 1.0f));
                        switch(imageSource){
                            case "clear-day":
                                two.setBackgroundResource(R.drawable.clear);
                                break;
                            case "clear-night":
                                two.setBackgroundResource(R.drawable.clear_night);
                                break;
                            case "partly-cloudy-day":
                                two.setBackgroundResource(R.drawable.cloud_day);
                                break;
                            case "partly-cloudy-night":
                                two.setBackgroundResource(R.drawable.cloud_night);
                                break;
                            case "rain":
                                two.setBackgroundResource(R.drawable.rain);
                                break;
                            case "snow":
                                two.setBackgroundResource(R.drawable.snow);
                                break;
                            case "sleet":
                                two.setBackgroundResource(R.drawable.sleet);
                                break;
                            case "wind":
                                two.setBackgroundResource(R.drawable.wind);
                                break;
                            case "fog":
                                two.setBackgroundResource(R.drawable.fog);
                                break;
                            case "cloudy":
                                two.setBackgroundResource(R.drawable.cloudy);
                                break;
                        }
                        TextView three=new TextView(context);
                        temp=String.format("%.0f",temperature);
                        three.setText(temp);
                        three.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                        three.setGravity(Gravity.CENTER);
                        three.setTextSize(30);
                        three.setTextColor(Color.rgb(128,128,128));
                        newRow.addView(one);
                        newRow.addView(two);
                        newRow.addView(three);
                        tableDetails.addView(newRow);
                    }




                }
            });

        for(int i=1;i<8;i++) {

            Integer dayAndMonth1=0;
            String ImageIcon1="";
            Float tempMin1=null;
            Float tempMax1=null;
            String minandmax1="";
            String formatMin1="";
            String formatMax1="";

            TextView dayMain=null;
            ImageView sideMain=null;
            TextView minMaxMain=null;

            switch(i){

                case 1: dayMain=(TextView)findViewById(R.id.day1);
                        sideMain=(ImageView)findViewById(R.id.image1);
                        minMaxMain=(TextView)findViewById(R.id.minmax1);
                        break;

                case 2: dayMain=(TextView)findViewById(R.id.day2);
                    sideMain=(ImageView)findViewById(R.id.image2);
                    minMaxMain=(TextView)findViewById(R.id.minmax2);
                    break;

                case 3: dayMain=(TextView)findViewById(R.id.day3);
                    sideMain=(ImageView)findViewById(R.id.image3);
                    minMaxMain=(TextView)findViewById(R.id.minmax3);
                    break;

                case 4: dayMain=(TextView)findViewById(R.id.day4);
                    sideMain=(ImageView)findViewById(R.id.image4);
                    minMaxMain=(TextView)findViewById(R.id.minmax4);
                    break;

                case 5: dayMain=(TextView)findViewById(R.id.day5);
                    sideMain=(ImageView)findViewById(R.id.image5);
                    minMaxMain=(TextView)findViewById(R.id.minmax5);
                    break;

                case 6: dayMain=(TextView)findViewById(R.id.day6);
                    sideMain=(ImageView)findViewById(R.id.image6);
                    minMaxMain=(TextView)findViewById(R.id.minmax6);
                    break;

                case 7: dayMain=(TextView)findViewById(R.id.day7);
                    sideMain=(ImageView)findViewById(R.id.image7);
                    minMaxMain=(TextView)findViewById(R.id.minmax7);
                    break;

            }

            try {
                dayAndMonth1 =Integer.parseInt(jsonParse.getJSONObject("daily").getJSONArray("data").getJSONObject(i).getString("time"));
                ImageIcon1 =jsonParse.getJSONObject("daily").getJSONArray("data").getJSONObject(i).getString("icon");
                tempMin1 =Float.parseFloat(jsonParse.getJSONObject("daily").getJSONArray("data").getJSONObject(i).getString("temperatureMin"));
                tempMax1 =Float.parseFloat(jsonParse.getJSONObject("daily").getJSONArray("data").getJSONObject(i).getString("temperatureMax"));
            }catch(JSONException e){

                throw new RuntimeException(e);
            }
            formatMin1=String.format("%.0f",tempMin1);
            formatMax1=String.format("%.0f",tempMax1);
            Date timeStamp1=new Date((long)dayAndMonth1*1000);
            Calendar cal=Calendar.getInstance();
            cal.setTime(timeStamp1);
            Integer dayOfWeek1=cal.get(Calendar.DAY_OF_WEEK);
            Log.d("dow",Integer.toString(dayOfWeek1));
            Integer date1=cal.get(Calendar.DAY_OF_MONTH);
            Log.d("dom",Integer.toString(date1));
            Integer month1=cal.get(Calendar.MONTH);
            Log.d("nameOfMonth",Integer.toString(month1));
            String dayAndDate="";

            String[] daysOfWeek={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
            String[] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

            for(Integer s=0;s<daysOfWeek.length;s++){
                Integer dayOfWeekCheck=dayOfWeek1-1;
                if(dayOfWeekCheck.equals(s)){

                    dayAndDate=daysOfWeek[s];
                }

            }

            Log.d("dayandDate",dayAndDate);

            for(Integer p=0;p<months.length;p++){

                if(month1.equals(p)){

                    dayAndDate=dayAndDate + "," + " " + months[p] + " "+ date1;
                }

            }
            Log.d("secondDayandDate",dayAndDate);

            switch(ImageIcon1){
                case "clear-day":
                    sideMain.setBackgroundResource(R.drawable.clear);
                    break;
                case "clear-night":
                    sideMain.setBackgroundResource(R.drawable.clear_night);
                    break;
                case "partly-cloudy-day":
                    sideMain.setBackgroundResource(R.drawable.cloud_day);
                    break;
                case "partly-cloudy-night":
                    sideMain.setBackgroundResource(R.drawable.cloud_night);
                    break;
                case "rain":
                    sideMain.setBackgroundResource(R.drawable.rain);
                    break;
                case "snow":
                    sideMain.setBackgroundResource(R.drawable.snow);
                    break;
                case "sleet":
                    sideMain.setBackgroundResource(R.drawable.sleet);
                    break;
                case "wind":
                    sideMain.setBackgroundResource(R.drawable.wind);
                    break;
                case "fog":
                    sideMain.setBackgroundResource(R.drawable.fog);
                    break;
                case "cloudy":
                    sideMain.setBackgroundResource(R.drawable.cloudy);
                    break;
            }

            minandmax1="Min:"+ " "+ formatMin1 + (char) 0x00B0 + t + " " + "|" + " " + "Max:"+ " " + formatMax1 + (char) 0x00B0 + t;
            dayMain.setText(dayAndDate);
            minMaxMain.setText(minandmax1);
        }


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout next24 = (RelativeLayout) findViewById(R.id.next24hours);
                RelativeLayout next7 = (RelativeLayout) findViewById(R.id.next7days);
                next7.setVisibility(View.GONE);
                next24.setVisibility(View.VISIBLE);
                Button getmore7 = (Button) findViewById(R.id.next7);
                Button getMore24 = (Button) findViewById(R.id.next24);
                getMore24.setBackgroundColor(Color.BLUE);
                getmore7.setBackgroundColor(Color.GRAY);
            }
        });

        more7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout next24=(RelativeLayout)findViewById(R.id.next24hours);
                RelativeLayout next7=(RelativeLayout)findViewById(R.id.next7days);
                next24.setVisibility(View.GONE);
                next7.setVisibility(View.VISIBLE);
                Button getmore7=(Button)findViewById(R.id.next7);
                Button getMore24=(Button)findViewById(R.id.next24);
                getMore24.setBackgroundColor(Color.GRAY);
                getmore7.setBackgroundColor(Color.BLUE);

            }
        });

    }


}
