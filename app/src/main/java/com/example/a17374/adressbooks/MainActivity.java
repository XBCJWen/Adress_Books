package com.example.a17374.adressbooks;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<Person> persons;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    lv.setAdapter(new MyAdapter());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);
        Button btnput=findViewById(R.id.btn_put);
        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,Putover.class);
            startActivity(intent);
            }
        });

        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                AddData();
                getPersons();
                if (persons.size()>0) {
                    handler.sendEmptyMessage(100);
                }
            };
        }.start();
    }

            private void AddData() {
                PersonDao2 dao = new PersonDao2(this);
                long number = 885900000;
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    dao.add("wangwu" + i, Long.toString(number + i), random.nextInt(5000));
                }
            }


           @RequiresApi(api = Build.VERSION_CODES.O)
            private void getPersons() {
                String path = "content://cn.itcast.db.personprovider/query";
                Uri uri = Uri.parse(path);
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null);
                persons = new ArrayList<Person>();
                if (cursor == null) {
                    return;
                }
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String number = cursor.getString(cursor.getColumnIndex("number"));
                    Person p = new Person(id, name, number);
                    persons.add(p);

                }
                cursor.close();
            }

          private class MyAdapter extends BaseAdapter {

                @Override
                public int getCount() {

                    return persons.size();
                }

                public Object getItem(int position) {
                    return persons.get(position);

                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    Person person = persons.get(position);
                    View view = View.inflate(MainActivity.this, R.layout.activity_lv, null);
                    TextView tv_name = (TextView) view.findViewById(R.id.name);
                    tv_name.setText("姓名：" + person.getName());
                    TextView tv_phone = (TextView) view.findViewById(R.id.moble);
                    tv_phone.setText("电话：" + person.getNumber());
                    return view;

                }
            }
}