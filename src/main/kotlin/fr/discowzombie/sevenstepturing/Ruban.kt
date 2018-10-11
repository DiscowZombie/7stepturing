/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

private const val RIGHT_SEPARATOR = 20
private const val LEFT_SEPARATOR = 20

object Ruban {
    var rubanInitial: MutableList<Byte?> = mutableListOf()

    fun buildRuban(values: MutableList<Byte?>) {
        for (i in 0 until values.size + RIGHT_SEPARATOR + LEFT_SEPARATOR) {
            var value: Byte? = null

            if (i >= RIGHT_SEPARATOR && i < LEFT_SEPARATOR + values.size)
                value = values[i - LEFT_SEPARATOR]

            rubanInitial.add(i, value)
        }
    }

    fun findBegin() = rubanInitial.indexOfFirst { it != null }
}