package io.mns.mpfm.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;

import io.mns.mpfm.AppExecutors;
import io.mns.mpfm.db.converters.DateConverter;
import io.mns.mpfm.db.converters.TransactionTypeConverter;
import io.mns.mpfm.db.dao.TransactionDao;
import io.mns.mpfm.db.entities.Tag;
import io.mns.mpfm.db.entities.Transaction;

@Database(entities = {Transaction.class, Tag.class}, version = 3, exportSchema = false)
@TypeConverters({DateConverter.class, TransactionTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    private static final String DATABASE_NAME = "pfm_db";

    private AppExecutors appExecutors;

    public abstract TransactionDao transactionDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                    sInstance.appExecutors = executors;
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addMigrations(MIGRATION_2_3)
                .build();
    }

    public Executor getExecutor() {
        return appExecutors.diskIO();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `tags_table` (`id` INTEGER NOT NULL, "
                    + "`title` TEXT , " + " `color` TEXT, " + " PRIMARY KEY(`id`))");
        }
    };
}

