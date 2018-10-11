/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import java.io.File

fun main(args: Array<String>) {
    val ruban = T7FileReader(File("simple.t7")).readRuban()
    println(ruban)

    val trans = T7FileReader(File("simple.t7")).readTransition()
    println(trans)
}