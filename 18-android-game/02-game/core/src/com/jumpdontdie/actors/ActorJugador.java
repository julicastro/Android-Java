package com.jumpdontdie.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJugador extends Actor {

    private Texture jugador;
    private boolean alive;

    public ActorJugador(Texture jugador) {
        this.jugador = jugador;
        this.alive = true;
        setSize(jugador.getWidth(), jugador.getHeight());
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void act(float delta) {
        // delta actualiza actor
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(jugador, getX(), getY());
    }
}
