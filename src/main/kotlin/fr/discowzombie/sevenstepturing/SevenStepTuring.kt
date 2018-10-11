package fr.discowzombie.sevenstepturing

private var fonctionTransition: Set<Transition> = setOf()
private var rubanInitial: List<Byte?> = listOf()

fun main(args: Array<String>) {
    // RUban d'entrée
    rubanInitial += 0.toByte()

    // Element à ajouter dans la liste des transitions
    fonctionTransition += Transition(
        State.BEGIN.toCode(),
        0.toByte(),
        State.BEGIN.toCode(),
        0.toByte(),
        Deplacment.RIGHT
    )
    fonctionTransition += Transition(
        State.BEGIN.toCode(),
        1.toByte(),
        State.END.toCode(),
        null,
        null
    )

    // == LOGIQUE DU PROGRAMME == //

    // => Construction du ruban (soit disant infini)
    var ruban = mutableListOf<Byte?>()
    for (i in 0 until rubanInitial.size + 200) {
        var value: Byte? = null

        if (i >= 100 && i < 100 + rubanInitial.size)
            value = rubanInitial[i - 100]

        ruban = ruban.plus(value).toMutableList()
    }

    // => Trouver l'élement le plus à gauche du ruban
    val indexBegin = ruban.indexOfFirst { it != null }

    // => Lire le ruban et suivre ses ordres
    var etatActuel = State.BEGIN.toCode()
    var index: Int = indexBegin
    // Le programme a-il pris fin dans un état acceptant ? Null si le programme tourne encore, True si il était dans un état acceptant, False sinon
    var properEnd: Boolean? = null

    // Notre machine de Turing tourne
    while (properEnd == null) {
        val instruction = Instruction.findOneWhoMatch(etatActuel, ruban[index], fonctionTransition)

        // Aucune instruction ne correspond, c'est embettant :/
        if (instruction != null) {
            // "Exécuter" les instructions sur le ruban
            etatActuel = instruction.first
            ruban[index] = instruction.second

            index += when (instruction.third) {
                Deplacment.RIGHT -> 1
                Deplacment.LEFT -> -1
                else -> 0
            }

            // Vérifier si on a atteint l'état de fin du programme
            if (etatActuel == State.END.toCode() && instruction.third == null) {
                properEnd = true
            }
        } else {
            properEnd = false
        }
    }

    print("End ? $properEnd")
}
