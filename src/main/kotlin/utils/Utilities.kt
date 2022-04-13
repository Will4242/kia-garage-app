package utils

object Utilities {

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun isValidNoDoors(doors: Int): Boolean {
        return (doors in 2..5)
    }

    @JvmStatic
    fun isValidYear(year: Int): Boolean {
        return (year in 0..99)
    }
}

