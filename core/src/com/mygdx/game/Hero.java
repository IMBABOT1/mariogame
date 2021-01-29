package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Map map;
    private Texture texture;
    private Vector2 position;
    private Vector2 tempPosition;
    private Vector2 velocity;


    public Hero(Map map, float x, float y) {
        this.map = map;
        this.texture = new Texture("runner.png");
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.tempPosition = new Vector2(0, 0);
    }

    public void update(float dt) {
        velocity.set(0, -50);
        tempPosition.set(position);
        tempPosition.mulAdd(velocity, dt);

        if (checkMovement(position, dt)) {
            position.set(tempPosition);
        }
    }


    public boolean checkMovement(Vector2 position, float dt){
        for (int i = 0; i <= 5 ; i++) {
            if (!map.checkSpaceIsEmpty(position.x + 25 + i * 10, position.y)){
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    velocity.y = 50;
                    velocity.x += 300;
                    position.mulAdd(velocity, dt);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    velocity.y = 50;
                    velocity.x -= 300;
                    position.mulAdd(velocity, dt);
                }
                return false;
            }
        }
        return true;
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
    }
}