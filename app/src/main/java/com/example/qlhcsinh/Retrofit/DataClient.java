package com.example.qlhcsinh.Retrofit;

import com.example.qlhcsinh.Object.HocSinh;
import com.example.qlhcsinh.Object.InfoGV;
import com.example.qlhcsinh.Object.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    //phương thức login
    //gửi dữ liệu dạng chuổi lên sever
    @FormUrlEncoded
    @POST("Login.php")
    Call<User> Login(@Field("pTaiKhoan") String User, @Field("pMatKhau") int Pass);

    //phương thức InsertDefault
    @FormUrlEncoded
    @POST("InsertDefault.php")
    Call<String> InsertDefault(@Field("pMSL") int MSL, @Field("pPhoto1") String Photo1, @Field("pName") String Name, @Field("pMail") String Mail, @Field("pPhoto2") String Photo2, @Field("Info") int Info, @Field("pSDT") String SDT);

    //phương thức up ảnh lên sever
    //tạo phương thức gửi ảnh với @Multipart: có thể up file lên sever
    @Multipart
    @POST("UpPhoto.php")
    Call<String> UpPhoto(@Part MultipartBody.Part photo);

    //up chuổi
    @FormUrlEncoded
    @POST("UpChuoi.php")
    Call<String> UpValue(@Field("pMSL") int MSL, @Field("pValue") String Chuoi, @Field("pIn") String Value);

    //Get Info GV
    @FormUrlEncoded
    @POST("GetInfoGV.php")
    Call<InfoGV> GetInfoGV(@Field("pMSL") int MSL);

    //Get HS
    @FormUrlEncoded
    @POST("GetHS.php")
    Call<List<HocSinh>> GetHS(@Field("pMSL") int MSL);

    //Up new HS
    @FormUrlEncoded
    @POST("UpNewHS.php")
    Call<String> UpNewHS(@Field("pMSL") int MSL,
                         @Field("pHoTen") String HoTen,
                         @Field("pMSHS") int MSHS,
                         @Field("pNamSinh") String NamSinh,
                         @Field("pGioiTinh") int GioiTinh,
                         @Field("pDanToc") String DanToc,
                         @Field("pNoiSinh") String NoiSinh,
                         @Field("pChucVu") String ChucVu,
                         @Field("pSdtPh") String SdtPh,
                         @Field("pLinkPhoto") String LinkPhoto,
                         @Field("pCheckID") int ID);

    //Get Detailt HS
    @FormUrlEncoded
    @POST("GetDetailtHS.php")
    Call<HocSinh> GetDetailtHS(@Field("pID") int ID, @Field("pCheck") int Check);

    //Up Link FB
    @FormUrlEncoded
    @POST("UpLinkFB.php")
    Call<String> UpLinkFB(@Field("pID") int ID, @Field("pValue") String LinkFB, @Field("pCheck") int Check);
}
