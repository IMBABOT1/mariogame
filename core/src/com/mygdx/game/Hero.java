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
        velocity.add(0, -400.0f *dt);
        position.mulAdd(velocity, dt);
        checkSide();
         if (Gdx.input.isKeyPressed(Input.Keys.D)) {
             velocity.x = 10000 * dt;
         }
         if (Gdx.input.isKeyPressed(Input.Keys.A)) {
             velocity.x = -10000 * dt;
         }
         if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                velocity.y = 8000 * dt;
         }
    }


    public void checkSide() {
        for (int i = 25; i <= 75; i += 10) {
            int temp = map.checkSpaceIsEmpty(position.x + i, position.y, "DOWN");
            if (temp != -1) {
                position.y = temp;
                velocity.y = 0;
            }
        }
        for (int i = 25; i <= 75; i += 10) {
            int temp = map.checkSpaceIsEmpty(position.x + i, position.y + 100, "UP");
            if (temp != -1) {
                position.x = temp;
            }
        }
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
    }
}