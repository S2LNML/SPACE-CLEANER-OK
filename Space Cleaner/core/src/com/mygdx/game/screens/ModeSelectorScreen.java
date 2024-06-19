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

public class ModeSelectorScreen extends ScreenAdapter {
    ButtonView endlessModeStartButton, spacePirateModeStartButton, endlessModePrefaceButton, spacePirateModePrefaceButton, returnButton;
    MovingBackgroundView backgroundView;
    MyGdxGame myGdxGame;

    public ModeSelectorScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        endlessModeStartButton = new ButtonView(150, 900, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "ENDLESS MODE");
        endlessModePrefaceButton = new ButtonView(50, 760, 630, 100, myGdxGame.commonWhiteFont, GameResources.BLACKOUT_MIDDLE_IMG_PATH, endlessModePreface());
        spacePirateModeStartButton = new ButtonView(150, 650, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "SPACE PIRATE MODE");
        spacePirateModePrefaceButton = new ButtonView(50, 500, 630, 100, myGdxGame.commonWhiteFont, GameResources.BLACKOUT_MIDDLE_IMG_PATH, spacePiratePreface());
        returnButton = new ButtonView(150, 200, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "RETURN");
    }
    @Override
    public void render(float delta) {
        GameSession.spacePirateMode = false;
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        endlessModeStartButton.draw(myGdxGame.batch);
        endlessModePrefaceButton.draw(myGdxGame.batch);
        spacePirateModeStartButton.draw(myGdxGame.batch);
        spacePirateModePrefaceButton.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (endlessModeStartButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (spacePirateModeStartButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.levelSelectorScreen);
            }
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        }
    }
    private String endlessModePreface(){
        return "Set records for points in endless mode";
    }
    private String spacePiratePreface(){
        return "Go scouring the expanses of space\n" +
                "           in search of treasures";
    }
    @Override
    public void dispose(){
        backgroundView.dispose();
       endlessModeStartButton.dispose();
        endlessModePrefaceButton.dispose();
        spacePirateModeStartButton.dispose();
        spacePirateModePrefaceButton.dispose();
        returnButton.dispose();
    }
}
