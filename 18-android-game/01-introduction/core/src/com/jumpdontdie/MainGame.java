package com.jumpdontdie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

public class MainGame extends ApplicationAdapter {

    // ApplicationAdapter = clase con eventos

    // nombre interno de las placas de video para imagenes.
    private Texture carita;
    private Texture pinches;
    private TextureRegion regionPinches; // creamos region para cortar las imagenes
    private SpriteBatch batch; // clase para dibujar cosas en pantalla
    private int width, height;
    private int widthCarita, heightCarita;

    @Override
    public void create() {
        carita = new Texture("carita.png");
        pinches = new Texture("pinches.png");
        batch = new SpriteBatch();
        regionPinches = new TextureRegion(pinches, 0, 64, 128, 64);

        // tamaño de pantalla:
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // tamaño de icono
        widthCarita = carita.getWidth();
        heightCarita = carita.getHeight();
    }

    @Override
    public void dispose() {
        // se ejecuta cuando cerramos el juego
        carita.dispose();
        batch.dispose();
        pinches.dispose();
        // las regiones no se disposean.
    }

    // mostrar y movimiento de imagen
    // representar textura.
    // darle comandos a la tarjeta grafica, las dibujamos de golpe ya q mostrar 1 o 50 tardan lo mismo
    @Override
    public void render() {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
            System.out.println("Estás tocando la pantalla");
        }

        batch.begin();
        batch.draw(carita, 50, 0);
        batch.draw(regionPinches, 250, 0);
        batch.end();

    }

    // las imagenes cargan mejor si su resolucion es una potencia de 2.


}
