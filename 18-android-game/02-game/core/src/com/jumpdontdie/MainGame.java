package com.jumpdontdie;

import com.badlogic.gdx.Game;
import com.jumpdontdie.screens.Box2DScreen;
import com.jumpdontdie.screens.MainGameScreen;

public class MainGame extends Game {
    // game extends de appAdapter

    @Override
    public void create() {
        setScreen(new Box2DScreen(this));
    }



}
