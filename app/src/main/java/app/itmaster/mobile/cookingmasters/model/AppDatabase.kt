package app.itmaster.mobile.cookingmasters.model

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [Recipe::class, Step::class], version = 1)
@TypeConverters(DBTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun stepDao(): StepDao

}

var LocalAppDatabase = staticCompositionLocalOf<AppDatabase> {
    error("No hay database")
}

class DBTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): Map<String, String> {
        return gson.fromJson(value, object : TypeToken<Map<String, String>>() {}.type)
    }

    @TypeConverter
    fun fromMap(map: Map<String, String>): String {
        return gson.toJson(map)
    }
}