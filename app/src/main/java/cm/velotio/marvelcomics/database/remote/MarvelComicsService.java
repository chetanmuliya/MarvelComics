package cm.velotio.marvelcomics.database.remote;

import cm.velotio.marvelcomics.constant.Util;
import cm.velotio.marvelcomics.model.CharactersResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelComicsService {

    @GET("/v1/public/characters")
    Observable<CharactersResponse> getCharacters(
            @Query("apikey") String API_KEY,
            @Query("hash") String HASH,
            @Query("ts") String TS,
            @Query("limit") int LIMIT
    );

    @GET("/v1/public/characters/{id}")
    Single<CharactersResponse> getCharactersById(
            @Path("id") int id,
            @Query("apikey") String API_KEY,
            @Query("hash") String HASH,
            @Query("ts") String TS,
            @Query("limit") int LIMIT
    );
}
