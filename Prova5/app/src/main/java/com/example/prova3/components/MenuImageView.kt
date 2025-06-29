import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MenuImageView(
    immagine : ImageBitmap?,
    modifier: Modifier = Modifier,
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
