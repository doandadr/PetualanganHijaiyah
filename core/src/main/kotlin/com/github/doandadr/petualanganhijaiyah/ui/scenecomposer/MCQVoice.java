package com.github.doandadr.petualanganhijaiyah.ui.scenecomposer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
        table.setFillParent(true);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(30.0f);

        Stack stack = new Stack();

        ImageTextButton imageTextButton = new ImageTextButton(null, skin);
        stack.addActor(imageTextButton);

        Image image = new Image(skin, "f01-alif");
        image.setScaling(Scaling.none);
        stack.addActor(image);
        horizontalGroup.addActor(stack);

        stack = new Stack();

        imageTextButton = new ImageTextButton(null, skin);
        stack.addActor(imageTextButton);

        image = new Image(skin, "f01-alif");
        image.setScaling(Scaling.none);
        stack.addActor(image);
        horizontalGroup.addActor(stack);

        stack = new Stack();

        imageTextButton = new ImageTextButton(null, skin);
        stack.addActor(imageTextButton);

        image = new Image(skin, "f01-alif");
        image.setScaling(Scaling.none);
        stack.addActor(image);
        horizontalGroup.addActor(stack);
        table.add(horizontalGroup);

        table.row();
        Button button = new Button(skin, "voice");
        table.add(button).spaceTop(50.0f);
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
