package id.aguskurniawan.jobhunter.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {
    @Query("SELECT * FROM applications ORDER BY appliedDate DESC")
    fun getAll(): Flow<List<JobApplication>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(app: JobApplication)

    @Query("UPDATE applications SET status = :status WHERE jobId = :jobId")
    suspend fun updateStatus(jobId: String, status: String)

    @Query("DELETE FROM applications WHERE jobId = :jobId")
    suspend fun delete(jobId: String)
}

@Database(entities = [JobApplication::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun applicationDao(): ApplicationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "job_hunter.db"
                ).build().also { INSTANCE = it }
            }
    }
}
