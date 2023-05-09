package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;

public class Util {

    public static final Color MINUS_COLOR;
    public static final Color PLUS_COLOR;
    public static final Color CONSTANT_COLOR;

    static {
        MINUS_COLOR = new Color(1f, 0.33f, 0.33f, 0.8f);
        PLUS_COLOR = new Color(0.33f, 1f, 0.33f, 0.8f);
        CONSTANT_COLOR = new Color(1f, 1f, 1f, 1f);
    }

}
