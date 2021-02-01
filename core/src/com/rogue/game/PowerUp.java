package com.rogue.game;

import com.badlogic.gdx.math.Vector2;

public class PowerUp {
    public enum Type {
        MONEY_10(0), MONEY_25(1), MONEY_50(2), MEDKIT(3);

        public int getImagePosition() {
            return imagePosition;
        }

        private int imagePosition;

        Type(int imagePosition){
            this.imagePosition = imagePosition;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    private Vector2 position;

    public boolean isActive() {
        return active;
    }

    private boolean active;

    public Type getType() {
        return type;
    }

    private Type type;
    private float startY;
    private float time;
    private float maxTime;

    public PowerUp(){
        this.position = new Vector2(0, 0);
        this.active = false;
        this.type = Type.MONEY_10;
        this.time = 0.0f;
        this.maxTime = 3.0f;
    }

    public void deactivate(){
        active = false;
    }

    public void activate(float x, float y){
        active = true;
        position.set(x, y);
        time = 0.0f;
        startY = y;
        type = Type.values()[(int)(Math.random() * Type.values().length)];
    }

    public void update(float dt){
        time += dt;
        position.y = startY + 15 * (float)Math.sin(time * 3.0f);
        if (time > maxTime){
            deactivate();
        }
    }

    public void use(Hero hero){
        switch (type){
            case MONEY_10:
                hero.addCoins(10);
                break;

            case MONEY_25:
                hero.addCoins(25);
                break;

            case MONEY_50:
                hero.addCoins(50);
                break;
            case MEDKIT:
                hero.addLive();
        }
    }
}