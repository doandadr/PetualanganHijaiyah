package com.github.doandadr.petualanganhijaiyah.ui.widget.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.github.doandadr.petualanganhijaiyah.asset.*
import com.github.doandadr.petualanganhijaiyah.audio.AudioService
import com.github.doandadr.petualanganhijaiyah.event.GameEventManager
import com.github.doandadr.petualanganhijaiyah.ui.animation.Animations
import com.github.doandadr.petualanganhijaiyah.ui.values.PADDING_INNER_SCREEN
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_MEDIUM
import com.github.doandadr.petualanganhijaiyah.ui.values.SCALE_BTN_SMALL
import com.github.doandadr.petualanganhijaiyah.ui.values.SIZE_DRAWING_BOARD
import com.github.doandadr.petualanganhijaiyah.ui.widget.popup.TutorialType
import ktx.actors.onChange
import ktx.actors.onTouchDown
import ktx.actors.plusAssign
import ktx.assets.async.AssetStorage
import ktx.log.logger
import ktx.scene2d.*
import space.earlygrey.shapedrawer.ShapeDrawer
import space.earlygrey.shapedrawer.scene2d.ShapeDrawerDrawable


class DrawingStage(
    private val assets: AssetStorage,
    private val audioService: AudioService,
    private val batch: Batch,
    private val gameEventManager: GameEventManager,
    private val skin: Skin = Scene2DSkin.defaultSkin
) : Table(skin), KTable {
    private var drawArea: Image
    private var resetButton: ImageButton
    private var submitButton: ImageTextButton
    private var drawingBoard: Table

    //    private var drawImage: Image
    private var incorrectButton: Button
    private var correctButton: Button
    private var skipButton: ImageButton
    private var hijaiyahText: Label
    private val hijaiyahEntries = Hijaiyah.entries.take(28)
    private lateinit var currentEntry: Hijaiyah

    private var drawer = ShapeDrawer(batch, skin.getRegion(Drawables.CIRCLE_BRUSH.drawable))

    private val textAtlas = assets[TextureAtlasAsset.HIJAIYAH.descriptor]

//    private val tensorFlowModel: TensorFlowModel = TensorFlowModel()

    // List to store the points of the drawing
    private val segments = mutableListOf<MutableList<Vector2>>()
    private var boardRect: List<Float>? = null
    private var drawRect: List<Float>? = null
    private val image1DArray: Array<Int> = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,16,26,39,45,46,47,47,47,47,43,35,28,20,13,6,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,51,86,129,149,152,153,154,155,153,142,116,92,65,42,18,6,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24,80,128,183,205,202,200,198,198,197,189,170,150,124,99,70,47,23,10,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,33,109,165,218,232,210,198,191,189,190,198,215,220,214,201,175,136,73,34,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,105,158,207,211,173,153,145,142,143,157,186,205,217,222,221,192,125,74,24,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,66,104,146,139,79,51,45,43,44,53,76,100,133,171,222,229,196,141,49,6,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,38,62,92,82,29,5,2,0,0,6,21,42,74,125,207,239,231,181,72,19,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,11,19,28,25,9,1,0,0,0,2,7,13,23,71,171,223,239,203,102,45,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,46,150,210,241,215,122,63,19,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,137,197,237,220,138,79,24,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,117,177,231,226,157,98,30,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,25,85,144,221,234,185,126,39,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,67,126,216,240,205,146,46,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,18,58,118,213,246,223,165,53,3,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,46,105,206,246,234,179,63,11,4,7,19,23,20,14,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,29,85,194,240,239,191,81,25,10,21,62,76,65,47,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,17,71,184,236,243,200,94,38,16,36,102,128,122,95,39,12,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,6,58,174,231,246,208,106,50,24,54,149,196,208,175,88,38,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,10,18,27,34,39,37,28,19,7,2,0,0,0,0,0,0,0,0,2,52,166,225,246,214,122,65,27,50,140,195,234,210,119,61,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,11,34,58,91,113,129,124,93,63,25,6,2,0,0,0,0,0,0,0,2,48,156,215,242,221,144,85,29,24,70,126,207,213,144,87,27,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,23,69,107,147,172,188,183,152,119,73,41,15,3,1,0,0,0,0,0,2,42,137,196,237,227,165,105,32,9,31,76,158,173,124,79,24,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,6,44,127,178,212,230,235,233,224,203,166,118,44,9,3,0,0,0,0,0,0,32,103,163,226,235,190,130,40,3,9,30,70,78,57,36,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,9,55,156,213,242,253,247,247,252,242,218,167,75,26,8,0,0,0,0,0,0,27,88,147,222,240,207,148,49,3,1,8,25,28,21,13,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,10,59,165,221,245,247,230,228,243,248,242,202,115,60,18,0,0,0,0,0,0,26,86,146,222,245,222,166,61,12,4,2,7,9,6,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,11,61,169,225,246,241,208,201,220,235,249,223,151,92,29,2,0,0,0,0,0,26,86,145,222,249,234,190,103,58,45,36,30,22,8,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,11,61,168,224,246,230,175,158,176,199,238,236,191,134,45,5,2,0,0,0,0,26,85,145,221,253,249,228,186,158,136,119,100,72,26,5,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,9,58,162,219,244,225,158,132,141,170,230,244,218,162,58,10,3,0,0,0,0,27,87,147,222,254,252,239,210,195,189,178,159,120,48,13,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,5,49,148,206,240,224,152,117,107,139,219,250,239,185,72,18,5,0,0,0,0,28,93,153,223,253,247,226,182,180,217,232,226,181,82,30,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,45,139,198,237,227,161,118,84,111,206,248,248,201,93,35,10,0,0,0,1,35,111,171,229,248,230,198,139,137,187,210,211,171,78,29,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,41,133,193,236,235,189,138,65,80,184,235,248,215,127,69,21,0,0,2,5,50,148,206,240,237,198,146,69,51,88,106,110,88,35,9,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,37,122,181,232,242,211,156,58,58,155,210,242,228,165,107,37,5,2,5,16,68,174,229,247,229,172,112,34,11,35,47,51,40,13,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,31,102,162,227,249,236,178,62,40,108,165,228,243,217,163,66,18,5,12,38,95,198,246,252,221,143,83,26,3,10,14,15,12,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22,74,128,201,230,225,172,58,26,68,119,194,229,236,200,110,59,30,35,74,129,215,250,245,203,114,57,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,28,71,149,181,177,135,45,11,24,60,131,182,228,229,185,140,82,80,135,181,232,245,223,172,75,26,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,6,35,97,122,120,91,31,4,3,26,79,127,182,205,201,178,130,130,181,212,232,222,180,126,46,9,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,11,29,37,36,28,9,1,1,8,24,47,84,120,163,184,186,200,227,232,217,177,102,54,19,3,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,32,64,113,144,165,182,198,193,165,122,53,17,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,10,19,34,46,58,71,86,84,62,42,17,5,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,9,18,28,28,13,5,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,3,5,9,8,4,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    )


    init {        // Create a 1x1 white texture
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.fill()
        val whiteTexture = TextureRegion(Texture(pixmap))
        pixmap.dispose()

        // Initialize ShapeDrawer
        drawer = ShapeDrawer(batch, whiteTexture)

        background(skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable))

        label("Tuliskan huruf...", Labels.SECONDARY_BORDER.style) {
            it.padTop(PADDING_INNER_SCREEN).colspan(3)
        }

        row()
        this@DrawingStage.hijaiyahText = label("", Labels.TEXTBOX_GREEN_SQUARE_LARGE.style) {
            setAlignment(Align.center)
            setFontScale(SCALE_BTN_MEDIUM)
            it.spaceTop(20f).colspan(3)
        }

        row()
        this@DrawingStage.drawingBoard = table {
            it.padLeft(PADDING_INNER_SCREEN).padRight(PADDING_INNER_SCREEN).size(SIZE_DRAWING_BOARD).spaceTop(30f)
                .colspan(3)
            background(skin.getDrawable(Drawables.BOX_WHITE_ROUNDED.drawable))
            color = Color.BLACK
            touchable = Touchable.enabled

//            this@DrawingStage.drawImage = image()
            this@DrawingStage.drawArea = image(object : ShapeDrawerDrawable(this@DrawingStage.drawer) {
                override fun drawShapes(shapeDrawer: ShapeDrawer?, x: Float, y: Float, width: Float, height: Float) {
                    this@DrawingStage.drawer.update()

                    // Draw each segment
                    for (segment in this@DrawingStage.segments) {
                        if (segment.isNotEmpty()) {
                            for (i in 0 until segment.size - 1) {
                                this@DrawingStage.drawer.filledCircle(segment[i].x, segment[i].y, 8f)
                                this@DrawingStage.drawer.line(segment[i], segment[i + 1], 16f)
                            }
                        }
                    }
//
//                    this@DrawingStage.boardRect?.let {
//                        this@DrawingStage.drawer.rectangle(it[0], it[1], it[2], it[3], Color.BLACK, 5f)
//                    }
//                    this@DrawingStage.drawRect?.let {
//                        this@DrawingStage.drawer.rectangle(it[0], it[1], it[2], it[3], Color.RED, 5f)
//                    }
//                    this@DrawingStage.drawGrayscaleImage()
                }

            })
        }

        row()
        horizontalGroup {
            it.colspan(3)
            this@DrawingStage.correctButton = button(Buttons.CHECK.style)
            this@DrawingStage.incorrectButton = button(Buttons.X.style)
        }

        row()
        horizontalGroup {
            it.padBottom(PADDING_INNER_SCREEN).expand().align(Align.bottom)

            this@DrawingStage.resetButton = imageButton(ImageButtons.REPEAT.style) {
                setScale(SCALE_BTN_SMALL)
                isTransform = true
                setOrigin(Align.center)
                onTouchDown {
                    setScale(SCALE_BTN_SMALL)
                    this.clearActions()
                    this += Animations.pulseAnimation()
                    this@DrawingStage.audioService.play(SoundAsset.BUTTON_POP)
                }
                onChange {
                    this@DrawingStage.resetDrawingBoard()
                }
            }
            this@DrawingStage.skipButton = imageButton(ImageButtons.SKIP.style) {
                setScale(SCALE_BTN_SMALL)
                isTransform = true
                setOrigin(Align.center)
                onTouchDown {
                    setScale(SCALE_BTN_SMALL)
                    this.clearActions()
                    this += Animations.pulseAnimation()
                    this@DrawingStage.audioService.play(SoundAsset.BUTTON_POP)
                }
                onChange {
                    this@DrawingStage.loadStage()
                }
            }
            this@DrawingStage.submitButton = imageTextButton("   Jawab", ImageTextButtons.SUBMIT.style) {
                isTransform = true
                setOrigin(Align.center)
                onTouchDown {
                    this.clearActions()
                    this += Animations.pulseAnimation()
                    this@DrawingStage.audioService.play(SoundAsset.BUTTON_POP)
                }
                onChange {
                    this@DrawingStage.handleSubmission()
                }
            }
        }

        loadStage()
        setTutorials()
    }

    private fun drawGrayscaleImage() {
        val imageSize = 64
        val pixelSize = 10f // Size of each pixel when drawn

        boardRect?.let {
            for (y in 0 until imageSize) {
                for (x in 0 until imageSize) {
                    val pixelValue = image1DArray[x * imageSize + y]
                    val grayValue = pixelValue / 255f
                    val color = Color(grayValue, grayValue, grayValue, 1f)
                    this@DrawingStage.drawer.setColor(color)
                    this@DrawingStage.drawer.filledRectangle(
                        boardRect!![0] + x * pixelSize,
                        boardRect!![1] + (imageSize - 1 - y) * pixelSize, pixelSize, pixelSize
                    )
                }
            }
        }

    }

    private fun resetDrawingBoard() {
        segments.clear()
    }

    private fun setTutorials() {
        Gdx.app.postRunnable {
            gameEventManager.dispatchShowTutorialEvent(drawingBoard, TutorialType.DRAW_START)
        }
    }

    private fun handleSubmission() {
        // TODO capture image in middle of table
        // TODO send image into ML
        // Reset image

//        val dbBottomLeft = drawingBoard.localToStageCoordinates(Vector2(0f, 0f))
//        val dbTopRight = drawingBoard.localToStageCoordinates(Vector2(drawingBoard.width, drawingBoard.height))
//
//        log.debug { "Drawing board x:${drawingBoard.x}, y:${drawingBoard.y}, width:${drawingBoard.width}, height:${drawingBoard.height}" }
//        log.debug { "Bottom left x:${dbBottomLeft.x}, y:${dbBottomLeft.y}" }
//        log.debug { "Top right x:${dbTopRight.x}, y:${dbTopRight.y}" }
//
//// Calculate the dimensions and ensure they are positive
//        val width = Math.abs(dbTopRight.x - dbBottomLeft.x).toInt()
//        val height = Math.abs(dbTopRight.y - dbBottomLeft.y).toInt()
//        log.debug { "Calculated width: $width, height: $height" }
//
//// Ensure that the coordinates are within the screen bounds
//        val startX = dbBottomLeft.x.toInt().coerceIn(0, Gdx.graphics.width)
//        val startY = (dbTopRight.y.toInt()).coerceIn(0, Gdx.graphics.height)
//
//        log.debug { "Start X: $startX, Start Y: $startY" }
//
//        val pixmap = Pixmap.createFromFrameBuffer(startX, startY, width, height)
//        PixmapIO.writePNG(Gdx.files.external("mypixmap.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true)
//        pixmap.dispose()
//        val filePath = "screenshot.png"
//        ScreenshotUtils.screenshotActor(drawingBoard, filePath)
//
//        var left: Float = Float.MAX_VALUE
//        var right: Float = 0f
//        var top: Float = 0f
//        var bottom: Float = Float.MAX_VALUE
//        for (segment in this@DrawingStage.segments) {
//            if (segment.isNotEmpty()) {
//                for (i in 0 until segment.size - 1) {
//                    left = MathUtils.clamp(min(left, segment[i].x), dbBottomLeft.x, dbTopRight.x)
//                    bottom = MathUtils.clamp(min(bottom, segment[i].y), dbBottomLeft.y, dbTopRight.y)
//                    right = MathUtils.clamp(max(right, segment[i].x), dbBottomLeft.x, dbTopRight.x)
//                    top = MathUtils.clamp(max(top, segment[i].y), dbBottomLeft.y, dbTopRight.y)
//                }
//            }
//        }
////        left = min(left, segment[i].x)
////        bottom = min(bottom, segment[i].y)
////        right = max(right, segment[i].x)
////        top = max(top, segment[i].y)
//
//        log.debug { "Segments left:$left , right:$right , top:$top , bottom:$bottom " }
//        log.debug { "$segments" }
//
////        drawer.rectangle(dbBottomLeft.x, dbBottomLeft.y, drawingBoard.width, drawingBoard.height, Color.BLACK, 5f)
////        drawer.rectangle(left, bottom, right-left, top-bottom, Color.BLUE, 5f)
//        boardRect = listOf(dbBottomLeft.x, dbBottomLeft.y, drawingBoard.width, drawingBoard.height)
//        drawRect = listOf(left, bottom, right - left, top - bottom)
//
////        segments.clear()
    }

    private fun pickRandomEntries(amount: Int): List<Hijaiyah> = hijaiyahEntries.shuffled().take(amount)

    private fun loadStage() {
        currentEntry = pickRandomEntries(1).first()
        hijaiyahText.setText(currentEntry.reading.uppercase())
        segments.clear()

        correctButton.onChange {
            gameEventManager.dispatchAnswerCorrectEvent(true)
        }
        incorrectButton.onChange {
            gameEventManager.dispatchAnswerIncorrectEvent(true)
        }

        drawingBoard.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val coords = drawingBoard.localToStageCoordinates(Vector2(x, y))
                segments.add(mutableListOf(coords, coords))
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                segments.last().add(drawingBoard.localToStageCoordinates(Vector2(x, y)))
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }
        })

//        drawImage.drawable = skin.getDrawable(Drawables.BOX_ORANGE_ROUNDED.drawable)
//        drawImage.touchable = Touchable.disabled
//        drawImage.setOrigin(Align.center)
//        drawImage.setScaling(Scaling.stretch)
//        drawImage.setScale(1.5f)
    }

//    override fun draw(batch: Batch?, parentAlpha: Float) {
//        super.draw(batch, parentAlpha)
//        drawer.update()
//
//        // Draw each segment
//        for (segment in segments) {
//            if (segment.isNotEmpty()) {
//                for (i in 0 until segment.size - 1) {
//                    drawer.filledCircle(segment[i].x, segment[i].y, 8f)
//                    drawer.line(segment[i], segment[i + 1], 16f)
//                }
//            }
//        }
//    }

    companion object {
        private val log = logger<DragAndDropStage>()
    }
}

inline fun <S> KWidget<S>.drawingStage(
    assets: AssetStorage,
    audioService: AudioService,
    batch: Batch,
    gameEventManager: GameEventManager,
    init: DrawingStage.(S) -> Unit = {}
) = actor(
    DrawingStage(
        assets,
        audioService,
        batch,
        gameEventManager,
    ), init
)
