package com.example.d3ll.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class textspeech extends AppCompatActivity {

    private EditText words;
    private Button sayit;
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textspeech);

        words=(EditText)findViewById(R.id.words);
        sayit=(Button)findViewById(R.id.sayit);

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            if(status==TextToSpeech.SUCCESS)
            {
                   int result= textToSpeech.setLanguage(Locale.CANADA);

                    if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA)
                    {
                        Toast.makeText(textspeech.this,"Language Not Supported",Toast.LENGTH_LONG).show();
                    }
            }
            }
        });

        sayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private  void speak()
    {
         textToSpeech.speak(words.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech==null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
