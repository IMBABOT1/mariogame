package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BulletEmitter extends ObjectPool<Bullet> {
    private TextureRegion bulletTexture;

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected Bullet newObject1(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        return null;
    }

    public BulletEmitter(TextureRegion bulletsTexture, int size) {
        super(size);
        this.bulletTexture = bulletsTexture;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            batch.draw(bulletTexture, activeList.get(i).getPosition().x - 24, activeList.get(i).getPosition().y - 24, 24, 24, 48, 48, 0.2f, 0.2f, 0.0f);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
    }

    public void setup(boolean isPlayersBullet, float x, float y, float vx, float vy) {
        Bullet b = getActiveElement();
        b.activate(isPlayersBullet, x, y, vx, vy);
    }
}