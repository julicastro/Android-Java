package com.jumpdontdie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpdontdie.BaseScreen;
import com.jumpdontdie.MainGame;


public class Box2DScreen extends BaseScreen {

    // body = object
    /*
    hay q dimensionar la camara para q el body (objeto) no quede tan chico
    en proporsion al ambiente.
    Tener en cuenta q la camara se pasan px y en el body se pasan metros
    debemos DIMENSIONAR. O sea, establecer cuantos metros sería la camara,
    es decir el ambiente en el q se maneja el body.
    DIMENSIONAR:
    camara = alto 360px, ancho 640px. Supongamos q la queremos de 4 metros
    Hacemos regla de 3: 640 * 4 / 360
    quiero utilizar 16:9. y tener 4 metros. hago lo siguiente
    4*(16/9) y esto es igual a 7.11
    Alto
     */

    public Box2DScreen(MainGame game) {
        super(game);
    }

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera camara;

    private Body caritaBody, sueloBody, pinchoBody;

    private Fixture caritaFixture, sueloFixture, pinchoFixture;

    private boolean debeSaltar, caritaSaltando, caritaVivo = true;


    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camara = new OrthographicCamera(16, 9);
        camara.translate(0, 1); // metodo q mueve la camara

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor"))
                        || (fixtureB.getUserData().equals("floor") && fixtureA.getUserData().equals("player"))) {
                    if (Gdx.input.isTouched()) {
                        debeSaltar = true;
                    }
                    caritaSaltando = false;
                }
                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("spike"))
                        || (fixtureB.getUserData().equals("spike") && fixtureA.getUserData().equals("player"))) {
                    caritaVivo = false;
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

        caritaBody = world.createBody(createCaritaBodyDef());
        sueloBody = world.createBody(createSueloBodyDef());
        pinchoBody = world.createBody(createPinchoBodyDef(1f));
        /* user data = asociar cuerpos a elementos box2d*/
        //CircleShape
        PolygonShape caritaShape = new PolygonShape();
        caritaShape.setAsBox(0.5f, 0.5f); // funciona en metros
        // luego se puede escalar
        caritaFixture = caritaBody.createFixture(caritaShape, 1);
        caritaShape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500, 1); // SE MULTIPLICA X 2 (1km, 1m);
        sueloFixture = sueloBody.createFixture(sueloShape, 1);
        sueloShape.dispose();

        pinchoFixture = createPinchoFixture(pinchoBody);

        caritaFixture.setUserData("player");
        sueloFixture.setUserData("floor");
        pinchoFixture.setUserData("spike");

    }

    private BodyDef createPinchoBodyDef(float x) {
        BodyDef def = new BodyDef();
        def.position.set(5f, 0.5f);
        return def;
    }

    private BodyDef createSueloBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, -1);
        // x defecto son estaticos
        return def;
    }

    private BodyDef createCaritaBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, 0);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    private Fixture createPinchoFixture(Body pinchoBody) {
        // triangulo. array de 3 xq el triamgulo tiene 3 lados.
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        Fixture fix = pinchoBody.createFixture(shape, 1);
        shape.dispose();
        return fix;

    }

    @Override
    public void dispose() {
        sueloBody.destroyFixture(sueloFixture);
        caritaBody.destroyFixture(caritaFixture);
        pinchoBody.destroyFixture(pinchoFixture);
        world.destroyBody(caritaBody);
        world.destroyBody(sueloBody);
        world.destroyBody(pinchoBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (debeSaltar) {
            debeSaltar = false;
            saltar();
        }
        if (Gdx.input.justTouched() && !caritaSaltando) {
            debeSaltar = true;
        }
        if (caritaVivo) {
            float velocidadY = caritaBody.getLinearVelocity().y;
            caritaBody.setLinearVelocity(8, velocidadY);
        }
        world.step(delta, 6, 2);
        camara.update(); // actualizar camara para generar matriz
        renderer.render(world, camara.combined);
    }

    private void saltar() {
        // impulse = de golpe
        // force = gradual
        Vector2 position = caritaBody.getPosition();
        caritaBody.applyLinearImpulse(0, 8, position.x, position.y, true);
    }
}

