package com.github.doandadr.petualanganhijaiyah.ui.scenecomposer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Skin skin;

    private Stage stage;

    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Button button = new Button(skin, "back");
        table.add(button);

        table.add().expandX();

        Label label = new Label("AZHARApreferences+rotate", skin, "sign-name");
        table.add(label);

        table.row();
        label = new Label("LEVEL 1", skin, "primary-gw");
        label.setColor(skin.getColor("RGBA_255_255_255_255"));
        table.add(label).spaceTop(100.0f).colspan(3);

        table.row();
        Image image = new Image(skin, "bismillah");
        table.add(image).colspan(3);

        table.row();
        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("box"));
        table.add(table1).colspan(3);

        table.row();
        table.add().expand().colspan(3);
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
