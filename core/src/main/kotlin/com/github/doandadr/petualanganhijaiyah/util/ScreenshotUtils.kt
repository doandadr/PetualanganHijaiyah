package com.github.doandadr.petualanganhijaiyah.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.PixmapIO
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.ScreenUtils

object ScreenshotUtils {
    fun screenshotActor(actor: Actor, filePath: String) {
        // Get the actor's dimensions
        val width = actor.width.toInt()
        val height = actor.height.toInt()

        // Create a FrameBuffer
        val frameBuffer = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false)

        // Bind the FrameBuffer
        frameBuffer.begin()

        // Clear the frame buffer
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Set up a custom viewport
        val batch: Batch = actor.stage.batch
        batch.projectionMatrix.setToOrtho2D(0f, 0f, width.toFloat(), height.toFloat())

        // Render the actor to the FrameBuffer
        batch.begin()
        actor.draw(batch, 1f)
        batch.end()

        // Read the pixels from the FrameBuffer
        val pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, width, height)

        // Unbind the FrameBuffer
        frameBuffer.end()

        // Save the pixmap as a PNG file
        PixmapIO.writePNG(Gdx.files.external(filePath), pixmap)

        // Dispose of the resources
        pixmap.dispose()
        frameBuffer.dispose()
    }
}
