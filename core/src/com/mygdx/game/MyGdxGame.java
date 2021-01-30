package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Coins.Coins;
import com.mygdx.game.Coins.RedCoin;
import com.mygdx.game.MyGdxGame;


public class MyGdxGame extends ApplicationAdapter  {

	private SpriteBatch batch;
	private Map map;
	private Hero hero;
	private BitmapFont font;
	private BitmapFont score;
	private Trash[] trashes;
	private Coins[] coins;
	private Vector2 redPosition;

	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map();
		map.generateMap();
		hero = new Hero(map, 300, 300);
		redPosition = new Vector2(0, 0);
		generateFonts();
		generateScoreFont();
		Texture texture = new Texture("asteroid64.png");
		Texture redCoin = new Texture("coin2.png");
		trashes = new Trash[30];
		coins = new Coins[5];
		for (int i = 0; i < trashes.length ; i++) {
			trashes[i] = new Trash(texture);
			trashes[i].prepare();
		}
		for (int i = 0; i < coins.length ; i++) {
			coins[i] = new RedCoin(redCoin,  redPosition.set(MathUtils.random(0, 1280), MathUtils.random(250, 300)), new Circle(redPosition, 60));
			coins[i].prepare();
		}
	}



	public void generateFonts(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameters.size = 48;
		parameters.color = Color.WHITE;
		parameters.borderWidth = 2;
		parameters.borderColor = Color.BLACK;
		parameters.shadowOffsetX = 2;
		parameters.shadowOffsetY = 2;
		parameters.shadowColor = Color.BLACK;
		font = generator.generateFont(parameters);
		generator.dispose();
	}

	public void generateScoreFont(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameters.size = 48;
		parameters.color = Color.WHITE;
		parameters.borderWidth = 2;
		parameters.borderColor = Color.BLACK;
		parameters.shadowOffsetX = 2;
		parameters.shadowOffsetY = 2;
		parameters.shadowColor = Color.BLACK;
		font = generator.generateFont(parameters);
		generator.dispose();
	}



	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0.9f, 0.9f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		for (int i = 0; i <trashes.length ; i++) {
			trashes[i].render(batch);
		}
		for (int i = 0; i <coins.length ; i++) {
			coins[i].render(batch);
		}
		hero.render(batch);
		hero.renderGUI(batch, font);
		hero.renderScore(batch, font);
		batch.end();
	}

	public void update(float dt){
		map.update(dt);
		hero.update(dt);
		for (int i = 0; i < trashes.length ; i++) {
			trashes[i].update(dt);
			if (hero.getHitArea().overlaps(trashes[i].getHitArea())){
				trashes[i].prepare();
				hero.takeDamage(5);
			}
		}

		for (int i = 0; i < coins.length; i++) {
			coins[i].update(dt, hero);
		}
	}



	@Override
	public void dispose () {
		batch.dispose();

	}
}
