package in.xparticle.myweatherapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result, resultD;
    RelativeLayout relativeLayout;

//    https://api.openweathermap.org/data/2.5/weather?q=lucknow&appid=e0b3d927aa7022aa51c89b8ccb75ff49
    String baseURL="https://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=e0b3d927aa7022aa51c89b8ccb75ff49";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.btn);
        city=(EditText) findViewById(R.id.et_city);
        result=(TextView) findViewById(R.id.result_weather);
        resultD=(TextView) findViewById(R.id.result_c);
        relativeLayout=findViewById(R.id.screen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(city.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    String myURL = baseURL + city.getText().toString() + API;
//                    Log.e("url", "onClick: url" + myURL);

                    JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, myURL, null,
                            new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                Log.e("TAG", "onResponse: "+jsonObject);
                                String info=jsonObject.getString("weather");

                                JSONArray ar=new JSONArray(info);
                                for(int i=0;i<ar.length();i++){
                                    JSONObject parObj= ar.getJSONObject(i);
                                    String myWeather= parObj.getString("main");
                                    result.setText(myWeather);
                                    if(myWeather.equals("Mist")){
                                        relativeLayout.setBackgroundResource(R.drawable.mistweather);
                                    }else if(myWeather.equals("Haze")){
                                        relativeLayout.setBackgroundResource(R.drawable.hazeweather);
                                    }else if(myWeather.equals("Clear")){
                                    relativeLayout.setBackgroundResource(R.drawable.clearweather);
                                    }else if(myWeather.equals("Clouds")){
                                        relativeLayout.setBackgroundResource(R.drawable.cloudsweather);
                                    }else if(myWeather.equals("Snow")){
                                        relativeLayout.setBackgroundResource(R.drawable.snowfallweather);
                                    }else if(myWeather.equals("Thunderstorm")){
                                        relativeLayout.setBackgroundResource(R.drawable.thunderweather2);
                                    }else if(myWeather.equals("Rain")){
                                        relativeLayout.setBackgroundResource(R.drawable.rainweather);
                                    }else {
                                        relativeLayout.setBackgroundResource(R.drawable.weather);

                                    }

                                }
                                String info2=jsonObject.getString("main");
                                JSONObject object=new JSONObject(info2);
                                int myWeather2= object.getInt("temp");
                                int cal= (int) (myWeather2-273.15);
                                String myWeather3=String.valueOf(cal);
                                resultD.setText(myWeather3+"°C");
//                                Log.e("tick", "onResponse: "+resultD );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

                    MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);


                }
            }
        });
    }
}