package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class AnimationImage extends Image {

    private Animation<TextureRegion> animation;
    private TextureRegion currentRegion;
    private TextureAtlas textureAtlas;
    private Actor actor;
    float time = 0f;
    float  partX =0;
    float  partY =0;
    float animDuration=0;
    private float shiftYanim;

    public AnimationImage(float animDuration,Animation<TextureRegion> animation, Actor actor) {
        partX = Gdx.graphics.getWidth()/90F;
        partY = Gdx.graphics.getHeight()/90F;
        this.actor=actor;
        this.animation = animation;
        this.animDuration=animDuration;
        this.setRotation(actor.getRotation());
    }

    public AnimationImage(float animDuration,float frameDuration,TextureAtlas textureAtlas,String prefixNameAnimation, Actor actor) {
        this.textureAtlas = textureAtlas;
                /*new TextureAtlas(Gdx.files.internal(pathAtlas +".atlas"));*/
            Array<TextureAtlas.AtlasRegion> textureRegion = textureAtlas.findRegions(prefixNameAnimation);
        this.animation = new Animation<TextureRegion>(frameDuration,textureRegion,Animation.PlayMode.NORMAL);

        partX = Gdx.graphics.getWidth()/90F;
        partY = Gdx.graphics.getHeight()/90F;
        this.actor=actor;
        this.animDuration=animDuration;
        this.setRotation(actor.getRotation());
    }
    boolean e = false;
    @Override
    public void act(float delta){
        super.act(delta);
        time += delta;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(time>animDuration) {
            this.remove();
        }
        currentRegion = animation.getKeyFrame(time,e);

        this.setRotation(actor.getRotation());
    }
    public void setRunAnimation(Stage stage) {
        if(stage == null) throw new IllegalArgumentException("Stage ссылается на null!");
        stage.addActor(this);
        time = 0f;
        e=true;
        shiftXanim = (actor.getRotation()==180F) ? +(partX * 6) + x : (-partX * 6) - x;
        shiftYanim = (actor.getRotation()==180F) ? +y :- y;
    }
    float x=0;
    float y=0;
    public void addToX(float addX) {
        x = addX;
    }
    public void addToY(float addY) {
        y =  addY;
    }
    float shiftXanim=0;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(e)batch.draw (currentRegion, actor.getX() + shiftXanim, actor.getY() + shiftYanim, 0F, 0F,  currentRegion.getRegionWidth(),  currentRegion.getRegionHeight(),
                1F, 1F,  actor.getRotation());
    }
}
