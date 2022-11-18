package cm.velotio.marvelcomics.database.local;

import android.content.Context;
import android.util.Log;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CharacterEntity.class},version = 1, exportSchema = false)
public abstract class MarvelComicsDatabase extends RoomDatabase {

    public abstract MarvelComicsDao marvelComicsDao();

    private static volatile MarvelComicsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MarvelComicsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MarvelComicsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MarvelComicsDatabase.class, "MarvelComics")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
