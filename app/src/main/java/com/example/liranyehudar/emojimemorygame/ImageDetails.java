package com.example.liranyehudar.emojimemorygame;

/**
 * Created by liran yehudar on 4/8/2018.
 */

public class ImageDetails {

    private int backDrawableId;
    private boolean isFlipped;
    private boolean isMatch;

    public ImageDetails(int backDrawableId) {
        this.backDrawableId = backDrawableId;
        this.isFlipped = false;
        this.isMatch = false;
    }

    public int getBackDrawableId() {
        return backDrawableId;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageDetails that = (ImageDetails) o;

        return backDrawableId == that.backDrawableId;
    }

}
