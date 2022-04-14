package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.CategoryUtility.categories
import utils.CategoryUtility.isValidCategory

class CategoryUtilityTest {

    @Nested
    inner class CategoryValid {
        @Test
        fun categoriesReturnsFullCategoriesSet() {
            Assertions.assertEquals(5, categories.size)
            Assertions.assertTrue(categories.contains("jeep"))
            Assertions.assertTrue(categories.contains("saloon"))
            Assertions.assertTrue(categories.contains("hatchback"))
            Assertions.assertTrue(categories.contains("sport"))
            Assertions.assertTrue(categories.contains("super"))
            Assertions.assertFalse(categories.contains(""))
        }

        @Test
        fun isValidCategoryTrueWhenCategoryExists() {
            Assertions.assertTrue(isValidCategory("jeep"))
            Assertions.assertTrue(isValidCategory("jEeP"))
            Assertions.assertTrue(isValidCategory("SUPER"))
            Assertions.assertTrue(isValidCategory("sporT"))
        }

        @Test
        fun isValidCategoryFalseWhenCategoryDoesNotExist() {
            Assertions.assertFalse(isValidCategory("spo"))
            Assertions.assertFalse(isValidCategory("supers"))
            Assertions.assertFalse(isValidCategory(""))
        }
    }
}
