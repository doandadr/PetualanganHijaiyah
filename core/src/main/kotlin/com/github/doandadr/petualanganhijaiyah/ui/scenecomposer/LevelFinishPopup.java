package com.github.doandadr.petualanganhijaiyah.ui.scenecomposer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Skin skin;

    private Stage stage;

    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setName("levelFinishPopup");
        table.setFillParent(true);

        Label label = new Label("BERHASIL", skin, "banner-orange");
        label.setName("finishText");
        label.setAlignment(Align.center);
        table.add(label).padBottom(-60.0f);

        table.row();
        Table table1 = new Table();
        table1.setName("finishBox");
        table1.setBackground(skin.getDrawable("box-orange-rounded"));

        Stack stack = new Stack();
        stack.setName("star-stack");

        Image image = new Image(skin, "level-finish-starbox");
        image.setScaling(Scaling.none);
        stack.addActor(image);

        Table table2 = new Table();
        table2.setName("starWidget");
        table2.setTouchable(Touchable.enabled);
        stack.addActor(table2);
        table1.add(stack).expandY();

        table1.row();
        ImageTextButton imageTextButton = new ImageTextButton("1000", skin, "level-finish-score");
        imageTextButton.setName("scoreView");
        table1.add(imageTextButton).spaceBottom(30.0f);

        table1.row();
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(30.0f);

        ImageButton imageButton = new ImageButton(skin, "menu");
        horizontalGroup.addActor(imageButton);

        imageButton = new ImageButton(skin, "repeat");
        horizontalGroup.addActor(imageButton);

        imageButton = new ImageButton(skin, "next");
        horizontalGroup.addActor(imageButton);
        table1.add(horizontalGroup).padBottom(15.0f).spaceBottom(16.0f);
        table.add(table1).prefSize(460.0f);
        stage.addActor(table);
    }

    public void render() {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
