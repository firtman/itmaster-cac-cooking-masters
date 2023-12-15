package app.itmaster.mobile.cookingmasters.model
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipes")
data class Recipe(
    // Propiedades del constructor primario
    @PrimaryKey
    @SerializedName("slug") val slug: String,

    @SerializedName("name") val recipeName: String,
    @SerializedName("type") val type: String,
    @SerializedName("duration") val cookingDuration: Int,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("description") val description: String,

    @SerializedName("ingredients") val ingredients: Map<String, String>,
) {
    // Propiedades que no son obligatorias y no están en el constructor
    @Ignore
    @SerializedName("steps") var preparationSteps: List<Step> = listOf()
}


@Entity(tableName = "steps", primaryKeys = ["recipeSlug", "order"])
data class Step(
    var recipeSlug: String,
    var order: Int,
    @SerializedName("name") val stepName: String,
    @SerializedName("description") val stepDescription: String,
    @SerializedName("timer") val timer: Int? = null
)