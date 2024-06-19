package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mainSettings.GameResources;
import com.mygdx.game.mainSettings.GameSession;
import com.mygdx.game.mainSettings.MyGdxGame;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

public class LevelSelectorScreen extends ScreenAdapter {
    ButtonView leftArrowButton, rightArrowButton, levelButton, returnButton, playButton, playPrefaceButton, differentChooseButton;
    MovingBackgroundView backgroundView;
    MyGdxGame myGdxGame;
    int level;
    TextView levelTextView;

    public LevelSelectorScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        level = 1;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        levelButton = new ButtonView(217, 460, 300, 90, GameResources.BUTTON_BACKGROUND_IMG_PATH);
        levelTextView = new TextView(myGdxGame.commonBlackFont, 373, 500, "" + level);
        leftArrowButton = new ButtonView(60, 460, 70, 90, GameResources.RIGHT_ARROW_PATH);
        rightArrowButton = new ButtonView(600, 460, 70, 90, GameResources.LEFT_ARROW_PATH);
        returnButton = new ButtonView(150, 200, 200, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "RETURN");
        playButton = new ButtonView(400, 200, 220, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "PLAY");
        playPrefaceButton = new ButtonView(50, 1000, 630, 100, myGdxGame.commonWhiteFont, GameResources.BLACKOUT_MIDDLE_IMG_PATH, "Grab all the coins");
        differentChooseButton = new ButtonView(150, 600, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "CHOOSE DIFFERENT");
    }
    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        levelButton.draw(myGdxGame.batch);
        levelTextView.draw(myGdxGame.batch);
        leftArrowButton.draw(myGdxGame.batch);
        rightArrowButton.draw(myGdxGame.batch);
        playButton.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        playPrefaceButton.draw(myGdxGame.batch);
        differentChooseButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (leftArrowButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                level -= 1;
                if (level <= 1) level = 1;
                levelTextView.setText("" + level);
            }
            if (rightArrowButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
               level += 1;
                levelTextView.setText("" + level);
            }
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.modeSelectorScreen);
            }
            if (playButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                GameSession.spacePirateMode = true;
                GameSession.coinsK = level;
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
        }
    }
    @Override
    public void dispose(){
        backgroundView.dispose();
        leftArrowButton.dispose();
        rightArrowButton.dispose();
        levelButton.dispose();
        playButton.dispose();
        returnButton.dispose();
        differentChooseButton.dispose();
        levelTextView.dispose();
        playPrefaceButton.dispose();
    }
}
