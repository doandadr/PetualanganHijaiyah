package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Scaling
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.data.*
import com.github.doandadr.petualanganhijaiyah.ui.animation.Animations
import com.github.doandadr.petualanganhijaiyah.ui.values.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.*
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.AnswerPopup
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.answerPopup
import com.github.doandadr.petualanganhijaiyah.ui.widget.stages.*
import ktx.actors.*
import ktx.log.logger
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import ktx.scene2d.*

class LevelScreen(
    game: Main,
) : BaseScreen(game) {

    private lateinit var popup: Table
    private lateinit var layout: Table
    private lateinit var levelTitle: Label
    private lateinit var stageBoard: Table
    private lateinit var timer: TimerWidget
    private lateinit var playerInfo: PlayerInfoWidget
    private lateinit var backgroundImg: Image
    private lateinit var hintButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var homeButton: ImageButton

    private var levelNumber: Int = 1
    private lateinit var levels: Array<LevelModel>
    private lateinit var levelsSavedData: MutableList<LevelSavedData>
    private lateinit var player: PlayerModel

    private var popupState = PopupState.NONE
    private var currentScore = 0f
    private var currentStar = 0
    private var currentRecordTime = Float.MAX_VALUE
    private var currentRound = 1
    private var currentStageIndex = 0
    private lateinit var currentLevel: LevelModel
    private lateinit var currentStage: StageModel
    private var newBestScore = false
    private var newBestTime = false

    override fun show() {
        super.show()

        setupData()
        setupUI()
        loadLevel(levelNumber)
    }

    private fun setupData() {
        levelNumber = preferences[PrefKey.CURRENT_LEVEL.key, 1]
        levels = levelsData
        levelsSavedData = preferences[PrefKey.LEVEL_SAVE_DATA.key, mutableListOf()]
        player = preferences[PrefKey.PLAYER.key, PlayerModel()]
        popupState = PopupState.NONE
        currentScore = 0f
        currentStar = 0
        currentRecordTime = Float.MAX_VALUE
        currentRound = 1
        currentStageIndex = 0
        newBestScore = false
        newBestTime = false
    }

    private fun setupUI() {
        val bgDim = TextureRegionDrawable(assets[TextureAsset.DIM.descriptor])

        stage.actors {
            backgroundImg = image {
                setFillParent(true)
                setScaling(Scaling.fill)
            }
            layout = table {
                setFillParent(true)

                timer = timerWidget(gameEventManager) {
                    it.padLeft(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).expand().align(Align.topLeft)
                }

                playerInfo = playerInfoWidget(preferences, gameEventManager) {
                    it.padRight(PADDING_INNER_SCREEN).padTop(PADDING_INNER_SCREEN).align(Align.topRight)
                }

                row()
                table {
                    it.colspan(2)
                    levelTitle = label("", Labels.PRIMARY_GREEN_WHITE_BORDER.style) {
                        setAlignment(Align.center)
                        setFontScale(SCALE_FONT_BIG)
                    }

                    row()
                    image(Drawables.BISMILLAH.drawable)

                }

                row()
                stageBoard = table {
                    it.colspan(2).expand().growY().prefWidth(STAGE_BOX_WIDTH).prefHeight(STAGE_BOX_HEIGHT)
                }

                row()
                horizontalGroup {
                    it.padLeft(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).expand().align(Align.bottomLeft)
                    space(SPACE_BETWEEN_BUTTONS)
                    homeButton = imageButton(ImageButtons.HOME.style) {
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
                }
                horizontalGroup {
                    it.padRight(PADDING_INNER_SCREEN).padBottom(PADDING_INNER_SCREEN).align(Align.bottomRight);
                    space(SPACE_BETWEEN_BUTTONS)
                    backButton = imageButton(ImageButtons.BACK.style) {
                        isTransform = true
                        setOrigin(Align.center)
                        onTouchDown {
                            this.clearActions()
                            this += Animations.pulseAnimation()
                        }
                        onChange {
                            game.setScreen<MapScreen>()
                        }
                    }
                }
            }

            popup = table {
                setFillParent(true)
                setBackground(bgDim)
                isVisible = false
            }
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        timer.update(delta)
    }

    enum class PopupState {
        NONE,
        FINISH,
        FAILED,
        CORRECT,
        INCORRECT,
    }

    private fun setPopup(state: PopupState) {
        popupState = state

        if (popupState != PopupState.NONE) {
            layout.touchable = Touchable.disabled

            popup.clear()
            popup += Actions.sequence(Actions.show(), fadeIn(0.2f))
        } else {
            layout.touchable = Touchable.childrenOnly

            popup += Actions.sequence(fadeOut(0.2f), Actions.hide(), Actions.run { popup.clear() })
        }

        when (popupState) {
            PopupState.FINISH -> {
                popup.add(
                    scene2d.stack {
                        image(Drawables.EFFECT_CONFETTI.drawable) {
                            setScaling(Scaling.none)
                            setOrigin(Align.top)
                            setScale(2f)
                            setPosition(0f, -300f)
                        }
                        image(Drawables.EFFECT_SALUTE.drawable) {
                            setScaling(Scaling.none)
                            setOrigin(Align.center)
                            setScale(2.5f)
                        }
                        levelFinishView(
                            currentScore,
                            currentStar,
                            currentRecordTime,
                            LevelFinishView.State.FINISH,
                            audioService,
                            newBestScore,
                            newBestTime
                        ) {
                            menuButton.onChangeEvent {
                                game.setScreen<MapScreen>()
                            }
                            repeatButton.onChangeEvent {
                                loadLevel(currentLevel.number)
                                setPopup(PopupState.NONE)
                            }
                            nextButton.onChangeEvent {
                                nextLevel()
                                setPopup(PopupState.NONE)
                            }
                        }
                    }
                )
            }

            PopupState.FAILED -> {
                popup.add(
                    scene2d.levelFinishView(
                        currentScore,
                        currentStar,
                        currentRecordTime,
                        LevelFinishView.State.FAILED,
                        audioService
                    ) {
                        menuButton.onChangeEvent {
                            game.setScreen<MapScreen>()
                        }
                        repeatButton.onChangeEvent {
                            loadLevel(currentLevel.number)
                            setPopup(PopupState.NONE)
                        }
                        nextButton.isVisible = false
                        nextButton.onChangeEvent {
                            nextLevel()
                            setPopup(PopupState.NONE)
                        }
                    })
            }

            PopupState.CORRECT -> {
                popup.add(scene2d.answerPopup(AnswerPopup.State.CORRECT, preferences) {
                    this@answerPopup.clearActions()
                    this@answerPopup += Actions.sequence(Animations.fadeInOutAnimation(), Actions.run {
                        if (popupState != PopupState.FINISH) {
                            setPopup(PopupState.NONE)
                            nextRound()
                        }
                    })
                    onClick {
                        this@answerPopup.clearActions()
                        setPopup(PopupState.NONE)
                        nextRound()
                        this@answerPopup.touchable = Touchable.disabled
                    }
                })
            }

            PopupState.INCORRECT -> {
                popup.add(scene2d.answerPopup(AnswerPopup.State.INCORRECT, preferences) {
                    this.clearActions()
                    this@answerPopup += Actions.sequence(Animations.fadeInOutAnimation(), Actions.run {
                        setPopup(PopupState.NONE)
                        loadStage(currentStage)
                    })
                    onClick {
                        this@answerPopup.clearActions()
                        setPopup(PopupState.NONE)
                        loadStage(currentStage)
                        this@answerPopup.touchable = Touchable.disabled
                    }
                })
            }

            else -> {}
        }
    }

    private fun loadLevel(levelNumber: Int) {
        layout.touchable = Touchable.childrenOnly

        val level = levels.find { it.number == levelNumber }
        if (level == null) {
            throw KotlinNullPointerException("Trying to load a level that does not exist")
        }
        currentLevel = level

        if (level.isHealthCounted) {
            playerInfo.loadWidget(level.maxHealth)
            playerInfo.isVisible = true
        } else {
            playerInfo.isVisible = false
        }

        if (level.isTimed) {
            timer.loadWidget(level.timerSeconds)
            timer.setState(TimerWidget.State.START)
            timer.isVisible = true
        } else {
            timer.setState(TimerWidget.State.STOP)
            timer.isVisible = false
        }

        levelTitle.setText(level.name.uppercase())
        backgroundImg.drawable = TextureRegionDrawable(assets[TextureAsset.entries[level.bgIndex].descriptor])

        audioService.play(MusicAsset.entries[level.musicIndex])

        loadStage(level.stages.first())
    }

    private fun loadStage(stage: StageModel) {
        currentStage = stage

        val newStage = scene2d {
            when (stage.type) {
                StageType.MCQ -> mcqStage(assets, audioService, gameEventManager)
                StageType.MCQ_JOIN -> mcqJoin(assets, audioService, gameEventManager)
                StageType.MCQ_VOICE -> mcqVoiceStage(assets, audioService, gameEventManager)
                StageType.DRAG_AND_DROP -> dragAndDropStage(assets, audioService, gameEventManager)
                StageType.MATCH_LINE -> matchLineStage(
                    assets,
                    audioService,
                    this@LevelScreen.stage.batch,
                    gameEventManager
                )

                StageType.DRAWING -> drawingStage(assets, audioService, batch, gameEventManager)
            }
        }

        newStage.setFillParent(true)
        stageBoard.clear()
        stageBoard.addActor(newStage)
    }

    private fun nextLevel() {
        val nextLevelNumber = currentLevel.number + 1

        val nextLevel = levels.find { it.number == nextLevelNumber }
        if (nextLevel != null) {
            log.debug { "Next level $nextLevelNumber" }
            preferences[PrefKey.CURRENT_LEVEL.key] = nextLevelNumber
            loadLevel(nextLevelNumber)
        } else {
            log.debug { "Adventure finished" }
            // TODO finish the adventure
            // game.setScreen<FinishScreen>()
        }
    }

    private fun nextStage() {
        currentStageIndex++

        val stages = currentLevel.stages
        if (stages.size > currentStageIndex) {
            log.debug { "Next stage of index $currentStageIndex" }
            currentStage = currentLevel.stages[currentStageIndex]
            loadStage(currentStage)
        } else {
            currentStageIndex = 0
            currentRound = 1
            levelComplete(timer.bar.levelScore, timer.bar.levelStars, timer.elapsedSeconds)
        }
    }

    private fun nextRound() {
        currentRound++
        if (currentRound > currentStage.rounds) {
            currentRound = 1
            nextStage()
        } else {
            log.debug { "Next round $currentRound" }
            loadStage(currentStage)
        }
    }

    private fun isEndOfLevel(): Boolean =
        currentStageIndex + 1 >= currentLevel.stages.size && currentRound >= currentStage.rounds

    private fun updateLevelData(level: LevelModel, newScore: Float, newStars: Int, newRecordTime: Float) {
        log.debug { "Updating level data of ${level.name}" }

        if (levelsSavedData.isNotEmpty() && level.isScored) {
            var levelData = levelsSavedData.find { it.number == level.number }

            if (levelData == null) {
                levelData = LevelSavedData()
                levelsSavedData.add(levelData)
            }

            with(levelData) {
                number = level.number
                recordTime = if (newRecordTime <= recordTime) newRecordTime.also {
                    newBestTime = true
                } else recordTime.also { newBestTime = false }
                highScore = if (newScore >= highScore) newScore.also {
                    newBestScore = true
                } else highScore.also { newBestScore = false }
                starCount = if (newStars >= starCount) newStars else starCount
                hasCompleted = true
            }

            preferences.flush {
                this[PrefKey.LEVEL_SAVE_DATA.key] = levelsSavedData
            }
        }
        setPopup(PopupState.FINISH)
    }

    override fun answerCorrect(isContinue: Boolean) {
        log.debug { "Answer is correct, continue? $isContinue" }
        audioService.play(if (isContinue) SoundAsset.CORRECT_BLING else SoundAsset.CORRECT_DING)

        if (isEndOfLevel() && isContinue) {
            nextRound()
            return
        }
        if (isContinue) {
            setPopup(PopupState.CORRECT)
        }
    }

    override fun answerIncorrect(isContinue: Boolean) {
        log.debug { "Answer is wrong, continue? $isContinue" }
        audioService.play(if (isContinue) SoundAsset.INCORRECT_BIG else SoundAsset.INCORRECT)

        if (playerInfo.loseHealth() <= 0) {
            levelFailed()
            return
        }
        if (isContinue) {
            setPopup(PopupState.INCORRECT)
        }
    }

    override fun levelComplete(score: Float, stars: Int, time: Float) {
        log.debug { "Level completed with score:$score, stars:$stars, time:$time" }
        currentScore = score
        currentStar = stars
        currentRecordTime = time

        timer.stop()
        audioService.play(SoundAsset.COMPLETE)
        updateLevelData(currentLevel, score, stars, time)
        layout.touchable = Touchable.disabled
    }

    override fun levelFailed() {
        log.debug { "Level failed" }
        currentScore = 0f
        currentStar = 0
        currentRound = 1
        currentStageIndex = 0
        currentRecordTime = timer.elapsedSeconds

        audioService.play(SoundAsset.FAILURE)
        setPopup(PopupState.FAILED)
        layout.touchable = Touchable.disabled
    }

    override fun debugMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            // refresh
            hide()
            show()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            // debug lines
            stage.isDebugAll = !stage.isDebugAll
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // finish level
            levelComplete(timer.bar.levelScore, timer.bar.levelStars, timer.elapsedSeconds)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // restart level
            loadLevel(currentLevel.number)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // go to the next level
            nextLevel()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            // load drag and drop level
            loadLevel(levels[6].number)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            // load join level
            loadLevel(levels[3].number)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            // load match line level
            loadLevel(levels[2].number)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            // redo tutorial for this level
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            // level hint
        }
    }

    companion object {
        private val log = logger<LevelScreen>()
    }
}
