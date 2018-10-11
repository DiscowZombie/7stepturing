/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import java.io.File

private const val DEFAULT_FILE = "simple.t7"

fun main(args: Array<String>) {
    println("[*] Copyright (c) 2018 Mathéo CIMBARO. This work is licensed under a CC-BY-NC-SA 4.0.")

    if (args.firstOrNull() != null)
        println("Using file: ${args[0]}")
    else
        println("No file specified, use default: $DEFAULT_FILE")

    // Charger le ruban et les transitions
    try {
        val file = File(args.firstOrNull() ?: DEFAULT_FILE)
        val result = T7FileReader(file).readFull()

        val rubanValue = result.first
        TransitionSaver.fonctionTransition = result.second

        // => Construction du ruban (soit disant infini)
        Ruban.buildRuban(rubanValue)
    } catch (exc: ArrayIndexOutOfBoundsException) {
        exc.printStackTrace()
    }

    // == LOGIQUE DU PROGRAMME == //

    // => Trouver l'élement le plus à gauche du ruban
    val indexBegin = Ruban.findBegin()

    // => Lire le ruban et suivre ses ordres
    var etatActuel = State.BEGIN.toCode()
    var index: Int = indexBegin
    // Le programme a-il pris fin dans un état acceptant ? Null si le programme tourne encore, True si il était dans un état acceptant, False sinon
    var properEnd: Boolean? = null
    // Compte le nombre d'instruction passé avec succès
    var instructionSuccess = 0
    // Dèrnière instruction
    var lastInstruction: Transition? = null

    // Notre machine de Turing tourne
    while (properEnd == null) {
        val instruction = Transition.findWhoMatch(etatActuel, Ruban.rubanInitial[index])

        // Aucune instruction ne correspond, c'est embettant :/
        if (instruction != null) {
            // "Exécuter" les instructions sur le ruban
            etatActuel = instruction.afterState
            Ruban.rubanInitial[index] = instruction.letterToWrite

            when (instruction.movement) {
                Movement.RIGHT -> ++index
                Movement.LEFT -> --index
            }

            // Vérifier si on a atteint l'état de fin du programme
            if (etatActuel == State.END.toCode() && instruction.movement == null) {
                properEnd = true
            }

            ++instructionSuccess
            lastInstruction = instruction
        } else {
            properEnd = false
        }
    }

    println("=-=-=-=-=-=-= 7stepturing =-=-=-=-=-=-=-=-=")
    println("Instruction(s) executée(s) avec succès: $instructionSuccess")
    println("Le mot est-il accepté (fin sur un état acceptant): $properEnd")
    if (!properEnd)
        println("Dèrnière instruction avec succès: $lastInstruction")
    println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
    println("Ruban à la fin: ${Ruban.rubanInitial}")
    println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
}
