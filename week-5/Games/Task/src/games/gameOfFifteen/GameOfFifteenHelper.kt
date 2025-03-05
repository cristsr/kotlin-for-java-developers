package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
//    return permutation.foldIndexed(mutableListOf<Int>()) { idx, acc, curr ->
//        (idx + 1 until permutation.size).forEach { j ->
//            if (curr > permutation[j]) acc.add(curr)
//        }
//
//        acc
//    }.let {
//        it.size % 2 == 0
//    }


    return permutation
        .indices
        .sumOf { i ->
            (i + 1 until permutation.size).count { j ->
                permutation[i] > permutation[j]
            }
        }
        .let { it % 2 == 0 }

}


fun <T> List<T>.swap(i: Int, j: Int): List<T> = toMutableList().apply {
    this[i] = this[j].also { this[j] = this[i] }
}

