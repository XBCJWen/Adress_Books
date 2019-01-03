package com.example.a17374.adressbooks;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
                Uri  uri=Uri.parse("content://cn.itcast.db.personprovider/insert");
                PersonDBProvider personDBProvider=new PersonDBProvider();
                ContentValues values=new ContentValues();
                values.put("name",String.valueOf(name.getText().toString().trim()));
                values.put("number",String.valueOf(phone.getText().toString().trim()));

                personDBProvider.insert(uri,values);
            }
        });

    }
}
