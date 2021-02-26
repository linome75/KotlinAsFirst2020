@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

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
    constructor(x: Double) : this(TODO(), TODO())

    /**
     * Конструктор из строки вида x+yi
     */
    constructor(s: String) : this(TODO(), TODO())

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex {
        val res: Complex
        res.re = other.re + re
        res.im = other.im + im
        return res
    }

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex {
        val res: Complex
        res.re = 0 - re
        res.im = 0 - im
        return res
    }

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex {
        val res: Complex
        res.re = re - other.re
        res.im = im-other.im
        return res
    }

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex {
        val res: Complex
        res.re = re * other.re - im * other.im
        res.im = im*other.re+re*other.im
        return res}

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = TODO()

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = TODO()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = TODO()
}
