package id.aguskurniawan.jobhunter.ui.screens

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import id.aguskurniawan.jobhunter.data.JobRepository
import id.aguskurniawan.jobhunter.data.MatchScorer
import id.aguskurniawan.jobhunter.data.Profile
import id.aguskurniawan.jobhunter.data.ProfileStore

private fun openPortal(context: Context, url: String) {
    CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
}

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val store = remember { ProfileStore(context) }
    val profile by store.profile.collectAsState(initial = Profile())
    var query by remember(profile) { mutableStateOf(profile.defaultQuery()) }
    var jobType by remember { mutableStateOf("Full-time") }
    var hideMismatched by remember { mutableStateOf(true) }

    val ranked = remember(query, jobType, hideMismatched, profile) {
        MatchScorer.rank(JobRepository.sampleJobs(), profile, hideMismatched)
    }

    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text("Cari Lowongan", style = MaterialTheme.typography.titleLarge)
            Text("Kueri dibuat otomatis dari CV. Portal dibuka di Custom Tab — login portal dan lamaran akhir tetap manual di portal itu.")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(query, { query = it }, label = { Text("Kata kunci") }, modifier = Modifier.fillMaxWidth())
            Row {
                FilterChip(jobType == "Full-time", { jobType = "Full-time" }, label = { Text("Full-time") })
                FilterChip(jobType == "Part-time", { jobType = "Part-time" }, label = { Text("Part-time") })
                FilterChip(hideMismatched, { hideMismatched = !hideMismatched }, label = { Text("Sembunyikan yang tidak cocok") })
            }
            Spacer(Modifier.height(8.dp))
            Text("Portal — ketuk untuk membuka pencarian:", style = MaterialTheme.typography.titleMedium)
        }
        items(JobRepository.portalSearchUrls(profile, query)) { (portal, url) ->
            Button(
                onClick = { openPortal(context, url) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            ) { Text("Buka ${portal.displayName}") }
        }
        item {
            Spacer(Modifier.height(12.dp))
            Text("Contoh kartu lowongan (skor dari CV):", style = MaterialTheme.typography.titleMedium)
            Text("Lowongan nyata didapat dari portal di atas — simpan lowongan yang kamu temukan, lalu skor dihitung di sini.")
        }
        items(ranked) { job ->
            Card(
                Modifier.fillMaxWidth().padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F4))
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(job.title, style = MaterialTheme.typography.titleMedium)
                    Text("${job.company} • ${job.location} • ${job.salaryText}")
                    Text("${job.portal} • ${job.jobType} • ${job.postedDate}")
                    Text("Skor kecocokan: ${job.matchScore}/100")
                    job.matchReasons.forEach { Text("• $it") }
                    if (job.greenFlagSignals.isNotEmpty()) {
                        Text("Green flag: ${job.greenFlagSignals.joinToString(", ")}")
                    }
                    job.hiddenReason?.let { Text("Disembunyikan: $it", color = Color.Red) }
                    Button(onClick = { openPortal(context, job.url) }) { Text("Buka di ${job.portal}") }
                }
            }
        }
    }
}
