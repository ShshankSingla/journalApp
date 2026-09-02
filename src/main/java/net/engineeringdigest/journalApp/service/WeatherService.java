package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey; // we use final if value is hardcore  // we remove static because spring keep the initial value and doesn't chnge it

    // if we are taking value from application.yaml then used below one , but now we are obtaining through application cache
    //private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

//    l-39
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){

        //l-33
        // when using api through application.yaml
//        String finalAPI = API.replace("CITY", city).replace("API_KEY",apiKey);

        //lec34
// //       String finalAPI = appCache.appCache.get("weather_api").replace("<city>", city).replace("<apiKey>",apiKey);
  //      String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY,apiKey);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
//        response.getStatusCode();
//        WeatherResponse body = response.getBody();
//        return body;

        //lec39
        // cache wala response
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);// key,
        // data ko pehle cache mei check karo
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            // agar for some reason data, cache mei nahi mila ya expire ho gya to actual call karo
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY,apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            // data mil jaye to useh redis mei bhi save karwa lo
            if(body != null){
                redisService.set("weather_of_"+city, body, 300l);
            }
            return body;
        }

    }
}
