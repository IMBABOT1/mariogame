package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Monster extends BaseUnit  {



    private float time = 0;
    private float maxTime = 5.0f;


    public Monster(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        super(gameScreen, map, original, 100, 120.0f, 1.0f , 35, x, y, 100, 100, true);

    }

    public Circle getHitArea(){
        return hitArea;
    }

    @Override
    public void update(float dt) {
        fire(dt, false);
        if (Math.abs(gameScreen.getHero().getPosition().x - position.x) > 100) {
            if (gameScreen.getHero().getPosition().x < position.x) {
                moveLeft();
            }
            if (gameScreen.getHero().getPosition().x > position.x) {
                moveRight();
            }
        }
        super.update(dt);
        if (Math.abs(gameScreen.getHero().getPosition().x - position.x) > 100) {
                if (Math.abs(velocity.x) < 0.1f) {
                    jump();
                }
        }
        destroy(dt);
    }

    public void hit(int damage){
        hp -= damage;
        System.out.println(hp);
        if (hp == 0){
            hp = 0;
            isAlive = false;
            position.x = -100;
            position.y = -100;
        }
    }



    public void destroy(float dt){
        if (!isAlive) {
        time +=dt;
        System.out.println(time);
        if (time > maxTime) {
            time = 0;
                position.x = MathUtils.random(100, 1200);
                position.y = MathUtils.random(300, 400);
                hp = maxHp;
                isAlive = true;
            }
        }
    }



    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.RED);
        super.render(batch);
        batch.setColor(Color.WHITE);
    }


}