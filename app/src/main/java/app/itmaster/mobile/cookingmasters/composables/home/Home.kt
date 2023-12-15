package app.itmaster.mobile.cookingmasters.composables.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.room.Room
import app.itmaster.mobile.cookingmasters.composables.shared.RecipeItem
import app.itmaster.mobile.cookingmasters.model.AI
import app.itmaster.mobile.cookingmasters.model.AppDatabase
import app.itmaster.mobile.cookingmasters.model.LocalAppDatabase
import app.itmaster.mobile.cookingmasters.model.Recipe
import app.itmaster.mobile.cookingmasters.model.RecipesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@Composable
fun Home(navHostController: NavHostController) {
    Column {
        val db = LocalAppDatabase.current
        var isLoadingRecipe by remember { mutableStateOf(false) }
        if (!isLoadingRecipe) {
            NewRecipe(onIngredientsReady = {
                isLoadingRecipe = true

                GlobalScope.launch(Dispatchers.Default) {
                    val newRecipe = AI.requestRecipe(it)
                    if (newRecipe == null) {
                        //TODO: show snackbar or error message
                    } else {
                        db.recipeDao().insertAll(newRecipe)
                        db.stepDao().insertFromRecipe(newRecipe)
                    }
                    isLoadingRecipe = false
                }
            })
        } else {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator()
            }
        }
        Filter()
        RecipeList(onRecipeClick = {
            navHostController.navigate("details/${it.slug}")
        })
    }

}

@Composable
fun Filter() {
    
}

@Composable
fun RecipeList(onRecipeClick: (Recipe)->Unit) {
    var db = LocalAppDatabase.current
    val vm: RecipesViewModel = viewModel() // ViewModel for Compose
    vm.loadRecipes(db.recipeDao())

    val recipes by vm.recipes.observeAsState()

    LazyColumn {
        if (recipes!=null) {
            items(recipes!!) {
                Box(Modifier.clickable { onRecipeClick(it) }) {
                    RecipeItem(recipe = it)
                }
            }
        }
    }

}