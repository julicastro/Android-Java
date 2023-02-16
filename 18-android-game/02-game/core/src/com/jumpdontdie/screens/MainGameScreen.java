package com.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jumpdontdie.MainGame;
import com.jumpdontdie.actors.ActorJugador;
import com.jumpdontdie.actors.ActorPinches;

public class MainGameScreen extends BaseScreen {

    private Stage stage;
    private ActorJugador jugador;
    private ActorPinches pinchos;
    private Texture texturaJugador, texturaPinchos;
    private MainGameScreen mainGameScreen;
    private TextureRegion regionPinchos;

    public MainGameScreen(MainGame game) {
        super(game);
        texturaJugador = new Texture("carita.png");
        texturaPinchos = new Texture("pinches.png");
        regionPinchos = new TextureRegion(texturaPinchos, 0, 64, 128, 64);
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true); // marca bordes de objetos
        jugador = new ActorJugador(texturaJugador);
        pinchos = new ActorPinches(regionPinchos);
        stage.addActor(jugador);
        stage.addActor(pinchos);
        jugador.setPosition(20, 100);
        pinchos.setPosition(500, 100);
    }

    @Override
    public void hide() {
        stage.dispose();
        texturaJugador.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(); // actualiza los actores
        comprobarColisiones();
        stage.draw();
    }

    private void comprobarColisiones(){
        if (jugador.isAlive() &&
                (jugador.getX() + jugador.getWidth()) > pinchos.getX()){
            System.out.println("Colision");
            jugador.setAlive(false);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
