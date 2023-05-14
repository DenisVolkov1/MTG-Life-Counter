package com.mygdx.game;


import static com.mygdx.game.ManagerResources.ATLAS_RESTART;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.animation.AnimationImage;
import com.mygdx.game.animation.RandomAnimationFromMassive;
import com.mygdx.game.util.HistoryScreenInformation;
import com.mygdx.game.util.Util;

public class MainCountPanel extends Group {

    private final AnimationImage anim_add_button;
    private final AnimationImage anim_minus_button;

    private enum PlusMinus {
        PLUS,
        MINUS
    }
    public Players player;

    private final Actor thisObject;
    private final BitmapFont myFont;
    private final TextButton buttonAddLife;
    private final TextButton buttonSubtractLife;
    private final Label.LabelStyle label1Style_LifeInAction;
    private final Label.LabelStyle label1Style_Life;
    final Label labelLife;

    final Label labelLifeInAction;
    final float partY = Gdx.graphics.getHeight()/120F;
    final float partX = Gdx.graphics.getWidth()/90F;
    private final RandomAnimationFromMassive anim_damage;
    private final RandomAnimationFromMassive anim_health;
    private final AnimationImage anim_restart;

    public MainCountPanel(Players player,TextureAtlas textureAtlas_heal, TextureAtlas textureAtlas_damage , TextureAtlas textureAtlas_plusMinus) {
        thisObject = this;
        this.player = player;

        label1Style_LifeInAction = new Label.LabelStyle();
        myFont = new BitmapFont(Gdx.files.internal("BimapFont/MyBitmapFont.fnt"));
        label1Style_LifeInAction.font = myFont;

        label1Style_Life = new Label.LabelStyle();
        label1Style_Life.font = myFont;

        labelLife = new Label(getLiveCount() ,label1Style_Life);
        labelLifeInAction = new Label("0",label1Style_LifeInAction);
        //shift after saved life count
        this.shiftLifeLabel(Integer.parseInt(getLiveCount()));

        TextureAtlas textureAtlas_restart = ManagerResources.getInstance().getResource(ATLAS_RESTART);
        Array<TextureAtlas.AtlasRegion> textureRegion = textureAtlas_restart.findRegions("restart1");
        Animation<TextureRegion> animation = new Animation<TextureRegion>(1F/45F,textureRegion,Animation.PlayMode.NORMAL);
        anim_restart = new AnimationImage(0.30F, animation, thisObject);
        anim_restart.addToY(3*partY);
        anim_restart.addToX(4*partX);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = myFont;

        labelLifeInAction.setFontScale(0.4F);
        labelLifeInAction.setPosition(partX * 6,partY * 2);
        labelLifeInAction.setColor(new Color(1,1,1, 0));

        buttonAddLife = new TextButton("+", textButtonStyle);
        buttonAddLife.setColor(new Color(1,1,1, 0.5F));
        buttonAddLife.setPosition(-24 * partX,0);

        buttonSubtractLife = new TextButton("-", textButtonStyle);
        buttonSubtractLife.setColor(new Color(1,1,1, 0.5F));
        buttonSubtractLife.setPosition(36 * partX,0);

        anim_health= new RandomAnimationFromMassive("health",10,textureAtlas_heal,0.5F,1F/30F,thisObject);
        anim_health.addToY(3*partY);
        anim_health.addToX(4.1F*partX);
        anim_damage = new RandomAnimationFromMassive("damage",10,textureAtlas_damage,0.5F,1F/30F,thisObject);
        anim_add_button= new AnimationImage(0.4F,1F/30F,textureAtlas_plusMinus,"plus",thisObject);
        anim_add_button.addToX(partX*27);
        anim_add_button.addToY(partY*3);

        anim_minus_button= new AnimationImage(0.4F,1F/30F,textureAtlas_plusMinus,"minus",thisObject);
        anim_minus_button.addToX(-partX*30);
        anim_minus_button.addToY(partY*3);

        ///ADD Listeners
        // ADD LIFE FOR PLAYER
        buttonAddLife.addListener(new ClickListener() {
            @SuppressWarnings("SuspiciousIndentation")
            public void clicked(InputEvent event, float x, float y) {
                runHealthAnimation();
                anim_add_button.setRunAnimation(thisObject.getStage());
                changeLabelLifeInAction(PlusMinus.PLUS);
                    changeLabelLife(PlusMinus.PLUS);
                        runAnimLabelLifeInAction();
                            runAnimScaleLifePlus();
                                saveLife();

            }
        });
        // SUBTRACT LIFE FOR PLAYER
        buttonSubtractLife.addListener(new ClickListener() {
            @SuppressWarnings("SuspiciousIndentation")
            public void clicked(InputEvent event, float x, float y) {
                runDamageAnimation();
                anim_minus_button.setRunAnimation(thisObject.getStage());
                changeLabelLifeInAction(PlusMinus.MINUS);
                changeLabelLife(PlusMinus.MINUS);
                        runAnimLabelLifeInAction();
                            runAnimScaleLifeMinus();
                                saveLife();
            }
        });

        this.addActor(buttonAddLife);
        this.addActor(buttonSubtractLife);
        this.addActor(labelLifeInAction);
        this.addActor(labelLife);
    }

    private void changeLabelLife(PlusMinus plusMinus) {
        switch (plusMinus) {
            case PLUS: {
                Integer new_setCount = Integer.parseInt(labelLife.getText().toString()) + 1;
                shiftLifeLabel(new_setCount);
                if(new_setCount <= 99) labelLife.setText(String.valueOf(new_setCount));
            }
            break;
            case MINUS:{
                Integer new_setCount = Integer.parseInt(labelLife.getText().toString()) - 1;
                shiftLifeLabel(new_setCount);
                if(new_setCount >= -9) labelLife.setText(String.valueOf(new_setCount));
            }
            break;
        }
    }

    private void shiftLifeLabel(Integer new_setCount) {
        if(new_setCount < 10 && new_setCount >= 0) labelLife.setPosition(7 * partX,0);
        else labelLife.setPosition(0,0);
    }

    private void changeLabelLifeInAction(PlusMinus pm) {
        String withoutSign = labelLifeInAction.getText().toString().replace("+", "");
        int new_setCount0 = Integer.parseInt(withoutSign);
        if(new_setCount0 == 1 && pm == PlusMinus.MINUS) new_setCount0 -= 1;
        if(new_setCount0 == -1 && pm == PlusMinus.PLUS) new_setCount0 += 1;
        Integer new_setCount = new_setCount0 + ((pm == PlusMinus.PLUS) ? +1 : (-1));

        if((new_setCount < 10 && new_setCount >= 0) || (new_setCount <= 0 && new_setCount > -10)) labelLifeInAction.setPosition(6 * partX,10 * partY);
        else labelLifeInAction.setPosition(3 * partX,10 * partY);
        String value = ((new_setCount > 0) ? "+" : "") + String.valueOf(new_setCount);

        if(new_setCount >= -99) labelLifeInAction.setText(value);
    }
    long beginTime=0;
    private void runAnimLabelLifeInAction() {
        runningAnimLabelLifeInAction =true;
        if(passedFromLaunch == 0) {
            beginTime=System.currentTimeMillis(); displayTime=1700;
        }
        else {
            if(displayTime - passedFromLaunch <= 550) displayTime += 550;
        }
    }

    long currentTime=0;
    int upScrollMove=0;
    float transparent=0F;
    boolean runningAnimLabelLifeInAction = false;
    long passedFromLaunch=0;
    long displayTime = 1700;
    private void animLabelLifeInAction() {
        currentTime = System.currentTimeMillis();

        if(runningAnimLabelLifeInAction) {
            passedFromLaunch = (currentTime - beginTime);

            if(displayTime > passedFromLaunch) {
                if (upScrollMove != 12) {
                    labelLifeInAction.setPosition(labelLifeInAction.getX(), partY * upScrollMove);
                    upScrollMove += 2;
                }
                if(transparent <= 1F) {
                    transparent += 0.25F;
                    labelLifeInAction.setColor(new Color(1, 1, 1, transparent));
                }
            } else {
                if (upScrollMove != 0) {
                    labelLifeInAction.setPosition(labelLifeInAction.getX(), partY * upScrollMove);
                    labelLifeInAction.setColor(new Color(1, 1, 1, transparent));
                    upScrollMove -= 2;
                    transparent -= 0.35F;
                    if(transparent < 0) transparent=0;
                } else {endingAnimLabelLifeInAction();}
            }
        }
    }

    private void endingAnimLabelLifeInAction() {
        saveChangeLife();
        MTGLifeCounter.saveAppInfo();
        //
        runningAnimLabelLifeInAction=false;
        passedFromLaunch=0;
        labelLifeInAction.setText("0");
    }

    public boolean saveChangeLife() {
        String changingLife = labelLifeInAction.getText().toString();
        if(!changingLife.equals("0")) {
            HistoryScreenInformation information = new HistoryScreenInformation(player, labelLifeInAction.getText().toString());
            MTGLifeCounter.historyScreenInformationList.add(information);
            return true;
        } else {
            return false;
        }
    }

    float fontScalePlus = 1;
    float fontScaleMinus = 1;
    boolean isScalePlus=false;
    boolean isScaleMinus =false;

    @SuppressWarnings("SuspiciousIndentation")
    private void animScaleLife() {

        if(isScalePlus) {
            if (fontScalePlus >= 1.1F) {
                fontScalePlus = 1F;
                isScalePlus = false;
                label1Style_Life.fontColor = Util.Colors.CONSTANT_COLOR;
            } else {
                fontScalePlus += 0.018F;
                label1Style_Life.fontColor = Util.Colors.PLUS_COLOR;
            }
            labelLife.setFontScale(fontScalePlus, fontScalePlus);
        }
        if(isScaleMinus) {
            if (fontScaleMinus <= 0.9F) {
                fontScaleMinus = 1F;
                isScaleMinus =false;
                label1Style_Life.fontColor = Util.Colors.CONSTANT_COLOR;
            } else {
                fontScaleMinus -= 0.018F;
                label1Style_Life.fontColor = Util.Colors.MINUS_COLOR;
            }
            labelLife.setFontScale(fontScaleMinus, fontScaleMinus);
        }
    }

    public void runHealthAnimation() {
        anim_health.setRunAnimation();
    }

    public void runRestartAnimation() {
        anim_restart.setRunAnimation(thisObject.getStage());
    }

    public void runDamageAnimation() {
        anim_damage.setRunAnimation();
    }

    public void startAnimation() {
        animScaleLife();
            animLabelLifeInAction();
    }

    public void restartLife() {
        Integer new_setCount = Integer.parseInt(MTGLifeCounter.getInitialLife().toString());
        shiftLifeLabel(new_setCount);
        labelLife.setText(String.valueOf(new_setCount));
        this.runRestartAnimation();
        this.runAnimScaleLifePlus();
    }

    public void saveLife() {
        switch (player) {
            case PLAYER_1: MTGLifeCounter.savedCountLifePlayer1 = String.valueOf(labelLife.getText());
                break;
            case PLAYER_2: MTGLifeCounter.savedCountLifePlayer2 = String.valueOf(labelLife.getText());
                break;
            default:
                throw new IllegalStateException("Unexpected Enum com.mygdx.game.Players value: " + player);
        }
    }

    public String getLiveCount() {
        switch (player) {
            case PLAYER_1: return MTGLifeCounter.savedCountLifePlayer1;
            case PLAYER_2: return MTGLifeCounter.savedCountLifePlayer2;
            default:
                throw new IllegalStateException("Unexpected Enum com.mygdx.game.Players value: " + player);
        }
    }

    public void runAnimScaleLifePlus() {
        isScalePlus=true;
    }
    public void runAnimScaleLifeMinus() {isScaleMinus =true;}
}
