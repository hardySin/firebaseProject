package com.example.d3ll.firebaseproject.utility_class;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertBuilder
{
    private Context context;
    private String title;
    private String message;
    private  AlertDialog.Builder builder;
    public AlertBuilder(Context context,String title,String message)
    {
        this.context=context;
        this.title=title;
        this.message=message;
     }

    public void alertCreater(String positive,DialogInterface.OnClickListener positivedialog,String negative,DialogInterface.OnClickListener negativedialog)
    {
        builder=new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive,positivedialog)
                .setNegativeButton(negative, negativedialog)
                .setCancelable(true)
                .show();

     }


}
