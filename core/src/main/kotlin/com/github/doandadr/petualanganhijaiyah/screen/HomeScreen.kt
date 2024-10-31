package com.github.doandadr.petualanganhijaiyah.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.Json.ReadOnlySerializer
import com.badlogic.gdx.utils.JsonValue
import com.github.doandadr.petualanganhijaiyah.Main
import com.github.doandadr.petualanganhijaiyah.asset.TextureAsset
import com.github.doandadr.petualanganhijaiyah.asset.TextureAtlasAsset
import ktx.app.clearScreen
import ktx.graphics.use
import ktx.log.logger
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.table


private val LOG = logger<HomeScreen>()

class HomeScreen(game: Main) : Screen(game) {

    override fun show() {
        LOG.debug { "Home Screen is shown" }
    }

//    override fun render(delta: Float) {
////        val atlas = assets[TextureAtlasAsset.DRAWABLE.descriptor]
////        val pic = atlas.findRegion("board")
////        val bgHome = assets[TextureAsset.HOME.descriptor]
////        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
////        batch.use {
//////            it.draw(image, 100f, 160f)
////            it.draw(pic, 9f, 16f)
////        }
//        val skin: Skin = object : Skin(Gdx.files.internal("skin/ui.json")) {
//            //Override json loader to process FreeType fonts from skin JSON
//            override fun getJsonLoader(skinFile: FileHandle): Json {
//                val json = super.getJsonLoader(skinFile)
//                val skin: Skin = this
//
//                json.setSerializer(
//                    FreeTypeFontGenerator::class.java,
//                    object : ReadOnlySerializer<FreeTypeFontGenerator?>() {
//                        override fun read(
//                            json: Json,
//                            jsonData: JsonValue, type: Class<*>?
//                        ): FreeTypeFontGenerator? {
//                            val path = json.readValue("font", String::class.java, jsonData)
//                            jsonData.remove("font")
//
//                            val hinting = Hinting.valueOf(
//                                json.readValue(
//                                    "hinting",
//                                    String::class.java, "AutoMedium", jsonData
//                                )
//                            )
//                            jsonData.remove("hinting")
//
//                            val minFilter = TextureFilter.valueOf(
//                                json.readValue("minFilter", String::class.java, "Nearest", jsonData)
//                            )
//                            jsonData.remove("minFilter")
//
//                            val magFilter = TextureFilter.valueOf(
//                                json.readValue("magFilter", String::class.java, "Nearest", jsonData)
//                            )
//                            jsonData.remove("magFilter")
//
//                            val parameter = json.readValue(FreeTypeFontParameter::class.java, jsonData)
//                            parameter.hinting = hinting
//                            parameter.minFilter = minFilter
//                            parameter.magFilter = magFilter
//                            val generator = FreeTypeFontGenerator(skinFile.parent().child(path))
//                            val font = generator.generateFont(parameter)
//                            skin.add(jsonData.name, font)
//                            if (parameter.incremental) {
//                                generator.dispose()
//                                return null
//                            } else {
//                                return generator
//                            }
//                        }
//                    })
//
//                return json
//            }
//        }
//
//        Scene2DSkin.defaultSkin = skin
//
//        stage.actors {
//            table {
//                setFillParent(true)
////                background("white")
//                label("Hello world!")
//            }
//        }
//
//        batch.use {
//            clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
//            stage.act()
//            stage.draw()
//        }
//    }

    override fun render(delta: Float) {
        val stage = Stage()

        val skin: Skin = object : Skin(Gdx.files.internal("skin/ui.json")) {
            //Override json loader to process FreeType fonts from skin JSON
            override fun getJsonLoader(skinFile: FileHandle): Json {
                val json = super.getJsonLoader(skinFile)
                val skin: Skin = this

                json.setSerializer(
                    FreeTypeFontGenerator::class.java,
                    object : ReadOnlySerializer<FreeTypeFontGenerator?>() {
                        override fun read(
                            json: Json,
                            jsonData: JsonValue, type: Class<*>?
                        ): FreeTypeFontGenerator? {
                            val path = json.readValue("font", String::class.java, jsonData)
                            jsonData.remove("font")

                            val hinting = Hinting.valueOf(
                                json.readValue(
                                    "hinting",
                                    String::class.java, "AutoMedium", jsonData
                                )
                            )
                            jsonData.remove("hinting")

                            val minFilter = TextureFilter.valueOf(
                                json.readValue("minFilter", String::class.java, "Nearest", jsonData)
                            )
                            jsonData.remove("minFilter")

                            val magFilter = TextureFilter.valueOf(
                                json.readValue("magFilter", String::class.java, "Nearest", jsonData)
                            )
                            jsonData.remove("magFilter")

                            val parameter = json.readValue(FreeTypeFontParameter::class.java, jsonData)
                            parameter.hinting = hinting
                            parameter.minFilter = minFilter
                            parameter.magFilter = magFilter
                            val generator = FreeTypeFontGenerator(skinFile.parent().child(path))
                            val font = generator.generateFont(parameter)
                            skin.add(jsonData.name, font)
                            if (parameter.incremental) {
                                generator.dispose()
                                return null
                            } else {
                                return generator
                            }
                        }
                    })

                return json
            }
        }
        Scene2DSkin.defaultSkin = skin

        stage.actors {
            // Root actor added directly to the stage - a table:
            table {
                // Table settings:
                setFillParent(true)
                background("white")
                // Table children:
                label("Hello world!")
            }
        }

        Gdx.input.inputProcessor = stage

        val batch = SpriteBatch()


    }

    override fun dispose() {
        batch.dispose()
    }
}
