package utils

object CategoryUtility {

    @JvmStatic
    var categories = listOf("jeep", "saloon", "hatchback", "sport", "super")

    @JvmStatic
    fun isValidCategory(category: String): Boolean {
        return categories.contains(category.lowercase())
    }
}