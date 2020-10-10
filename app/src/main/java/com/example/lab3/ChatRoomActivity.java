package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    MyListAdapter myAdapter;
    ListView listView;
    ImageView imgsend, imgreceive;
    TextView tv9;
    Button btnsend, btnreceive;
    EditText etmessage;
    private ArrayList<Msg> elements = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        listView = findViewById(R.id.lv);
        btnsend = findViewById(R.id.btnsend);
        btnreceive = findViewById(R.id.btnreceive);
        etmessage = findViewById(R.id.etmessage);
        initMsg();
        listView.setAdapter(myAdapter = new MyListAdapter());
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("A title").setMessage("My message");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {
                                                alertDialogBuilder.setPositiveButton("positive", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface click, int e) {
                                                        elements.remove(position);
                                                        myAdapter.notifyDataSetChanged();
                                                    }
                                                }).setNegativeButton("negative", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface click, int arg) {
                                                    }
                                                });
                                                alertDialogBuilder.create().show();
                                            }
                                        }

        );

        btnsend.setOnClickListener((v) -> {
            String message = etmessage.getText().toString();
            Msg msg=new Msg(message,0);
            elements.add(msg);
            myAdapter.notifyDataSetChanged();
        });
        btnreceive.setOnClickListener((v) -> {
            String message = etmessage.getText().toString();
            Msg msg=new Msg(message,1);
            elements.add(msg);
            myAdapter.notifyDataSetChanged();
        });

    }
    public void initMsg(){
        Msg msg_1 = new Msg("Hello", 0);
        elements.add(msg_1);
        Msg msg_2 = new Msg("How are you?", 1);
        elements.add(msg_2);
        Msg msg_3 = new Msg("I am fine! Thank you!", 0);
        elements.add(msg_3);
    }
    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Msg getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            Msg msg = getItem(position);

            View view;

            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.mylist, parent, false );
                viewHolder = new ViewHolder();

                viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);

                viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);

                viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);

                viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);

                view.setTag(viewHolder);

            } else {

                view = convertView;

                viewHolder = (ViewHolder) view.getTag();

            }

            if (msg.getType() == 0) {

                viewHolder.leftLayout.setVisibility(View.VISIBLE);

                viewHolder.rightLayout.setVisibility(View.GONE);

                viewHolder.leftMsg.setText(msg.getContent());

            } else if (msg.getType() == 1) {

                viewHolder.rightLayout.setVisibility(View.VISIBLE);

                viewHolder.leftLayout.setVisibility(View.GONE);

                viewHolder.rightMsg.setText(msg.getContent());

            }

            return view;
        }
    }
}
