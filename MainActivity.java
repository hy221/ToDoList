package com.applicationcommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ToDoListDB helper = null;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button_new = findViewById(R.id.button_new);

        //＋ボタンクリック処理
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),com.applicationcommunity.ToDoListDetail.class);
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onStart(){
        super.onStart();

        ListView listview = findViewById(R.id.listview);
        ArrayList<String> titleList = new ArrayList<>();
        //ListViewに値をセットするためのアダプター
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titleList);
        listview.setAdapter(adapter);

        String [] cols = {"title"};
        helper = new ToDoListDB(this);

        try(SQLiteDatabase db = helper.getReadableDatabase()){ 
            Cursor cs = db.query("todolist",cols,null,null,null,null,null,null);
            boolean bo = cs.moveToFirst();
            while(bo){
                adapter.add(cs.getString(0));
                adapter.notifyDataSetChanged();
                bo = cs.moveToNext();
            }
        }
    }
}