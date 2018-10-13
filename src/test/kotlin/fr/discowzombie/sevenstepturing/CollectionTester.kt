/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import fr.discowzombie.sevenstepturing.exts.reduceByNullable

fun main(args: Array<String>) {
    val reduced = listOf(null, "coucou", null, "fjushvhgsyh", "vjshs", null).reduceByNullable()

    println(reduced.joinToString { it.toString() })
}