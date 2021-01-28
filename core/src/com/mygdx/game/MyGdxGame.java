package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class MyGdxGame extends ApplicationAdapter {
	private static final char SYMB_GRASS = 'g';
	SpriteBatch batch;
	Texture img;
	char[][] map;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("ground.png");
		map = new char[32][18];
		generateMap();
	}


	public void fillGroundPart(int x1, int x2, int height){
		if (x2 > 31) x2 = 31;
		for (int i = x1; i <=x2 ; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = SYMB_GRASS;
			}
		}
	}

	public void generateMap(){
		int height = 2;
		int position = 0;
		fillGroundPart(0, 3, height);
		position = 4;
		while (position < 32){
			int len = MathUtils.random(3, 6);
			height += MathUtils.random(-1, 1);
			 if (height < 1) height = 1;
			 fillGroundPart(position, position + len-1, height);
			 position +=  len;
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.9f, 0.9f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 18; j++) {
				if (map[i][j] == SYMB_GRASS) {
					batch.draw(img, i * 40, j * 40);
				}
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
