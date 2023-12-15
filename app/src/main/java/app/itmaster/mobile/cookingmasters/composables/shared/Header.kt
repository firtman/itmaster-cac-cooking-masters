package app.itmaster.mobile.cookingmasters.composables.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.itmaster.mobile.cookingmasters.R

@Preview(showBackground = true, widthDp = 380)
@Composable
fun Header() {
    Box(
        Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Cooking Masters",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(35.dp)
        )
    }

}