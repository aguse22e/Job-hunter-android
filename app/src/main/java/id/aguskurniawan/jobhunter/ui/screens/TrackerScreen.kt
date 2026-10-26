package id.aguskurniawan.jobhunter.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import id.aguskurniawan.jobhunter.data.AppDatabase
import id.aguskurniawan.jobhunter.data.ApplyStatus
import id.aguskurniawan.jobhunter.data.JobApplication
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TrackerScreen() {
    val context = LocalContext.current
    val dao = remember { AppDatabase.get(context).applicationDao() }
    val apps by dao.getAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text("Pelacak Lamaran", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Text("Catat setiap lamaran. Ingat: lamaran akhir dilakukan manual di portal — aplikasi ini hanya melacak.")
            OutlinedTextField(title, { title = it }, label = { Text("Posisi") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(company, { company = it }, label = { Text("Perusahaan") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(notes, { notes = it }, label = { Text("Catatan / follow-up") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = {
                if (title.isBlank() || company.isBlank()) return@Button
                scope.launch {
                    dao.upsert(
                        JobApplication(
                            jobId = "${company.trim()}-${title.trim()}-${System.currentTimeMillis()}",
                            title = title.trim(), company = company.trim(),
                            portal = "-", url = "",
                            appliedDate = LocalDate.now().toString(), notes = notes
                        )
                    )
                    title = ""; company = ""; notes = ""
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Simpan Lamaran") }
            Spacer(Modifier.height(12.dp))
        }
        items(apps) { app ->
            var expanded by remember { mutableStateOf(false) }
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text("${app.title} — ${app.company}")
                    Text("Status: ${ApplyStatus.valueOf(app.status).label} • ${app.appliedDate}")
                    Text("CV: ${app.cvVersion}")
                    if (app.notes.isNotBlank()) Text("Catatan: ${app.notes}")
                    Button(onClick = { expanded = true }) { Text("Ubah Status") }
                    DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                        ApplyStatus.values().forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.label) },
                                onClick = {
                                    scope.launch { dao.updateStatus(app.jobId, status.name) }
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
