package id.aguskurniawan.jobhunter.data

/**
 * Sumber lowongan.
 *
 * JUJUR: aplikasi ini TIDAK punya API berbayar dan TIDAK bisa
 * mengambil daftar lowongan dari portal tanpa login. Yang dilakukan:
 *  - membangun URL pencarian publik tiap portal dari kueri CV,
 *  - membukanya di Custom Tab agar pengguna login & melamar manual,
 *  - memberi skor kecocokan untuk lowongan yang pengguna simpan.
 *
 * Contoh lowongan di bawah HANYA contoh format (bukan lowongan nyata
 * yang sedang dibuka) — pengguna mengisi/menyimpan lowongan dari
 * portal, lalu aplikasi memberi skor dan melacak statusnya.
 */
object JobRepository {

    fun portalSearchUrls(profile: Profile, query: String = profile.defaultQuery()): List<Pair<Portal, String>> =
        Portal.all().map { portal ->
            portal to portal.searchUrl(query, profile.targetArea)
        }

    /** Contoh format kartu lowongan (bukan lowongan nyata). */
    fun sampleJobs(): List<Job> = listOf(
        Job(
            id = "contoh-1",
            title = "IT Support",
            company = "Contoh Perusahaan",
            location = "Jakarta Selatan",
            salaryText = "Rp 5–8 jt",
            salaryMin = 5_000_000,
            salaryMax = 8_000_000,
            jobType = "Full-time",
            portal = "JobStreet",
            url = "https://id.jobstreet.com/",
            postedDate = "Contoh — buka portal untuk lowongan terbaru",
            description = "IT support, troubleshooting hardware software network, BPJS, jenjang karir"
        )
    )
}
