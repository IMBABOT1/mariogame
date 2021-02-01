package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class BaseUnit {
    protected GameScreen gameScreen;
    protected Map map;
    protected Texture blood;
    protected TextureRegion[] regions;
    protected float firePressTimer;

    public Vector2 getTempPosition() {
        return tempPosition;
    }

    protected Vector2 position;
    protected Vector2 tempPosition;

    public Vector2 getVelocity() {
        return velocity;
    }

    protected Vector2 velocity;
    protected float animationTime;
    protected boolean right;
    protected int maxHp;
    protected int hp;
    protected int width;
    protected int height;
    protected Circle hitArea;
    protected float timeBetweenFire;
    protected Circle getHitArea() {
        return hitArea;
    }
    protected Vector2 getPosition() {
        return position;
    }
    protected float speed;
    protected int radius;

    public boolean isAlive() {
        return isAlive;
    }

    protected boolean isAlive;

    public BaseUnit(GameScreen gameScreen, Map map, TextureRegion original, int maxHp, float speed, float timeBetweenFire, float radius, float x, float y, int width, int height, boolean isAlive) {
        this.gameScreen = gameScreen;
        this.map = map;
        this.width = width;
        this.height = height;
        this.regions = new TextureRegion(original).split(width, height)[0];
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.tempPosition = new Vector2(0, 0);
        this.animationTime = 0.0f;
        this.right = true;
        this.maxHp = maxHp;
        this.hp = this.maxHp;
        this.hitArea = new Circle(position, radius);
        this.timeBetweenFire = timeBetweenFire;
        this.speed = speed;
        this.isAlive = true;
    }

    public void update(float dt) {
        velocity.add(0, -600.0f * dt);
        tempPosition.set(position);
        tempPosition.add(width / 2, height / 2);
        velocity.x *= 0.6f;
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
                animationTime += dt * 50.0f;
            }
        }
        tempPosition.add(-width/2, -height/2);
        position.set(tempPosition);
        hitArea.setPosition(position.x + width / 2, position.y + height/2);
    }

    public void takeDamage(int damage){
        hp -= damage;
    }

    public void moveLeft(){
        velocity.x = -speed;
        right = false;
    }

    public void moveRight(){
        velocity.x = speed;
        right = true;
    }

    public void jump(){
        tempPosition.set(position).add(0, 1);
        if (Math.abs(velocity.y) < 0.1f && checkCollision(tempPosition)) {
            velocity.y = 400.0f;
        }
    }

    public void fire(float dt, boolean isPlayer){
        firePressTimer += dt;
        if (firePressTimer > timeBetweenFire){
            firePressTimer -= timeBetweenFire;
            float bulletVelX = 600.0f;
            if (!right) bulletVelX *= -1;
            gameScreen.getBulletEmitter().setup(isPlayer, position.x + width / 2, position.y + height / 2, bulletVelX, 0);
        }
    }



    public boolean checkCollision(Vector2 position){
        for (float i = 0; i <6.28 ; i+= 0.1f) {
            if (!map.checkSpaceIsEmpty(position.x + hitArea.radius * (float) Math.cos(i), position.y + hitArea.radius * (float) Math.sin(i))){
                return true;
            }
        }
        return false;
    }


    public void render(SpriteBatch batch) {
        // batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
        int frameIndex = getCurrentFrame();
        if (!right && !regions[frameIndex].isFlipX()) {
            regions[frameIndex].flip(true, false);
        }
        if (right && regions[frameIndex].isFlipX()) {
            regions[frameIndex].flip(true, false);
        }
        batch.draw(regions[frameIndex], position.x, position.y, width/2, height/2, width, height, 1, 1, 0);
    }


    public int getCurrentFrame(){
        return (int) animationTime % regions.length;
    }
}