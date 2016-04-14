package com.example.sanika.forecastsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.Window;
import android.content.Intent;
import android.widget.RadioGroup.*;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import android.net.Uri;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;
import com.hamweather.aeris.communication.AerisEngine;
import java.util.ArrayList;
import java.util.List;

public class SearchFormActivity extends AppCompatActivity{

    private static final String TAG = SearchFormActivity.class.getSimpleName();
    Context context;
    public static String citySend;
    public static String stateSend;
    public static String units_value;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_form);
        context=this.getApplicationContext();
        final Spinner spinner = (Spinner) findViewById(R.id.text1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, R.layout.my_spinner_layout);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.my_spinner_layout);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final EditText streetVal   = (EditText)findViewById(R.id.streetValue);
        final EditText cityVal = (EditText)findViewById(R.id.cityValue);

        Button search=(Button)findViewById(R.id.searchButton);
        final TextView errorLabel=(TextView)findViewById(R.id.errorLabel);
        Button about=(Button)findViewById(R.id.about);
        Button clear=(Button)findViewById(R.id.clearButton);



        streetVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String street = streetVal.getText().toString();

                if (!street.equals("")) {

                    errorLabel.setVisibility(View.GONE);

                }
            }
        });
        cityVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                city = cityVal.getText().toString();

                if (!city.equals("")) {

                    errorLabel.setVisibility(View.GONE);

                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = spinner.getSelectedItem().toString();
                if (!text.equals("Select")) {

                    errorLabel.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        about.setOnClickListener(new View.OnClickListener(){


            @Override
               public void onClick(View v) {

                Intent intent = new Intent(SearchFormActivity.this, AboutActivity.class);
                startActivity(intent);

                   }
                } );

        clear.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                streetVal.setText("");
                cityVal.setText("");
                spinner.setSelection(0);
                    RadioButton fah=(RadioButton)findViewById(R.id.fah);
                    fah.setChecked(true);
                    RadioButton cel=(RadioButton)findViewById(R.id.cel);
                    cel.setChecked(false);
                    errorLabel.setText("");
                    errorLabel.setVisibility(View.GONE);

            }





        });

        ImageButton forecastIO=(ImageButton)findViewById(R.id.forecastIO);
        forecastIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://forecast.io/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });





        search.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (streetVal.getText().toString().equals("")) {

                    errorLabel.setText("Please enter a Street Address");
                    errorLabel.setVisibility(View.VISIBLE);

                } else if (cityVal.getText().toString().equals("")) {

                    errorLabel.setText("Please enter a City");
                    errorLabel.setVisibility(View.VISIBLE);
                } else if (spinner.getSelectedItem().toString().equals("Select")) {

                    errorLabel.setText("Please select a State");
                    errorLabel.setVisibility(View.VISIBLE);

                } else {


                    String stateName = spinner.getSelectedItem().toString();
                    RadioButton radFah = (RadioButton) findViewById(R.id.fah);
                    RadioButton radCel = (RadioButton) findViewById(R.id.cel);
                    if (radFah.isChecked()) {

                        units_value = "us";

                    } else if (radCel.isChecked()) {

                        units_value = "si";
                    }


                    citySend = cityVal.getText().toString();
                    stateSend = spinner.getSelectedItem().toString();
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("forecast2015-env.elasticbeanstalk.com")
                            .appendPath("index.php")
                            .appendQueryParameter("street", streetVal.getText().toString())
                            .appendQueryParameter("city", cityVal.getText().toString())
                            .appendQueryParameter("state", stateName)
                            .appendQueryParameter("units_value", units_value);
                    String myUrl = builder.build().toString();
                    Log.d("JSON", myUrl);


                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        new GenerateResult(context).execute(myUrl);
                    } else {
                        Log.d(TAG, "There is a network error");
                    }
                }

            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
