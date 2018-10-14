/*
 * Copyright (c) 2018 Mathéo CIMBARO.
 * This work is licensed under a CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)
 */

package fr.discowzombie.sevenstepturing.obj

class Param(val name: String, val regex: Regex)

object Params {

    private val params: HashSet<Param> = hashSetOf()
    private const val EGAL_OPERATOR = "="

    private var validParams = hashMapOf<Param, String>()

    init {
        params += Param(
            "lang",
            "(fr|en)".toRegex()
        )
        params += Param(
            "file",
            "([A-Za-z0-9_.-])+".toRegex()
        )
    }

    fun loadParams(args: Array<String>): HashMap<Param, String> {
        val paramMatch = args.filter { it.contains("($EGAL_OPERATOR)".toRegex()) }
        val paramValue: HashMap<Param, String> = hashMapOf()

        paramMatch.forEach {
            val paramSplit = it.split("($EGAL_OPERATOR)".toRegex())

            val param = paramSplit.first()
            val value = if (paramSplit.size >= 2) paramSplit[1] else null
            val paramC = params.firstOrNull { it.name == param }

            if (value != null && paramC != null && value.matches(paramC.regex)) {
                paramValue[paramC] = value
            }
        }

        validParams = paramValue
        return validParams
    }

    fun getParamValue(param: String): String? = validParams.filter {
        it.key.name == param
    }.values.firstOrNull()

    fun isSpecified(param: String): Boolean = getParamValue(param) != null

}