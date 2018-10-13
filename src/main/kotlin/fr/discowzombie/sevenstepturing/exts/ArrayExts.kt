/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing.exts

/**
 * Reduce a collection by removing nullables elements
 * @param keep - Number of elements to keep in each side (right and left)
 */
fun List<Any?>.reduceByNullable(keep: Int = 2): List<Any?> {
    var lowerLimit = this.indexOfFirst { it != null }
    var upperLimit = this.indexOfLast { it != null }

    // Define lower-limit - Safe way, never outside of the list
    if (lowerLimit - keep < 0)
        lowerLimit = 0
    else
        lowerLimit -= keep

    // TODO: ne produit pas encore l'effet attendu si il n'y a qu'un nul à la fin
    // Define upper-limit - Safe way, never outside of the list
    if (upperLimit + keep + 1 > this.size)
        upperLimit = this.size - 2
    else
        upperLimit += 2

    return this.subList(lowerLimit, upperLimit + 1)
}