/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import java.io.File
import java.io.FileWriter

private const val DEFAULT_FILE = "simple.t7"

fun main(args: Array<String>) {
    println("[*] Copyright (c) 2018 Mathéo CIMBARO. This work is licensed under a CC-BY-NC-SA 4.0.\n[*] By using this software, you agree the licence.")

    // Load params
    Params.loadParams(args)

    val fileName = Params.getParamValue("file") ?: DEFAULT_FILE
    if (Params.isSpecified("file"))
        println("Using file: $fileName")
    else
        println("No file specified, use default: $DEFAULT_FILE")

    // Charger le ruban et les transitions
    try {
        val file = File(fileName)
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
    // Ecriture dans le fichier
    val logFile = File("execution.log")
    if (logFile.exists())
        logFile.delete()

    logFile.createNewFile()
    val writter = FileWriter(logFile)

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

        writter.appendln("$lastInstruction")
        writter.appendln(" -> ${Ruban.rubanInitial}")
    }

    if (properEnd)
        writter.appendln("Mot accepté: Fin sur un état acceptant")
    else
        writter.appendln("Mot rejeté: Fin sur un état non acceptant")
    writter.flush()
    writter.close()

    println("=-=-=-=-=-=-= 7stepturing =-=-=-=-=-=-=-=-=")
    println("Instruction(s) exécutée(s) avec succès: $instructionSuccess")
    println("Le mot est-il accepté (fin sur un état acceptant): $properEnd")
    if (!properEnd)
        println("Dernière instruction avec succès: $lastInstruction")
    println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
    println("Ruban à la fin: ${Ruban.rubanInitial}")
    println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
}
