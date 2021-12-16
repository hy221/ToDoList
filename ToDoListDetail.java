package com.applicationcommunity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class ToDoListDetail extends AppCompatActivity {

    private ToDoListDB helper = null;
    private String keyvalue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_detail);

        EditText editText_title = findViewById(R.id.editText_title);
        ImageButton button_done = findViewById(R.id.button_done);
        ImageButton button_cal = findViewById(R.id.button_notification);
        ImageButton button_time = findViewById(R.id.Button_Time);
        TextView textview_cal = findViewById(R.id.textView_cal);
        TextView textview_time = findViewById(R.id.textView_time);
        TextView textview_detail = findViewById(R.id.textView_detail);

        //SQLiteOpenHelperクラスのサブクラスをインスタンス化
        helper = new ToDoListDB(this);

        //doneボタン押下時の処理
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //値を取得
                String title = editText_title.getText().toString();     //タイトル
                String date = textview_cal.getText().toString();        //日付
                String time = textview_time.getText().toString();       //時刻
                String detail = textview_detail.getText().toString();   //詳細

                //新規作成
                if(keyvalue == null) {
                    // 今日日付を取得
                    final CharSequence nowdate = android.text.format.DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()); //今日の日付
                    keyvalue = nowdate.toString();
                    //DB登録
                    try (SQLiteDatabase db = helper.getWritableDatabase()){
                        ContentValues cv = new ContentValues();
                        cv.put("now",keyvalue);
                        cv.put("title",title);
                        cv.put("date",date);
                        cv.put("time",time);
                        cv.put("detail",detail);
                        //db.insertWithOnConflict("todolist",null,cv,SQLiteDatabase.CONFLICT_REPLACE);
                        db.insert("todolist",null,cv);
                    }
                    //DB更新
                }else{
                        String [] Key = {keyvalue};
                    try (SQLiteDatabase db = helper.getWritableDatabase()){
                        ContentValues cv = new ContentValues();
                        cv.put("now",keyvalue);
                        cv.put("title",title);
                        cv.put("date",date);
                        cv.put("time",time);
                        cv.put("detail",detail);
                        db.update("todolist",cv,"now=?",Key);
                    }
                }
                finish();
            }
        });

        Intent intent = getIntent();
        keyvalue = intent.getStringExtra("key");
        //メインアクティビティから受け取ったインテントに値があれば以下が実行される タップされたリストのkeyと一致する行にカーソルを移動させたのち、DBから値を取得。その後各欄に値を代入
        if(!(keyvalue == null)){
            String [] cols = {"title","date","detail","time","now"};
            String [] key = {intent.getStringExtra("key")};
            try(SQLiteDatabase db = helper.getReadableDatabase()){
                Cursor cs = db.query("todolist",cols,"now=?",key,null,null,null,null);

                if(cs.moveToFirst()){
                    editText_title.setText(cs.getString(0));
                    textview_cal.setText(cs.getString(1));
                    textview_detail.setText(cs.getString(2));
                    textview_time.setText(cs.getString(3));
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"nulllllll",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }

       /* Intent intent = getIntent();
        //メインアクティビティから受け取ったインテントに値があれば以下が実行される タップされたリストポジションID行目にカーソルを移動させたのち、DBから値を取得。その後タイトル欄に値を代入
        //※リストを並べ替えるとこの部分は機能しなくなるので要検討
        if(!(-1==intent.getIntExtra("keyid",-1))){  //defaultvalueはとりあえず-1にしとく
            String [] cols = {"title"};
            try(SQLiteDatabase db = helper.getReadableDatabase()){
                Cursor cs = db.query("todolist",cols,null,null,null,null,null,null);

                if(cs.moveToFirst()){
                    cs.moveToPosition(intent.getIntExtra("keyid",-1));
                    editText_title.setText(cs.getString(0));
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"nulllllll",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }*/


        //カレンダーボタンクリック処理
        button_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment c_dialog = new Calendar_Dialog();
                c_dialog.show(getSupportFragmentManager(),"Cal_dialog");
            }
        });
        //タイマーボタンクリック処理
        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment t_dialog = new Time_Dialog();
                t_dialog.show(getSupportFragmentManager(),"Time_dialog");
            }
        });

    }


}
