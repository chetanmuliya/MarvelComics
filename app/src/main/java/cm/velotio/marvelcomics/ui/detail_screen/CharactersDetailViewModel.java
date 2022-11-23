package cm.velotio.marvelcomics.ui.detail_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.model.ItemsItem;
import cm.velotio.marvelcomics.repository.MarvelComicsRepository;
import cm.velotio.marvelcomics.repository.MarvelComicsRepositoryImpl;

public class CharactersDetailViewModel extends AndroidViewModel {

    private MarvelComicsRepository repository;


    public CharactersDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new MarvelComicsRepositoryImpl(application);
    }

    public LiveData<List<ItemsItem>> getCharactersById(String id,MutableLiveData<Response> responseObserver){
        return repository.getCharactersById(id,responseObserver);
    }
}
