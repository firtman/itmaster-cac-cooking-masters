package app.itmaster.mobile.cookingmasters.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipesViewModel: ViewModel() {

    lateinit var recipes: LiveData<List<Recipe>>

    fun loadRecipes(recipeDao: RecipeDao) {
        viewModelScope.launch {
            recipes = recipeDao.getAll()
        }
    }
}