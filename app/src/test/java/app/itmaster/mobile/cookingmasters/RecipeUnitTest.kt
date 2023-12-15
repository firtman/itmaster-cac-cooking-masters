package app.itmaster.mobile.cookingmasters

import app.itmaster.mobile.cookingmasters.model.Recipe
import org.junit.Test

class RecipeUnitTest {

    @Test
    fun testIngredients() {
        val r = Recipe("test", "nombre", "", 23, "", "",
            mapOf()
        )
        assert(r.ingredients.size==1, { "Ingrediente vacío" })

    }

}