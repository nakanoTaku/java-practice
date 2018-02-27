package com.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvImportWeather {
    public static void main (String args[]) {
        ArrayList<Weather> weatherList = new ArrayList<Weather>();
        // csv import
        try {
            File f = new File("/Users/taku/IdeaProjects/sample/src/main/java/com/weather/okayama_weather_20180101-to-20180125.csv");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;
            while ((line = br.readLine()) != null) {
                String[] date = line.split(",");
                Weather weather = new Weather();
                weather.setDate(date[0]);
                weather.setMaxTemperature(date[1]);
                weather.setMinTemperature(date[2]);
                weather.setAvgTemperature(date[3]);

                weatherList.add(weather);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // sort
        weatherList.forEach(v ->
                System.out.println(v.getDate() + ","
                        + v.getMaxTemperature() + ","
                        + v.getMinTemperature() + ","
                        + v.getAvgTemperature()
                )
        );

        // 標準出力
    }
}
