import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun ErrorHandler(errorMessage: String?) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(errorMessage != null) }

    if (showDialog && errorMessage != null) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        LaunchedEffect(Unit) {
            delay(5000)
            showDialog = false
        }
    }
}