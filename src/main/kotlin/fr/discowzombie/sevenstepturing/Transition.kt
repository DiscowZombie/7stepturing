package fr.discowzombie.sevenstepturing

enum class Deplacment {
    RIGHT,
    LEFT
}

class Transition(val etatInitial: String, val alphabetFini: Byte?, val nouvelEtat: String, val lettreAEcrire: Byte?, val deplacement: Deplacment?)
