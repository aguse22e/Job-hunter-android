package id.aguskurniawan.jobhunter.data

/** Template surat lamaran lokal — hanya memakai data CV, bisa diedit. */
object CoverLetter {
    fun generate(profile: Profile, company: String, position: String): String {
        val companyText = company.ifBlank { "[Nama Perusahaan]" }
        val positionText = position.ifBlank { profile.targetRole }
        return """
            |${profile.location}, ${java.time.LocalDate.now()}
            |
            |Kepada Yth.
            |HRD $companyText
            |di tempat
            |
            |Dengan hormat,
            |
            |Saya, ${profile.fullName}, ${profile.degree}, lulusan ${profile.education} (${profile.gpa}), mengajukan lamaran untuk posisi $positionText di $companyText.
            |
            |Saya memiliki pengalaman sebagai ${profile.experience}. Keahlian saya meliputi ${profile.skills}.
            |
            |Saya terbiasa menangani troubleshooting hardware, software, dan jaringan, serta dokumentasi dan dukungan pengguna. Saya siap bekerja full-time di wilayah ${profile.targetArea}.
            |
            |Besar harapan saya dapat bergabung dan berkontribusi di $companyText. Saya dapat dihubungi melalui ${profile.phone} atau ${profile.email}.
            |
            |Hormat saya,
            |
            |${profile.fullName}
            |${profile.phone} | ${profile.email}
            |${profile.linkedIn}
        """.trimMargin()
    }
}
