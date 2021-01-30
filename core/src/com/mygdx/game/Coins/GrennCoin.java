package com.mygdx.game.Coins;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Hero;

public class GrennCoin extends Coins{


        public GrennCoin(Texture texture, Vector2 position, Circle circle) {
            super(texture, position, circle);
        }

        @Override
        public void prepare() {
            super.prepare();
        }

        @Override
        public void render(SpriteBatch batch) {
            super.render(batch);
        }

        @Override
        public void update(float dt, Hero hero) {
            super.update(dt, hero);
        }
    }


