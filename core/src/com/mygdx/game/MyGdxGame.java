package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Coins.GrennCoin;
import com.mygdx.game.Coins.OrangeCoin;
import com.mygdx.game.Coins.PurpleCoin;
import com.mygdx.game.Coins.RedCoin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MyGdxGame extends ApplicationAdapter  {

	private SpriteBatch batch;
	private Map map;
	private Hero hero;
	private BitmapFont font;
	private Trash[] trashes;
	private RedCoin[] redCoins;
	private Vector2 redPosition;
	private OrangeCoin[] orange;
	private Vector2 orangePosition;
	private Vector2 greenPosition;
	private GrennCoin[] grennCoins;
	private Vector2 purplePosition;
	private PurpleCoin[] purpleCoins;


	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map();
		map.generateMap();
		hero = new Hero(map, 300, 300);
		redPosition = new Vector2(0, 0);
		orangePosition = new Vector2(0, 0);
		greenPosition = new Vector2(0, 0);
		purplePosition = new Vector2(0, 0);
		generateFonts();
		Texture texture = new Texture("asteroid64.png");
		Texture redCoin = new Texture("coin2.png");
		Texture orangeCoin = new Texture("coin1.png");
		Texture greenCoin = new Texture("coin3.png");
		Texture purpleCoin = new Texture("coin4.png");
		trashes = new Trash[30];
		redCoins = new RedCoin[5];
		orange = new OrangeCoin[1];
		grennCoins = new GrennCoin[3];
		purpleCoins = new PurpleCoin[10];
		for (int i = 0; i < trashes.length ; i++) {
			trashes[i] = new Trash(texture);
			trashes[i].prepare();
		}
		for (int i = 0; i < redCoins.length ; i++) {
			redCoins[i] = new RedCoin(redCoin,  redPosition.set(MathUtils.random(0, 1280), MathUtils.random(150, 420)), new Circle(redPosition, 60));
			redCoins[i].prepare();
		}
		for (int i = 0; i < orange.length ; i++) {
			orange[i] = new OrangeCoin(orangeCoin,  orangePosition.set(MathUtils.random(0, 1280), MathUtils.random(150, 420)), new Circle(orangePosition, 60));
			orange[i].prepare();
		}

		for (int i = 0; i < grennCoins.length ; i++) {
			grennCoins[i] = new GrennCoin(greenCoin,  greenPosition.set(MathUtils.random(0, 1280), MathUtils.random(150, 420)), new Circle(greenPosition, 60));
			grennCoins[i].prepare();
		}

		for (int i = 0; i < purpleCoins.length ; i++) {
			purpleCoins[i] = new PurpleCoin(purpleCoin,  purplePosition.set(MathUtils.random(0, 1280), MathUtils.random(150, 420)), new Circle(greenPosition, 60));
			purpleCoins[i].prepare();
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
		for (int i = 0; i <redCoins.length ; i++) {
			redCoins[i].render(batch);
		}
		for (int i = 0; i <orange.length ; i++) {
			orange[i].render(batch);
		}
		for (int i = 0; i <grennCoins.length ; i++) {
			grennCoins[i].render(batch);
		}
		for (int i = 0; i <purpleCoins.length ; i++) {
			purpleCoins[i].render(batch);
		}
		hero.render(batch);
		hero.renderGUI(batch, font);
		hero.renderScore(batch, font);
		batch.end();
	}

	public void update(float dt) {
		map.update(dt);
		hero.update(dt);
		for (int i = 0; i < trashes.length; i++) {
			trashes[i].update(dt);
			if (hero.getHitArea().overlaps(trashes[i].getHitArea())) {
				trashes[i].prepare();
				hero.takeDamage(5);
			}
		}

		for (int i = 0; i < redCoins.length; i++) {
			if (hero.getHitArea().overlaps(redCoins[i].getHitArea())) {
				redCoins[i].update(dt, hero);
				hero.getScore(25);
			}
		}

		for (int i = 0; i < orange.length; i++) {
			if (hero.getHitArea().overlaps(orange[i].getHitArea())) {
				orange[i].update(dt, hero);
				hero.getScore(100);
			}
		}

		for (int i = 0; i < grennCoins.length; i++) {
			if (hero.getHitArea().overlaps(grennCoins[i].getHitArea())) {
				grennCoins[i].update(dt, hero);
				hero.getScore(50);
			}
		}

		for (int i = 0; i < purpleCoins.length; i++) {
			if (hero.getHitArea().overlaps(purpleCoins[i].getHitArea())) {
				purpleCoins[i].update(dt, hero);
				hero.getScore(10);
			}
		}
		restartGame();

	}

	public void restartGame(){
		if (hero.getHp() <= 0) {
			hero.setHp(0);
		}
		if (hero.getHp() == 0){
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				hero.setVelocity(new Vector2(0, 0));
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				hero.setVelocity(new Vector2(0, 0));
			}
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
