package cm.velotio.marvelcomics.ui.home_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.repository.MarvelComicsRepository;
import cm.velotio.marvelcomics.repository.MarvelComicsRepositoryImpl;

public class MarvelCharactersViewModel extends AndroidViewModel {

    private MarvelComicsRepository repository;


    public MarvelCharactersViewModel(@NonNull Application application) {
        super(application);
        repository = new MarvelComicsRepositoryImpl(application);
    }

    public LiveData<List<CharacterEntity>> getAllCharacters(MutableLiveData<Response> responseObserver, boolean isFetchFromApi){
        return repository.getAllCharacters(responseObserver,isFetchFromApi);
    }

    public LiveData<List<CharacterEntity>> getSearchList(String query){
        return repository.searchCharacterByName(query);
    }
}
