package api;

import java.util.List;

import model.FoundPostModel;
import model.ImageResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostAPI {
    @POST("founditems/add")
    Call<Void> foundpost(@Body FoundPostModel foundPostModel);
    

    @GET("founditems/add")
    Call<List<FoundPostModel>> getFoundPost();

    @Multipart
    @POST("upload/photo")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);


}
