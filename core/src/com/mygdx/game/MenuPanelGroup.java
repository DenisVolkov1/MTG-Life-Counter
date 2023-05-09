package com.mygdx.game;

import static com.mygdx.game.ManagerResources.*;
import static com.mygdx.game.screens.ManagerScreen.Screens.HISTORY_SCREEN;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.screens.ManagerScreen;

public class MenuPanelGroup extends HorizontalGroup {
   final static float partY = Gdx.graphics.getHeight()/120F;
   final static float partX = Gdx.graphics.getWidth()/120F;

   private final ImageButton historyButton;
   private final ImageButton restartButton;
   private final ImageButton setInitLifeButton;
   private final ImageButton diceButton;
   private final MTGLifeCounter game;

   private final Texture icon_history_texture;
   private final Texture icon_history_hover_texture;

   Dice dicePlayer_1;
   Dice dicePlayer_2;


    public MenuPanelGroup(final MTGLifeCounter game, final MainCountPanel panelCountPlayer1, final MainCountPanel panelCountPlayer2, InputMultiplexer inputMultiplexer) {
       this.game = game;
       dicePlayer_1 = new Dice(game.stage);
       dicePlayer_2 = new Dice(game.stage);
       icon_history_texture = ManagerResources.getInstance().getResource(ICON_HISTORY);
       icon_history_hover_texture = ManagerResources.getInstance().getResource(ICON_HISTORY_HOVER);

       Drawable icon_history_drawable = new Image(icon_history_texture).getDrawable();
       Drawable icon_history_hover_drawable = new Image(icon_history_hover_texture).getDrawable();
       Drawable icon_restart_drawable = new Image(ManagerResources.getInstance().getResource(ICON_RESTART)).getDrawable();
       Drawable icon_restart_hover_drawable = new Image(ManagerResources.getInstance().getResource(ICON_RESTART_HOVER)).getDrawable();
       Drawable icon_initLife_drawable = new Image(ManagerResources.getInstance().getResource(ICON_INIT_LIFE)).getDrawable();
       Drawable icon_initLife_hover_drawable = new Image(ManagerResources.getInstance().getResource(ICON_INIT_LIFE_HOVER)).getDrawable();
       Drawable icon_dice_drawable = new Image(ManagerResources.getInstance().getResource(ICON_DICE)).getDrawable();
       Drawable icon_dice_hover_drawable = new Image(ManagerResources.getInstance().getResource(ICON_DICE_HOVER)).getDrawable();

       historyButton = new ImageButton(icon_history_drawable, icon_history_hover_drawable, icon_history_drawable);
       restartButton = new ImageButton(icon_restart_drawable, icon_restart_hover_drawable, icon_restart_drawable);
       setInitLifeButton = new ImageButton(icon_initLife_drawable, icon_initLife_hover_drawable, icon_initLife_drawable);
       diceButton = new ImageButton(icon_dice_drawable, icon_dice_hover_drawable, icon_dice_drawable);

       //ADD LISTENERS//
       // HISTORY BUTTON CLICK //
       historyButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
               panelCountPlayer1.saveChangeLife();
               panelCountPlayer2.saveChangeLife();
               game.clearStage();
               game.getScreen().dispose();
               game.setScreen(ManagerScreen.stepOnScreen(HISTORY_SCREEN, game));
           }
       });
        // RESTART LIFE BUTTON CLICK //
       restartButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
               MTGLifeCounter.setInitialLife();
               panelCountPlayer1.restartLife();
               panelCountPlayer2.restartLife();
                       MTGLifeCounter.clearHistory();
           }
       });
        // CHOICE INIT LIFE BUTTON CLICK //
       final HealthChoiceContainer healthChoiceContainer = new HealthChoiceContainer( panelCountPlayer1, panelCountPlayer2 , inputMultiplexer , setInitLifeButton);
       final Container<VerticalGroup> choiceContainer = healthChoiceContainer.getHealthChoiceContainer();
       setInitLifeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(setInitLifeButton.isChecked()) {
                    choiceContainer.setVisible(true);
                    MTGLifeCounter.InitLife init = MTGLifeCounter.getInitialLife();
                        healthChoiceContainer.setClickInitHealth(init);
                            game.stage.addActor( choiceContainer );
                } else {
                    choiceContainer.setVisible(false);
                }
            }
       });
       // DICE ROLL //
       diceButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
               dicePlayer_1.rollTheDice();
               dicePlayer_2.rollTheDice();
           }
       });

       this.space(partX * 10);
       this.addActor(historyButton);
       this.addActor(restartButton);
       this.addActor(setInitLifeButton);
       this.addActor(diceButton);

       dicePlayer_1.setPosition(partX * 60 - Dice.WIDTH_DICE/2,partY * 60 - Dice.HEIGHT_DICE/2 - partY * 23);
       dicePlayer_2.setPosition(partX * 60 - Dice.WIDTH_DICE/2,partY * 60 - Dice.HEIGHT_DICE/2 + partY * 23);
       game.stage.addActor( dicePlayer_1 );
       game.stage.addActor( dicePlayer_2 );
    }
}
