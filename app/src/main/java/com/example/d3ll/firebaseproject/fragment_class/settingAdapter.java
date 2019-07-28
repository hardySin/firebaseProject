package com.example.d3ll.firebaseproject.fragment_class;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d3ll.firebaseproject.Model_class.settingPojo;
import com.example.d3ll.firebaseproject.R;
import com.example.d3ll.firebaseproject.utility_class.progressBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.List;

import static com.example.d3ll.firebaseproject.fragment_class.profile_setting.mdatabase;
import static com.example.d3ll.firebaseproject.fragment_class.profile_setting.sheetBehavior;


public class settingAdapter extends RecyclerView.Adapter<settingAdapter.myholder>
{
    private List<settingPojo> settingPojos;
    private View view;

    public settingAdapter(List<settingPojo> settingPojos,View view)
    {
        this.settingPojos=settingPojos;
        this.view=view;
    }
    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_part,parent,false);
         return new myholder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        Resources resources= holder.edit.getContext().getResources();

        holder.headTitle.setText(settingPojos.get(position).gettitleKey());
        holder.User_info.setText(settingPojos.get(position).getUser_info());

        TextView textView = (TextView)view.findViewById(R.id.suggestion);
        TextInputLayout textInputLayout= (TextInputLayout) view.findViewById(R.id.textInputLayout);
        TextInputEditText textInputEditText= (TextInputEditText) view.findViewById(R.id.update_name);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        Button save = (Button) view.findViewById(R.id.save);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i)
                {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        ((AppCompatActivity)textView.getContext()).getCurrentFocus();
                        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        InputMethodManager imm1 = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        ((AppCompatActivity)textView.getContext()).getCurrentFocus();
                        imm1.hideSoftInputFromWindow(view.getWindowToken(),0);


                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:



                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                 if(position==0)
                {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    textView.setText("Enter your "+settingPojos.get(position).gettitleKey());
                    textInputLayout.setHint(settingPojos.get(position).gettitleKey());
                    textInputEditText.setText(settingPojos.get(position).getUser_info());

                    textInputEditText.setSelection(0,textInputEditText.getText().length());
                     cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                        }
                    });
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBuilder builder =new progressBuilder(textInputEditText.getContext());
                            builder.create_Dialog("Please Wait","While your Status is updating");
                            mdatabase.child("Status").setValue(textInputEditText.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            builder.close_dialog();
                                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                            Snackbar snackbar =Snackbar.make(view.findViewById(R.id.mycoordinator),"Status Updated",Snackbar.LENGTH_LONG);
                                            View view = snackbar.getView();
                                            snackbar.setActionTextColor(Color.RED);
                                            final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                            snackbar.show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar snackbar =Snackbar.make(view.findViewById(R.id.mycoordinator),"Got error",Snackbar.LENGTH_LONG)

                                                     .setAction("Setting", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v)
                                                        {

                                                        }
                                                    });
                                            View view = snackbar.getView();
                                            snackbar.setActionTextColor(Color.RED);
                                            final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                            snackbar.show();
                                        }
                                    });
                        }
                    });
                }
                else
                {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    textView.setText("Enter your "+settingPojos.get(position).gettitleKey());
                    textInputLayout.setHint(settingPojos.get(position).gettitleKey());
                    textInputEditText.setText(settingPojos.get(position).getUser_info());
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    });
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBuilder builder =new progressBuilder(textInputEditText.getContext());
                            builder.create_Dialog("Please Wait","While your name is updating");
                            mdatabase.child("Name").setValue(textInputEditText.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            builder.close_dialog();
                                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                            Snackbar snackbar =Snackbar.make(view.findViewById(R.id.mycoordinator),"Name Updated",Snackbar.LENGTH_LONG);
                                            View view = snackbar.getView();
                                            snackbar.setActionTextColor(Color.RED);
                                            final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                            snackbar.show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar snackbar =Snackbar.make(view.findViewById(R.id.mycoordinator),"Got error",Snackbar.LENGTH_LONG)

                                                    .setAction("Setting", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v)
                                                        {

                                                        }
                                                    });
                                            View view = snackbar.getView();
                                            snackbar.setActionTextColor(Color.RED);
                                            final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                            snackbar.show();
                                        }
                                    });
                        }
                    });
                }
             }
        });

          if(position==0)
        {
            Bitmap bitmap=BitmapFactory.decodeResource(resources,R.drawable.status);
            holder.icon.setImageBitmap(bitmap);
         }
        else
        {
            Bitmap bitmap=BitmapFactory.decodeResource(resources,R.drawable.request);
            holder.icon.setImageBitmap(bitmap);
         }
    }

    @Override
    public int getItemCount() {
        return settingPojos.size();
    }

    public class myholder extends RecyclerView.ViewHolder
    {
             TextView headTitle,User_info;
            ImageView edit,icon;
            TextView suugestion;
                          public myholder(@NonNull View itemView) {
            super(itemView);
            headTitle =(TextView)itemView.findViewById(R.id.head_title);
            User_info=(TextView)itemView.findViewById(R.id.user_info);
            edit=(ImageView)itemView.findViewById(R.id.edit);
            icon=(ImageView)itemView.findViewById(R.id.titleIcon);


        }
    }
}
