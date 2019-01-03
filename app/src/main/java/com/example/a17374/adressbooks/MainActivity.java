package com.example.a17374.adressbooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private ListView lv;
    private List<Person> persons;
    private Button  search;
    private  Button clrean;
    private  EditText e_search;
    private  PersonSQLiteOpenHelper helper;

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
        lv =  findViewById(R.id.listview);
        Button btnput=findViewById(R.id.btn_put);
        search=findViewById(R.id.btn_search);
        clrean=findViewById(R.id.btn_del);
        e_search=findViewById(R.id.entTxt_search);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"sdf",Toast.LENGTH_SHORT);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("通讯录");
                dialog.setMessage("确定要删除这个联系人吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                return false;
            }
        });



        clrean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_search.setText(null);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://cn.itcast.db.personprovider/query");

           PersonDBProvider p=new PersonDBProvider();
                p.query(uri,new String []{"name"},"name=?",null,null,null);
                ListView listView=findViewById(R.id.listview);
                MyAdapter myAdapter=new MyAdapter();
                listView.setAdapter(myAdapter);
            }
        });

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
                Intent intent=getIntent();
                intent.getData();
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
                    tv_name.setText("姓名：" + person.getNumber());
                    TextView tv_phone = (TextView) view.findViewById(R.id.phone);
                    tv_phone.setText("电话：" + person.getName());
                    return view;
                }
            }
}