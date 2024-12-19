package com.example.beanydrinks.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

public class CheckConnection {
    public static boolean haveNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        Network activeNetwork = cm.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }

        NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities == null) {
            return false;
        }

        // Kiểm tra kết nối Wifi hoặc Mobile
        boolean haveConnectedWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        boolean haveConnectedMobile = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

        return haveConnectedWifi || haveConnectedMobile;
    }
    public static void ShowToast_Short(Context context, String thongbao){
        Toast.makeText(context, thongbao, Toast.LENGTH_SHORT).show();
    }
}
