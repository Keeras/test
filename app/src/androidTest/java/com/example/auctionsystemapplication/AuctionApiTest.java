package com.example.auctionsystemapplication;

import com.example.auctionsystemapplication.BLL.AddBidBLL;
import com.example.auctionsystemapplication.BLL.LoginBLL;
import com.example.auctionsystemapplication.BLL.SignupBLL;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AuctionApiTest {


    @Test
    public void testLoginPass(){
        LoginBLL loginBLL = new LoginBLL("anish", "anish");
        boolean result = loginBLL.checkUser();
        assertEquals(true, result);
    }

    @Test
    public void testLoginFail(){
        LoginBLL loginBLL = new LoginBLL("aish", "anish");
        boolean result = loginBLL.checkUser();
        assertEquals(true, result);
    }

    @Test
     public void testSignUpPass()
    {
        SignupBLL signupBLL= new SignupBLL("z","z","z","z");
        boolean result = signupBLL.checkRegister();
        assertEquals(true,result);
    }

    @Test
    public void testSignUpFail()
    {
        SignupBLL signupBLL= new SignupBLL("anish","anish","anish","anish");
        boolean result = signupBLL.checkRegister();
        assertEquals(true,result);
    }

    @Test
    public void AddBidPass()
    {
        AddBidBLL addBidBLL= new AddBidBLL(1,"imageFile-1562757615898.jpg","Test",100,200,300,"2019-07-15","Other",0,"Ongoing");
        boolean result = addBidBLL.checkAddBid();
        assertEquals(true,result);
    }


}
