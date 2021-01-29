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
        position.mulAdd(velocity, dt);
        position.set(position.x, position.y);
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            velocity.x += 300;
            position.mulAdd(velocity, dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            velocity.x -= 300;
            position.mulAdd(velocity, dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            velocity.y += 300;
            position.mulAdd(velocity, dt);
        }
    }






    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
    }
}