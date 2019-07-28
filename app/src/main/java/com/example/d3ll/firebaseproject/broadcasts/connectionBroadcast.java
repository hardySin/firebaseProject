package com.example.d3ll.firebaseproject.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.d3ll.firebaseproject.HolderPage;
import com.example.d3ll.firebaseproject.NoInternet;
import com.google.android.material.snackbar.Snackbar;

public class connectionBroadcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
    if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
    {
      boolean noconnection=  intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
      if(noconnection)
      {
          Intent i=new Intent(context, NoInternet.class);
          context.startActivity(i);
      }

     }
    }
}
