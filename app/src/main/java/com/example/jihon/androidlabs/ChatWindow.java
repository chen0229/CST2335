package com.example.jihon.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.jihon.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {

    private static final String ACTIVITY_NAME = "ChatWindow";
    ListView chatView;
    EditText chatEdit;
    Button sendButton;

    ArrayList<String> list = new ArrayList<String>();
    ChatDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        chatView = (ListView) findViewById(R.id.chatview);
        chatEdit = (EditText) findViewById(R.id.chatEdit);
        sendButton = (Button) findViewById(R.id.sendButton);


        //create a ChatDatabaseHelper object
        dbHelper = new ChatDatabaseHelper(this);
        //get a writeable database and stores that as an instance variable
        db = dbHelper.getWritableDatabase();

        messageAdapter= new ChatAdapter(this);
        chatView.setAdapter(messageAdapter);


            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String chatContent = chatEdit.getText().toString();
                    list.add(chatContent);

                    //this restarts the process of getCount()& getView()
                    messageAdapter.notifyDataSetChanged();
                    //clear the textView and ready for a new message
                    chatEdit.setText("");

                    //insert rows into the database
                    ContentValues cValues = new ContentValues();
                    cValues.put("MESSAGE", chatContent);

                    //don't need to insert KEY_ID, because it is autoincrement
                    db.insert(TABLE_NAME, "", cValues);
                }
            });

            //execute a query for the existing chat messages
            //Cursor contains rows from the rawQuery, similar to an iterator
            cursor = db.rawQuery("select * from " + TABLE_NAME, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(
                        cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                list.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());
            //print out the name of columns
            for(int i=0; i<cursor.getColumnCount();i++){
                System.out.println(cursor.getColumnName(i));
            }


}

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    //close super() and also clase the database opened in onCreate()
    @Override
    protected void onDestroy(){
        super.onDestroy();

        db.close();
        cursor.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

        }

    //inner class of ChatWindow
    private class ChatAdapter extends ArrayAdapter<String>{
        //constructor
        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        //return the numberr of rows in listView
        @Override
        public int getCount(){
            return list.size();
        }

        //returns the item to show in the list at the specified position
        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        //return the layout that will be positioned at the specified row in the list
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //set the layout of a row, result is a container that has objects from the layout
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_Text);
            message.setText(getItem(position));//get the string at position
            return result;

        }

        //return the database id of the item at position, not using SQL yet
        public long getID(int position){
            return position;
        }
    }



}
