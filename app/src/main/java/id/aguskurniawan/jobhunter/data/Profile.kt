package id.aguskurniawan.jobhunter.data

/**
 * Data profil yang sudah diisi dari CV Agus Kurniawan.
 * HANYA fakta dari CV yang diberikan — jangan menambah pengalaman,
 * skill, atau gelar yang tidak ada di CV.
 */
data class Profile(
    val fullName: String = "Agus Kurniawan",
    val degree: String = "S.Kom",
    val title: String = "Front-End Developer & IT Support",
    val education: String = "S1 Teknik Informatika, Universitas Pamulang, 2026",
    val gpa: String = "IPK 3.00",
    val location: String = "Tangerang Selatan, Banten",
    val phone: String = "0895-2259-8705",
    val email: String = "agusekurnia@gmail.com",
    val linkedIn: String = "linkedin.com/in/agus-kurniawann-49a130304",
    val experience: String =
        "IT Support Freelance, Mahardhika, Jan 2025–Agu 2026; " +
            "Junior Front-End Developer & IT Project Assistant, 2022–2024",
    val skills: String =
        "JavaScript ES6+, HTML5, CSS3, PHP, MySQL, Bootstrap, " +
            "responsive design, IT/network troubleshooting, Draw.io, Git",
    val targetRole: String = "IT Support",
    val targetArea: String = "Jabodetabek",
    val targetSalaryMin: Int = 5_000_000,
    val targetSalaryMax: Int = 8_000_000,
    val preferFullTime: Boolean = true,
    val acceptPartTime: Boolean = true,
    val greenFlagOnly: Boolean = true
) {
    /** Kueri pencarian yang dibangun otomatis dari CV. */
    fun defaultQuery(): String = "IT Support $targetArea"

    fun skillKeywords(): List<String> = skills
        .split(",")
        .map { it.trim().lowercase() }
        .filter { it.isNotEmpty() }
}
