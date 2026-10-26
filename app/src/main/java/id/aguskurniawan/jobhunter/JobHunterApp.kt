package id.aguskurniawan.jobhunter

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import id.aguskurniawan.jobhunter.workers.DigestWorker
import java.util.concurrent.TimeUnit

class JobHunterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Pengingat digest harian (lokal, tanpa server).
        val request = PeriodicWorkRequestBuilder<DigestWorker>(24, TimeUnit.HOURS)
            .addTag(DigestWorker.TAG)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            DigestWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
