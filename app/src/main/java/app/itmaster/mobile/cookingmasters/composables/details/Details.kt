package app.itmaster.mobile.cookingmasters.composables.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.itmaster.mobile.cookingmasters.model.LocalAppDatabase
import app.itmaster.mobile.cookingmasters.model.Recipe
import app.itmaster.mobile.cookingmasters.model.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Details(slug: String?, navHostController: NavHostController) {

    val db = LocalAppDatabase.current
    var recipe by remember { mutableStateOf<Recipe?>(null) }

    GlobalScope.launch(Dispatchers.IO) {
        if (slug != null) {
            var r = db.recipeDao().getBySlug(slug)
            var steps = db.stepDao().getByRecipe(slug)
            r?.preparationSteps = steps
            GlobalScope.launch(Dispatchers.Main) {
                // En el main thread (o main coroutine)
                recipe = r
            }
        }
    }

    if (recipe == null) {
        CircularProgressIndicator()
    } else {
        Column(
            Modifier
                .padding(16.dp)
                .background(Color.White)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            RecipeHeader(recipe!!, onBack = {
                navHostController.popBackStack()
            })
            Spacer(Modifier.height(24.dp))
            Ingredients(recipe!!.ingredients)
            Spacer(Modifier.height(24.dp))
            Steps(recipe!!.preparationSteps)
            Spacer(Modifier.height(24.dp))

            val db = LocalAppDatabase.current
            Button(onClick = {
                // Delete recipe
                GlobalScope.launch(Dispatchers.IO) {
                    db.stepDao().deleteByRecipe(recipe!!.slug)
                    db.recipeDao().delete(recipe!!)
                }
                navHostController.popBackStack()
            }) {
                Text("Delete Recipe")
            }
        }
    }
}

@Composable
fun RecipeHeader(recipe: Recipe, onBack: ()->Unit) {
    Column(

    ) {
        Row {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Back")
            }
            Text(recipe.recipeName, style = TextStyle(fontWeight = FontWeight.Black,
                fontSize = 20.sp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("${recipe.ingredients.size} ingredients | ${recipe.cookingDuration} min.",
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Description", style = TextStyle(fontWeight = FontWeight.Black,
            fontSize = 16.sp, color = MaterialTheme.colorScheme.primary))
        Text(recipe.description, style = TextStyle(fontWeight = FontWeight.Normal,
            fontSize = 13.sp))

    }
}

@Composable
fun Steps(steps: List<Step>) {
    val  style = TextStyle(fontWeight = FontWeight.Normal,
            fontSize = 13.sp)
    Text("Steps", style = TextStyle(fontWeight = FontWeight.Black,
        fontSize = 16.sp, color = MaterialTheme.colorScheme.primary))
    for (step in steps) {
        Spacer(Modifier.height(16.dp))
        Row {
            Text("${step.order+1}", style = style)
            Spacer(modifier = Modifier.width(12.dp))
            Text(step.stepName, style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 13.sp))
        }
        Text(step.stepDescription, style = style)
    }
}

@Composable
fun Ingredients(ing: Map<String, String>) {
    Text("Ingredients", style = TextStyle(fontWeight = FontWeight.Black,
        fontSize = 16.sp, color = MaterialTheme.colorScheme.primary))

    ing.forEach { (key, value) ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key,
                style = TextStyle(fontSize = 13.sp),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = value,
                textAlign = TextAlign.Right,
                style = TextStyle(fontSize = 13.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}