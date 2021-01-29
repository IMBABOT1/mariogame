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
        velocity.add(0, -600.0f * dt);
        tempPosition.set(position);
        tempPosition.add(50, 50);
        velocity.x *= 0.8f;

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            velocity.x = 240.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            velocity.x = -240.0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            velocity.y = 400.0f;
        }

        float len = velocity.len() * dt;
        float dx = velocity.x * dt / len;
        float dy = velocity.y * dt / len;
        for (int i = 0; i < len; i++) {
            tempPosition.y += dy;
            if (checkCollision(tempPosition)) {
                tempPosition.y -= dy;
                velocity.y = 0.0f;
            }
            tempPosition.x = dx;
            if (checkCollision(tempPosition)){
                tempPosition.x -= dx;
                velocity.x = 0.0f;
            }
        }

        tempPosition.add(-50, -50);
        position.set(tempPosition);


    }


    public boolean checkCollision(Vector2 pos){
        for (int i = 0; i <6.28; i += 0.1f) {
            if (!map.checkSpaceIsEmpty(pos.x + 30 * (float) Math.cos(i), pos.y + 30 * (float) Math.sin(i))){
                return true;
            }
        }
        return false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
    }
}