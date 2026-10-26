package id.aguskurniawan.jobhunter.data

/**
 * Penilaian kecocokan lowongan terhadap CV — semuanya lokal dan jujur.
 * Tidak ada klaim "auto-apply": skor hanya membantu mengurutkan.
 */
object MatchScorer {

    private val jabodetabek = listOf(
        "jakarta", "bogor", "depok", "tangerang", "bekasi",
        "tangsel", "tangerang selatan", "jabodetabek"
    )

    /** Sinyal perusahaan yang cenderung "green flag". */
    private val greenFlagKeywords = listOf(
        "bpjs", "asuransi kesehatan", "tunjangan", "thr",
        "jenjang karir", "pelatihan", "training", "wfh", "hybrid",
        "cuti", "bonus"
    )

    /** Sembunyikan lowongan yang jelas tidak cocok, dengan alasan. */
    fun hiddenReason(job: Job): String? {
        val text = (job.title + " " + job.description).lowercase()
        // Batas umur maksimum yang umum ditulis di lowongan.
        val maxAge = Regex("""maks(?:imal)?\s*(\d{2})\s*(?:tahun|thn)""")
            .find(text)?.groupValues?.getOrNull(1)?.toIntOrNull()
        if (maxAge != null && maxAge < 31) {
            return "Batas umur maks $maxAge tahun — di bawah umur pelamar (31–35)"
        }
        if ("pengalaman minimal 5 tahun" in text || "min. 5 tahun" in text) {
            return "Syarat pengalaman min. 5 tahun melebihi pengalaman di CV"
        }
        return null
    }

    fun score(job: Job, profile: Profile): Job {
        val text = (job.title + " " + job.description).lowercase()
        var score = 0
        val reasons = mutableListOf<String>()

        if ("it support" in text) { score += 30; reasons += "Judul cocok: IT Support" }
        if ("front-end" in text || "frontend" in text) { score += 10; reasons += "Ada unsur Front-End" }
        if ("troubleshoot" in text || "troubleshooting" in text) { score += 10; reasons += "Troubleshooting" }
        if ("network" in text || "jaringan" in text) { score += 10; reasons += "Jaringan" }
        if ("hardware" in text || "software" in text) { score += 5; reasons += "Hardware/Software" }

        val matchedSkills = profile.skillKeywords().filter { it in text }
        if (matchedSkills.isNotEmpty()) {
            score += matchedSkills.size * 5
            reasons += "Skill cocok: ${matchedSkills.take(3).joinToString(", ")}"
        }
        if (jabodetabek.any { it in job.location.lowercase() }) {
            score += 15; reasons += "Lokasi Jabodetabek"
        }
        if (job.jobType.equals("Full-time", ignoreCase = true)) {
            score += 10; reasons += "Full-time (target utama)"
        } else if (job.jobType.equals("Part-time", ignoreCase = true)) {
            score += 5; reasons += "Part-time (cadangan)"
        }
        job.salaryMin?.let {
            if (it >= profile.targetSalaryMin) { score += 10; reasons += "Gaji sesuai target" }
        }

        val greenFlags = greenFlagKeywords.filter { it in text }
        return job.copy(
            matchScore = score.coerceIn(0, 100),
            matchReasons = reasons,
            greenFlagSignals = greenFlags,
            hiddenReason = hiddenReason(job)
        )
    }

    fun rank(jobs: List<Job>, profile: Profile, hideMismatched: Boolean = true): List<Job> {
        val scored = jobs.map { score(it, profile) }
        return if (hideMismatched) {
            scored.filter { it.hiddenReason == null }
        } else {
            scored
        }.sortedByDescending { it.matchScore }
    }
}
