package cm.velotio.marvelcomics.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.model.CharactersResponse;
import cm.velotio.marvelcomics.model.ResultsItem;

public interface MarvelComicsRepository {

    LiveData<List<CharacterEntity>> getAllCharacters(MutableLiveData<Response> responseObserver,boolean isFetchFromApi);

    List<CharacterEntity> getCharactersById();

    void addCharacters(CharacterEntity character);

    void addAllCharacters(List<CharacterEntity> characters);

    LiveData<List<CharacterEntity>> searchCharacterByName(String name);

}
