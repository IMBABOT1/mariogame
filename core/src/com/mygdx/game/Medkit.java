package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Hero;

public class Medkit {

    private Texture texture;
    private Vector2 position;
    private boolean isActive;
    private float time = 0;
    private float maxTime = 20.0f;

    public Circle getHitArea() {
        return hitArea;
    }

    private Circle hitArea;
    private float scale;


    public Medkit(Texture texture, Vector2 position, Circle circle) {
        this.texture = texture;
        this.position = new Vector2(0, 0);
        this.hitArea = new Circle(position, 60);
        this.isActive = true;
    }


    public void prepare() {
        position.set(MathUtils.random(0, 1280), MathUtils.random(300, 350));
        hitArea.setPosition(position);
        scale = MathUtils.random(0.2f, 0.6f);
        hitArea.radius = 60 * scale;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 37, position.y - 37, 37, 37, 74, 74, scale, scale, 0, 0, 0, 74, 74, false, false);
    }

    public void update(float dt, Hero hero) {
        if (hero.getHitArea().overlaps(hitArea)) {
            hero.addLive();
            position.set(-100, -100);
            isActive = false;
            time += dt;
            System.out.println(time);
            if (isActive) {
                if (time > maxTime) ;
                prepare();
            }
        }
        hitArea.setPosition(position);
        heal(dt, hero);
    }



    public void heal(float dt, Hero hero) {
        if (hero.getHitArea().overlaps(hitArea)) {
            isActive = false;
        }
        if (!isActive) {
            time += dt;;
            if (time > maxTime) {
                time = 0;
                prepare();
            }
        }
    }
}