package com.applicationcommunity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class ToDoListDetail extends AppCompatActivity {

    private ToDoListDB helper = null;

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
                // 今日日付を取得
                final CharSequence nowdate = android.text.format.DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance());

                //値を取得
                String now = nowdate.toString();                         //今日の日付
                String title = editText_title.getText().toString();     //タイトル
                String date = textview_cal.getText().toString();        //日付
                String time = textview_time.getText().toString();       //時刻
                String detail = textview_detail.getText().toString();   //詳細

                try (SQLiteDatabase db = helper.getWritableDatabase()){
                    ContentValues cv = new ContentValues();
                    cv.put("now",now);
                    cv.put("title",title);
                    cv.put("date",date);
                    cv.put("time",time);
                    cv.put("detail",detail);
                    db.insert("todolist",null,cv);
                }
                finish();
            }
        });
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
