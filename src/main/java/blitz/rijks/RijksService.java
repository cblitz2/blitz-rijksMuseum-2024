package blitz.rijks;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * http://www.rijksmuseum.nl/
 */

public interface RijksService {

    @GET("/api/en/collection")
    Single<ArtObjects> getPage(
            @Query("key") String apiKey,
            @Query("p") int pageNum
    );

    @GET("/api/en/collection")
    Single<ArtObjects> query(
            @Query("key") String apiKey,
            @Query("p") int pageNum,
            @Query("q") String query
    );

    @GET("/api/en/collection")
    Single<ArtObjects> artist(
            @Query("key") String apiKey,
            @Query("p") int pageNum,
            @Query("involvedMaker") String artist
    );
}
