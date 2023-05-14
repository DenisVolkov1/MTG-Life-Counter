package com.mygdx.game.screens;


import static com.mygdx.game.ManagerResources.BACKGROUND;
import static com.mygdx.game.ManagerResources.HISTORY_ROW_BACKGROUND;
import static com.mygdx.game.ManagerResources.ICON_BACK_HISTORY;
import static com.mygdx.game.ManagerResources.ICON_BACK_HISTORY_HOVER;
import static com.mygdx.game.ManagerResources.MIDDLE_BAR;
import static com.mygdx.game.ManagerResources.MIDDLE_HISTORY_BAR;
import static com.mygdx.game.ManagerResources.MY_FONT;
import static com.mygdx.game.screens.util.ManagerScreens.Screens.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MTGLifeCounter;
import com.mygdx.game.ManagerResources;
import com.mygdx.game.Players;
import com.mygdx.game.screens.util.ManagerScreens;
import com.mygdx.game.util.HistoryScreenInformation;
import com.mygdx.game.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryScreen extends ScreenAdapter implements GetScreenEnum {

    private static final float partX ;
    private static final float partY ;
    private static final float widthContainer ;
    private static final float heightRow ;

    private static ScrollPane scrollPane_Player1;
    private static ScrollPane scrollPane_Player2;
    private static Container<Table> tableContainer_Player1;
    private static Container<Table> tableContainer_Player2;
    private static Table table_Player1;
    private static Table table_Player2;
    //
    private final MTGLifeCounter game;
    private final ImageButton backButton;
    private final Stage stage;
    private final BitmapFont myFont;
    private final Texture icon_back_history_texture;
    private final Texture icon_back_history_hover_texture;
    private final Texture history_row_background;
    private final Texture middle_bar;
    //
    private static Label.LabelStyle labelStyle;
    static {
        partX = Gdx.graphics.getWidth()/80F;
        partY = Gdx.graphics.getHeight()/130F;
        widthContainer = partX * 65;
        heightRow = partY * 5;
    }

    public HistoryScreen(final MTGLifeCounter game) {
        this.game = game;
        history_row_background = ManagerResources.getInstance().getResource(HISTORY_ROW_BACKGROUND);
        stage = game.stage;
        myFont = ManagerResources.getInstance().getResource(MY_FONT);
        myFont.getData().setScale(0.3F, 0.3F);

        middle_bar = ManagerResources.getInstance().getResource(MIDDLE_HISTORY_BAR);
        Image middle_barImage = new Image(middle_bar);
        middle_barImage.setY(partY * 65);
        //////////////
        ///Player 1
        tableContainer_Player1 = new Container<Table>();
        scrollPane_Player1 = new ScrollPane(tableContainer_Player1);
        tableContainer_Player1.setSize(widthContainer, (partY*40) / 2);
        tableContainer_Player1.setPosition(Gdx.graphics.getWidth()/2F - widthContainer/2F, partY*45);
            scrollPane_Player1.setSize(widthContainer, (partY*110) / 2);
            scrollPane_Player1.setPosition(Gdx.graphics.getWidth()/2F - widthContainer/2F, partY);
        tableContainer_Player1.fill();

        table_Player1 = new Table();
        table_Player1.bottom();
        table_Player1.setDebug(false);
        table_Player1.setFillParent(true);

        List<HistoryScreenInformation> reverseList = new ArrayList<>(MTGLifeCounter.historyScreenInformationList);
            Collections.reverse(reverseList);
        for (HistoryScreenInformation info : reverseList) {
            if(info.getPlayer()==Players.PLAYER_1) addHistoryRow(info.getMessageCountLife(),info.getPlayer());
        }
        tableContainer_Player1.setActor(table_Player1);
        //////////////
        ///Player 2
        tableContainer_Player2 = new Container<Table>();
        scrollPane_Player2 = new ScrollPane(tableContainer_Player2);
        tableContainer_Player2.setSize(widthContainer, (partY*40) / 2);
        tableContainer_Player2.setPosition(Gdx.graphics.getWidth()/2F - widthContainer/2F, partY*90);
            scrollPane_Player2.setSize(widthContainer, (partY*110) / 2);
            scrollPane_Player2.setPosition(Gdx.graphics.getWidth()/2F - widthContainer/2F, partY*70);
        tableContainer_Player2.fill();

        table_Player2 = new Table();
        table_Player2.bottom();
        table_Player2.setDebug(false);
        table_Player2.setFillParent(true);

        for (HistoryScreenInformation info : reverseList) {
            if(info.getPlayer()==Players.PLAYER_2) addHistoryRow(info.getMessageCountLife(),info.getPlayer());
        }
        tableContainer_Player2.setActor(table_Player2);

        icon_back_history_texture = ManagerResources.getInstance().getResource(ICON_BACK_HISTORY);
        icon_back_history_hover_texture = ManagerResources.getInstance().getResource(ICON_BACK_HISTORY_HOVER);

        Drawable icon_back_history_drawable = new Image(icon_back_history_texture).getDrawable();
        Drawable icon_back_history_hover_drawable = new Image(icon_back_history_hover_texture).getDrawable();

        addBackgroundScreen();

        backButton = new ImageButton(icon_back_history_drawable, icon_back_history_hover_drawable, icon_back_history_drawable);
        backButton.setX(partX);
        backButton.setY(partY * 121.5F);
        //ADD LISTENERS
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.clearStage();
                game.getScreen().dispose();
                game.setScreen(ManagerScreens.stepOnScreen(LIFE_COUNTER_SCREEN, game));
            }
        });

        stage.addActor(backButton);
        stage.addActor(middle_barImage);
        stage.addActor(scrollPane_Player1);
        stage.addActor(scrollPane_Player2);
    }

    @Override
    public ManagerScreens.Screens getScreenEnum() {
        return HISTORY_SCREEN;
    }

    private class RowActor extends Group {

        public RowActor(float width, String text) {
            labelStyle = new Label.LabelStyle();
            labelStyle.font = myFont;
            Label nameLabel = new Label(text, labelStyle);
            nameLabel.setY(-4);
            float shift = 0;

            if (text.contains("-")) {
                if (text.length() == 3) {
                    if (text.contains("+")) {shift = (widthContainer - partX * 12) - 5;}
                    if (text.contains("-")) {shift = (widthContainer - partX * 10) - 5;}
                } else if (text.length() == 2) {
                    if (text.contains("-")) {shift = (widthContainer - partX * 6) - 5;}
                    if (text.contains("+")) {shift = (widthContainer - partX * 8) - 5;}
                }
            }
            if(text.contains("+")) labelStyle.fontColor = Util.Colors.PLUS_COLOR;
            else if(text.contains("-")) labelStyle.fontColor = Util.Colors.MINUS_COLOR;

            nameLabel.setX(shift);

            Image background = new Image(history_row_background);
            background.setWidth(width);

            this.addActor(background);
            this.addActor(nameLabel);
        }
    }
    public void addHistoryRow(String text, Players player) {

        switch (player) {
            case PLAYER_1: {
                table_Player1.row();
                table_Player1.add(new RowActor(widthContainer, text)).height(heightRow).left().expandX();
            }
                break;
            case PLAYER_2: {
                table_Player2.row();
                table_Player2.add(new RowActor(widthContainer, text)).height(heightRow).left().expandX();
            }
                break;
            default:
                throw new IllegalStateException("Unexpected Enum com.mygdx.game.Players value: " + player);
        }
    }
    private void addBackgroundScreen() {
        Texture background_drawable = ManagerResources.getInstance().getResource(BACKGROUND);
        Image background = new Image(background_drawable);
        background.setSize(1080,2246);
        stage.addActor(background);
    }
    private float time;
    @Override
    public void render(float delta) {
        time+= Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
