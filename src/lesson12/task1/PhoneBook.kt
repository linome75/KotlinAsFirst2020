@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    private var PhBook: MutableMap<String, MutableSet<String>> = mutableMapOf()


    private fun addSmTh(name: String, phone: String): Boolean {
        if ((name != "") && (phone == "") && name !in PhBook) {
            PhBook.put(name, mutableSetOf(phone))
            PhBook[name]!!.clear()
            return true
        }
        if ((name != "") && (phone != "") && (name in PhBook)) {
            for (names in PhBook.keys) {
                if (phone in PhBook[names]!!) return false
            }
            PhBook[name]!!.add(phone)
            return true
        }
        return false
    }

    private fun rmvSmTh(name: String, phone: String): Boolean {
        if (name != "" && name in PhBook) {
            PhBook.remove(name)
            return true
        }
        if (phone != "")
            for (names in PhBook.keys) {
                if (phone in PhBook[names]!!) {
                    PhBook.remove(names)
                    return true
                }
            }
        return false
    }

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean = addSmTh(name, "")

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean = rmvSmTh(name, "")

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean = addSmTh(name, phone)

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean = rmvSmTh("", phone)

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        return if (name in PhBook.keys) PhBook[name]!!
        else setOf()
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for (names in PhBook.keys) if (phone in PhBook[names]!!) return names
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneBook) return false
        for ((key, value) in PhBook)
            if (value != other.PhBook.getValue(key)) return false
        return true
    }

    override fun hashCode(): Int {
        return PhBook.hashCode()
    }
}

