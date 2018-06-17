package com.example.liranyehudar.emojimemorygame;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class EntryActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtAge;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_entry);

        bindUI();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String age  = edtAge.getText().toString().trim();

                boolean validName = isValidName(name);
                boolean validAge  = isValidAge(age);

                // check if the name and age are invalid
                if(!validName && !validAge){
                        showDialogMessage("Your name and your age are invalid");
                        return;
                }

                // check if the name is invalid
                if(!validName){
                    showDialogMessage("Your name is invalid");
                    return;
                }

                if(!validAge){
                    showDialogMessage("Your age is invalid");
                    return;
                }

                Intent i = new Intent(getBaseContext(),MainActivity.class);
                i.putExtra("name",name);
                i.putExtra("age",age);
                startActivity(i);
            }
        });

    }

    public void showDialogMessage(String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EntryActivity.this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Error Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }

    public boolean isValidName(String name){
        return !(name.equals(""));
    }

    public boolean isValidAge(String age){
        double  doubleAge = 0;
        try{
            doubleAge = Double.parseDouble(age);
        }
        catch (Exception e){
            return false;
        }
        return doubleAge > 0;
    }

    public void bindUI(){
        edtName = findViewById(R.id.edit_name);
        edtAge  = findViewById(R.id.edit_age);
        btnPlay = findViewById(R.id.btn_play);
    }
}
