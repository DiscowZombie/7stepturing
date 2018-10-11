package fr.discowzombie.sevenstepturing

object Instruction {

    fun findOneWhoMatch(etat: String, lettre: Byte?, transitions: Set<Transition>): Triple<String, Byte?, Deplacment?>? {
        val transition = transitions.firstOrNull {
            it.etatInitial == etat && it.alphabetFini == lettre
        }

        return if (transition == null)
            null
        else
            Triple(transition.nouvelEtat, transition.lettreAEcrire, transition.deplacement)
    }

}
