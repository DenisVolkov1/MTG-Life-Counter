package com.mygdx.game.listeners;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.HealthChoiceContainer;
import com.mygdx.game.MTGLifeCounter;

public class LifeClickListener extends ClickListener {
    private final TextButton textButton;
    private final HealthChoiceContainer hcc;

    public LifeClickListener(TextButton textButton, HealthChoiceContainer hcc) {
        this.textButton =textButton;
        this.hcc =hcc;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        String clicked_Life = textButton.getText().toString();
        MTGLifeCounter.InitLife initLife = MTGLifeCounter.InitLife.getInitLife(clicked_Life);
        MTGLifeCounter.setInitialLife(initLife);
        hcc.setClickInitHealth(initLife);
        hcc.getPanelCountPlayer1().restartLife();
        hcc.getPanelCountPlayer2().restartLife();
    }
}
