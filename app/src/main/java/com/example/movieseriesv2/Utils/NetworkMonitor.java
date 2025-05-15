package com.example.movieseriesv2.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

public class NetworkMonitor extends LiveData<Boolean> {
    private final Context context;

    public NetworkMonitor(Context context) {
        this.context = context;
        checkNetwork();
    }

    private void checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        postValue(networkInfo != null && networkInfo.isConnected());
    }

    // (You can enhance this by registering a NetworkCallback for real-time)
}
