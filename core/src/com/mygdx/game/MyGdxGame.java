package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class MyGdxGame extends ApplicationAdapter {

	private SpriteBatch batch;
	private Map map;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map();
		map.generateMap();
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0.9f, 0.9f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
