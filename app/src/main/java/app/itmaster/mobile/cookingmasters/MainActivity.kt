package app.itmaster.mobile.cookingmasters

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.room.Room
import app.itmaster.mobile.cookingmasters.composables.App
import app.itmaster.mobile.cookingmasters.model.AI
import app.itmaster.mobile.cookingmasters.model.AI.generativeModel
import app.itmaster.mobile.cookingmasters.model.AppDatabase
import app.itmaster.mobile.cookingmasters.model.LocalAppDatabase
import app.itmaster.mobile.cookingmasters.model.Recipe
import app.itmaster.mobile.cookingmasters.ui.theme.CookingMastersTheme
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "cooking-recipes"
        ).build()

        setContent {
            CookingMastersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(LocalAppDatabase provides db) {
                        App()
                    }

                }
            }
        }
    }
}
