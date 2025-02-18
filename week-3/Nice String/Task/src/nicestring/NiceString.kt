package nicestring

fun String.isNice(): Boolean {
    if (isEmpty()) return false
    val hasSubStrings = listOf("bu", "ba", "be").count { it in this } > 0
    val vowels = listOf("a", "e", "i", "o", "u")
    val hasVowels = this.count { it.toString() in vowels } >= 3
    val hasDoubleLetter = this.zipWithNext().count { (c1, c2) -> c1 == c2 } >= 1
    return listOf(!hasSubStrings, hasVowels, hasDoubleLetter).count { it } >= 2
}
