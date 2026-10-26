package id.aguskurniawan.jobhunter.ui.screens

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import id.aguskurniawan.jobhunter.data.Profile
import id.aguskurniawan.jobhunter.data.ProfileStore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val store = remember { ProfileStore(context) }
    val profile by store.profile.collectAsState(initial = Profile())
    val scope = rememberCoroutineScope()
    var name by remember(profile) { mutableStateOf(profile.fullName) }
    var title by remember(profile) { mutableStateOf(profile.title) }
    var location by remember(profile) { mutableStateOf(profile.location) }
    var phone by remember(profile) { mutableStateOf(profile.phone) }
    var email by remember(profile) { mutableStateOf(profile.email) }
    var skills by remember(profile) { mutableStateOf(profile.skills) }
    var saved by remember { mutableStateOf(false) }

    Column(
        Modifier.padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Text("Profil — data dari CV", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Text("Ubah bila CV berubah. Data hanya tersimpan di HP ini.")
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(name, { name = it }, label = { Text("Nama lengkap") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(title, { title = it }, label = { Text("Jabatan / gelar") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(location, { location = it }, label = { Text("Lokasi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(phone, { phone = it }, label = { Text("Telepon / WA") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(skills, { skills = it }, label = { Text("Skill (pisahkan koma)") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
        Spacer(Modifier.height(8.dp))
        Text("Pendidikan: ${profile.education} (${profile.gpa})")
        Text("Pengalaman: ${profile.experience}")
        Text("Target: ${profile.targetRole} full-time ${profile.targetArea}, Rp 5–8 jt (part-time cadangan)")
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            scope.launch {
                store.save(profile.copy(fullName = name, title = title, location = location, phone = phone, email = email, skills = skills))
                saved = true
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("Simpan Profil") }
        if (saved) Text("Profil tersimpan di perangkat.")
    }
}
