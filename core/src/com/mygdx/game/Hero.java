package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class Hero {
    private Map map;
    private Texture texture;
    private Texture blood;
    private TextureRegion[] regions;
    private Vector2 position;
    private Vector2 tempPosition;



    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    private Vector2 velocity;
    private float animationTime;
    private boolean right;
    private int maxHp;

    private int score;


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    private int hp;

    private static final int RADIUS = 35;

    public Circle getHitArea() {
        return hitArea;
    }

    private Circle hitArea;


    public Hero(Map map, float x, float y) {
        this.map = map;
        this.texture = new Texture("runner.png");
        this.blood = new Texture("blood.png");
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.tempPosition = new Vector2(0, 0);
        this.animationTime = 0.0f;
        this.regions = new TextureRegion(texture).split(100, 100)[0];
        this.right = true;
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.hitArea = new Circle(position, RADIUS);
        this.score = 0;
    }

    public void update(float dt) {
        // animationTime += dt * 10.0f;
        if (hp > 0) {
            velocity.add(0, -600.0f * dt);
            tempPosition.set(position);
            tempPosition.add(50, 50);
            velocity.x *= 0.8f;

            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velocity.x = 240.0f;
                right = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velocity.x = -240.0f;
                right = false;
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
                tempPosition.x += dx;
                if (checkCollision(tempPosition)) {
                    tempPosition.x -= dx;
                    velocity.x = 0.0f;
                }
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && velocity.y == 0) {
                velocity.y = 500.0f;
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
    }

    public void takeDamage(int damage){
        hp -= damage;
    }


    public void getScore(int sc){
        score += sc;
    }





    public boolean checkCollision(Vector2 position){
        for (float i = 0; i <6.28 ; i+= 0.1f) {
            if (!map.checkSpaceIsEmpty(position.x + RADIUS * (float) Math.cos(i), position.y + RADIUS * (float) Math.sin(i))){
                return true;
            }
        }
        return false;
    }



    public void render(SpriteBatch batch) {
       // batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);

        if (hp > 0) {
            int frameIndex = getCurrentFrame();
            if (!right && !regions[frameIndex].isFlipX()) {
                regions[frameIndex].flip(true, false);
            }
            if (right && regions[frameIndex].isFlipX()) {
                regions[frameIndex].flip(true, false);
            }
            batch.draw(regions[frameIndex], position.x, position.y, 50, 50, 100, 100, 1, 1, 0);
        }

        if (hp == 0){
            batch.draw(blood, position.x, position.y, 0, 0, 100, 100, 1.0f, 1.0f, 0, 0, 0, 100, 100, false, false);
        }
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font){
        font.draw(batch, "HP: " + hp + " / " + maxHp, 20, 700);
    }

    public void renderScore(SpriteBatch batch, BitmapFont font){
        font.draw(batch, "Score: " + score + "", 900, 700);
    }

    public int getCurrentFrame(){
        return (int) animationTime % 6;
    }
}