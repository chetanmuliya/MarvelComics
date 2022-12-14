package cm.velotio.marvelcomics.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.constant.Util;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.database.local.MarvelComicsDao;
import cm.velotio.marvelcomics.database.local.MarvelComicsDatabase;
import cm.velotio.marvelcomics.database.remote.MarvelComicsService;
import cm.velotio.marvelcomics.database.remote.RetrofitClient;
import cm.velotio.marvelcomics.model.CharactersResponse;
import cm.velotio.marvelcomics.model.ItemsItem;
import cm.velotio.marvelcomics.model.ResultsItem;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarvelComicsRepositoryImpl implements MarvelComicsRepository{

    public static final String TAG = "REPOSITORY_IMPL";
    private MarvelComicsDatabase database;
    private MarvelComicsService api;
    private MarvelComicsDao dao;

    private MutableLiveData<List<CharacterEntity>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CharacterEntity>> searchlistMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ItemsItem>> comicsItems = new MutableLiveData<>();
   /* private MutableLiveData<Response> _responseObserver = new MutableLiveData<>();
    public LiveData<Response> responseObserver = _responseObserver;*/

    List<CharacterEntity> resultsItems = new ArrayList<>();

    public MarvelComicsRepositoryImpl(Application application){
        database = MarvelComicsDatabase.getInstance(application);
        api = RetrofitClient.getClient().create(MarvelComicsService.class);
        dao  = database != null ? database.marvelComicsDao() : null;
    }

    @Override
    public LiveData<List<CharacterEntity>> getAllCharacters(MutableLiveData<Response> responseObserver,boolean isFetchFromApi){
        return getAllCharactersFromRemote(responseObserver,isFetchFromApi);
    }

    private LiveData<List<CharacterEntity>> getAllCharactersFromRemote(MutableLiveData<Response> responseObserver,boolean isFetchFromApi) {
        responseObserver.postValue(Response.loading());
        if (isFetchFromApi){
            getCharacterListfromApi(responseObserver);
        }else {
            dao.getAllCharactersSize().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<CharacterEntity>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }

                @Override
                public void onSuccess(@NonNull List<CharacterEntity> characterEntities) {
                    if (characterEntities.isEmpty()) {
                        getCharacterListfromApi(responseObserver);
                    } else {
                        listMutableLiveData.postValue(characterEntities);
                        responseObserver.postValue(Response.success("Success"));
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    responseObserver.postValue(Response.error("Something went wrong!"));
                }
            });
        }
        return listMutableLiveData;
    }

    private void getCharacterListfromApi(MutableLiveData<Response> responseObserver) {
        api.getCharacters(Util.API_KEY, Util.hash(), Util.ts, Util.LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CharactersResponse>() {
                    @Override
                    public void onNext(@NonNull CharactersResponse charactersResponse) {
                        List<ResultsItem> list = charactersResponse != null ? charactersResponse.getData().getResults() : null;
                        if (!resultsItems.isEmpty()) resultsItems.clear();
                        for (ResultsItem item:list){
                            resultsItems.add(new CharacterEntity(Integer.toString(item.getId()),item.getName(),
                                    item.getThumbnail().getPath(),item.getThumbnail().getExtension()));
                        }
                        Log.d(TAG, "onNext: size "+resultsItems.size());
                        listMutableLiveData.postValue(resultsItems);
                        deleteCharactersToLocal();
                        insertCharactersToLocal(resultsItems);
                        responseObserver.postValue(Response.success("Success"));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseObserver.postValue(Response.error("Something went wrong!"));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void deleteCharactersToLocal() {
        dao.deleteAll().subscribeOn(Schedulers.io()).subscribe();
    }

    private void insertCharactersToLocal(List<CharacterEntity> resultsItems) {
        dao.insertAll(resultsItems).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public MutableLiveData<List<ItemsItem>> getCharactersById(int id,MutableLiveData<Response> responseObserver) {
        return getCharacterComicsListByIdfromApi(id,responseObserver);
    }

    @Override
    public void addCharacters(CharacterEntity character) {

    }

    @Override
    public void addAllCharacters(List<CharacterEntity> characters) {

    }

    @Override
    public Single<CharacterEntity> getCharacterInfoByID(String id) {
        return dao.getCharactersById(id);
    }

    @Override
    public LiveData<List<CharacterEntity>> searchCharacterByName(String name) {
        return getSearchList(name);
    }

    private LiveData<List<CharacterEntity>> getSearchList(String name) {
        dao.searchCharacters(name).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<CharacterEntity>>() {
                    @Override
                    public void accept(List<CharacterEntity> characterEntities) throws Throwable {
                        Log.d(TAG, "addTextChangedListener accept: "+characterEntities);
                        searchlistMutableLiveData.postValue(characterEntities);
                    }
                });
        return searchlistMutableLiveData;
    }

    //get characters comics list by character id
    private MutableLiveData<List<ItemsItem>> getCharacterComicsListByIdfromApi(int id, MutableLiveData<Response> responseObserver) {
        api.getCharactersById(id,Util.API_KEY, Util.hash(), Util.ts, Util.LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CharactersResponse>() {
                    @Override
                    public void onSuccess(@NonNull CharactersResponse charactersResponse) {
                        if (charactersResponse.getData()!= null){
                            if (charactersResponse.getData().getResults().get(0).getComics().getItems().size()>5){
                                List<ItemsItem> topComics = charactersResponse.getData().getResults().get(0).getComics().getItems();
                                Log.d("*************", "onChanged: "+topComics.size());
                                comicsItems.postValue(topComics);
                                responseObserver.postValue(Response.success("SUCCESS"));
                            }
                        }else{
                            Log.d("*************", "onChanged: nulllll "+id);
                            comicsItems.postValue(null);
                            responseObserver.postValue(Response.error("Something went wrong!"));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        comicsItems.postValue(null);
                        Log.d("*************", "onChanged: erroorr "+e.getMessage()+"id "+id);
                        responseObserver.postValue(Response.error("Something went wrong!"));
                    }
                });
        return comicsItems;
    }
}
