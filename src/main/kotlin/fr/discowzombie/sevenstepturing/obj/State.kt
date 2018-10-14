/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing.obj

enum class State(private val code: String) {
    BEGIN("q0"),
    END("qf");

    fun toCode(): String = code

}