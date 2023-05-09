package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class RandomAnimationFromMassive {

    private final AnimationImage[] arrayAnimImage;
    private final TextureAtlas textureAtlas;
    private Actor actor;

    public RandomAnimationFromMassive(String prefixNameAnimation, int countAnim, TextureAtlas textureAtlas, float animDuration, float frameDuration, Actor actor) {
        this.actor=actor;
        arrayAnimImage = new AnimationImage[countAnim];
        this.textureAtlas = textureAtlas;
        for(int i = 0;i < arrayAnimImage.length; i++) {
            Array<TextureAtlas.AtlasRegion> textureRegion = textureAtlas.findRegions(prefixNameAnimation + (i+1));
            if(textureRegion.size>0) {
                Animation<TextureRegion> animation = new Animation<TextureRegion>(frameDuration,textureRegion,Animation.PlayMode.NORMAL);
                arrayAnimImage[i] = new AnimationImage(animDuration, animation, actor);
            } else break;
        }
    }

    public void setRunAnimation() {
        int i = getRandomIndex();
        if(arrayAnimImage[i] != null) arrayAnimImage[i].setRunAnimation(actor.getStage());
    }

    int previousIndex=-1;
    private int getRandomIndex() {
        int maxIndex=-1;
        int retIndex;
        for (int i = 0;i < arrayAnimImage.length; ++i) {
            if(arrayAnimImage[i] != null) { maxIndex = i; }
        }

        if(maxIndex == -1) throw new RuntimeException("AnimationImage[] arrayAnimImage пустой массив!");
        int i = new Random().nextInt(maxIndex + 1);
        if (previousIndex == -1) {
            retIndex = i;
        } else if (i == previousIndex) {
            if(previousIndex==maxIndex) retIndex = 0;
            else retIndex = i + 1;
        } else retIndex = i;

        previousIndex = retIndex;

        return retIndex;
    }

    public void addToX(float addToX) {
        for(int i = 0;i < arrayAnimImage.length; i++) {
            if(arrayAnimImage[i] != null) { arrayAnimImage[i].addToX(addToX); }
        }
    }

    public void addToY(float addToY) {
        for(int i = 0;i < arrayAnimImage.length; i++) {
            if(arrayAnimImage[i] != null) { arrayAnimImage[i].addToY(addToY); }
        }
    }
}
