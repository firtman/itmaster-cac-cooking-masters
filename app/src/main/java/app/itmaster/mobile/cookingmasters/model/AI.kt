package app.itmaster.mobile.cookingmasters.model

import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson

object AI {
    val generativeModel = GenerativeModel(
        // Use a model that's applicable for your use case (see "Implement basic use cases" below)
        modelName = "gemini-pro", // "models/gemini-pro"
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "" -----
        //TODO: INSERT YOUR API KEY HERE!
    )

    suspend fun requestRecipe(ingredients: String) : Recipe? {
        val template = """
                {
                    "name": "Dulce de Leche Brownies",
                    "type": "Dessert",
                    "slug": "ddl-brownies",
                    "image": "/images/original/ddl-brownies.png",
                    "duration": 60,
                    "description": "Rich, fudgy brownies swirled with velvety dulce de leche, creating a decadent and irresistible dessert.",
                    "ingredients": {
                        "Unsalted butter": "1 cup (2 sticks)",
                        "Semisweet chocolate chips": "1 1/2 cups",
                        "Granulated sugar": "1 1/2 cups",
                        "Eggs": "4",
                        "Vanilla extract": "1 teaspoon",
                        "All-purpose flour": "1 cup",
                        "Cocoa powder": "1/3 cup",
                        "Salt": "1/2 teaspoon",
                        "Dulce de leche": "1 cup"
                    },
                    "steps": [
                        {
                            "name": "Preheat Oven",
                            "description": "Preheat your oven to 350°F (175°C). Grease a 9x13-inch baking pan and line with parchment paper, leaving an overhang on two sides."
                        },
                        {
                            "name": "Melt Butter and Chocolate",
                            "description": "In a large microwave-safe bowl, combine the butter and semisweet chocolate chips. Microwave in 30-second intervals, stirring after each, until completely melted and smooth."
                        },
                        {
                            "name": "Mix Wet Ingredients",
                            "description": "Add the granulated sugar to the melted chocolate mixture, stirring until combined. Stir in the eggs, one at a time, followed by the vanilla extract."
                        },
                        {
                            "name": "Mix Dry Ingredients",
                            "description": "In a separate bowl, whisk together the all-purpose flour, cocoa powder, and salt. Gradually add the dry ingredients to the wet ingredients, stirring until just combined."
                        }
                    ]
                }
            """.trimIndent()
        val prompt = """
                You are a professional chef and you will be responsible of creating recipes 
                or end users. Create a recipe for the list of ingredients in ***, take basic 
                ingredients such as salt, pepper, and others as always available. 
                If the list of ingredients contains no-ingredients elements, return "false" 
                without any other characters; otherwise, return the recipe in JSON format valid
                without any other characters around
                including list of ingredients and the list of steps following the <template>. 
                *** 
                $ingredients
                ***
                <template>
                $template
                </template>
            """.trimIndent()
        val response = generativeModel.generateContent(prompt)
        println("Respuesta de la IA: ${response.text}")
        if (response.text == "false") {
            return null // No hay receta
        } else {
            val recipe = Gson().fromJson(response.text, Recipe::class.java)
            return recipe
        }
    }
}