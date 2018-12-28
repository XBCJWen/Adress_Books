package com.example.a17374.adressbooks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class Putover extends AppCompatActivity {


    private EditText name, phone;
    private Button btn_over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putover);
        btn_over = findViewById(R.id.btn_over);
        name = findViewById(R.id.edtTxt_name);
        phone = findViewById(R.id.edtTxt_phone);


        btn_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersonDao2 personDao2=new PersonDao2((MainActivity) getApplicationContext());
                String n=String.valueOf(name.getText());
                String p=String.valueOf(phone.getText());
                Random random = new Random();
                personDao2.add(n,p,random.nextInt(5000) );

            finish();
            }
        });

    }
}
