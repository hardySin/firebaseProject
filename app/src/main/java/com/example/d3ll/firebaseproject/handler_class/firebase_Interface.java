package com.example.d3ll.firebaseproject.handler_class;

import android.content.Context;
import android.view.View;

import com.example.d3ll.firebaseproject.Model_class.createAcc_pojo;
import com.example.d3ll.firebaseproject.Model_class.settingPojo;

import java.util.ArrayList;
import java.util.List;

public interface firebase_Interface
{
  public void  CreateEmailAndPassowrd(createAcc_pojo acc_pojo, Context context);

  public boolean userSignIn();


 public void LoginAccount(String email, String password, Context context);

    public void signOut();


}
