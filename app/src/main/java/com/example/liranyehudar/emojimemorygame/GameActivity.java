package com.example.liranyehudar.emojimemorygame;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private int numOfImages;
    private Integer[] frontImagesReferences; // refernceses to front images for this game
    private Integer[] backImagesReferences = {R.drawable.image1,R.drawable.image2,
                                              R.drawable.image3,R.drawable.image4,
                                              R.drawable.image5,R.drawable.image6,
                                              R.drawable.image7,R.drawable.image8,
                                              R.drawable.image9,R.drawable.image10,
                                              R.drawable.image11,R.drawable.image12,
                                              R.drawable.image13,R.drawable.image14,
                                              R.drawable.image15,R.drawable.image16,
                                              R.drawable.image17,R.drawable.image18};
    private Integer[] currentBackImagesReferences; // refernceses to back images for this game
    private ImageDetails[] backImagesDetails;

    private ImageDetails imageSelected1;
    private ImageDetails imageSelected2;
    private ImageView imageViewSelected1;
    private ImageView imageViewSelected2;

    private int frontImageId = R.drawable.question;
    private boolean isBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        int rowNum = intent.getIntExtra("row",-1);
        int colNum = intent.getIntExtra("col", -1);

        numOfImages = rowNum*colNum;
        frontImagesReferences = new Integer[numOfImages];
        currentBackImagesReferences = new Integer[numOfImages/2]; // num Of Images/2 - every image appear 2 times
        backImagesDetails = new ImageDetails[numOfImages];

        initCurrentBackImages();
        initFrontImages();
        initBackImagesDetails();
        shuffle();

        ConstraintLayout constLayout = findViewById(R.id.constraint_board);
        GridView myGrid = findViewById(R.id.gridview);
        int height = constLayout.getLayoutParams().height;
        int width = constLayout.getLayoutParams().width;

        myGrid.setNumColumns(rowNum);
        myGrid.setAdapter(new ImageAdapter(this,height,width,rowNum,colNum,frontImagesReferences));

        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ImageView img = (ImageView)v;
                checkBackImagesMatching(img,position);
            }
        });
    }

    public void checkBackImagesMatching(ImageView img,int position){
            if(isBusy)
                return;

            if(imageSelected1 == null) {
                imageSelected1 = backImagesDetails[position];
                if(!imageSelected1.isFlipped()) {
                    imageViewSelected1 = img;
                    imageViewSelected1.setImageResource(imageSelected1.getBackDrawableId());
                    imageSelected1.setFlipped(true);
                }
                else {
                    imageSelected1 = null;
                    return;
                }
            }
            if(imageSelected2 == null) {
                imageSelected2 = backImagesDetails[position];
                if (!imageSelected2.isFlipped()) {
                    imageViewSelected2 = img;
                    imageViewSelected2.setImageResource(imageSelected2.getBackDrawableId());
                    imageSelected2.setFlipped(true);
                } else {
                    imageSelected2 = null;
                    return;
                }

                // do in background check with delay;
                if (imageSelected1.equals(imageSelected2)) {
                    imageSelected1.setMatch(true);
                    imageSelected2.setMatch(true);
                    if (checkWinGame()) {
                        Toast.makeText(GameActivity.this, "You Win!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    imageSelected1 = null;
                    imageSelected2 = null;
                } else {
                    isBusy = true;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageSelected1.setFlipped(false);
                            imageSelected2.setFlipped(false);
                            imageViewSelected1.setImageResource(frontImageId);
                            imageViewSelected2.setImageResource(frontImageId);
                            imageSelected1 = null;
                            imageSelected2 = null;
                            isBusy = false;
                        }
                    },700);
                }
            }
    }

    public boolean checkWinGame(){
        for(int i = 0 ; i < backImagesDetails.length; i ++ ){
            if(!backImagesDetails[i].isMatch())
                return false;
        }
        return true;
    }

    public void shuffle(){
        List<ImageDetails> listBackImages = Arrays.asList(backImagesDetails);
        Collections.shuffle(listBackImages);
        backImagesDetails = (ImageDetails[]) listBackImages.toArray();
    }

    public void initBackImagesDetails(){
        int countCurrentBackImages = 0;
        for(int i = 0; i < backImagesDetails.length; i ++){
            if(countCurrentBackImages == currentBackImagesReferences.length)
                countCurrentBackImages = 0;
            backImagesDetails[i] = new ImageDetails(currentBackImagesReferences[countCurrentBackImages]);
            countCurrentBackImages++;
        }
    }
    
    public void initCurrentBackImages(){
        for(int i = 0; i < currentBackImagesReferences.length; i ++){
            currentBackImagesReferences[i] = backImagesReferences[i];
        }
    }

    // This function initialize refernces to front image by num of images.
    public void initFrontImages(){
        for(int i = 0 ; i < numOfImages; i ++)
            frontImagesReferences[i] = frontImageId;
    }
}
