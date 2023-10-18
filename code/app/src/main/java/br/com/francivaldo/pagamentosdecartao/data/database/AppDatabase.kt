package br.com.confchat.mobile.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.francivaldo.pagamentosdecartao.data.database.dao.PaymentDao
import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment

@Database(
    entities = [
        Payment::class
    ],
    version = 2
)
@TypeConverters(Convertes::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paymentDao(): PaymentDao
    companion object{
        private lateinit var INSTANCE: AppDatabase
        fun getInstance(context:Context) : AppDatabase{
            if(!::INSTANCE.isInitialized) {
                synchronized(AppDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "pagamentos_db"
                        )
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
        private val MIGRATION_1_2:Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE payment ADD COLUMN success INTEGER NOT NULL DEFAULT 0;")
            }
        }
    }
}