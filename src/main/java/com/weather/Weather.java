package com.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    private String date;
    private String maxTemperature;
    private String minTemperature;
    private String avgTemperature;
}
