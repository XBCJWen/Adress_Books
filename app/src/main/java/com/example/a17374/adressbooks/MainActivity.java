package com.example.a17374.adressbooks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView lv;
    private List<Person> persons;
    private Button  search;
    private  Button clrean;
    private  EditText e_search;

    private MyAdapter adapter;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    adapter= new MyAdapter();
                    lv.setAdapter(adapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, 1);
        lv =  findViewById(R.id.listview);
        Button btnput=findViewById(R.id.btn_put);
        search=findViewById(R.id.btn_search);
        clrean=findViewById(R.id.btn_sc);
        e_search=findViewById(R.id.entTxt_search);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
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

                getPersons();
                if (persons.size()>0) {
                    handler.sendEmptyMessage(100);
                }
            };
        }.start();
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

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    final Person person = persons.get(position);
                    View view = View.inflate(MainActivity.this, R.layout.activity_lv, null);
                    Button lv_del=(Button) view.findViewById(R.id.btn_sc);
                    Button lv_query=(Button) view.findViewById(R.id.btn_xg);
                    Button lv_phone=(Button) view.findViewById(R.id.btn_phome);

                    final TextView tv_name = (TextView) view.findViewById(R.id.name);
                    final TextView tv_phone = (TextView) view.findViewById(R.id.phone);
                    lv_phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            String number= person.getName();
                            Uri uri=Uri.parse("tel:"+number);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    lv_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri=Uri.parse("content://cn.itcast.db.personprovider/delete");
                            int id=person.getId();
                            getContentResolver().delete(uri,"id=?",new String[]{id+""});
                            Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                          persons.remove(position);
                          adapter.notifyDataSetChanged();
                        }
                    });
                    lv_query.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phone=person.getNumber();
                            String name=person.getName();
                            Intent intent=new Intent(MainActivity.this,activity_query.class);
                            Log.d("name",name);
                            intent.putExtra("name",name);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                        }
                    });

                    tv_name.setText("姓名：" + person.getName());

                    tv_phone.setText("电话：" + person.getNumber());
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

                            String search=String.valueOf(e_search.getText().toString().trim());
                            Uri uri=Uri.parse("content://cn.itcast.db.personprovider/query");
                            Cursor cursor=getContentResolver().query(uri,null,"name=?", new String[]{search},null);
                            if (cursor !=null){
                                while(cursor.moveToNext()) { Toast.makeText(MainActivity.this, "df", Toast.LENGTH_SHORT).show();
                                    String name=cursor.getString(cursor.getColumnIndex("name"));
                                    String phone=cursor.getString(cursor.getColumnIndex("phone"));
                                }
                                adapter.notifyDataSetChanged();
                                cursor.close();
                            }else {
                                Toast.makeText(MainActivity.this, "联系人不存在", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    return view;
                }
            }
}