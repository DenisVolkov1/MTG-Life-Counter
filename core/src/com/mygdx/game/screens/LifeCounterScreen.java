package com.mygdx.game.screens;

import static com.mygdx.game.ManagerResources.*;
import static com.mygdx.game.Players.*;
import static com.mygdx.game.screens.ManagerScreens.Screens.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MTGLifeCounter;
import com.mygdx.game.MainCountPanel;
import com.mygdx.game.ManagerResources;
import com.mygdx.game.MenuPanelGroup;

public class LifeCounterScreen extends ScreenAdapter implements GetScreenEnum {

    public Stage stage;
    private final MainCountPanel panelCountPlayer1;
    private final MainCountPanel panelCountPlayer2;
    private final MenuPanelGroup menuPanelGroup;
    InputMultiplexer inputMultiplexer;

    private Texture background_drawable;
    private final Texture middleBarBackground_drawable;

    float  partX =0;
    float  partY =0;
    private final MTGLifeCounter game;


    public LifeCounterScreen(final MTGLifeCounter game) {
        this.game = game;
        partX = Gdx.graphics.getWidth()/120F;
        partY = Gdx.graphics.getHeight()/120F;

        stage = this.game.stage;
        inputMultiplexer = new InputMultiplexer();

        addBackgroundScreen();
        TextureAtlas textureAtlas_damage = ManagerResources.getInstance().getResource(ATLAS_DAMAGE);
        TextureAtlas textureAtlas_heal = ManagerResources.getInstance().getResource(ATLAS_HEAL);
        TextureAtlas textureAtlas_plusMinus = ManagerResources.getInstance().getResource(ATLAS_PLUS_MINUS);

        panelCountPlayer1= new MainCountPanel(PLAYER_1, textureAtlas_heal, textureAtlas_damage, textureAtlas_plusMinus);
        panelCountPlayer2= new MainCountPanel(PLAYER_2 ,textureAtlas_heal, textureAtlas_damage, textureAtlas_plusMinus);
        menuPanelGroup = new MenuPanelGroup(game, panelCountPlayer1, panelCountPlayer2, inputMultiplexer);

        middleBarBackground_drawable = ManagerResources.getInstance().getResource(MIDDLE_BAR);
        Image backgroundMiddleBar = new Image(middleBarBackground_drawable);

        panelCountPlayer1.setPosition( 45 * partX , 8 * partY );
        panelCountPlayer2.setPosition( 75 * partX , 112 * partY );
        panelCountPlayer2.setRotation(180);

        menuPanelGroup.setPosition( 2.8F * partX , 60 * partY );
        backgroundMiddleBar.setPosition(
                menuPanelGroup.getX() - partX * 2.8F ,
                menuPanelGroup.getY() - partY * 5.5F);

        stage.addActor(panelCountPlayer1);
        stage.addActor(panelCountPlayer2);
        stage.addActor(backgroundMiddleBar);
        stage.addActor(menuPanelGroup);

        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void addBackgroundScreen() {
        background_drawable = ManagerResources.getInstance().getResource(BACKGROUND);
        Image background = new Image(background_drawable);
        background.setSize(1080,2246);
        stage.addActor(background);
    }

    float time = 0f;
    //String lifeC="20";
    @Override
    public void render(float delta) {

        time+= Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        panelCountPlayer1.startAnimation();
        panelCountPlayer2.startAnimation();
        stage.draw();
    }
    @Override
    public void hide() {
        panelCountPlayer1.saveLife();
        panelCountPlayer2.saveLife();
    }

    @Override
    public ManagerScreens.Screens getScreenEnum() {
        return LIFE_COUNTER_SCREEN;
    }
}
