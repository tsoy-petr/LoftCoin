package hootor.com.loftcoin.data.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import hootor.com.loftcoin.data.db.room.AppDatabase;
import hootor.com.loftcoin.data.db.room.DatabaseImplRoom;

public class DatabaseInitializer {

    public Database init(Context context) {
        AppDatabase appDatabase = Room
                .databaseBuilder(context, AppDatabase.class, "loftcoin.db")
                .allowMainThreadQueries()
                .build();
        return new DatabaseImplRoom(appDatabase);
    }

}
