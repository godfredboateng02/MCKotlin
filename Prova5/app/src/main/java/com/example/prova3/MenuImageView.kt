import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MenuImageView(
    base64: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    /* 1️⃣  Decodifica una sola volta quando cambia la stringa */
    val imageBitmap by produceState<ImageBitmap?>(initialValue = null, base64) {
        value = withContext(Dispatchers.IO) {
            runCatching {
                val bytes   = Base64.decode(base64, Base64.DEFAULT)
                val bitmap  = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                bitmap?.asImageBitmap()
            }.getOrNull()
        }
    }

    /* 2️⃣  Disegna l’immagine o un placeholder */
    if (imageBitmap != null) {
        Image(
            bitmap         = imageBitmap!!,
            contentDescription = null,
            modifier       = modifier,
            contentScale   = contentScale
        )
    } else {
        Box(modifier.background(Color.LightGray))   // shimmer / placeholder
    }
}
