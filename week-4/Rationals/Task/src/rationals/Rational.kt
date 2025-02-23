package rationals

import java.math.BigInteger

class RationalRange(private val start: Rational, private val end: Rational) {
    operator fun contains(value: Rational): Boolean {
        return start <= value && value <= end
    }
}

class Rational(private val numerator: BigInteger, private val denominator: BigInteger) {
    init {
        require(denominator != BigInteger.ZERO) { "The denominator must not be zero" }
    }

    operator fun plus(other: Rational): Rational {
        val mcm = (denominator * other.denominator).abs() / denominator.gcd(other.denominator)

        return Rational(
            numerator * (mcm / denominator) + other.numerator * (mcm / other.denominator),
            mcm
        )

    }

    operator fun minus(other: Rational): Rational {
        val mcm = (denominator * other.denominator).abs() / denominator.gcd(other.denominator)

        return Rational(
            numerator * (mcm / denominator) - other.numerator * (mcm / other.denominator),
            mcm
        )
    }

    operator fun times(other: Rational): Rational = Rational(
        numerator * other.numerator,
        denominator * other.denominator
    )

    operator fun div(other: Rational): Rational = Rational(
        numerator * other.denominator,
        denominator * other.numerator
    )

    operator fun compareTo(other: Rational): Int {
        val a = numerator * other.denominator
        val b = other.numerator * denominator

        return when {
            a == b -> 0
            a < b -> -1
            else -> 1
        }
    }

    operator fun rangeTo(other: Rational) = RationalRange(this, other)

    operator fun unaryMinus(): Rational = Rational(-numerator, denominator)

    override fun equals(other: Any?): Boolean {
        if (other !is Rational) return false
        val normalized = normalize()
        val otherNormalized = other.normalize()
        return normalized.toString() == otherNormalized.toString()
    }

    override fun toString(): String {
        if (denominator == BigInteger.ONE) return numerator.toString()

        val normalized = normalize()

        if (normalized.denominator == BigInteger.ONE) return normalized.numerator.toString()

        return "${normalized.numerator}/${normalized.denominator}"
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    private fun normalize(): Rational {
        if (denominator == BigInteger.ONE) return Rational(numerator, denominator)

        val gcd = numerator.gcd(denominator)

        val a = numerator / gcd
        val b = denominator / gcd

        if (b < BigInteger.ZERO) return Rational(a * -BigInteger.ONE, b.abs())

        return Rational(a, b)
    }
}

infix fun Int.divBy(other: Int): Rational {
    return Rational(this.toBigInteger(), other.toBigInteger())
}

infix fun Long.divBy(other: Long): Rational {
    return Rational(this.toBigInteger(), other.toBigInteger())
}

infix fun BigInteger.divBy(other: BigInteger): Rational {
    return Rational(this, other)
}

fun String.toRational(): Rational {
    if (!this.contains("/")) return Rational(toBigInteger(), BigInteger.ONE)
    val (numerator, denominator) = split("/")
    return Rational(numerator.toBigInteger(), denominator.toBigInteger())
}


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}
