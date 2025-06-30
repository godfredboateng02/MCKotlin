
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun MenuImageView(
    immagine : ImageBitmap?,
    modifier: Modifier = Modifier.fillMaxWidth(),
    contentScale: ContentScale = ContentScale.Crop
) {
    /* 1️⃣  Decodifica una sola volta quando cambia la stringa */

    /* 2️⃣  Disegna l’immagine o un placeholder */
    if (immagine != null) {
        Image(
            bitmap         = immagine,
            contentDescription = null,
            modifier       = modifier,
            contentScale   = contentScale
        )
    } else {
        Box(modifier.background(Color.LightGray))   // shimmer / placeholder
    }
}
