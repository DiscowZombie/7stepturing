/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing.obj

object TransitionSaver {
    var fonctionTransition: Set<Transition> = setOf()
}

// A transition is one line of the Turing's machine
class Transition(
    val initialState: String,
    val letterToMatch: Byte?,
    val afterState: String,
    val letterToWrite: Byte?,
    val movement: Movement?
) {

    override fun toString(): String {
        return "initialState = $initialState, letterToMatch = $letterToMatch, afterState = $afterState, letterToWrite = $letterToWrite, movement = $movement"
    }

    companion object {
        @JvmStatic
        fun findWhoMatch(initialState: String, letterToMatch: Byte?): Transition? =
            TransitionSaver.fonctionTransition.firstOrNull {
                it.initialState == initialState && it.letterToMatch == letterToMatch
            }
    }
}
