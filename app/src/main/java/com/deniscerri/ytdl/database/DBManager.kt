package com.involvex.ytmp3dlp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.involvex.ytmp3dlp.database.dao.CommandTemplateDao
import com.involvex.ytmp3dlp.database.dao.CookieDao
import com.involvex.ytmp3dlp.database.dao.DownloadDao
import com.involvex.ytmp3dlp.database.dao.HistoryDao
import com.involvex.ytmp3dlp.database.dao.LogDao
import com.involvex.ytmp3dlp.database.dao.ObserveSourcesDao
import com.involvex.ytmp3dlp.database.dao.ResultDao
import com.involvex.ytmp3dlp.database.dao.SearchHistoryDao
import com.involvex.ytmp3dlp.database.dao.TerminalDao
import com.involvex.ytmp3dlp.database.models.CommandTemplate
import com.involvex.ytmp3dlp.database.models.CookieItem
import com.involvex.ytmp3dlp.database.models.DownloadItem
import com.involvex.ytmp3dlp.database.models.HistoryItem
import com.involvex.ytmp3dlp.database.models.LogItem
import com.involvex.ytmp3dlp.database.models.ResultItem
import com.involvex.ytmp3dlp.database.models.SearchHistoryItem
import com.involvex.ytmp3dlp.database.models.TemplateShortcut
import com.involvex.ytmp3dlp.database.models.TerminalItem
import com.involvex.ytmp3dlp.database.models.observeSources.ObserveSourcesItem

@TypeConverters(Converters::class)
@Database(
    entities = [
        ResultItem::class,
        HistoryItem::class,
        DownloadItem::class,
        CommandTemplate::class,
        SearchHistoryItem::class,
        TemplateShortcut::class,
        CookieItem::class,
        LogItem::class,
        TerminalItem::class,
        ObserveSourcesItem::class
   ],
    version = 28
)
abstract class DBManager : RoomDatabase(){
    abstract val resultDao : ResultDao
    abstract val historyDao : HistoryDao
    abstract val downloadDao : DownloadDao
    abstract val commandTemplateDao : CommandTemplateDao
    abstract val searchHistoryDao: SearchHistoryDao
    abstract val cookieDao: CookieDao
    abstract val logDao: LogDao
    abstract val terminalDao: TerminalDao
    abstract val observeSourcesDao: ObserveSourcesDao

    enum class SORTING{
        DESC, ASC
    }

    companion object {
        //prevents multiple instances of db getting created at the same time
        @Volatile
        private var instance : DBManager? = null
        //if its not null return it, otherwise create db
        fun getInstance(context: Context) : DBManager {
            return instance ?: synchronized(this){

                val dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DBManager::class.java,
                    "YTDLnisDatabase"
                )
                    .addTypeConverter(Converters())
                    .addMigrations(*Migrations.migrationList)
                    .fallbackToDestructiveMigration()
                    .build()
                instance = dbInstance
                dbInstance
            }
        }

    }

}

