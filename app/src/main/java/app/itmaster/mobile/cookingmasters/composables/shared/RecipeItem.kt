package app.itmaster.mobile.cookingmasters.composables.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.itmaster.mobile.cookingmasters.model.Recipe

@Composable
fun RecipeItem(recipe: Recipe) {

    Box(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Text(recipe.recipeName, style = TextStyle(fontWeight = FontWeight.Black,
                fontSize = 20.sp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("${recipe.ingredients.size} ingredients | ${recipe.cookingDuration} min.",
                style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
            )
        }
    }

}