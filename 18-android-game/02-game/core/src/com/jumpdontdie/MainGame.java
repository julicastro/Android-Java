package com.jumpdontdie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {
    // game extends de appAdapter

    private AssetManager manager;

    public AssetManager getManager() {
        return manager;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("floor.png", Texture.class);
        manager.load("overfloor.png", Texture.class);
        manager.load("spike.png", Texture.class);
        manager.load("player.png", Texture.class);
        manager.finishLoading();

        setScreen(new GameScreen(this));
    }



}
