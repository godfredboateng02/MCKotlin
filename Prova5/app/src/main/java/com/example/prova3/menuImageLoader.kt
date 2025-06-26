package com.example.prova3

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova3.model.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuImageViewModel : ViewModel() {

    /**  mappa osservabile   */
    private val _images = MutableStateFlow<Map<Int, ImageBitmap?>>(emptyMap())
    val images: StateFlow<Map<Int, ImageBitmap?>> = _images

    fun load(mid: Int) = viewModelScope.launch {
        if (_images.value[mid] != null) return@launch               // già in cache

        val base64 = withContext(Dispatchers.IO) {
            //Storage.getImage(mid)?.base64
        } ?: return@launch

        //val clean  = base64.substringAfter(',')
        //val bytes  = Base64.decode(clean, Base64.DEFAULT)
        //val bmp    = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()

        //_images.update { it + (mid to bmp) }                        // aggiorna solo quella chiave
    }
}
