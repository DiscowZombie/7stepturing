/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

// Represent a movement on the ribbon
enum class Movement(protected val char: List<String>) {
    RIGHT(listOf("r", "d")),
    LEFT(listOf("l", "g"));

    companion object {
        @JvmStatic
        fun fromString(char: String): Movement? = values().firstOrNull { it.char.contains(char.toLowerCase()) }
    }
}