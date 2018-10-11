package fr.discowzombie.sevenstepturing

enum class State(private val code: String) {
    BEGIN("q0"),
    END("qf");

    fun toCode(): String = code

}