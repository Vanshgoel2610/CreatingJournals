package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherResponse {
    private Current current;

    public void setCurrent(Current current) {
        this.current = current;
    }
    public Current getCurrent() {
        return current;
    }

    public static class Current {
        private int temperature;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;
        private int feelslike;

        // All getters and setters
        public int getTemperature() { return temperature; }
        public void setTemperature(int temperature) { this.temperature = temperature; }

        public List<String> getWeather_descriptions() { return weatherDescriptions; }
        public void setWeather_descriptions(List<String> weather_descriptions) { this.weatherDescriptions = weather_descriptions; }

        public int getFeelslike() { return feelslike; }
        public void setFeelslike(int feelslike) { this.feelslike = feelslike; }
    }
}