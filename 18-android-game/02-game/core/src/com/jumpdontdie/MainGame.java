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

    @Override
    public void create() {
        Procesador p = new Procesador();
        Gdx.input.setInputProcessor(p);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
            System.out.println("Estás tocando la pantalla");
        }

     }




}
