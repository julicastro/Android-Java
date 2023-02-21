package com.jumpdontdie;

import static com.jumpdontdie.Constantes.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jumpdontdie.entities.FloorEntity;
import com.jumpdontdie.entities.PlayerEntity;
import com.jumpdontdie.entities.SpikeEntity;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen {

    private Stage stage;

    private World world;

    private PlayerEntity player;

    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();

    private List<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();

    private Vector3 position;

    private Sound jumpSound, dieSound;

    private Music backgroundMusic;

    public GameScreen(MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 360));
        position = new Vector3(stage.getCamera().position);

        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new GameContactListener());

        jumpSound = game.getManager().get("audio/jump.ogg");
        dieSound = game.getManager().get("audio/die.ogg");
        backgroundMusic = game.getManager().get("audio/song.ogg");
    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("player.png");
        Texture floorTexture = game.getManager().get("floor.png");
        Texture overfloorTexture = game.getManager().get("overfloor.png");
        Texture spikeTexture = game.getManager().get("spike.png");
        player = new PlayerEntity(world, playerTexture, new Vector2(1.5f, 1.5f));

        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, 0, 1000, 1));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, 12, 10, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, 30, 10, 3.1f));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, 40, 5, 4));

        spikeList.add(new SpikeEntity(world, spikeTexture, 10, 1));
        spikeList.add(new SpikeEntity(world, spikeTexture, 18, 2));
        spikeList.add(new SpikeEntity(world, spikeTexture, 32, 3));
        spikeList.add(new SpikeEntity(world, spikeTexture, 50, 1));


        stage.addActor(player);
        for (FloorEntity floor : floorList) {
            stage.addActor(floor);
        }
        for (SpikeEntity spike : spikeList) {
            stage.addActor(spike);
        }

        backgroundMusic.setVolume(0.50f);
        backgroundMusic.play();
    }

    @Override
    public void hide() {
        stage.clear();

        player.detach();
        for (FloorEntity floor : floorList)
            floor.detach();
        for (SpikeEntity spike : spikeList)
            spike.detach();

        floorList.clear();
        spikeList.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.justTouched()) {
            jumpSound.play();
            player.jump();
        }

        if (player.getX() > 150 && player.isAlive()) {
            float speed = PLAYER_SPEED * delta * PIXELS_IN_METER;
            stage.getCamera().translate(speed, 0, 0);
        }
        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();

        world.dispose();
    }

    private class GameContactListener implements ContactListener {

        private boolean areCollided(Contact contact, Object userA, Object userB) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null) {
                return false;
            }

            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {
            if (areCollided(contact, "player", "floor")) {
                player.setJumping(false);
                if (Gdx.input.isTouched()) {
                    jumpSound.play();
                    player.setMustJump(true);
                }
            }

            if (areCollided(contact, "player", "spike")) {
                if (player.isAlive()) {
                    player.setAlive(false);
                    backgroundMusic.stop();
                    dieSound.play();
                    stage.addAction( // sirve para hacer animaciones
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() { // run ejecuta codigo
                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameOverScreen);
                                        }
                                    })
                            )
                    );
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
            if (areCollided(contact, "player", "floor")) {
                if (player.isAlive()) {
                }
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    }
}
