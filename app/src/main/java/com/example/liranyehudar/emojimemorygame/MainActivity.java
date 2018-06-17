package com.example.liranyehudar.emojimemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity  extends AppCompatActivity implements UpdateData{

    private SectionPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    private  Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Intent intent = getIntent();
            player = (Player) intent.getSerializableExtra("Player");


            TextView details = findViewById(R.id.text_view_details);
            String detailsPlayer = player.getName() + ", " + player.getAge();
            details.setText(detailsPlayer);


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
        bundle.putSerializable("Player",player);
        fragInfo.setArguments(bundle);
        viewPager.setAdapter(mSectionsPageAdapter);
    }

    @Override
    public void onUpdateData() {
        mSectionsPageAdapter.notifyDataSetChanged();
    }
}
