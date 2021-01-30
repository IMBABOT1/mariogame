package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PowerUpsEmitter {
    public PowerUp[] getPowerUps() {
        return powerUps;
    }

    private PowerUp[] powerUps;
    private TextureRegion[] textureRegions;

    public PowerUpsEmitter(TextureRegion texture){
        textureRegions = new TextureRegion(texture).split(32, 32)[0];
        powerUps = new PowerUp[20];
        for (int i = 0; i < powerUps.length ; i++) {
            powerUps[i] = new PowerUp();
        }
    }

    public void render(SpriteBatch batch){
        for (int i = 0; i < powerUps.length ; i++) {
            if (powerUps[i].isActive()){
                batch.draw(textureRegions[powerUps[i].getType().getImagePosition()], powerUps[i].getPosition().x, powerUps[i].getPosition().y);
            }
        }
    }

    public void tryToCreatePowerUP(float x, float y, float probability){
        if (Math.random() < probability){
            for (int i = 0; i < powerUps.length; i++) {
                if (!powerUps[i].isActive()){
                    powerUps[i].activate(x, y);
                    break;
                }
            }
        }
    }

    public void update(float dt){
        for (int i = 0; i <powerUps.length ; i++) {
            if (powerUps[i].isActive()){
                powerUps[i].update(dt);
            }
        }
    }
}
