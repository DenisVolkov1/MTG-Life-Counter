package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MTGLifeCounter;

public class ManagerScreens {

    private ManagerScreens() {}

    public enum Screens {
        LIFE_COUNTER_SCREEN, HISTORY_SCREEN, LOADING_SCREEN;
    }

    public static <T extends Game> Screen stepOnScreen(Screens screen, T game) {

        switch (screen) {
            case HISTORY_SCREEN: {
                Gdx.app.log("<<Screen>>",screen.toString());
                return new HistoryScreen((MTGLifeCounter) game);
            }
            case LIFE_COUNTER_SCREEN: {
                Gdx.app.log("<<Screen>>",screen.toString());
                return new LifeCounterScreen((MTGLifeCounter) game);
            }
            case LOADING_SCREEN: {
                Gdx.app.log("<<Screen>>",screen.toString());
                return new LoadingScreen((MTGLifeCounter) game);
            }
            default: throw new RuntimeException("Screen not exists!");
        }
    }
}
