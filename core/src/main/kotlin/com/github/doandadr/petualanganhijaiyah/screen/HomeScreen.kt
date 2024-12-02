package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_FONT_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.*
import com.kotcrab.vis.ui.layout.FloatingGroup
import ktx.actors.onChange
import ktx.actors.onChangeEvent
import ktx.actors.plusAssign
import ktx.assets.disposeSafely
import ktx.log.logger
import ktx.scene2d.*
import ktx.scene2d.vis.floatingGroup

private val log = logger<HomeScreen>()

class HomeScreen(game: Main) : BaseScreen(game) {
    private val playerName: String = "AZHARA" // TODO get from playerData
    private val playerGender: String = "female" // TODO get from playerData
    private val volumeValue: Float = 1f // TODO get from settingsData
    private val volumeIsMuted: Boolean = false // TODO get from settingsData

    private var popup: Table = Table()
    private var layout: Table = Table()
    private lateinit var homeUI: FloatingGroup
    private lateinit var settingsPopup: SettingsPopup
    private lateinit var nameChange: NameChangePopup
    private lateinit var characterSelectPopup: CharacterSelectPopup

    private var popupState = PopupState.NONE

    enum class PopupState {
        NONE,
        SETTING,
        CHARACTER,
        NAME
    }

    override fun show() {
        log.debug { "Home Screen is shown" }
        setupUI()

        audioService.enabled = true

        audioService.play(MusicAsset.HOME)
    }

    private fun setupUI() {
        val skin = Scene2DSkin.defaultSkin
        // TODO tooltips
        val tooltip: TextTooltip =
            TextTooltip("This is a book tooltip", skin, TextTooltips.LEFT_UP.style)

        // TODO settings window: volume, gender
        val bgHome = assets[TextureAsset.HOME.descriptor]
        val bgDim = assets[TextureAsset.DIM.descriptor]

        // Setup actions and animations


        stage.actors {

            layout = table {
                background(TextureRegionDrawable(bgHome))
                setFillParent(true)
                align(Align.bottomLeft)

                homeUI = floatingGroup {
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
                        // TODO coin functionalities
                        imageButton(ImageButtons.COIN.style) {
                            isTransform = true
                            setOrigin(Align.right)
                            setScale(SCALE_BTN_MEDIUM)
                        }
                        imageButton(ImageButtons.BOOK.style) {
                            addListener(tooltip)
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
                        textButton("MULAI", TextButtons.BOARD.style) {
                            isTransform = true
                            rotation = 10f
                            setOrigin(Align.bottom)
                            setScale(SCALE_BTN_MEDIUM)
                            onChange {
                                game.setScreen<MapScreen>()
                            }
                        }
                        textButton("PENGATURAN", TextButtons.BOARD.style) {
                            this.label.setFontScale(SCALE_FONT_SMALL)
                            onChange {
                                setPopup(PopupState.SETTING)
                            }
                        }
                        textButton("KELUAR", TextButtons.BOARD.style) {
                            this.label.setFontScale(SCALE_FONT_SMALL)
                            isTransform = true
                            setOrigin(Align.center)
                            setScale(SCALE_BTN_SMALL)
                            onChange {
                                if (isPressed) {
                                    // TODO dispatch save game event
                                    Gdx.app.exit()
                                    System.exit(0)
                                }
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
                            label("Selamat\nDatang!", Labels.PRIMARY_GREEN_WHITE_BORDER.style)
                        }
                        textButton(playerName, TextButtons.SIGN.style) {
                            setPopup(PopupState.NAME)
                        }
                    }
                }
            }

            popup = table {
                setFillParent(true)
                setBackground(TextureRegionDrawable(bgDim))
            }

            // TODO TextTooltip sequence on first play; better to just have floating TT pointing to those
        }
    }

    private fun setPopup(state: PopupState) {
        popupState = state

        if (popupState != PopupState.NONE) {
            popup.clearActions()
            popup += Actions.show()
            popup += fadeIn(0.5f)
        } else {
            popup.clearActions()
            popup += fadeOut(0.5f)
            popup += Actions.hide()
        }

        // opening popup:
        // add popup to table
        // show popup using actions -> show --> fadeIn
        //
        when (popupState) {
            PopupState.SETTING -> {
                settingsPopup = scene2d.settingsPopup(audioService) {
                    confirmButton.onChange {
//                    homeUI.isTouchable = Touchable.childrenOnly
//                    settingsPopup.isVisible = false
//                    layout.background = TextureRegionDrawable(bgHome)
                        // TODO on confirm/
                        // set preferences (volume, mute, ????)
                        setPopup(PopupState.NONE)
                    }
                }
                popup.add(settingsPopup)
            }

            PopupState.CHARACTER -> {
                characterSelectPopup = scene2d.characterSelectPopup {
                    girlButton.onChangeEvent {
                        // TODO CHANGE GENDER ON PREFERENCE
                    }
                    boyButton.onChangeEvent {
                        // TODO CHANGE GENDER ON PREFERENCE
                    }
                }
                popup.add(characterSelectPopup)
            }

            PopupState.NAME -> {
                nameChange = scene2d.nameChangePopup {
                    confirmButton.onChangeEvent {

                    }
                    nameField.onChangeEvent {

                    }
                }
                popup.add(nameChange)
            }

            else -> {}
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.run {
            viewport.apply()
            act()
            draw()
        }

        debugMode()
    }

    private fun debugMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            hide()
            show()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // SHOW settings popup
            popupState = PopupState.SETTING
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // HIDE settings popup
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // SHOW character popup
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            // HIDE character popup
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            // SHOW name popup
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            // HIDE name popup
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
        }
    }

    override fun hide() {
        stage.clear()
    }

    override fun dispose() {
        stage.disposeSafely()
    }
}
