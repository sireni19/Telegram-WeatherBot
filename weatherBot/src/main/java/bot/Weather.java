package bot;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Weather {

    private String weatherText;
    private String APIkey = ""; // Сюда требуется вствавить API ключ сайта "openweathermap.org"

    public String getURLContent(String urlAddress) {

        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAddress);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Город не найден");
        }
        return content.toString();
    }

    public String getCityWeather(String city) {
        String output = getURLContent("https://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=" + APIkey + "&units=metric");
        if (!output.isEmpty()) {
            JSONObject object = new JSONObject(output);
            weatherText = "Погода в городе " + city + ":"
                    + "\n\nТемпература: " + object.getJSONObject("main").getDouble("temp")
                    + "\nОщущается: " + object.getJSONObject("main").getDouble("feels_like")
                    + "\nВлажность: " + object.getJSONObject("main").getDouble("humidity")
                    + "\nДавление: " + object.getJSONObject("main").getDouble("pressure");
        }
        else {
            weatherText="Не знаю я такой город";
        }
        return weatherText;
    }
}
