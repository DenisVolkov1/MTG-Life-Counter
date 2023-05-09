package com.mygdx.game;


import static com.mygdx.game.MTGLifeCounter.InitLife.FORTY;
import static com.mygdx.game.MTGLifeCounter.InitLife.SIXTY;
import static com.mygdx.game.MTGLifeCounter.InitLife.TWENTY;
import static com.mygdx.game.ManagerResources.BACKGROUND_HEALTH_CHOICE;
import static com.mygdx.game.ManagerResources.BACKGROUND_HEALTH_CHOICE_PICK;
import static com.mygdx.game.ManagerResources.ICON_RESTART;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.mygdx.game.listeners.LifeClickListener;

public class HealthChoiceContainer {
    VerticalGroup vg ;
    private final MainCountPanel panelCountPlayer1;
    private final MainCountPanel panelCountPlayer2;
    private final BitmapFont myFont = new BitmapFont(Gdx.files.internal("BimapFont/MyBitmapFont_100.fnt"));
    private final Container<VerticalGroup> verticalGroupContainer;
    private Skin skinButton;
    final float partY = Gdx.graphics.getHeight()/120F;
    final float partX = Gdx.graphics.getWidth()/90F;


    public HealthChoiceContainer(final MainCountPanel panelCountPlayer1, final MainCountPanel panelCountPlayer2, InputMultiplexer inputMultiplexer, final ImageButton setInitLifeButton) {
        final Image imageBackground = new Image(ManagerResources.getInstance().getResource(BACKGROUND_HEALTH_CHOICE));
        ManagerResources.getInstance().getResource(ICON_RESTART);
        final Image imageBackgroundPick = new Image(ManagerResources.getInstance().getResource(BACKGROUND_HEALTH_CHOICE_PICK));
        this.panelCountPlayer1 = panelCountPlayer1;
        this.panelCountPlayer2 = panelCountPlayer2;
        vg = new VerticalGroup();
        vg.space(10.0F);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = myFont;
        textButtonStyle.checked = imageBackgroundPick.getDrawable();

        final TextButton b1 = new TextButton( TWENTY.toString(), textButtonStyle);
        final TextButton b2 = new TextButton( FORTY.toString(), textButtonStyle);
        final TextButton b3 = new TextButton( SIXTY.toString(), textButtonStyle);
        final LifeClickListener clickListenerLife1 = new LifeClickListener(b1, this);
        LifeClickListener clickListenerLife2 = new LifeClickListener(b2, this);
        LifeClickListener clickListenerLife3 = new LifeClickListener(b3, this);
        b1.addListener(clickListenerLife1);
        b2.addListener(clickListenerLife2);
        b3.addListener(clickListenerLife3);

        vg.addActor(b1);
        vg.addActor(b2);
        vg.addActor(b3);

        verticalGroupContainer = new Container<VerticalGroup>(vg);
        verticalGroupContainer.setBackground(imageBackground.getDrawable());
        verticalGroupContainer.setHeight(460.0F);
        verticalGroupContainer.setWidth(200.0F);
        verticalGroupContainer.setX(partX * 48.5F);
        verticalGroupContainer.setY(partY * 30);

        GestureDetector.GestureAdapter clickListenerLifeContainer = new GestureDetector.GestureAdapter() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                boolean isOutsideClick = false;
                y = (Gdx.graphics.getHeight()-y);
                if(x < verticalGroupContainer.getX() || x > (verticalGroupContainer.getX() + verticalGroupContainer.getWidth())) {
                    isOutsideClick =true;
                } else {
                    if(y < verticalGroupContainer.getY() || y > (verticalGroupContainer.getY() + verticalGroupContainer.getHeight())) {
                        isOutsideClick=true;
                    }
                }
                if(isOutsideClick) {
                    verticalGroupContainer.setVisible(false);
                    setInitLifeButton.setChecked(false);
                }

                return false;
            }
        };
        inputMultiplexer.addProcessor(new GestureDetector(clickListenerLifeContainer));
    }

    public Container<VerticalGroup> getHealthChoiceContainer() {
        return verticalGroupContainer;
    }

    public void setClickInitHealth(MTGLifeCounter.InitLife initHealth) {

        for (final Actor children :vg.getChildren()) {
            final TextButton textButton = (TextButton) children;

            if((textButton.getText().toString()).equals(initHealth.toString())) textButton.setChecked(true);
            else textButton.setChecked(false);
        }
    }

    public MainCountPanel getPanelCountPlayer1() {
        return panelCountPlayer1;
    }

    public MainCountPanel getPanelCountPlayer2() {
        return panelCountPlayer2;
    }
}
