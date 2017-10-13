package alefvanltd.alefvanltdtest;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by Dmitry Shatalin
 * mailto: shatalinds@gmail.com
 * AlefVanLTDTest
 * 13.10.17
 */

public interface NetworkService {
    @GET("list.php")
    Observable<ResponseBody> list();
}
