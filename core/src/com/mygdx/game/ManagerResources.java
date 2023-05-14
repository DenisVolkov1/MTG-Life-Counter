package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
Get resource - assetManager.get(resource_name);
 resource name - string name
 */

public final class ManagerResources {

    private static ManagerResources MANAGER_RESOURCES;
    private final AssetManager ASSET_MANAGER;
    public final static AssetDescriptor<TextureAtlas> ATLAS_DAMAGE;
    public final static AssetDescriptor<TextureAtlas> ATLAS_HEAL;
    public final static AssetDescriptor<TextureAtlas> ATLAS_PLUS_MINUS;
    public final static AssetDescriptor<TextureAtlas> ATLAS_RESTART;
    public final static AssetDescriptor<Texture> HISTORY_ROW_BACKGROUND;
    public final static AssetDescriptor<Texture> BACKGROUND;
    public final static AssetDescriptor<Texture> BACKGROUND_HEALTH_CHOICE;
    public final static AssetDescriptor<Texture> BACKGROUND_HEALTH_CHOICE_PICK;
    public final static AssetDescriptor<Texture> MIDDLE_BAR;
    public static final AssetDescriptor<Texture> MIDDLE_HISTORY_BAR;

    public final static AssetDescriptor<Texture> ICON_HISTORY;
    public final static AssetDescriptor<Texture> ICON_HISTORY_HOVER;
    public final static AssetDescriptor<Texture> ICON_RESTART;
    public final static AssetDescriptor<Texture> ICON_RESTART_HOVER;
    public final static AssetDescriptor<Texture> ICON_INIT_LIFE;
    public final static AssetDescriptor<Texture> ICON_INIT_LIFE_HOVER;
    public final static AssetDescriptor<Texture> ICON_DICE;
    public final static AssetDescriptor<Texture> ICON_DICE_HOVER;

    public final static AssetDescriptor<Texture> ICON_BACK_HISTORY;
    public final static AssetDescriptor<Texture> ICON_BACK_HISTORY_HOVER;

    public final static AssetDescriptor<Texture> DICE;
    public final static AssetDescriptor<Texture> DICE_POINT;
    public final static AssetDescriptor<BitmapFont> MY_FONT;

    // Icon //
    static {
        ICON_HISTORY = new AssetDescriptor<Texture>("icon_/icon_history.png", Texture.class);
        ICON_HISTORY_HOVER = new AssetDescriptor<Texture>("icon_/icon_history_hover.png", Texture.class);
        ICON_RESTART = new AssetDescriptor<Texture>("icon_/icon_restart.png", Texture.class);
        ICON_RESTART_HOVER = new AssetDescriptor<Texture>("icon_/icon_restart_hover.png", Texture.class);
        ICON_INIT_LIFE = new AssetDescriptor<Texture>("icon_/icon_heart.png", Texture.class);
        ICON_INIT_LIFE_HOVER = new AssetDescriptor<Texture>("icon_/icon_heart_hover.png", Texture.class);
        ICON_DICE = new AssetDescriptor<Texture>("icon_/icon_dice.png", Texture.class);
        ICON_DICE_HOVER = new AssetDescriptor<Texture>("icon_/icon_dice_hover.png", Texture.class);

        ICON_BACK_HISTORY = new AssetDescriptor<Texture>("icon_/icon_back.png", Texture.class);
        ICON_BACK_HISTORY_HOVER = new AssetDescriptor<Texture>("icon_/icon_back_hover.png", Texture.class);
    }
    // Image //
    static {
        DICE = new AssetDescriptor<Texture>("Textures/dice.png", Texture.class);
        DICE_POINT = new AssetDescriptor<Texture>("Textures/dice_point.png", Texture.class);
        HISTORY_ROW_BACKGROUND = new AssetDescriptor<Texture>("backgrounds/row.png", Texture.class);
        BACKGROUND = new AssetDescriptor<Texture>("backgrounds/main.png", Texture.class);
        BACKGROUND_HEALTH_CHOICE = new AssetDescriptor<Texture>("backgrounds/healthChoice.png", Texture.class);
        BACKGROUND_HEALTH_CHOICE_PICK = new AssetDescriptor<Texture>("backgrounds/healthChoicePick.png", Texture.class);
        MIDDLE_BAR = new AssetDescriptor<Texture>("backgrounds/middleBar.png", Texture.class);
        MIDDLE_HISTORY_BAR = new AssetDescriptor<Texture>("backgrounds/history_middleBar.png", Texture.class);
    }
    // Animation //
    static {
        ATLAS_DAMAGE = new AssetDescriptor<TextureAtlas>("atlases/anim_damage/anim_damage.atlas", TextureAtlas.class);
        ATLAS_HEAL = new AssetDescriptor<TextureAtlas>("atlases/anim_health/anim_health.atlas", TextureAtlas.class);
        ATLAS_PLUS_MINUS = new AssetDescriptor<TextureAtlas>("atlases/anim_plus_minus/plus_minus_b.atlas", TextureAtlas.class);
        ATLAS_RESTART = new AssetDescriptor<TextureAtlas>("atlases/anim_restart/anim_restart.atlas", TextureAtlas.class);
    }
    // Font //
    static {
        MY_FONT = new AssetDescriptor<BitmapFont>("BimapFont/MyBitmapFont.fnt", BitmapFont.class);
    }

    private ManagerResources() {

        ASSET_MANAGER = new AssetManager();
        ASSET_MANAGER.load(ATLAS_DAMAGE);
        ASSET_MANAGER.load(ATLAS_HEAL);
        ASSET_MANAGER.load(ATLAS_PLUS_MINUS);
        ASSET_MANAGER.load(ATLAS_RESTART);
        ASSET_MANAGER.load(HISTORY_ROW_BACKGROUND);
        ASSET_MANAGER.load(BACKGROUND);
        ASSET_MANAGER.load(BACKGROUND_HEALTH_CHOICE);
        ASSET_MANAGER.load(BACKGROUND_HEALTH_CHOICE_PICK);
        ASSET_MANAGER.load(MIDDLE_BAR);
        ASSET_MANAGER.load(MIDDLE_HISTORY_BAR);

        ASSET_MANAGER.load(ICON_HISTORY);
        ASSET_MANAGER.load(ICON_HISTORY_HOVER);
        ASSET_MANAGER.load(ICON_RESTART);
        ASSET_MANAGER.load(ICON_RESTART_HOVER);
        ASSET_MANAGER.load(ICON_INIT_LIFE);
        ASSET_MANAGER.load(ICON_INIT_LIFE_HOVER);
        ASSET_MANAGER.load(ICON_DICE);
        ASSET_MANAGER.load(ICON_DICE_HOVER);

        ASSET_MANAGER.load(ICON_BACK_HISTORY);
        ASSET_MANAGER.load(ICON_BACK_HISTORY_HOVER);

        ASSET_MANAGER.load(DICE);
        ASSET_MANAGER.load(DICE_POINT);
        ASSET_MANAGER.load(MY_FONT);

    }

    public static ManagerResources getInstance() {
        return MANAGER_RESOURCES == null ? loadNewAssets() : MANAGER_RESOURCES;
    }

    public AssetManager getAssetManager() {
        return ASSET_MANAGER;
    }

    public <T> T getResource(AssetDescriptor<T> assetDescriptor) {
        return getAssetManager().get(assetDescriptor);
    }

    public static ManagerResources loadNewAssets() {
        MANAGER_RESOURCES = new ManagerResources();
        return MANAGER_RESOURCES;
    }
}
