package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen {
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Map map;

    public Hero getHero() {
        return hero;
    }

    private Hero hero;
    private BitmapFont font;
    private Trash[] trashes;

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }
    private Monster monster;
    private BulletEmitter bulletEmitter;
    private PowerUpsEmitter powerUpsEmitter;
    private int counter;
    private ShapeRenderer shapeRenderer;
    private static final boolean DEBUG_MODE = true;
    private Sound sound;




    public GameScreen(SpriteBatch batch){
        this.batch = batch;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas();
        atlas = new TextureAtlas(Gdx.files.internal("mainPack.pack"));
        map = new Map(atlas.findRegion("star16"), atlas.findRegion("ground"));
        map.generateMap();
        hero = new Hero(this, map, atlas.findRegion("runner"),300, 300);
        generateFonts();
        TextureRegion asteroidTexture = atlas.findRegion("asteroid64");
        trashes = new Trash[30];
        monster = new Monster(this, map, atlas.findRegion("runner"), 700, 500);
        for (int i = 0; i < trashes.length ; i++) {
            trashes[i] = new Trash(asteroidTexture);
            trashes[i].prepare();
        }
        sound = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
        bulletEmitter = new BulletEmitter(atlas.findRegion("bullet48"), 0);
        powerUpsEmitter = new PowerUpsEmitter(atlas.findRegion("money"));
        if (DEBUG_MODE) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setAutoShapeType(true);
        }
    }

    public void generateFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 24;
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
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        for (int i = 0; i <trashes.length ; i++) {
            trashes[i].render(batch);
        }
        hero.render(batch);
        monster.render(batch);
        bulletEmitter.render(batch);
        powerUpsEmitter.render(batch);
        hero.renderGUI(batch, font);;
        batch.end();
        if (DEBUG_MODE) {
            shapeRenderer.begin();
            shapeRenderer.circle(hero.getHitArea().x, hero.getHitArea().y, hero.getHitArea().radius);
            shapeRenderer.circle(monster.getHitArea().x, monster.getHitArea().y, monster.getHitArea().radius);
            shapeRenderer.end();

        }

    }


    public void update(float dt) {
        counter++;
        if (counter % 50 == 0){
            powerUpsEmitter.tryToCreatePowerUP(MathUtils.random(0, 1280), MathUtils.random(200, 250), 1);
        }
        map.update(dt);
        hero.update(dt);
        monster.update(dt);
        powerUpsEmitter.update(dt);
        bulletEmitter.update(dt);
        for (int i = 0; i < trashes.length; i++) {
            trashes[i].update(dt);
            if (hero.getHitArea().overlaps(trashes[i].getHitArea())) {
                trashes[i].prepare();
                hero.takeDamage(5);
            }
        }
        for (int i = 0; i < powerUpsEmitter.getPowerUps().length ; i++) {
            PowerUp p = powerUpsEmitter.getPowerUps()[i];
            if (p.isActive() && hero.getHitArea().contains(p.getPosition())){
                p.use(hero);
                p.deactivate();
            }
        }

        for (int i = 0; i < bulletEmitter.getActiveList().size(); i++) {
            if (!map.checkSpaceIsEmpty(bulletEmitter.getActiveList().get(i).getPosition().x, bulletEmitter.getActiveList().get(i).getPosition().y)) {
                Bullet b = bulletEmitter.getActiveList().get(i);
                bulletEmitter.getActiveList().get(i).deactivate();
            }
        }

        for (int i = 0; i < bulletEmitter.getActiveList().size(); i++) {
            Bullet b = bulletEmitter.getActiveList().get(i);
            if (b.isPlayersBullet() && monster.getHitArea().contains(b.getPosition())){
                sound.play();
                System.out.println(1);
                b.hit(monster);
                bulletEmitter.getActiveList().get(i).deactivate();
            }
        }
        bulletEmitter.checkPool();

    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (DEBUG_MODE){
            shapeRenderer.dispose();
        }
    }
}
