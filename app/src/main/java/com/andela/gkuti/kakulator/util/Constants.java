package com.andela.gkuti.kakulator.util;

public enum Constants {

    API_URL("http://openexchangerates.org/api/latest.json?"),
    APP_TOKEN("app_id=a4c2366df7e347a4a5b5d0594bb8e41f"),
    DATA_FILENAME("Kakulator");
    String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
