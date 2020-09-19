package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText city;
    Button search;
    TextView result;

    class Weather extends AsyncTask<String, Void, String>{   //First String means URL is in String, Void mean nothing, Third String means Return type will be String
     public void button(View view){

         city = findViewById( R.id.cityname );
         search = findViewById( R.id.button );
         result = findViewById( R.id.result);

         String cName = city.getText().toString();

         String content;

         Weather weather = new Weather();
         try {
             content = weather.execute( "https://samples.openweathermap.org/data/2.5/weather?q="+cName+"&appid=5383277e2acaa8ef6b20a505209545e9").get();
             //First we will check whether data is retrieved successfully or not
             Log.i(content,content);

             //JSON

             JSONObject jsonObject = new JSONObject( content );
             String weatherData = jsonObject.getString( "weather" );
             String mainTemperature = jsonObject.getString( "main" );// this main is not a part
             // of weather array it is separate variable like weather

             Log.i("weatherData",weatherData);
             //weatherData is in array
             JSONArray array = new JSONArray( weatherData );

             String main = "";
             String description ="";
             String temperature = "";


             for (int i= 0 ; i<array.length() ; i++){

                 JSONObject weatherPart = array.getJSONObject( i );
                 main = weatherPart.getString( "main" );
                 description = weatherPart.getString( "description" );

             }
             JSONObject mainPart = new JSONObject( mainTemperature );
              temperature = mainPart.getString( "temp" );
              Log.i("Temperature",temperature);




             Log.i("main",main);
             Log.i("description",description);


             String resultText = "Main :" +main+ "\nDescription :"+description+ "\nTemperature :"+temperature;

             result.setText( resultText );

             //Now we will show this result on a screen







         } catch (Exception e) {
             e.printStackTrace();
         }

     }
        @Override
        protected String doInBackground(String... address) {
            //String means multiple address can be sent. it acts as array

            try {
                URL url = new URL( address[0] );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Establish connection with address
                connection.connect();
                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader( is );

                //retrieve data and return as string
                int data = isr.read();
                String content = "";
                char ch;
                while(data != 0){
                    ch = (char)data;
                    content = content+ch;
                    data=isr.read();

                }
                return content;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


    }
}