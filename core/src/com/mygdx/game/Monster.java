package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Monster {
    private Map map;
    private Vector2 position;
    private Vector2 velocity;
    private TextureRegion[] regions;
    private Vector2 tempPosition;
    private float animationTime;
    private boolean right;
    private Circle hitArea;
    private static final int RADIUS = 35;
    private Hero hero;



    public Monster(Map map, TextureRegion original, float x, float y, Hero hero) {
        this.map = map;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.tempPosition = new Vector2(0, 0);
        this.animationTime = 0.0f;
        this.regions = new TextureRegion(original).split(100, 100)[0];
        this.right = true;
        this.hitArea = new Circle(position, RADIUS);
        this.hero = hero;

    }

    public void render(SpriteBatch batch){
        int frameIndex = getCurrentFrame();
        batch.draw(regions[frameIndex], position.x, position.y, 50, 50, 100, 100, 1, 1, 0);
    }


    public boolean checkCollision(Vector2 position){
        for (float i = 0; i <6.28 ; i+= 0.1f) {
            if (!map.checkSpaceIsEmpty(position.x + RADIUS * (float) Math.cos(i), position.y + RADIUS * (float) Math.sin(i))){
                return true;
            }
        }
        return false;
    }

    public void update(float dt){
        velocity.add(0, -600.0f * dt);
        tempPosition.set(position);
        tempPosition.add(50, 50);
        velocity.x *= 0.8f;







        float len = velocity.len() * dt;
        float dx = velocity.x * dt / len;
        float dy = velocity.y * dt / len;
        for (int i = 0; i < len; i++) {
            tempPosition.y += dy;
            if (checkCollision(tempPosition)) {
                tempPosition.y -= dy;
                velocity.y = 0.0f;
            }
            tempPosition.x += dx;
            if (checkCollision(tempPosition)) {
                tempPosition.x -= dx;
                velocity.x = 0.0f;
            }
        }





        if (Math.abs(velocity.x) > 1.0f) {
            if (Math.abs(velocity.y) < 1.0f) {
                animationTime += (Math.abs(velocity.x) / 1200.0f);
            }
        } else {
            if (getCurrentFrame() > 0) {
                animationTime += dt * 10.0f;
            }
        }
        tempPosition.add(-50, -50);
        position.set(tempPosition);
        hitArea.setPosition(position.x + 50, position.y + 50);
    }




    public int getCurrentFrame(){
        return (int) animationTime % 6;
    }

}
