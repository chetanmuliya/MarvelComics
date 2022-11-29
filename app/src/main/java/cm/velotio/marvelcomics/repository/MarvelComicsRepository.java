package cm.velotio.marvelcomics.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.model.ItemsItem;
import io.reactivex.rxjava3.core.Single;

public interface MarvelComicsRepository {

    LiveData<List<CharacterEntity>> getAllCharacters(MutableLiveData<Response> responseObserver,boolean isFetchFromApi);

    MutableLiveData<List<ItemsItem>> getCharactersById(int id,MutableLiveData<Response> responseObserver);

    void addCharacters(CharacterEntity character);

    void addAllCharacters(List<CharacterEntity> characters);

    Single<CharacterEntity> getCharacterInfoByID(String id);

    LiveData<List<CharacterEntity>> searchCharacterByName(String name);

}
