package com.example.auctionsystemapplication.BLL;

import auctionsystemapi.AuctionSystemAPI;
import model.LoginSignupResponse;
import model.Users;
import retrofit2.Call;
import retrofit2.Response;
import url.Url;

public class LoginBLL {
    private String username;
    private String password;
    boolean isSuccess = false;

    public LoginBLL(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public boolean checkUser()

    {

        Users users = new Users(this.username, this.password);

        AuctionSystemAPI auctionSystemAPI = Url.getInstance().create(AuctionSystemAPI.class);
        Call<LoginSignupResponse> usersCall = auctionSystemAPI.checkUser(users);
        try {
            Response<LoginSignupResponse> loginSignupResponseResponse = usersCall.execute();
            if (loginSignupResponseResponse.body().getSuccess()) {

                Url.Token = loginSignupResponseResponse.body().getToken();
                Url.userId = loginSignupResponseResponse.body().getUserId();
                isSuccess = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }
}
