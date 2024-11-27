package com.github.doandadr.petualanganhijaiyah.event

import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Array
import ktx.app.KtxInputAdapter

enum class Key {
    CHOICE_1,
    CHOICE_2,
    CHOICE_3,

    // debug
    REFRESH
}

enum class DebugKey {
    REFRESH,
}

class GameEventManager : KtxInputAdapter {
    // Input event related
    private val inputListeners = Array<InputListener>()
    private var ignoreInput = false

    fun addInputListener(listener: InputListener) = inputListeners.add(listener)

    fun removeInputListener(listener: InputListener) = inputListeners.removeValue(listener, true)

    // PLACEHOLDER input events
    fun dispatchInputKeyPressEvent(key: Key) {
        if (ignoreInput) return
        inputListeners.forEach { it.keyPressed(key) }
    }

    fun dispatchInputKeyReleaseEvent(key: Key) {
        if (ignoreInput) return
        inputListeners.forEach { it.keyReleased(key) }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (ignoreInput) return true
        when (keycode) {
            Input.Keys.R -> dispatchInputKeyPressEvent(Key.REFRESH)
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if (ignoreInput) return true
        when (keycode) {
            Input.Keys.R -> dispatchInputKeyReleaseEvent(Key.REFRESH)
        }
        return true
    }

    fun disablePlayerInput() {
        ignoreInput = true
    }

    fun enablePlayerInput() {
        ignoreInput = false
    }

    // Game event related
    private val gameEventListeners = Array<GameEventListener>()

    fun addGameEventListener(listener: GameEventListener)
    = gameEventListeners.add(listener)

    fun removeGameEventListener(listener: GameEventListener)
    = gameEventListeners.removeValue(listener, true)

    fun dispatchShowTutorialEvent(tutorialKey: String) =
        gameEventListeners.forEach { it.showTutorial(tutorialKey) }

}
