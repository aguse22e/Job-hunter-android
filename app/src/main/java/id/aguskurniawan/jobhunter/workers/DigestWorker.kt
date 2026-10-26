package id.aguskurniawan.jobhunter.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import id.aguskurniawan.jobhunter.data.Profile

/** Pengingat harian lokal: cek lowongan baru di portal. Tanpa server. */
class DigestWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "daily_job_digest"
        const val TAG = "job_digest"
        private const val CHANNEL_ID = "job_digest_channel"
    }

    override suspend fun doWork(): Result {
        val profile = Profile()
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "Pengingat Lowongan Harian",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Job Hunter — cek lowongan hari ini")
            .setContentText(
                "Cari \"${profile.defaultQuery()}\" di JobStreet, Glints, dan LinkedIn. " +
                    "Target: ${profile.targetRole} ${profile.targetArea}."
            )
            .setAutoCancel(true)
            .build()
        manager.notify(1, notification)
        return Result.success()
    }
}
