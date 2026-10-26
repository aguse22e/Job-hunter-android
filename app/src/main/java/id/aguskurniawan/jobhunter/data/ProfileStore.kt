package id.aguskurniawan.jobhunter.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("profile")

/** Profil tersimpan lokal (DataStore). Default = data CV. */
class ProfileStore(private val context: Context) {
    private val nameKey = stringPreferencesKey("fullName")
    private val titleKey = stringPreferencesKey("title")
    private val locationKey = stringPreferencesKey("location")
    private val phoneKey = stringPreferencesKey("phone")
    private val emailKey = stringPreferencesKey("email")
    private val skillsKey = stringPreferencesKey("skills")
    private val targetKey = stringPreferencesKey("targetRole")

    val profile: Flow<Profile> = context.dataStore.data.map { prefs ->
        Profile(
            fullName = prefs[nameKey] ?: Profile().fullName,
            title = prefs[titleKey] ?: Profile().title,
            location = prefs[locationKey] ?: Profile().location,
            phone = prefs[phoneKey] ?: Profile().phone,
            email = prefs[emailKey] ?: Profile().email,
            skills = prefs[skillsKey] ?: Profile().skills,
            targetRole = prefs[targetKey] ?: Profile().targetRole
        )
    }

    suspend fun save(profile: Profile) {
        context.dataStore.edit { prefs ->
            prefs[nameKey] = profile.fullName
            prefs[titleKey] = profile.title
            prefs[locationKey] = profile.location
            prefs[phoneKey] = profile.phone
            prefs[emailKey] = profile.email
            prefs[skillsKey] = profile.skills
            prefs[targetKey] = profile.targetRole
        }
    }
}
