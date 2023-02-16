package com.jumpdontdie.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorPinches extends Actor {

    private TextureRegion pinchos;

    public ActorPinches(TextureRegion pinchos) {
        this.pinchos = pinchos;
        setSize(pinchos.getRegionWidth(), pinchos.getRegionHeight());
    }

    @Override
    public void act(float delta) {
       setX(getX() - 250 * delta); // 500 / 2 = 250. 2 segundos
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pinchos, getX(), getY());
    }
}
