package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.*;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
    public static MyGdxGame myGdxGame = new MyGdxGame();

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;;
        new LwjglApplication(myGdxGame, config);;
    }

}
