package api;


import model.Account;
import model.Register;
import model.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;



public interface UserApi {

    @POST("users/login")
    Call<Response> login(@Body Account account);

    @POST("users/signup")
    Call<Response> register(@Body Register register);



    }

