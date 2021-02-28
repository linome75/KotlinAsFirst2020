@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1


import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.*


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

class FixedPointNumber(num: BigDecimal, prec: Int) : Comparable<FixedPointNumber> {
    /**
     * Точность - число десятичных цифр после запятой.
     */
    val precision: Int = prec
    private val number: BigDecimal = num


    /**
     * Конструктор из строки, точность выбирается в соответствии
     * с числом цифр после десятичной точки.
     * Если строка некорректна или цифр слишком много,
     * бросить NumberFormatException.
     *
     * Внимание: этот или другой конструктор можно сделать основным
     */
    constructor(s: String) : this(s.toBigDecimal(), s.split(".")[1].length)

    /**
     * Конструктор из вещественного числа с заданной точностью
     */
    constructor(d: Double, p: Int) : this(d.toBigDecimal().setScale(p, RoundingMode.HALF_UP), p)

    /**
     * Конструктор из целого числа (предполагает нулевую точность)
     */
    constructor(i: Int) : this(i.toDouble().toBigDecimal(), 0)

    /**
     * Сложение.
     *
     * Здесь и в других бинарных операциях
     * точность результата выбирается как наибольшая точность аргументов.
     * Лишние знаки отрбрасываются, число округляется по правилам арифметики.
     */
    operator fun plus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.number.add(other.number), max(this.precision, other.precision))

    /**
     * Смена знака
     */
    operator fun unaryMinus(): FixedPointNumber = FixedPointNumber(this.number.unaryMinus(), this.precision)

    /**
     * Вычитание
     */
    operator fun minus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.number.minus(other.number), max(this.precision, other.precision))

    /**
     * Умножение
     */
    operator fun times(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.number.times(other.number)
            .setScale(max(this.precision, other.precision), RoundingMode.HALF_UP), max(this.precision, other.precision))

    /**
     * Деление
     */
    operator fun div(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.number.div(other.number)
            .setScale(min(this.precision, other.precision), RoundingMode.HALF_UP), min(this.precision, other.precision))

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        (this === other || other is FixedPointNumber && this.number == other.number && this.precision == other.precision)

    /**
     * Сравнение на больше/меньше
     */
    override fun compareTo(other: FixedPointNumber): Int = when {
        this.number > other.number -> 1
        this.number < other.number -> -1
        else -> 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = this.number.toString()

    /**
     * Преобразование к вещественному числу
     */
    fun toDouble(): Double = this.number.toDouble()
    override fun hashCode(): Int {
        var result = precision
        result = 31 * result + number.hashCode()
        return result
    }
}
