package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mainSettings.GameState;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.mainSettings.GameResources;
import com.mygdx.game.mainSettings.GameSession;
import com.mygdx.game.mainSettings.GameSettings;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.objects.BonusObject;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.LiveView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.mainSettings.MyGdxGame;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.objects.TrashObject;
import com.mygdx.game.view.RecordsListView;
import com.mygdx.game.view.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {
    RecordsListView recordsListView;
    ButtonView pauseButton, homeButton, continueButton, homeButton2;
    TextView scoreTextView, pauseTextView, recordsTextView, lossTextView, winTextView;
    LiveView liveView;
    ImageView imageView, fullBlackoutView;
    MovingBackgroundView movingBackgroundView;
    MyGdxGame myGdxGame;
    public static GameSession gameSession;
    ShipObject shipObject;

    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ArrayList<BonusObject> bonusArray;
    ContactManager contactManager;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        imageView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 20, 1215);//???
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        fullBlackoutView = new ImageView(0, 0, GameResources.FULL_BLACKOUT_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.commonWhiteFont, 330, 1000, "PAUSED");
        homeButton = new ButtonView(200, 800, 160, 60, myGdxGame.commonWhiteFont, GameResources.BUTTON_BACKGROUND_IMG_PATH, "HOME" );
        continueButton = new ButtonView(400, 800, 180, 60, myGdxGame.commonWhiteFont, GameResources.BUTTON_BACKGROUND_IMG_PATH, "CONTINUE");
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "LAST RECORDS");
        lossTextView = new TextView(myGdxGame.largeWhiteFont, 220, 642, "GAMEOVER");
        homeButton2 = new ButtonView(280, 365, 160, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_BACKGROUND_IMG_PATH, "HOME");

        contactManager = new ContactManager(myGdxGame.world);

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        bonusArray = new ArrayList<>();


        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
    }

    @Override
    public void show() {
         restartGame();
    }
    @Override
    public void render(float delta) {
        handleInput();
        if (gameSession.state == GameState.PLAYING){
            if (gameSession.shouldSpawnTrash()) {
                TrashObject trashObject = new TrashObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.TRASH_IMG_PATH,
                        myGdxGame.world, GameSettings.TRASH_VELOCITY, GameSettings.TRASH_BIT
                );
                trashArray.add(trashObject);
            }
            if (!GameSession.spacePirateMode){
            if (gameSession.shouldBonusSpawn()) {
                BonusObject bonusObject = new BonusObject(
                        GameSettings.BONUS_WIDTH, GameSettings.BONUS_HEIGHT,
                        bonText(),
                        myGdxGame.world, GameSettings.TRASH_VELOCITY, GameSettings.BONUS_BIT
                );
                bonusArray.add(bonusObject);
            }
            } else {
                if (gameSession.shouldSpawnCoins()) {
                    BonusObject bonusObject = new BonusObject(
                            GameSettings.BONUS_WIDTH, GameSettings.BONUS_HEIGHT,
                            bonText(),
                            myGdxGame.world, GameSettings.TRASH_VELOCITY, GameSettings.BONUS_BIT
                    );
                    bonusArray.add(bonusObject);
                }
            }

            if (gameSession.shouldMeteorSpawn()) {
                if (myGdxGame.audioManager.isSoundsOn) myGdxGame.audioManager.meteorSound.play(0.3f);
                TrashObject trashObject = new TrashObject(
                        GameSettings.METEOR_WIDTH, GameSettings.METEOR_HEIGHT,
                        GameResources.METEOR_IMG_PATH,
                        myGdxGame.world, GameSettings.METEOR_VELOCITY, GameSettings.METEOR_BIT
                );
                trashArray.add(trashObject);
            }
            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);
                if (myGdxGame.audioManager.isSoundsOn) myGdxGame.audioManager.shootSound.play(0.1f);
            }
            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
            if (GameSession.spacePirateMode) {
                if (GameSession.coinsNow == GameSession.shouldCoins) gameSession.endGame();
            }
            updateBonus();
            updateTrash();
            updateBullets();
            movingBackgroundView.move();
            if (!GameSession.spacePirateMode){
            gameSession.updateScore();
            }
            if (!GameSession.spacePirateMode) {
                scoreTextView.setText("Score: " + gameSession.getScore());
            } else {
                scoreTextView.setText("Coins grabbed: " + gameSession.getCoins() + " / " + GameSession.shouldCoins);
            }
            liveView.setLeftLives(shipObject.getLiveLeft());

            myGdxGame.stepWorld();
        }
        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    shipObject.move(myGdxGame.touch);
                    break;
                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                        myGdxGame.setScreen(myGdxGame.modeSelectorScreen);
                    }
                    shipObject.dispose();
                    break;
            }
        }
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
            movingBackgroundView.draw(myGdxGame.batch);
            for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
            shipObject.draw(myGdxGame.batch);
            for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
            for (BonusObject bonus : bonusArray) bonus.draw(myGdxGame.batch);
            imageView.draw(myGdxGame.batch);
            liveView.draw(myGdxGame.batch);
            pauseButton.draw(myGdxGame.batch);
            scoreTextView.draw(myGdxGame.batch);
        if (gameSession.state == GameState.PAUSED){
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            if (!GameSession.spacePirateMode) {
                recordsTextView.draw(myGdxGame.batch);
                recordsListView.draw(myGdxGame.batch);
            } else  {
                if (!shipObject.isAlive()) lossTextView.draw(myGdxGame.batch);
                else {winTextView = new TextView(myGdxGame.largeWhiteFont, 120, 642, "            WIN!!!\n" + "YOU GRAB " + gameSession.getCoins() + " COINS");
                      winTextView.draw(myGdxGame.batch);}
            }
            homeButton2.draw(myGdxGame.batch);
        }
        myGdxGame.batch.end();
    }

    private void updateTrash() {
        Iterator<TrashObject> trashObjectIterator = trashArray.iterator();
        while (trashObjectIterator.hasNext()) {
            TrashObject trash = trashObjectIterator.next();
            boolean hasToBeDestroyed = !trash.isAlive() || !trash.isInFrame();
            if (!trash.isAlive()) {
                if (myGdxGame.audioManager.isSoundsOn){
                    myGdxGame.audioManager.explosionSound.play(0.4f);
                    myGdxGame.audioManager.meteorSound.stop();
                }
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trash.body);
                trashObjectIterator.remove();
            }
        }
    }

    private void updateBullets() {
        Iterator<BulletObject> bulletObjectIterator = bulletArray.iterator();
        while (bulletObjectIterator.hasNext()) {
            BulletObject bullet = bulletObjectIterator.next();
            if (bullet.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bullet.body);
                bulletObjectIterator.remove();
            }
        }
    }
    private void updateBonus() {
        Iterator<BonusObject> bonusObjectIterator = bonusArray.iterator();
        while (bonusObjectIterator.hasNext()) {
            BonusObject bonus = bonusObjectIterator.next();
            if ((!bonus.isAlive()) || (!bonus.isInFrame())) {
                myGdxGame.world.destroyBody(bonus.body);
                bonusObjectIterator.remove();
                 if ((!bonus.isAlive()) && myGdxGame.audioManager.isSoundsOn && (!GameSession.spacePirateMode)){
                     myGdxGame.audioManager.healSound.play(0.5f);}
                     if ((!bonus.isAlive()) && myGdxGame.audioManager.isSoundsOn && GameSession.spacePirateMode){
                         myGdxGame.audioManager.coinSound.play(0.2f);
                     }
                }
            }
        }

    private void restartGame() {
        trashArray.clear();

        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }

        shipObject = new ShipObject(GameSettings.SCREEN_WIDTH / 2, 150, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, GameResources.SHIP_IMG_PATH, myGdxGame.world);

        bulletArray.clear();

        bonusArray.clear();

        gameSession.startGame();
     }
     private String bonText(){
        String bonText = null;
        if (!GameSession.spacePirateMode) bonText = GameResources.LIVES_IMG_PATH;
        if (GameSession.spacePirateMode) bonText = GameResources.COINS_IMG_PATH;
        return bonText;
     }

}




