package id.aguskurniawan.jobhunter.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import id.aguskurniawan.jobhunter.data.CoverLetter
import id.aguskurniawan.jobhunter.data.Profile
import id.aguskurniawan.jobhunter.data.ProfileStore

@Composable
fun CoverLetterScreen() {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val store = remember { ProfileStore(context) }
    val profile by store.profile.collectAsState(initial = Profile())
    var company by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("IT Support") }
    var text by remember(profile) { mutableStateOf(CoverLetter.generate(profile, "", "IT Support")) }

    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Surat Lamaran", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Text("Template lokal dari data CV — edit sebelum dipakai.")
        OutlinedTextField(company, { company = it }, label = { Text("Perusahaan") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(position, { position = it }, label = { Text("Posisi") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { text = CoverLetter.generate(profile, company, position) }, modifier = Modifier.fillMaxWidth()) {
            Text("Buat Surat Lamaran")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(text, { text = it }, modifier = Modifier.fillMaxWidth(), minLines = 12)
        Spacer(Modifier.height(8.dp))
        Button(onClick = { clipboard.setText(AnnotatedString(text)) }, modifier = Modifier.fillMaxWidth()) {
            Text("Salin")
        }
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Lamaran $position — ${profile.fullName}")
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(intent, "Bagikan surat lamaran"))
        }, modifier = Modifier.fillMaxWidth()) { Text("Bagikan") }
    }
}
