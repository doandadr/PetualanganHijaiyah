package com.github.doandadr.petualanganhijaiyah.ui.scenecomposer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
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
        table.setBackground(skin.getDrawable("box"));
        table.pad(0.0f);
        table.setFillParent(true);

        VerticalGroup verticalGroup = new VerticalGroup();

        Stack stack = new Stack();

        ImageTextButton imageTextButton = new ImageTextButton(null, skin);
        stack.addActor(imageTextButton);

        Image image = new Image(skin, "f01-alif");
        image.setScaling(Scaling.none);
        stack.addActor(image);
        verticalGroup.addActor(stack);

        stack = new Stack();

        imageTextButton = new ImageTextButton(null, skin);
        stack.addActor(imageTextButton);

        image = new Image(skin, "f01-alif");
        image.setScaling(Scaling.none);
        stack.addActor(image);
        verticalGroup.addActor(stack);
        table.add(verticalGroup);

        table.add().spaceLeft(100.0f).spaceRight(100.0f);

        verticalGroup = new VerticalGroup();

        imageTextButton = new ImageTextButton("ALIF", skin);
        verticalGroup.addActor(imageTextButton);

        imageTextButton = new ImageTextButton("BA", skin);
        verticalGroup.addActor(imageTextButton);
        table.add(verticalGroup);
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
