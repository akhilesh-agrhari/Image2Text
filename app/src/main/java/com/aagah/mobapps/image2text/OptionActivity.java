package com.aagah.mobapps.image2text;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {

    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Log.e("optionActivity: ","whileloop");
        Intent intent = getIntent();
        String string[] = intent.getStringArrayExtra("strings");

        textView = findViewById(R.id.textView);
        textView.setText(string[0]);
        int i =1;
        while(!string[i].equals("EOS"))
        {
            Log.e("whileloop : ",""+i);
            textView.append(string[i]);
            textView.append("\n");
            i++;
        }
        Log.e("whileloop : ","END");
    }
}
