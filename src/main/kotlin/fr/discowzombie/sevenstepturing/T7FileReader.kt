/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import fr.discowzombie.sevenstepturing.obj.Movement
import fr.discowzombie.sevenstepturing.obj.Transition
import java.io.File

class T7FileReader(private val file: File) {

    private val lineAsList = file.readLines()

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun readFull(): Pair<MutableList<Byte?>, Set<Transition>> = Pair(readRuban(), readTransition())

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun readRuban(): MutableList<Byte?> {
        val indexBegin = lineAsList.indexOf("ruban:") + 1
        val indexEnd = lineAsList.indexOf(":ruban")

        return lineAsList.subList(indexBegin, indexEnd).map { it.toByte() }.toMutableList()
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun readTransition(): Set<Transition> {
        val indexBegin = lineAsList.indexOf("transition:") + 1
        val indexEnd = lineAsList.indexOf(":transition")

        return lineAsList.subList(indexBegin, indexEnd).map {
            val line = it.split(" ")

            Transition(
                line[0],
                if (line[1] == "null") null else line[1].toByte(),
                line[2],
                if (line[3] == "null") null else line[3].toByte(),
                Movement.fromString(line[4])
            )
        }.toSet()
    }

}