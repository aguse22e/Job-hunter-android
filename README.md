# Job Hunter Otomatis — Agus Kurniawan

Aplikasi Android untuk mencari pekerjaan berdasarkan data CV, otomatis
menyusun kueri dan memberi skor kecocokan — lalu membuka halaman lamaran
di portal aslinya.

**Profil (sudah diisi dari CV):** Agus Kurniawan, S.Kom — Front-End Developer
& IT Support, S1 Teknik Informatika Universitas Pamulang 2026 (IPK 3.00),
Tangerang Selatan, Banten, 0895-2259-8705, agusekurnia@gmail.com,
linkedin.com/in/agus-kurniawann-49a130304.
Pengalaman: IT Support Freelance Mahardhika Jan 2025–Agu 2026; Junior
Front-End Developer & IT Project Assistant 2022–2024.
Skill: JavaScript ES6+, HTML5, CSS3, PHP, MySQL, Bootstrap, responsive
design, IT/network troubleshooting, Draw.io, Git.
Target: IT Support full-time Jabodetabek, perusahaan green flag, gaji
Rp 5–8 jt, part-time sebagai cadangan.

## Fitur

- **Profil** — data CV bisa diedit, tersimpan lokal (DataStore, di HP saja).
- **Cari Lowongan** — kueri otomatis dari CV ("IT Support Jabodetabek"),
  filter full-time / part-time, portal: JobStreet, LinkedIn, Indeed,
  Glints, Kalibrr, Karir.com, Jobindo, Google Jobs. Setiap portal dibuka
  di Custom Tab.
- **Auto-match** — skor 0–100 dari judul/skill/lokasi/gaji, sinyal
  green-flag (BPJS, tunjangan, jenjang karir, dll.), lowongan yang jelas
  tidak cocok (mis. batas umur maks < 31) disembunyikan dengan alasan.
- **Pelacak Lamaran** — status Disimpan / Dilamar / Interview / Ditolak /
  Diterima, tanggal, catatan, versi CV, pengingat follow-up. Tersimpan
  lokal (Room).
- **Surat Lamaran** — template lokal dari data CV, bisa diedit, salin/bagikan.
- **Pengingat harian** — WorkManager lokal, notifikasi digest tiap 24 jam.

## Cara membuka & build APK

1. Buka Android Studio → **Open** → pilih folder `job-hunter-android`.
2. Biarkan Gradle sync selesai (butuh internet pertama kali untuk
   mengunduh Gradle 8.7, AGP 8.5.2, dan dependensi).
3. Sambungkan HP Android (USB debugging) atau jalankan emulator.
4. **Build → Build App Bundle(s) / APK(s) → Build APK(s)**.
   APK hasil build ada di `app/build/outputs/apk/debug/app-debug.apk`.
   Atau dari terminal (dengan Android SDK terpasang):
   `./gradlew assembleDebug`

## Batasan yang jujur

- Aplikasi ini **tidak auto-submit lamaran**. Portal kerja (JobStreet,
  LinkedIn, dll.) mewajibkan login akun masing-masing, dan sering ada
  CAPTCHA. Aplikasi menyiapkan, mengurutkan berdasarkan skor, dan membuka
  halaman lamaran — langkah terakhir (login + kirim lamaran) dilakukan
  pengguna secara manual di portal.
- Tidak ada API berbayar dan tidak ada server: semua data tersimpan lokal
  di HP. Lowongan nyata didapat dari portal; kartu "contoh" di layar Cari
  hanya menunjukkan format skor, bukan lowongan yang sedang dibuka.

## Teknologi

Kotlin + Jetpack Compose, Material 3, Gradle Kotlin DSL, minSdk 26,
targetSdk 34, package `id.aguskurniawan.jobhunter`, Room, DataStore,
WorkManager, Navigation Compose, Custom Tabs.
