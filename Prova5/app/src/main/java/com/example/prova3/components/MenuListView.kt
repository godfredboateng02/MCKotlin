/*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prova4.components.Menu
import com.example.prova4.components.MenuElement

@Composable
fun MenuListView(navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items((1..10).toList().chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (i in rowItems) {
                    MenuElement(
                        menu = Menu(
                            nome = "Titolo $i",
                            descrizione = "Descrizione $i",
                            prezzo = 10f + i,
                            distanza = "${10 + i} min"
                        ),
                        navController
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.size(187.dp, 260.dp))
                }
            }
        }
    }
}
*/