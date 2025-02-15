package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    if (secret == guess) return Evaluation(4, 0)

    var rightPosition = 0
    var wrongPosition = 0

    val words = secret.associateWith { 0 }.toMutableMap()

    for ((i, s) in guess.withIndex()) {
        if (secret[i] == s) {
            words[s] = words[s]!! + 1
            rightPosition += 1
        }
    }

    for ((i, char) in guess.withIndex()) {
        if (char == secret[i]) continue
        if (!secret.contains(char)) continue
        if (secret.count { it == char } == words[char]!!) continue
        words[char] = words[char]!! + 1
        wrongPosition += 1
    }

    return Evaluation(rightPosition, wrongPosition)
}
