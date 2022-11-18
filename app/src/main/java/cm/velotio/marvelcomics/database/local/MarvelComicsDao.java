package cm.velotio.marvelcomics.database.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cm.velotio.marvelcomics.model.ResultsItem;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MarvelComicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<CharacterEntity> characters);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterEntity character);

    @Query("SELECT * FROM characters")
    LiveData<List<CharacterEntity>> getAllCharacters();

    @Query("SELECT * FROM characters")
    Single<List<CharacterEntity>> getAllCharactersSize();

    @Query("SELECT * FROM characters")
    List<CharacterEntity> isCharactersStored();

    @Query("SELECT * FROM characters WHERE id = :cid")
    CharacterEntity getCharactersById(String cid);
}



