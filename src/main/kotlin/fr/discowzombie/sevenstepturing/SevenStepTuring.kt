/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import fr.discowzombie.sevenstepturing.app.SevenStepTuringApp
import fr.discowzombie.sevenstepturing.exts.reduceByNullable
import fr.discowzombie.sevenstepturing.obj.Param
import fr.discowzombie.sevenstepturing.obj.Params
import fr.discowzombie.sevenstepturing.obj.Ruban
import java.io.IOException

fun main(args: Array<String>) {
    val application = SevenStepTuringApp(args = args)

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

    val result = application.loop()

    println("=-=-=-=-=-=-= 7stepturing =-=-=-=-=-=-=-=-=")
    println("Instruction(s) exécutée(s) avec succès: ${result.first}")
    println("Le mot est-il accepté (fin sur un état acceptant): ${result.second}")
    if (!result.second)
        println("Dernière instruction avec succès: ${result.third}")
    println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
    println("Ruban à la fin: ${Ruban.rubanInitial.reduceByNullable(keep = 2).joinToString { it.toString() }}")
    println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
}
