package com.jumpdontdie.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jumpdontdie.MainGame;
import com.jumpdontdie.BaseScreen;

public class Screen2DScreen extends BaseScreen {

    private Stage stage;
    private ActorJugador jugador;
    private ActorPinches spike;
    private Texture texturaJugador, texturaspike;
    private Screen2DScreen mainGameScreen;
    private TextureRegion regionspike;

    public Screen2DScreen(MainGame game) {
        super(game);
        texturaJugador = new Texture("player.png");
        texturaspike = new Texture("spike.png");
        regionspike = new TextureRegion(texturaspike, 0, 64, 128, 64);
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true); // marca bordes de objetos
        jugador = new ActorJugador(texturaJugador);
        spike = new ActorPinches(regionspike);
        stage.addActor(jugador);
        stage.addActor(spike);
        jugador.setPosition(20, 100);
        spike.setPosition(500, 100);
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
                (jugador.getX() + jugador.getWidth()) > spike.getX()){
            System.out.println("Colision");
            jugador.setAlive(false);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
