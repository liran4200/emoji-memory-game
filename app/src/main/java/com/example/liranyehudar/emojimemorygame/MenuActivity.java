package com.example.liranyehudar.emojimemorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private  String[] menuArr = {"Easy","Medium","Hard"};
    private  int []   levelArr= {2,4,6}; //2x2 , 4x4, 6x6 grid size
    private  int []   timer = {30000,45000,60000}; // millsecond for timer
    private  String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");

        TextView txt = findViewById(R.id.txt_view_welcome);
        txt.setText("Hey "+name+", "+age+" years old");

        ListView lstView = findViewById(R.id.listView);
        ArrayAdapter<String> adapterArr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuArr);
        lstView.setAdapter(adapterArr);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // move to the game activity
                Intent i = new Intent(getApplicationContext(),GameActivity.class);
                i.putExtra("row",levelArr[position]);
                i.putExtra("col",levelArr[position]);
                i.putExtra("name",name);
                i.putExtra("time",timer[position]);
                startActivity(i);
            }
        });
    }
}
