package com.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpdontdie.MainGame;
import com.jumpdontdie.screens.BaseScreen;

import org.graalvm.compiler.word.Word;

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

    private Body caritaBody;

    private Fixture caritaFixture;


    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camara = new OrthographicCamera(32, 18);

        BodyDef caritaDef = createCaritaBodyDef();
        caritaBody = world.createBody(caritaDef);
        // no se disposea

        //CircleShape
        PolygonShape caritaShape = new PolygonShape();
        caritaShape.setAsBox(1,1); // funciona en metros
        // luego se puede escalar
        caritaFixture = caritaBody.createFixture(caritaShape, 1);
        caritaShape.dispose();

    }

    private BodyDef createCaritaBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, 10);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    @Override
    public void dispose() {
        world.destroyBody(caritaBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1/60f, 6, 2);

        camara.update(); // actualizar camara para generar matriz
        renderer.render(world, camara.combined);
    }
}

