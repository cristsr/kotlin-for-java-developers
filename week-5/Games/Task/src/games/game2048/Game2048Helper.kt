package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   b, a, a -> b, aa
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
//    val filtered = filterNotNull().toMutableList()
//    val result = mutableListOf<T>()
//
//    if (filtered.size == 1) return filtered
//
//    while (filtered.isNotEmpty()) {
//        val first = filtered.removeAt(0)
//        val second = filtered.getOrNull(0) // Ya eliminaste `first`, así que el siguiente ahora está en `0`
//
//        println(first)
//        println(second)
//        println()
//
//        if (first == second) {
//            result.add(merge(first))
//            filtered.removeAt(0) // Elimina el segundo elemento porque ya lo combinaste
//        } else {
//            result.add(first)
//        }
//
//        println(filtered)
//    }
//
//    return result

    return this
        .filterNotNull()
        .fold(mutableListOf()) { acc, current ->
            if (acc.isNotEmpty() && acc.last() == current) {
                acc[acc.lastIndex] = merge(acc.last())
            } else {
                acc.add(current)
            }
            acc
        }


}



