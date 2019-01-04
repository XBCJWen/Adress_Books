package com.example.a17374.adressbooks;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_query extends AppCompatActivity {
        private EditText tv_name;
        private  EditText tv_phone;
        private Button btn_query;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        tv_name=findViewById(R.id.query_name);
        tv_phone=findViewById(R.id.query_phone);
        btn_query=findViewById(R.id.query_btn_over);
        Intent intent=getIntent();
       String name=intent.getStringExtra("name");
        String phone=intent.getStringExtra("phone");
        tv_name.setText(name);
        tv_phone.setText(phone);

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Uri uri=Uri.parse("content://cn.itcast.db.personprovider/update");
                ContentValues values=new ContentValues();
                String name=String.valueOf(tv_name.getText().toString().trim());
                String phone=String.valueOf(tv_phone.getText().toString().trim());
                values.put("name",name);
                values.put("number",phone);
                getContentResolver().update(uri,values,null,null);
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(activity_query.this,MainActivity.class);
                startActivity(intent1);

            }
        });

    }
}
