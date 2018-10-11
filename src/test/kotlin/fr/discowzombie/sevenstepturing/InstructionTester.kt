/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

fun main(args: Array<String>) {
    val instruction = Transition.findWhoMatch(
        "q0",
        1.toByte())

    println(instruction)
}