/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import fr.discowzombie.sevenstepturing.app.SevenStepTuringApp
import java.io.IOException

fun main(args: Array<String>) {
    val application = SevenStepTuringApp(args = arrayOf(""))

    try {
        application.initFile()
    } catch (exc: SecurityException) {
        exc.printStackTrace()
    } catch (exc: IOException) {
        exc.printStackTrace()
    }

    try {
        application.loadTransitions()
    } catch (exc: ArrayIndexOutOfBoundsException) {
        exc.printStackTrace()
    }

    try {
        application.loadRuban()
    } catch (exc: ArrayIndexOutOfBoundsException) {
        exc.printStackTrace()
    }
}