package com.jumpdontdie.entities;

import static com.jumpdontdie.Constantes.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jumpdontdie.Constantes;

import org.w3c.dom.Text;

public class PlayerEntity extends Actor {

    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;

    private boolean alive = true, jumping = false, mustJump = false;

    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        // Create the player body.
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        // Give it some shape.
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("player");
        box.dispose();

        // Set the size to a value that is big enough to be rendered on the screen.
        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                    (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        // salta si tocamos poantalla
        if (Gdx.input.justTouched()) {
            jump();
        }
        if (mustJump) {
            mustJump = false;
            jump();
        }
        if (alive) {
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(PLAYER_SPEED, speedY);
        }
        if (jumping) {
            body.applyForceToCenter(0, -IMPULSE_JUMP * 1.15f, true);
        }
    }

    public void jump() {
        if (!jumping && alive) {
            jumping = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, IMPULSE_JUMP, position.x, position.y, true);
        }
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isMustJump() {
        return mustJump;
    }

    public void setMustJump(boolean mustJump) {
        this.mustJump = mustJump;
    }

    /*
    * Box2d -> mts
    * Scene2d -> px
    * si 4mt son 360 px = X
    * pasar de metros a pixeles
    * */
}
