package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {
    private Button button;
    String info;
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        editText=findViewById(R.id.editText3);
        textView=findViewById(R.id.textView3);
        button=findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info = editText.getText().toString();
                textView.setText(info);
                editText.setText("");
            }
        });
    }

}
