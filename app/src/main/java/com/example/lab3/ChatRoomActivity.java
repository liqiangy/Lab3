package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    SQLiteDatabase db;
    private ArrayList<Msg> elements = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        listView = findViewById(R.id.lv);
        btnsend = findViewById(R.id.btnsend);
        btnreceive = findViewById(R.id.btnreceive);
        etmessage = findViewById(R.id.etmessage);
        listView.setAdapter(myAdapter = new MyListAdapter());
        MyOpener myopener = new MyOpener(this);
        loadDataFromDatabase();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.ask);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {
                                                Msg m=elements.get(position);
                                                alertDialogBuilder.setMessage("select row is "+position+"\nselect database id is " +m.getId()+ " "  + "\n");
                                                alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface click, int e) {
                                                        myopener.DeleteMsg(m);
                                                        elements.remove(position);
                                                        myAdapter.notifyDataSetChanged();
                                                    }
                                                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface click, int arg) {
                                                    }
                                                });
                                                alertDialogBuilder.create().show();
                                            }
                                        }

        );

        btnsend.setOnClickListener((v) -> {
            db = myopener.getWritableDatabase();
            String message = etmessage.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MSG, message);
            newRowValues.put(MyOpener.COL_TYPE, 0);
            long newRowId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Msg msg = new Msg(message, newRowId,0);
            Cursor c = db.rawQuery("SELECT * FROM "+MyOpener.TABLE_NAME+" WHERE _id = ?", new String[] {String.valueOf(newRowId)});
            printCursor(c,2);
            //msg.setSent(true);
            elements.add(msg);
            myAdapter.notifyDataSetChanged();
            etmessage.setText("");
        });
        btnreceive.setOnClickListener((v) -> {
            db = myopener.getWritableDatabase();
            String message = etmessage.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MSG, message);
            newRowValues.put(MyOpener.COL_TYPE, 1);
            long newRowId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            Msg msg = new Msg(message, newRowId,1);
            Cursor c = db.rawQuery("SELECT * FROM "+MyOpener.TABLE_NAME+" WHERE _id = ?", new String[] {String.valueOf(newRowId)});
            printCursor(c,2);
            //msg.setRecieved(true);
            elements.add(msg);
            myAdapter.notifyDataSetChanged();
            etmessage.setText("");
        });
    }
    private void loadDataFromDatabase() {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String query = "SELECT * FROM "+ MyOpener.TABLE_NAME;
        Cursor results = db.rawQuery(query,null);
        results.getCount();
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);
        int messageColIndex = results.getColumnIndex(MyOpener.COL_MSG);
        int typeColIndex=results.getColumnIndex(MyOpener.COL_TYPE);
        while (results.moveToNext()) {
            long id = results.getLong(idColIndex);
            String message = results.getString(messageColIndex);
            int type=results.getInt(typeColIndex);
            elements.add(new Msg(message, id,type));
            myAdapter.notifyDataSetChanged();
            etmessage.setText("");
        }
        printCursor(results,2);
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

            if (msg.getType()==1) {

                viewHolder.leftLayout.setVisibility(View.VISIBLE);

                viewHolder.rightLayout.setVisibility(View.GONE);

                viewHolder.leftMsg.setText(msg.getContent());

            } else if (msg.getType()==0) {

                viewHolder.rightLayout.setVisibility(View.VISIBLE);

                viewHolder.leftLayout.setVisibility(View.GONE);

                viewHolder.rightMsg.setText(msg.getContent());

            }

            return view;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void printCursor( Cursor c, int version){
        Log.d("version number", Integer.toString(db.getVersion()) );
        Log.d("column number", Integer.toString(c.getColumnIndex(MyOpener.COL_MSG)) );
        Log.d("column name", c.getColumnName(c.getColumnIndex(MyOpener.COL_MSG)));
        Log.d("row number", Integer.toString(c.getCount()));
        if (c.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                int columnsQty = c.getColumnCount();
                for (int idx=0; idx<columnsQty; ++idx) {
                    sb.append(c.getString(idx));
                    if (idx < columnsQty - 1)
                        sb.append("; ");
                }
                Log.d("each",sb.toString());
            } while (c.moveToNext());
        }
    }
}
