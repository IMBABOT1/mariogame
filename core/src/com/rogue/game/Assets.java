package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import java.util.logging.FileHandler;

public class Assets {
    private static final Assets ourInstance = new Assets();

    private AssetManager assetManager;

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private TextureAtlas atlas;

    public static Assets getInstance(){
        return ourInstance;
    }

    private Assets(){
        assetManager = new AssetManager();
    }

    public void loadAssets(ScreenManager.ScreenType type){
        switch (type){
            case MENU:
                break;
            case GAME:
                FileHandleResolver resolver = new InternalFileHandleResolver();
                    assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
                    assetManager.setLoader(BitmapFont.class, "ttf", new FreetypeFontLoader(resolver));
                    FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter24 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
                    fontParameter24.fontFileName = "zorque.ttf";
                    fontParameter24.fontParameters.size = 24;
                    fontParameter24.fontParameters.color = Color.WHITE;
                    fontParameter24.fontParameters.borderWidth = 1;
                    fontParameter24.fontParameters.borderColor = Color.BLACK;
                    fontParameter24.fontParameters.shadowOffsetX = 3;
                    fontParameter24.fontParameters.shadowOffsetY = 3;
                    fontParameter24.fontParameters.shadowColor = Color.BLACK;
                    assetManager.load("zorgue24.ttf", BitmapFont.class, fontParameter24);
                    assetManager.load("newPack.pack", TextureAtlas.class);
                    break;
                 }
                 atlas = assetManager.get("mainPack.pack", TextureAtlas.class);
              }

    public void clear(){
        assetManager.clear();
    }
}
