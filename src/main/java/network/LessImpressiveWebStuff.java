package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LessImpressiveWebStuff {

    public static void main(String[] args) throws MalformedURLException, IOException {

        try {
            String vancouverWeather = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&APPID=";
            String theURL = vancouverWeather + "43729f7acccdd4a309d899eddc35ed3d";
            URL url = new URL(theURL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            parseAndPrint(sb);
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseAndPrint(StringBuilder sb) throws ParseException {

        Object jsonParser = new JSONParser().parse(sb.toString());
        JSONObject jobject = (JSONObject) jsonParser;
        System.out.println("Location of weather: " + (String) jobject.get("name"));

        JSONArray jsonArray = (JSONArray) jobject.get("weather");
        String weather = jsonArray.get(0).toString();
        Object jsonParser1 =  new JSONParser().parse(weather);
        JSONObject jobject1 = (JSONObject) jsonParser1;

        System.out.println("Forcast: " + (String) jobject1.get("main"));
        System.out.println("Description: " + (String) jobject1.get("description"));

        jobject1 = (JSONObject) jobject.get("main");

        System.out.println("Temperature: " + (double) jobject1.get("temp"));
        System.out.println("Humidity: " + (long) jobject1.get("humidity"));
    }
}
