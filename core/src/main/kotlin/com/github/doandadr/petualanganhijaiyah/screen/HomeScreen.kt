package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.data.PlayerModel
import com.github.doandadr.petualanganhijaiyah.data.PrefKey
import com.github.doandadr.petualanganhijaiyah.ui.animation.Animations
import com.github.doandadr.petualanganhijaiyah.ui.values.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.characterSelectPopup
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.nameChangePopup
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.settingsPopup
import ktx.actors.onChange
import ktx.actors.onTouchDown
import ktx.actors.plusAssign
import ktx.log.logger
import ktx.preferences.get
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

private val log = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {
    private lateinit var player: PlayerModel

    private lateinit var exitButton: TextButton
    private lateinit var settingButton: TextButton
    private lateinit var startButton: TextButton
    private lateinit var nameButton: TextButton
    private lateinit var bookButton: ImageButton
    private var popup: Table = Table()
    private var layout: Table = Table()
    private var popupState = PopupState.NONE
    private val bgDim = TextureRegionDrawable(assets[TextureAsset.DIM.descriptor])

    enum class PopupState {
        NONE,
        SETTING,
        CHARACTER,
        NAME,
    }

    override fun show() {
        super.show()
        setupAudio()
        setupData()
        setupUI()
        setupTutorials()
    }

    private fun setupTutorials() {
        Gdx.app.postRunnable {
            gameEventManager.dispatchShowTutorialEvent(nameButton, TutorialType.HOME_NAME)
            gameEventManager.dispatchShowTutorialEvent(bookButton, TutorialType.HOME_PRACTICE)
            gameEventManager.dispatchShowTutorialEvent(startButton, TutorialType.HOME_START)
        }
    }

    private fun setupData() {
        player = preferences[PrefKey.PLAYER.key, PlayerModel()]
    }

    private fun setupAudio() {
        audioService.musicVolume = preferences[PrefKey.MUSIC_VOLUME.key, 1f]
        audioService.soundVolume = preferences[PrefKey.SOUND_VOLUME.key, 1f]
        audioService.play(MusicAsset.HOME)
    }

    private fun setupUI() {
        val bgHome = TextureRegionDrawable(assets[TextureAsset.HOME.descriptor])

        stage.actors {
            image(bgHome) {
                setFillParent(true)
                setScaling(Scaling.fill)
            }

            layout = table {
                setFillParent(true)
                align(Align.bottomLeft)

                floatingGroup {
                    setFillParent(true)

                    container {
                        setFillParent(true)
                        align(Align.topLeft)
                        pad(50f)
                        label("PETUALANGAN\nHIJAIYAH", Labels.PRIMARY_GREEN_WHITE_BORDER.style) {
                            setAlignment(Align.center)
                            setOrigin(Align.center)
                            setFontScale(SCALE_FONT_MEDIUM)
                        }
                    }
                    horizontalGroup {
                        setFillParent(true)
                        align(Align.topRight)
                        pad(50f)
                        space(50f)
                        bookButton = imageButton(ImageButtons.BOOK.style) {
                            isTransform = true
                            setOrigin(Align.center)
                            onTouchDown {
                                this.clearActions()
                                this += Animations.pulseAnimation()
                            }
                            onChange {
                                game.setScreen<PracticeScreen>()
                                audioService.play(SoundAsset.CLICK_BUTTON)
                            }
                        }
                    }
                    verticalGroup {
                        setFillParent(true)
                        center()
                        space(40f)
                        padBottom(250f)
                        startButton = textButton("MULAI", TextButtons.BOARD.style) {
                            isTransform = true
                            setOrigin(Align.center)
                            setScale(SCALE_BTN_MEDIUM)
                            rotation = 10f
                            onTouchDown {
                                this.clearActions()
                                this += Animations.pulseAnimation()
                            }
                            onChange {
                                game.setScreen<MapScreen>()
                            }
                        }
                        settingButton = textButton("PENGATURAN", TextButtons.BOARD.style) {
                            isTransform = true
                            setOrigin(Align.center)
                            this.label.setFontScale(SCALE_FONT_SMALL)
                            onTouchDown {
                                this.clearActions()
                                this += Animations.pulseAnimation()
                            }
                            onChange {
                                setPopup(PopupState.SETTING)
                            }
                        }
                        exitButton = textButton("KELUAR", TextButtons.BOARD.style) {
                            isTransform = true
                            setOrigin(Align.center)
                            this.label.setFontScale(SCALE_FONT_SMALL)
                            setScale(SCALE_BTN_SMALL)
                            onTouchDown {
                                this.clearActions()
                                this += Animations.pulseAnimation()
                            }
                            onChange {
                                Gdx.app.exit()
                                System.exit(0)
                            }
                        }
                    }
                    verticalGroup {
                        setFillParent(true)
                        align(Align.bottomLeft)
                        space(20f)
                        container {
                            isTransform = true
                            setOrigin(Align.center)
                            rotation = 5f
                            label("Selamat\nDatang!", Labels.PRIMARY_GREEN_WHITE_BORDER.style) {
                                setFontScale(SCALE_FONT_MEDIUM)
                            }
                        }
                        nameButton = textButton(player.name, TextButtons.SIGN.style) {
                            isTransform = true
                            setOrigin(Align.center)
                            onTouchDown {
                                this.clearActions()
                                this += Animations.pulseAnimation()
                            }
                            onChange {
                                setPopup(PopupState.NAME)
                            }
                        }
                        padLeft(PADDING_INNER_SCREEN)
                    }
                }
            }

            popup = table {
                setFillParent(true)
                setBackground(bgDim)
                align(Align.center)
                isVisible = false
            }
        }
    }

    private fun setPopup(state: PopupState) {
        popupState = state

        if (popupState != PopupState.NONE) {
            layout.touchable = Touchable.disabled
            popup.clear()
            popup += Actions.sequence(Actions.show(), fadeIn(0.5f))
        } else {
            layout.touchable = Touchable.childrenOnly
            popup += Actions.sequence(fadeOut(0.5f), Actions.hide(), Actions.run { popup.clear() })
        }

        when (popupState) {
            PopupState.SETTING -> {
                popup.add(scene2d.settingsPopup(preferences, audioService, gameEventManager))
            }

            PopupState.CHARACTER -> {
                popup.add(scene2d.characterSelectPopup(preferences, audioService, gameEventManager))
            }

            PopupState.NAME -> {
                popup.add(scene2d.nameChangePopup(preferences, gameEventManager))
            }

            PopupState.NONE -> {}
        }
    }

    override fun setHomePopupState(state: PopupState) {
        setPopup(state)
        log.debug { "Changing popup to $state" }
    }

    override fun playerChanged(player: PlayerModel) {
        nameButton.setText(player.name)
        log.debug { "Changing player name to ${player.name}" }
    }

    override fun debugMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            hide()
            show()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // SHOW settings popup
            setPopup(PopupState.SETTING)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // SHOW character popup
            setPopup(PopupState.CHARACTER)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // SHOW name popup
            setPopup(PopupState.NAME)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            // HIDE popup
            setPopup(PopupState.NONE)
        }
    }
}
