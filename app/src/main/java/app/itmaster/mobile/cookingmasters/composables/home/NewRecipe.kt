package app.itmaster.mobile.cookingmasters.composables.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
private fun NewRecipe_Preview() {
    NewRecipe(onIngredientsReady = {})
}

@Composable
fun NewRecipe(onIngredientsReady: (String)->Unit) {
    val ingredients = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier.padding(8.dp)
    ) {
        OutlinedTextField(value = ingredients.value,
            onValueChange = { ingredients.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter a list of ingredients") },
            placeholder = { Text("eg. eggs, butter, chicken")},
            maxLines = 3,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (ingredients.value.length<5) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("You have to enter a list of ingredients")
                }
            } else {
                onIngredientsReady(ingredients.value)
            }
        }) {
            Text("Generate AI Recipe 🪄")
        }
        SnackbarHost(hostState = snackbarHostState)
    }


    
}