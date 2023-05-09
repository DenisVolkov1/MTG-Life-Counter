package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.mygdx.game.MTGLifeCounter;
import com.mygdx.game.ManagerResources;

public class LoadingScreen extends ScreenAdapter implements GetScreenEnum {

    private final MTGLifeCounter game;
    private final Stage stage;

    private static Label.LabelStyle labelStyle;
    private final Label loadingLabel;
    private final Label loadingPercentageLabel;
    private final AssetManager assetManager;

    private ManagerScreen.Screens onScreen;
    private HorizontalGroup loadingSticksHorizontalGroup;
    private Texture textureLoadingStick;
    private static final float partX;
    private static final float partY;

    static {
        partX = Gdx.graphics.getWidth()/80F;
        partY = Gdx.graphics.getHeight()/80F;
    }

    public LoadingScreen(final MTGLifeCounter game) {
        this.game = game;
        this.stage = game.stage;
        this.assetManager = ManagerResources.getInstance().getAssetManager();
        onScreen = ManagerScreen.Screens.LIFE_COUNTER_SCREEN;
        loadingSticksHorizontalGroup = new HorizontalGroup();

        Texture texture = new Texture(Gdx.files.internal("backgrounds/loading.png"));
        Image background = new Image(texture);

        Texture texture2 = new Texture(Gdx.files.internal("icon_/loading_line.png"));
        Image loadingLine = new Image(texture2);
        loadingLine.setX(partX*2);
        loadingLine.setY(partY*40);

        textureLoadingStick = new Texture(Gdx.files.internal("icon_/loading_stick.png"));

        BitmapFont myFont = new BitmapFont(Gdx.files.internal("BimapFont/MyBitmapFont.fnt"));
        myFont.getData().setScale(0.3F, 0.3F);

        labelStyle = new Label.LabelStyle();
        labelStyle.font=myFont;

        VerticalGroup loadVerticalGroup = new VerticalGroup();
        loadVerticalGroup.setY(partY*40F);
        loadVerticalGroup.setX(partX*40F);

        loadingLabel = new Label("Loading ...", labelStyle);
        loadingPercentageLabel = new Label("", labelStyle);
        loadVerticalGroup.space(partY);
        loadVerticalGroup.addActor(loadingLabel);
        loadVerticalGroup.addActor(loadingSticksHorizontalGroup);

        stage.addActor(background);
        stage.addActor(loadVerticalGroup);
        //
    }
    private String threeAnimateSpot(String textLabel) {
        String postfix = textLabel.substring(8);
        if(postfix.length()==3) return  "";
        else {
            if(time >= 0.3F) {
                time = 0F;
                return postfix = postfix + ".";
            }
        }
        return postfix;
    }

    private void loadingFill(int deltaProgress) {
        for (int i = 0; i < deltaProgress/2; i++) {
            loadingSticksHorizontalGroup.addActor(new Image(textureLoadingStick));
        }
    }

    private float time;
    private int progressS;

    @Override
    public void render(float delta) {

        if(assetManager.update()) {
            game.setScreen(ManagerScreen.stepOnScreen(onScreen, game));

        } else {
            time+= Gdx.graphics.getDeltaTime();
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            String postfixThreeDots = threeAnimateSpot(String.valueOf(loadingLabel.getText()));
            loadingLabel.setText("Loading "+postfixThreeDots);

            int progress = ((int) (assetManager.getProgress() * 100));
            int deltaProgress = progress - progressS;
            progressS = progress;

            loadingFill(deltaProgress);

            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    @Override
    public ManagerScreen.Screens getScreenEnum() {
        return ManagerScreen.Screens.LOADING_SCREEN;
    }
}
