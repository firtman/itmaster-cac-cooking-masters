package app.itmaster.mobile.cookingmasters.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// DAO = Data Access Objects
@Dao
interface RecipeDao {
    @Insert
    fun insertAll(vararg recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE slug = :slug")
    suspend fun getBySlug(slug: String): Recipe?
}

@Dao
interface StepDao {

    fun insertFromRecipe(recipe: Recipe) {
         recipe.preparationSteps.forEachIndexed { i, step ->
             step.recipeSlug = recipe.slug
             step.order = i
             insertAll(step)
         }
    }

    @Insert
    fun insertAll(vararg step: Step)

    @Query("SELECT * FROM steps")
    fun getAll(): List<Step>

    @Query("SELECT * FROM steps WHERE recipeSlug = :slug ORDER BY `order`")
    suspend fun getByRecipe(slug: String): List<Step>

    @Query("DELETE FROM steps WHERE recipeSlug = :slug")
    fun deleteByRecipe(slug: String)
}