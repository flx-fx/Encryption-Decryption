package encryptdecrypt

import java.io.File

fun main(args: Array<String>) {
    val argsMap = args.toList().zipWithNext().toMap()

    val alg = argsMap.getOrDefault("-alg", "shift")
    val data = if (argsMap.containsKey("-data")) argsMap["-data"] else if (argsMap.containsKey("-in")) File(argsMap["-in"]!!).readText() else ""
    val mode = if (argsMap.getOrDefault("-mode", "enc") == "enc") 1 else -1
    val key = argsMap.getOrDefault("-key", "0").toInt() * mode
    val out = File(argsMap.getOrDefault("-out", ""))

    val result = data!!.map {
        when (alg) {
            "shift" -> when (it) {
                in 'a'..'z' -> (it + key - if (it + key !in 'a'..'z') 26 * mode else 0)
                in 'A'..'Z' -> (it + key - if (it + key !in 'A'..'Z') 26 * mode else 0)
                else -> it
            }
            else -> it + key
        }
    }.joinToString("")
    if (out.path.isNotEmpty()) out.writeText(result) else println(result)
}