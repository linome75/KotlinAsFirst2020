@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1



import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Класс "вещественное число с фиксированной точкой"
 *
 * Общая сложность задания - сложная, общая ценность в баллах -- 20.
 * Объект класса - вещественное число с заданным числом десятичных цифр после запятой (precision, точность).
 * Например, для ограничения в три знака это может быть число 1.234 или -987654.321.
 * Числа можно складывать, вычитать, умножать, делить
 * (при этом точность результата выбирается как наибольшая точность аргументов),
 * а также сравнить на равенство и больше/меньше, преобразовывать в строку и тип Double.
 *
 * Вы можете сами выбрать, как хранить число в памяти
 * (в виде строки, целого числа, двух целых чисел и т.д.).
 * Представление числа должно позволять хранить числа с общим числом десятичных цифр не менее 9.
 */
class FixedPointNumber(num: Double, prec: Int) : Comparable<FixedPointNumber> {
    /**
     * Точность - число десятичных цифр после запятой.
     */
    val precision: Int = prec
    private val number: Double = num


    /**
     * Конструктор из строки, точность выбирается в соответствии
     * с числом цифр после десятичной точки.
     * Если строка некорректна или цифр слишком много,
     * бросить NumberFormatException.
     *
     * Внимание: этот или другой конструктор можно сделать основным
     */
    constructor(s: String) : this(
        s.toDouble(),
        if (s.contains("."))
            s.length - s.indexOf(".") - 1
        else 0
    )

    /**
     * Конструктор из вещественного числа с заданной точностью
     */
    //constructor(d: Double, p: Int) : this(d, p)

    /**
     * Конструктор из целого числа (предполагает нулевую точность)
     */
    constructor(i: Int) : this(i.toDouble(), 0)

    /**
     * Сложение.
     *
     * Здесь и в других бинарных операциях
     * точность результата выбирается как наибольшая точность аргументов.
     * Лишние знаки отрбрасываются, число округляется по правилам арифметики.
     */
    operator fun plus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.number + other.number, max(this.precision, other.precision))

    /**
     * Смена знака
     */
    operator fun unaryMinus(): FixedPointNumber = FixedPointNumber(-this.number, this.precision)

    /**
     * Вычитание
     */
    operator fun minus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.number - other.number, max(this.precision, other.precision))

    /**
     * Умножение
     */
    operator fun times(other: FixedPointNumber): FixedPointNumber {
        val simpleMultiple = (this.number * other.number)
        val beforeDot = simpleMultiple - simpleMultiple % 1.0
        val herePrec = max(this.precision, other.precision)
        val afterDot = ((simpleMultiple - beforeDot) * 10.0.pow(herePrec)).roundToInt() / 10.0.pow(herePrec)
        return FixedPointNumber(beforeDot + afterDot, herePrec)
    }

    /**
     * Деление
     */
    operator fun div(other: FixedPointNumber): FixedPointNumber {
        val simpleDiv = (this.number / other.number)
        val beforeDot = simpleDiv - simpleDiv % 1.0
        val herePrec = min(this.precision, other.precision)
        val afterDot = ((simpleDiv - beforeDot) * 10.0.pow(herePrec)).roundToInt() / 10.0.pow(herePrec)
        return FixedPointNumber(beforeDot + afterDot, herePrec)
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        (other is FixedPointNumber && this.number == other.number && this.precision == other.precision)

    /**
     * Сравнение на больше/меньше
     */
    override fun compareTo(other: FixedPointNumber): Int = when {
        this.number - other.number >= 0.1.pow(precision) -> 1
        this.number - other.number <= 0.1.pow(precision) -> -1
        else -> 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = this.number.toString()

    /**
     * Преобразование к вещественному числу
     */
    fun toDouble(): Double = this.number
    override fun hashCode(): Int {
        var result = precision
        result = 31 * result + number.hashCode()
        return result
    }
}

