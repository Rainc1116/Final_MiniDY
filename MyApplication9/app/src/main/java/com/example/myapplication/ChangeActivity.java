package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeActivity extends AppCompatActivity {
    EditText name;
    EditText id;
    Button b1;
    String n1;
    String i;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_id);
        name = findViewById(R.id.editText);
        id = findViewById(R.id.editText2);
        b1 = findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n1=name.getText().toString();
                i=id.getText().toString();
                Intent intent = new Intent(ChangeActivity.this,MainActivity.class);
                intent.putExtra("cname",n1);
                intent.putExtra("cid",i);
                startActivity(intent);
            }
        });
    }

}
