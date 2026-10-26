package id.aguskurniawan.jobhunter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Satu lowongan dari sebuah portal. Skor dihitung lokal oleh MatchScorer. */
data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salaryText: String,
    val salaryMin: Int? = null,
    val salaryMax: Int? = null,
    val jobType: String, // "Full-time" / "Part-time" / "Kontrak" / "Remote"
    val portal: String,
    val url: String,
    val postedDate: String,
    val description: String = "",
    val matchScore: Int = 0,
    val matchReasons: List<String> = emptyList(),
    val greenFlagSignals: List<String> = emptyList(),
    val hiddenReason: String? = null // terisi jika disembunyikan (mis. batas umur)
)

/** Portal kerja + URL pencarian publiknya. Dibuka di Custom Tab,
 *  login / CAPTCHA portal tetap ditangani pengguna di sana. */
enum class Portal(val displayName: String, val searchUrl: (String, String) -> String) {
    JOBSTREET("JobStreet", { q, loc ->
        "https://id.jobstreet.com/id/jobs?keywords=${q.encode()}&location=${loc.encode()}"
    }),
    LINKEDIN("LinkedIn", { q, loc ->
        "https://www.linkedin.com/jobs/search/?keywords=${q.encode()}&location=${loc.encode()}"
    }),
    INDEED("Indeed", { q, loc ->
        "https://id.indeed.com/jobs?q=${q.encode()}&l=${loc.encode()}"
    }),
    GLINTS("Glints", { q, _ ->
        "https://glints.com/id/opportunities/jobs/explore?keyword=${q.encode()}"
    }),
    KALIBRR("Kalibrr", { q, _ ->
        "https://www.kalibrr.com/home?query=${q.encode()}"
    }),
    KARIR("Karir.com", { q, _ ->
        "https://karir.com/search?q=${q.encode()}"
    }),
    JOBINDO("Jobindo", { q, _ ->
        "https://www.jobindo.com/search?q=${q.encode()}"
    }),
    GOOGLE_JOBS("Google Jobs", { q, loc ->
        "https://www.google.com/search?q=${(q + " " + loc + " lowongan").encode()}&ibp=htl;jobs"
    });

    companion object {
        fun all(): List<Portal> = values().toList()
    }
}

private fun String.encode(): String =
    java.net.URLEncoder.encode(this, Charsets.UTF_8)

/** Status lamaran yang dilacak pengguna. */
enum class ApplyStatus(val label: String) {
    SAVED("Disimpan"),
    APPLIED("Dilamar"),
    INTERVIEW("Interview"),
    REJECTED("Ditolak"),
    ACCEPTED("Diterima")
}

@Entity(tableName = "applications")
data class JobApplication(
    @PrimaryKey val jobId: String,
    val title: String,
    val company: String,
    val portal: String,
    val url: String,
    val status: String = ApplyStatus.SAVED.name,
    val appliedDate: String = "",
    val notes: String = "",
    val cvVersion: String = "CV IT Support 2026"
)
