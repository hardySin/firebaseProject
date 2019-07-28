package com.example.d3ll.firebaseproject.utility_class;

import android.app.ProgressDialog;
import android.content.Context;

public class progressBuilder {

    private Context context;
    private ProgressDialog progressDialog;
    public progressBuilder(Context context)
    {
        this.context=context;
    }

    public void create_Dialog(String title,String message)
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    public void close_dialog()
    {
        progressDialog.dismiss();
    }

}
