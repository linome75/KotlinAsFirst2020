@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson1.task1.sqr

val rightArg = Regex("""(-?\d+(?:\.\d+)?)([+-]\d+(?:\.\d+)?)i""")
fun strToComplex(s: String, i: Int): Double {
    val complexString = rightArg.matchEntire(s) ?: throw IllegalArgumentException()
    return complexString.groupValues[i].toDouble()
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */


    constructor(s: String) : this(strToComplex(s, 1), strToComplex(s, 2))

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(other.re + re, other.im + im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(re * other.re - im * other.im, im * other.re + re * other.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = Complex(
        (re * other.re + im * other.im) / (sqr(other.re) + sqr(other.im)),
        (other.re * im - re * other.im) / (sqr(other.re) + sqr(other.im))
    )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        else return (other is Complex) && (re == other.re) && (im == other.im)
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String =
        when {
            re == 0.0 && im != 0.0 -> ("${im}i")
            re != 0.0 && im > 0.0 -> ("${re}+${im}i")
            else -> ("${re}${im}i")
        }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }

}
