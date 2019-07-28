package com.example.d3ll.firebaseproject.utility_class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetConnection {

    private Context context;
    private ConnectivityManager connectivityManager;
    public InternetConnection(Context context)
    {
        this.context=context;
    }

    public boolean check()
    {
        connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
