package com.example.liranyehudar.emojimemorygame;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    static final int RESULT_REQUEST = 1;
    static final int MAX_ROWS = 10;

    private  String[] menuArr = {"Easy - 2x2","Medium - 4x4","Hard - 6x6","Table Of Records","View Map Records"};
    private  int []   levelArr= {2,4,6}; //2x2 , 4x4, 6x6 grid size
    private  int []   timer = {30000,45000,60000}; // millsecond for timer
    private  int []   getPointsFromOneMatching = {20,40,60}; //by the level,get point to final result if there is 2 card matching.
    private  String name;
    private  String age;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        db = new DBHandler(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");

        TextView txt = findViewById(R.id.txt_view_welcome);
        txt.setText("Hey "+name+", "+age+" years old");

        ListView lstView = findViewById(R.id.listView);
        ArrayAdapter<String> adapterArr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuArr);
        lstView.setAdapter(adapterArr);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 3){
                    return; // will add here fregment table of contents
                }

                if(position == 4){
                    return; // will add here fregment map
                }
                // move to the game activity
                Intent i = new Intent(getApplicationContext(),GameActivity.class);
                i.putExtra("row",levelArr[position]);
                i.putExtra("col",levelArr[position]);
                i.putExtra("name",name);
                i.putExtra("time",timer[position]);
                i.putExtra("point",getPointsFromOneMatching[position]);
                startActivityForResult(i,RESULT_REQUEST);

            }
        });
    }
    // get result from game
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == RESULT_REQUEST) {
                if (resultCode == RESULT_OK) {
                    checkResult(data.getStringExtra("result"));
                }
        }
    }

    public boolean checkResult(String result) {
        int res = Integer.parseInt(result);
        int currentAge = Integer.parseInt(age);
        Cursor data = db.getAllData();
        Cursor cur = db.findMinByResult();
        int minData = cur.getCount();
        int dataCount = data.getCount();
        if (dataCount < MAX_ROWS)
            db.insertData(name, currentAge, res);
        else {
            cur.moveToFirst();
            String s = cur.getString(1);
            int minInTable = Integer.parseInt(cur.getString(1));
            if (res > minInTable) {
                db.updateData(cur.getString(0), name, currentAge, res);
                Toast.makeText(this, "updated" + result, Toast.LENGTH_LONG).show();
            } else
                return false;
        }
        return true;
    }
}
