   package com.example.mel.weatherapplication;

   import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
   import android.view.View;
   import android.widget.EditText;
   import android.widget.TextView;
   import android.widget.Toast;

   import org.json.JSONArray;
   import org.json.JSONObject;

   import java.io.InputStream;
   import java.io.InputStreamReader;
   import java.net.HttpURLConnection;
   import java.net.URL;

   public class WeatherActivity extends AppCompatActivity {

    class WeatherInfo extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            try{

               URL url =  new URL (strings[0]);
                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                int data = reader.read();
                String apiDetails = "";
                char current;

                while(data != -1){
                    current = (char) data;
                    apiDetails += current;
                    data = reader.read();

                }
                return apiDetails;

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

    }

    public void getWeatherInfo(View view){

                /*
        the following line of code is getting the weather information from openweathermap
        by calling the weather.excute followed by the url of the website and get method.
         */
        WeatherInfo weatherInfo = new WeatherInfo();
        EditText cityName = (EditText) findViewById(R.id.cityEditText);
        TextView weatherTextView = (TextView) findViewById(R.id.weatherTextView);


        /*
        this line of code will determine if the city name is empty the a toast message will pop up
         */

        if(cityName.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter city name", Toast.LENGTH_LONG).show();
            weatherTextView.setText("");
        }
        try{
            String weatherApiDetails = weatherInfo.execute("http://api.openweathermap.org/data/2.5/weather?q="
                    + cityName.getText().toString() + "&apikey=53691a7864ab40eaddfc13c5eb91ecb9").get();

            //this is a log message that will print out the weather api info of the city that you search
            Log.i("Weather Api Info", weatherApiDetails);

            /*
            creating JSON object for weatherapidetails
             */

            JSONObject jsonObject =new JSONObject(weatherApiDetails);
            String weather = jsonObject.getString("weather");
            Log.i("WeatherDetails",weather);

            /*
            generate array for weather details
             */
            JSONArray array = new JSONArray(weather);
            String Main = "";
            String Description = "";

            for (int i =0; i<array.length(); i++){
                JSONObject arrayObject = array.getJSONObject(i);
                Main = arrayObject.getString("main");
                Description = arrayObject.getString("description");
            }
            Log.i("Main", Main);
            Log.i("Description", Description);

            /*
            setting the text for the weather details
            note: you can change this later to get detailed weather info
             */
            weatherTextView.setText("Main: " + Main + "\n"
                            + "Description: " + Description);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


    }
}
