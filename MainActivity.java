package com.applicationcommunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ToDoListDB helper = null;
    private int position = 0;
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

        ListView listView = findViewById(R.id.listview);
        String [] title_cols = {"title","date","detail","now"};
        helper = new ToDoListDB(this);

        //コールバックメソッドOnStart内に記述する？？？

        ArrayList<ListItem> data = new ArrayList<>();

        //DBに登録されている値(列指定)を全て取得し、ListViewに表示させる
        try(SQLiteDatabase db = helper.getReadableDatabase()){
            Cursor cs = db.query("todolist",title_cols,null,null,null,null,null,null);
            boolean boo = cs.moveToFirst();
            while(boo){
                //dbから取得した値をListItemオブジェクトに詰め替え
                ListItem item = new ListItem();
                item.setId((new Random()).nextLong());
                item.setTitle(cs.getString(0));
                item.setDate(cs.getString(1));
                item.setDetail(cs.getString(2));
                item.setKey(cs.getString(3));
                data.add(item);
                boo = cs.moveToNext();

            }
        }
        //ListViewに値をセットするためのアダプター
        ListAdapter adapter = new ListAdapter(this,data,R.layout.list_items);
        listView.setAdapter(adapter);

        //リストをタップしたときの処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //タップされた値を取得し、インテントにてToDoListDBクラスに送る
                Intent intent = new Intent(getApplicationContext(),com.applicationcommunity.ToDoListDetail.class);
                intent.putExtra("key",adapter.getItemKey(position));
                startActivity(intent);

                //テスト用
                //String test = adapter.getItemKey(position);
                //Toast toast = Toast.makeText(getApplicationContext(),),Toast.LENGTH_SHORT);
                //toast.show();
            }
        });


        //リスト長押し処理
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                setPos(position);
                alertCheck(adapter.getItemKey(position),data,adapter);
                Toast toast = Toast.makeText(getApplicationContext(),adapter.getItemKey(position),Toast.LENGTH_SHORT);
                toast.show();

               /* String [] key = {adapter.getItemKey(position)};
                try(SQLiteDatabase db = helper.getWritableDatabase()){
                    //dbから値を削除
                    db.delete("todolist","now=?",key);
                    //ArrayListからも削除する
                    data.remove(adapter.getItem(position));
                    //ListView更新
                    adapter.notifyDataSetChanged();
                    Toast toast = Toast.makeText(getApplicationContext(),"削除しました",Toast.LENGTH_SHORT);
                    toast.show();
                }*/
                //リストタップを検知させるにはfalseを指定。今回は長押しのみ検知させたいためtrueにしてる
                return true;
            }
        });

    }

    private void alertCheck(String keyval,ArrayList<ListItem> data,ListAdapter la){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("削除しますか？");
        alert.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String [] key = {keyval};
                try(SQLiteDatabase db = helper.getReadableDatabase()){
                    db.delete("todolist","now=?",key);
                    data.remove(la.getItem(getPos()));
                    la.notifyDataSetChanged();
                }
                Toast toast = Toast.makeText(getApplicationContext(),"削除しました",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        alert.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }

    private  void setPos(int pos){
        position = pos;
    }

    private int getPos(){
        return position;
    }


}