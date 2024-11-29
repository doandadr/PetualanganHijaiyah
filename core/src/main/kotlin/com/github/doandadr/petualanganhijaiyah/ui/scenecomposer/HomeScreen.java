package com.github.doandadr.petualanganhijaiyah.ui.scenecomposer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
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

        Label label = new Label("PETUALANGAN\n"
                        + "HIJAIYAH", skin, "primary-gw");
        label.setAlignment(Align.center);
        table.add(label).align(Align.topLeft);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(50.0f);

        Button button = new Button(skin, "coin");
        horizontalGroup.addActor(button);

        button = new Button(skin, "book");
        horizontalGroup.addActor(button);
        table.add(horizontalGroup).align(Align.topRight);

        table.row();
        table.add().expand().colspan(2);

        table.row();
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(1.0f);

        TextButton textButton = new TextButton("MULAI", skin, "board");
        verticalGroup.addActor(textButton);

        textButton = new TextButton("PENGATURAN", skin, "board-small");
        verticalGroup.addActor(textButton);

        textButton = new TextButton("KELUAR", skin, "board-small");
        verticalGroup.addActor(textButton);
        table.add(verticalGroup).colspan(2);

        table.row();
        table.add().spaceTop(100.0f).expand().colspan(2);

        table.row();
        verticalGroup = new VerticalGroup();

        Container container = new Container();

        label = new Label("Selamat Datang!", skin, "primary-gw");
        container.setActor(label);
        verticalGroup.addActor(container);

        textButton = new TextButton("AZHARA", skin, "sign");
        verticalGroup.addActor(textButton);
        table.add(verticalGroup).align(Align.bottomLeft).colspan(2);
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
