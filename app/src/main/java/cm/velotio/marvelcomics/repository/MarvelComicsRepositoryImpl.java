package cm.velotio.marvelcomics.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
import cm.velotio.marvelcomics.model.ResultsItem;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class MarvelComicsRepositoryImpl implements MarvelComicsRepository{

    public static final String TAG = "REPOSITORY_IMPL";
    private MarvelComicsDatabase database;
    private MarvelComicsService api;
    private MarvelComicsDao dao;

    private MutableLiveData<List<CharacterEntity>> listMutableLiveData = new MutableLiveData<>();
   /* private MutableLiveData<Response> _responseObserver = new MutableLiveData<>();
    public LiveData<Response> responseObserver = _responseObserver;*/

    List<CharacterEntity> resultsItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isInsertedList = false;

    public MarvelComicsRepositoryImpl(Application application){
        database = MarvelComicsDatabase.getInstance(application);
        api = RetrofitClient.getClient().create(MarvelComicsService.class);
        dao  = database != null ? database.marvelComicsDao() : null;
    }

    @Override
    public LiveData<List<CharacterEntity>> getAllCharacters(MutableLiveData<Response> responseObserver){
        return getAllCharactersFromRemote(responseObserver);
    }

    private LiveData<List<CharacterEntity>> getAllCharactersFromRemote(MutableLiveData<Response> responseObserver) {
        responseObserver.postValue(Response.loading());
        dao.getAllCharactersSize().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<CharacterEntity>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<CharacterEntity> characterEntities) {
                if (characterEntities.isEmpty() && !isInsertedList){
                    getCharacterListfromApi(responseObserver);
                    responseObserver.postValue(Response.success("Success"));
                    isInsertedList = false;
                }else {
                    listMutableLiveData.postValue(characterEntities);
                    responseObserver.postValue(Response.success("Success"));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                responseObserver.postValue(Response.error("Something went wrong!"));
            }
        });
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
                        for (ResultsItem item:list){
                            resultsItems.add(new CharacterEntity(Integer.toString(item.getId()),item.getName(),
                                    item.getThumbnail().getPath(),item.getThumbnail().getExtension()));
                        }
                        listMutableLiveData.postValue(resultsItems);
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

    private void insertCharactersToLocal(List<CharacterEntity> resultsItems) {
        dao.insertAll(resultsItems).subscribeOn(Schedulers.io()).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    @Override
    public List<CharacterEntity> getCharactersById() {
        return null;
    }

    @Override
    public void addCharacters(CharacterEntity character) {

    }

    @Override
    public void addAllCharacters(List<CharacterEntity> characters) {

    }
}
