package com.example.qlhcsinh.Retrofit;

public class UtilsAPI {
    public static final String BaseUrl = "http://192.168.43.84/QLHS/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BaseUrl).create(DataClient.class);
    }

}
