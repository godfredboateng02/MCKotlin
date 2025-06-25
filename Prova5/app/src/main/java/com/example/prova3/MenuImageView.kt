import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prova3.MenuImageViewModel


@Composable
fun MenuImageView(
    mid: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val vm: MenuImageViewModel = viewModel()

    /* avvia il download solo la prima volta per questo mid */
    LaunchedEffect(mid) { vm.load(mid) }

    /* ① ottieni l’intera mappa dal StateFlow */
    val images by vm.images.collectAsState()      // Map<Int, ImageBitmap?>

    /* ② prendi la bitmap di questo mid */
    val image: ImageBitmap? = images[mid]

    if (image != null) {
        Image(
            bitmap            = image,            // è già ImageBitmap
            contentDescription = null,
            modifier          = modifier,
            contentScale      = contentScale
        )
    } else {
        Box(modifier.background(Color.LightGray))
    }
}

