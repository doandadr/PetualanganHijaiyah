package com.github.doandadr.petualanganhijaiyah.util

import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.Labels


//
//                        levelDotsWidget {
//                            setPosition(centerX(width) - 20f, 40f)
//                        }1
//                        levelDotsWidget {
//                            setPosition(centerX(width) + 20f, 330f)
//                            flipHorizontal()
//                        }2
//                        levelDotsWidget {
//                            setPosition(centerX(width) - 50f, 650f)
//                        }3
//                        levelDotsWidget {
//                            setPosition(centerX(width) + 20f, 950f)
//                            flipHorizontal()
//                        }4
//                        levelDotsWidget {
//                            setPosition(centerX(width) - 100f, 1270f)
//                        }5
//                        levelDotsWidget {
//                            setPosition(centerX(width) +0f, 1600f)
//                            addCircle()
//                            isTransform = true
//                            setOrigin(Align.center)
//                            rotation = 20f
//                        }6
//                        levelDotsWidget {
//                            setPosition(centerX(width) +20f, 1950f)
//                            flipHorizontal()
//                        }7
//                        levelDotsWidget {
//                            setPosition(centerX(width) +20f, 1950f)
//                        }8

//stage = new Stage(new ScreenViewport());
//skin = new Skin(Gdx.files.internal("skin.json"));
//Gdx.input.setInputProcessor(stage);
//
//Table table = new Table();
//table.setBackground(skin.getDrawable("box"));
//table.setColor(skin.getColor("RGBA_165_165_165_255"));
//table.setFillParent(true);
//
//TextButton textButton = new TextButton("PENGATURAN", skin, "board-s");
//table.add(textButton);
//
//table.row();
//Table table1 = new Table();
//table1.setBackground(skin.getDrawable("window-trans"));
//table1.align(Align.top);
//
//Image image = new Image(skin, "icon-volume");
//table1.add(image).spaceRight(20.0f);
//
//Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin, "default-horizontal");
//table1.add(slider).spaceLeft(20.0f);
//table.add(table1).spaceTop(20.0f).prefWidth(400.0f).prefHeight(800.0f);
//stage.addActor(table);


//                            onChange {
//                                this += scaleTo(1.2f, 1.2f, 0.3f, /*BounceOut(2)*/)
//                                game.setScreen<MapScreen>()
//                            }


//            val characterSelect2Popup = table {
//                setFillParent(true)
//
//                verticalGroup {
//                    space(50f)
//                    label("KARAKTER", Labels.BOARD.style).setAlignment(Align.center)
//                    button(Buttons.GIRL_SELECT.style).onChange {
//
//                    }
//                    button(Buttons.BOY_SELECT.style).onChange {
//
//                    }
//                }
//            }
