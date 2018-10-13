/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException

private const val COPYRIGHT =
    "[*] Copyright (c) 2018 Mathéo CIMBARO. This work is licensed under a CC-BY-NC-SA 4.0. By using this software, you agree the licence."

private const val DEFAULT_FILE = "simple.t7"

class SevenStepTuringApp(val args: Array<String>, private val filePath: String? = null) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)
    private val params = Params.loadParams(this.args)
    private val file = File(filePath ?: DEFAULT_FILE)
    private var t7file: T7FileReader? = null

    init {
        // Display copyright
        LOGGER.info(COPYRIGHT)
    }

    @Throws(SecurityException::class, IOException::class)
    fun initFile() {
        // Try to load file
        try {
            if (file.exists()) {
                try {
                    if (file.canRead()) {
                        this.t7file = T7FileReader(file)
                    } else {
                        throw IOException("Les permissions de lecture n'ont pas été accordé pour le fichier ${file.name}")
                    }
                } catch (exc: SecurityException) {
                    throw SecurityException("Les permissions de lecture n'ont pas été accordé pour le fichier ${file.name}")
                }
            } else {
                try {
                    file.parentFile?.mkdirs()
                    file.createNewFile()
                    LOGGER.warn("Le fichier ${file.name} n'existe pas et a été créer. Veuillez le remplir.")
                } catch (exc: IOException) {
                    throw IOException("Impossible de créer le fichier : ${exc.message}")
                } catch (exc: SecurityException) {
                    throw SecurityException("Impossible de créer le fichier : ${exc.message}")
                }
            }
        } catch (exc: SecurityException) {
            throw SecurityException("Impossible de vérifier si le fichier existe : ${exc.message}")
        }
    }

    // == DANS LE FUTUR, VERIFIER LE FICHIER !! == //

    @Throws(ArrayIndexOutOfBoundsException::class, IOException::class)
    fun loadTransitions() {
        TransitionSaver.fonctionTransition = t7file?.readTransition() ?: throw IOException("Le fichier de transition semble invalide ou introuvable")
    }

    @Throws(ArrayIndexOutOfBoundsException::class, IOException::class)
    fun loadRuban() {
        val t7f = t7file?.readRuban() ?: throw IOException("Le fichier de transition semble invalide ou introuvable")

        Ruban.buildRuban(t7file!!.readRuban())
    }

    // Retourner des exceptions d'éxecution
    fun loop(logFile: File = File("execution.log")): Triple<Int, Boolean, Transition?> {
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

        return Triple(instructionSuccess, properEnd, lastInstruction)
    }

}