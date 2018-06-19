package com.example.liranyehudar.emojimemorygame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by liran yehudar on 4/10/2018.
 */

public class GameBoard {

    private ImageDetails[] backImagesDetails;
    private int numOfImages;
    private int countImages;

    private ImageDetails imageSelectedFirst;
    private ImageDetails imageSelectedSecond;
    private Stack<ImageDetails> gameImageDetails;

    public GameBoard(int numOfImages) {
        this.numOfImages = numOfImages;
        backImagesDetails = new ImageDetails[numOfImages];
        countImages = 0;
        imageSelectedFirst = null;
        imageSelectedSecond = null;
        gameImageDetails = new Stack<>();
    }

    /**
     *
     * @param firstOrSecond - 0 is the first image selected, 1 - is the second image selected,
     * @return false if first or second image has not selected or index invalid,else return true
     */
    public boolean isImageSelected(int firstOrSecond){
        ImageDetails img = selectedImage(firstOrSecond);
        return img != null;
    }

    public int getDrawableId(int firstOrSecond){
       ImageDetails img = selectedImage(firstOrSecond);
       if(img == null ) // invalid index
           return -1;
       return img.getBackDrawableId();
    }

    /**
     * This function change the state of first image selected.
     * @return false if image flipped, else return true;
     */
    public boolean flipFirst(){
        if(imageSelectedFirst.isFlipped()) {
            imageSelectedFirst = null;
            return true;
        }

        imageSelectedFirst.setFlipped(true);
        return false;
    }

    public boolean isSelectedImagesMatching(){
        if(imageSelectedFirst.equals(imageSelectedSecond)){
            imageSelectedFirst.setMatch(true);
            imageSelectedSecond.setMatch(true);
            gameImageDetails.push(imageSelectedFirst);
            gameImageDetails.push(imageSelectedSecond);
            imageSelectedFirst = null;
            imageSelectedSecond = null;
            return true;
        }
        return false;
    }

    public void flipBack(){
        ImageDetails imageDetails1, imageDetails2;

        if(!gameImageDetails.isEmpty())
        {
            imageDetails2 = gameImageDetails.pop();
            imageDetails1= gameImageDetails.pop();
            imageDetails1.setFlipped(false);
            imageDetails2.setFlipped(false);
            imageDetails1.setMatch(false);
            imageDetails2.setMatch(false);

        }
    }
    public void resetSelectedImages(){
        imageSelectedFirst.setFlipped(false);
        imageSelectedSecond.setFlipped(false);
        imageSelectedFirst = null;
        imageSelectedSecond = null;
    }

    /**
     * This function change the state of second image selected.
     * @return false if image flipped, else return true;
     */
    public boolean flipSecond(){
        if(imageSelectedSecond.isFlipped()) {
            imageSelectedSecond = null;
            return true;
        }

        imageSelectedSecond.setFlipped(true);
        return false;
    }

    private ImageDetails selectedImage(int firstOrSecond){
        ImageDetails img = null;
        if(firstOrSecond == 0)
            img = imageSelectedFirst;
        else if(firstOrSecond == 1)
            img = imageSelectedSecond;
        return img;
    }

    public void setImageSelectedFirstByIndex(int i){
        if(i < 0 || i>countImages)
            return;
        imageSelectedFirst = backImagesDetails[i];
    }

    public void setImageSelectedSecondByIndex(int i){
        if(i < 0 || i>countImages)
            return;
        imageSelectedSecond = backImagesDetails[i];
    }

    public boolean addImage(int drawableId){
        if(countImages == numOfImages)
            return false;

        backImagesDetails[countImages] = new ImageDetails(drawableId);
        countImages++;
        return true;
    }

    /**
     *This function checks if all images had match.
     * @return true if all images match, else return false.
     */
    public boolean isAllImagesMatch(){
        for(int i = 0 ; i < countImages; i ++ ){
            if(!isMatchByIndex(i))
                return false;
        }
        return true;
    }

    public void shuffle(){
        List<ImageDetails> listBackImages = Arrays.asList(backImagesDetails);
        Collections.shuffle(listBackImages);
        backImagesDetails = (ImageDetails[]) listBackImages.toArray();
    }

    public boolean isMatchByIndex(int index){
        if(index < 0 || index > countImages)
            return false;
        return backImagesDetails[index].isMatch();
    }

}
