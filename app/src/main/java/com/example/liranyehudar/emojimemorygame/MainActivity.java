package com.example.liranyehudar.emojimemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity  extends AppCompatActivity {

    private SectionPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    private  String name;
    private  String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Intent intent = getIntent();
            name = intent.getStringExtra("name");
            age = intent.getStringExtra("age");

            TextView details = findViewById(R.id.text_view_details);
            details.setText(name + ", " + age);


            mViewPager = (ViewPager) findViewById(R.id.container);
            setupViewAdapter(mViewPager);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

        }
        catch (Exception e){
            Log.e("boom",e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void setupViewAdapter(ViewPager viewPager) {
        FragmentGameMenu fragInfo = new FragmentGameMenu();
        mSectionsPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mSectionsPageAdapter.addFragment(fragInfo,"Game Menu");
        mSectionsPageAdapter.addFragment(new FragmentRecords(),"Table Of Records");
        mSectionsPageAdapter.addFragment(new FragmentMapRecords(),"Map Records");
        Bundle bundle = new Bundle();

        bundle.putString("name", name );
        bundle.putString("age", age );
        fragInfo.setArguments(bundle);
        viewPager.setAdapter(mSectionsPageAdapter);
    }
}
