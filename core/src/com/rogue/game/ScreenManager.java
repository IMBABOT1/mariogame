package com.rogue.game;

public class ScreenManager {
    public enum ScreenType{
        MENU, GAME;
    }

    private static final ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance(){
        return ourInstance;
    }

    private ScreenManager(){

    }
}
