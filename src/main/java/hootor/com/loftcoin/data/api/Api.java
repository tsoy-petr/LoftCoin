package hootor.com.loftcoin.data.api;


import hootor.com.loftcoin.data.api.model.RateResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("ticker")
    Observable<RateResponse> ticker(@Query("structure") String structure, @Query("convert") String convert);

}
