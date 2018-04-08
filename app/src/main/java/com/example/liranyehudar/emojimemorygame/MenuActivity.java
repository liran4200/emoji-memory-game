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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");

        TextView txt = findViewById(R.id.txt_view_welcome);
        txt.setText(name+" welcome, "+age+" years old");

        ListView lstView = findViewById(R.id.listView);
        ArrayAdapter<String> adapterArr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuArr);
        lstView.setAdapter(adapterArr);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // move to the next activity using intent by the choosen
                Toast.makeText(MenuActivity.this, menuArr[position],Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),GameActivity.class);
                i.putExtra("row",levelArr[position]);
                i.putExtra("col",levelArr[position]);
                startActivity(i);
            }
        });
    }
}
