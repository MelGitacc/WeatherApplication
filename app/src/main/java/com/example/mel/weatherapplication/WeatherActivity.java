   package com.example.mel.weatherapplication;

   import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WeatherInfo weatherInfo = new WeatherInfo();

        try{
            String weatherApiDetails = weatherInfo.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get();

            Log.i("Weather Api Info", weatherApiDetails);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
