package com.jumpdontdie;

import com.badlogic.gdx.InputAdapter;

public class Procesador extends InputAdapter {

    // ya implemta la interfaz InputProcessor


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Has tocado en la posicion " + screenX + ", " + screenY);
        System.out.println("Has usado el dedo " + pointer + " y el boton " + button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
