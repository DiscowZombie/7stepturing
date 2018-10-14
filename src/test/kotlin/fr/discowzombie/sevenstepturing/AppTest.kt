/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing

import fr.discowzombie.sevenstepturing.obj.Param
import fr.discowzombie.sevenstepturing.obj.Params

val params: HashSet<Param> = hashSetOf()

fun main(args: Array<String>) {
    val r = Params.loadParams(
        arrayOf(
            "lang=en", "exc=it"
        )
    )

    println(r)
}

fun testParams(args: Array<String>): HashMap<Param, String> {
    params += Param("lang", "(fr|en)".toRegex())

    val paramMatch = args.filter { it.contains("(=)".toRegex()) }
    val paramValue: HashMap<Param, String> = hashMapOf()

    paramMatch.forEach {
        val paramSplit = it.split("(=)".toRegex())
        val param = paramSplit.first()
        val value = if (paramSplit.size >= 2) paramSplit[1] else null
        val paramC = params.firstOrNull { it.name == param }

        if (value != null && paramC != null && value.matches(paramC.regex)) {
            paramValue[paramC] = value
        }
    }

    return paramValue
}