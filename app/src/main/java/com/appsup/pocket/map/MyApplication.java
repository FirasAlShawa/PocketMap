package com.appsup.pocket.map;


import android.app.Application;

import com.huawei.agconnect.AGConnectOptionsBuilder;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.maps.MapsInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String apiKey = new AGConnectOptionsBuilder().build(MyApplication.this).getString("client/api_key");

        // Set the API key.
        MapsInitializer.setApiKey(apiKey);

        // Initialize the SDK.
        MapsInitializer.initialize(this);
    }
}
