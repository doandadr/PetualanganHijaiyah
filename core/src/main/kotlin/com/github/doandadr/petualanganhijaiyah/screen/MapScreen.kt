package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.data.LevelModel
import com.github.doandadr.petualanganhijaiyah.data.LevelSavedData
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.data.levelsData
import com.github.doandadr.petualanganhijaiyah.ui.animation.Animations
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_MAP_STAR
import com.github.doandadr.petualanganhijaiyah.ui.widget.LevelButton
import com.github.doandadr.petualanganhijaiyah.ui.widget.StarWidget
import com.github.doandadr.petualanganhijaiyah.ui.widget.levelButton
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import com.github.doandadr.petualanganhijaiyah.util.centerX
import ktx.actors.onChange
import ktx.actors.onChangeEvent
import ktx.actors.onTouchDown
import ktx.actors.plusAssign
import ktx.log.logger
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

private val log = logger<MapScreen>()

class MapScreen(game: Main) : BaseScreen(game) {
    private lateinit var tutorialButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var totalScore: Label
    private lateinit var totalStar: Label

    private lateinit var levels: Array<LevelModel>
    private lateinit var levelsSavedData: MutableList<LevelSavedData>
    private lateinit var levelButtons: MutableList<LevelButton>

    override fun show() {
        super.show()
        log.debug { "Map Screen is shown" }

        audioService.play(MusicAsset.MAP)

        setupData()
        setupUI()
        loadLevels()
        Gdx.app.postRunnable {
            showTutorials()
        }
    }

    private fun showTutorials() {
        if (levelsSavedData.find { it.number == 1 && !it.hasCompleted } != null) {
            gameEventManager.dispatchShowTutorialEvent(levelButtons.first().label, TutorialType.MAP_LEVEL1)
        }
        if (levelsSavedData.find { it.number == 8 && it.hasCompleted } != null
            && levelsSavedData.find { it.number == 9 && !it.hasCompleted } != null
        ) {
            gameEventManager.dispatchShowTutorialEvent(levelButtons[8].label, TutorialType.MAP_LEVEL9)
        }
    }

    private fun setupData() {
        levels = levelsData
        levelsSavedData =
            preferences[PrefKey.LEVEL_SAVE_DATA.key, mutableListOf<LevelSavedData>()].apply { this.sortBy { it.number } }
        levelButtons = mutableListOf()
    }

    private fun setupUI() {
        val bgMap = assets[TextureAsset.MAP.descriptor]

        stage.actors {
            scrollPane {
                setFillParent(true)
                setScrollingDisabled(true, false)
                setSmoothScrolling(false)

                table {
                    background(TextureRegionDrawable(bgMap))
                    align(Align.bottomLeft)

                    floatingGroup {
                        setFillParent(true)

                        levelButtons += levelButton(Buttons.LEVEL1.style) {
                            setPosition(-30f, -40f)
                            text.setPosition(570f, 380f)
                            rotateText(5f)
                            dots.setPosition(centerX(width) - 20f, 100f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(450f, 200f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL2.style) {
                            setPosition(30f, 440f)
                            text.setPosition(200f, 150f)
                            rotateText(-5f)
                            dots.setPosition(centerX(width) - 10f, -80f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(0f, -50f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL3.style) {
                            setPosition(450f, 750f)
                            text.setPosition(100f, 230f)
                            rotateText(-8f)
                            dots.setPosition(-130f, -100f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(0f, -50f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL4.style) {
                            setPosition(-100f, 1000f)
                            text.setPosition(300f, 150f)
                            rotateText(5f)
                            dots.setPosition(centerX(width) + 120f, -50f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(150f, -50f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL5.style) {
                            setPosition(-10f, 1370f)
                            text.setPosition(370f, 300f)
                            dots.setPosition(centerX(width) - 100f, -100f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(250f, 150f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL6.style) {
                            setPosition(420f, 1850f)
                            text.setPosition(120f, 200f)
                            rotateText(5f)
                            dots.setPosition(-120f, -120f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(20f, -50f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL7.style) {
                            setPosition(30f, 2000f)
                            text.setPosition(200f, 200f)
                            rotateText(-10f)
                            dots.setPosition(centerX(width) - 30f, -30f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(20f, 0f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL8.style) {
                            setPosition(350f, 2400f)
                            text.setPosition(200f, 100f)
                            rotateText(8f)
                            dots.setPosition(0f, -180f)
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(100f, -50f)
                        }

                        levelButtons += levelButton(Buttons.LEVEL9.style) {
                            setPosition(50f, 2600f)
                            text.setPosition(100f, 100f)
                            rotateText(-5f)
                            dots.setPosition(centerX(width) - 40f, -120f)
                            dots.flipHorizontal()
                            starWidget.setScale(SCALE_MAP_STAR)
                            starWidget.setPosition(0f, -50f)
                        }
                    }
                }
                validate()
                scrollPercentY = 1f
            }

            table {
                setFillParent(true)
                table {
                    it.padLeft(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expandX().align(Align.topLeft)
                    image(Drawables.ICON_STAR_SMALL.drawable) {
                        it.padRight(-40f)
                    }
                    totalStar = label("", Labels.TEXTBOX_ORANGE_ROUNDED.style) {
                        toBack()
                        setAlignment(Align.center)
                    }
                }
                table {
                    it.padRight(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expandX().align(Align.topRight)
                    image(Drawables.ICON_DIAMOND.drawable) {
                        it.padRight(-40f)
                    }
                    totalScore = label("", Labels.TEXTBOX_BLUE_ROUNDED.style) {
                        toBack()
                        setAlignment(Align.center)
                    }
                }

                row()
                label("PETA", Labels.MAP.style) {
                    it.padTop(PADDING_INNER_SCREEN).align(Align.top).colspan(2)
                    setAlignment(Align.center)
                }

                row()
                add().expand()

                row()
                homeButton = imageButton(ImageButtons.HOME.style) {
                    it.padLeft(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).align(Align.bottomLeft)
                    isTransform = true
                    setOrigin(Align.center)
                    onTouchDown {
                        this.clearActions()
                        this += Animations.pulseAnimation()
                        audioService.play(SoundAsset.BUTTON_POP)
                    }
                    onChange {
                        game.setScreen<HomeScreen>()
                    }
                }
                tutorialButton = imageButton(ImageButtons.QUESTION.style) {
                    it.padRight(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).align(Align.bottomRight)
                    isTransform = true
                    setOrigin(Align.center)
                    onTouchDown {
                        this.clearActions()
                        this += Animations.pulseAnimation()
                        audioService.play(SoundAsset.BUTTON_POP)
                    }
                    onChange {
                        log.debug { "Tutorial button pressed" }
                    }
                }
            }
        }
    }

    override fun hide() {
        super.hide()
        levelButtons.clear()
    }

    private fun loadLevels() {
        var totalScoreSum = 0f
        var totalStarSum = 0
        levelButtons.forEachIndexed { index, levelButton ->
            val number = index + 1

            var levelSave = levelsSavedData.find { it.number == number }
            if (levelSave == null) {
                levelSave = LevelSavedData(number = number)
                levelsSavedData.add(levelSave)
            }

            levelButton.setLevel(levels[index])

            if (levelSave.hasCompleted) {
                levelButton.setState(LevelButton.LevelButtonState.PASSED)
                levelButton.setStarCount(levelSave.starCount)
                setOnTouchEvent(levelButton, index)
                totalScoreSum += levelSave.highScore
                totalStarSum += levelSave.starCount
            } else {
                if (levelSave.number == 1 || levelsSavedData.find { it.number == number - 1 }?.hasCompleted == true) {
                    levelButton.setState(LevelButton.LevelButtonState.AVAILABLE)
                    levelButton.setStarCount(0)
                    setOnTouchEvent(levelButton, index)
                } else if (levelSave.number in listOf(2) || levelsSavedData.find { it.number in listOf(number - 2) }?.hasCompleted == true) {
                    levelButton.setState(LevelButton.LevelButtonState.INACCESSIBLE)
                    levelButton.starWidget.setState(StarWidget.StarState.HIDDEN)
                } else {
                    levelButton.setState(LevelButton.LevelButtonState.HIDDEN)
                }
            }

            log.debug { "Show ${levels[index].name}, completed? ${levelSave.hasCompleted} with saved score:${levelSave.highScore} star:${levelSave.starCount} time:${levelSave.recordTime}" }
        }
        totalStar.setText(totalStarSum.toString())
        totalScore.setText(totalScoreSum.toInt().toString())

        preferences.flush {
            this[PrefKey.LEVEL_SAVE_DATA.key] = levelsSavedData
        }
    }

    private fun setOnTouchEvent(levelButton: LevelButton, index: Int) {
        levelButton.onTouchDown {
            this.clearActions()
            this += Animations.pulseAnimation(0.1f)
            audioService.play(SoundAsset.BUTTON_POP)
        }
        levelButton.onChangeEvent {
            preferences.flush { this[PrefKey.CURRENT_LEVEL.key] = levels[index].number }
            game.setScreen<LevelScreen>()
        }
    }

    override fun debugMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            hide()
            show()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // Unlock all levels
            levelsSavedData.forEach { it.hasCompleted = true }
            loadLevels()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            levelButtons.first().setState(LevelButton.LevelButtonState.AVAILABLE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            levelButtons.first().setState(LevelButton.LevelButtonState.PASSED)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            levelButtons.first().starWidget.setState(StarWidget.StarState.HIDDEN)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            levelButtons.first().starWidget.setState(StarWidget.StarState.ZERO)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            levelButtons.first().starWidget.setState(StarWidget.StarState.ONE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            levelButtons.first().starWidget.setState(StarWidget.StarState.TWO)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            levelButtons.first().starWidget.setState(StarWidget.StarState.THREE)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            levelButtons.first().starWidget.setState(StarWidget.StarState.HIDDEN)
            levelButtons.first().setState(LevelButton.LevelButtonState.HIDDEN)
        }
    }
}
