package com.example.qlhcsinh.Retrofit;

import com.example.qlhcsinh.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DataClient {
    //phương thức login
    //gửi dữ liệu dạng chuổi lên sever
    @FormUrlEncoded
    @POST("Login.php")
    Call<User> Login(@Field("pTaiKhoan") String User, @Field("pMatKhau") int Pass);
}
