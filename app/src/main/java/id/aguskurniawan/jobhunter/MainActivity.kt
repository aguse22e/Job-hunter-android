package id.aguskurniawan.jobhunter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import id.aguskurniawan.jobhunter.ui.screens.CoverLetterScreen
import id.aguskurniawan.jobhunter.ui.screens.ProfileScreen
import id.aguskurniawan.jobhunter.ui.screens.SearchScreen
import id.aguskurniawan.jobhunter.ui.screens.TrackerScreen
import id.aguskurniawan.jobhunter.ui.theme.JobHunterTheme

private data class Tab(val label: String, val icon: ImageVector)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { JobHunterAppContent() }
    }
}

@Composable
fun JobHunterAppContent() {
    val tabs = listOf(
        Tab("Cari", Icons.Filled.Search),
        Tab("Profil", Icons.Filled.Person),
        Tab("Lacak", Icons.Filled.List),
        Tab("Surat", Icons.Filled.Mail)
    )
    var selected by remember { mutableStateOf(0) }
    JobHunterTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selected == index,
                            onClick = { selected = index },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        ) { padding ->
            androidx.compose.foundation.layout.Box(Modifier.padding(padding)) {
                when (selected) {
                    0 -> SearchScreen()
                    1 -> ProfileScreen()
                    2 -> TrackerScreen()
                    3 -> CoverLetterScreen()
                }
            }
        }
    }
}
