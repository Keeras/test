package com.example.auctionsystemapplication.BLL;

import auctionsystemapi.AuctionSystemAPI;
import model.LoginSignupResponse;
import model.Users;
import retrofit2.Call;
import retrofit2.Response;
import url.Url;

public class SignupBLL {
    private String userFname;
    private String userLname;
    private String username;
    private String password;
    boolean isSuccess = false;

    public SignupBLL(String userFname, String userLname, String username, String password) {
        this.userFname = userFname;
        this.userLname = userLname;
        this.username = username;
        this.password = password;
    }

    public boolean checkRegister()

    {

        Users users = new Users(this.userFname,this.userLname,this.username, this.password);

        AuctionSystemAPI auctionSystemAPI = Url.getInstance().create(AuctionSystemAPI.class);
        Call<LoginSignupResponse> usersCall = auctionSystemAPI.addUser(users);
        try {
            Response<LoginSignupResponse> loginSignupResponseResponse = usersCall.execute();
            if (loginSignupResponseResponse.body().getSuccess()) {
                isSuccess = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }
}
