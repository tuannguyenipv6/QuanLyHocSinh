package com.example.qlhcsinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qlhcsinh.Object.HocSinh;
import com.example.qlhcsinh.Retrofit.DataClient;
import com.example.qlhcsinh.Retrofit.UtilsAPI;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_AddHs extends AppCompatActivity {
    EditText Edt_NewNameHS, Edt_NewMSHS, Edt_NewNamSinh, Edt_NewDanToc, Edt_NewNoiSinh, Edt_NewChucVu, Edt_NewSDT;
    ImageView Img_NewPhoto;
    int CheckImage = 1, REQUEST_CODE_IMAGE =123;
    String realPath = "";
    DataClient dataClient = UtilsAPI.getData();
    int MSL = 0;

    boolean GioiTinh = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_hs);
        Anhxa();
        Intent intent = getIntent();
        MSL = intent.getIntExtra("Key_MSL", 0);
    }
    private void Anhxa(){
        Edt_NewNameHS = findViewById(R.id.Edt_NewNameHS);
        Edt_NewMSHS = findViewById(R.id.Edt_NewMSHS);
        Edt_NewNamSinh = findViewById(R.id.Edt_NewNamSinh);
        Edt_NewDanToc = findViewById(R.id.Edt_NewDanToc);
        Edt_NewNoiSinh = findViewById(R.id.Edt_NewNoiSinh);
        Edt_NewChucVu = findViewById(R.id.Edt_NewChucVu);
        Edt_NewSDT = findViewById(R.id.Edt_NewSDT);
        Img_NewPhoto = findViewById(R.id.Img_NewPhoto);
    }
    public void OnClickAddHS(View view){
        switch (view.getId()){
            case R.id.Huy_New:
                finish();
                break;

            case R.id.OK_New:
                String HoTen = Edt_NewNameHS.getText().toString().trim();
                String MSHS = Edt_NewMSHS.getText().toString().trim();
                String NamSinh = Edt_NewNamSinh.getText().toString().trim();
                String DanToc = Edt_NewDanToc.getText().toString().trim();
                String NoiSinh = Edt_NewNoiSinh.getText().toString().trim();
                String ChucVu = Edt_NewChucVu.getText().toString().trim();
                String SDT_PH = Edt_NewSDT.getText().toString().trim();
                if (HoTen.length() > 0 && MSHS.length() > 0 && NamSinh.length() > 0 && DanToc.length() > 0 && NoiSinh.length() > 0 && ChucVu.length() > 0 && SDT_PH.length() > 0){
                    int intMSHS = Integer.parseInt(MSHS), intNamSinh = Integer.parseInt(NamSinh);
                    HocSinh hocSinh = new HocSinh(HoTen, intMSHS, intNamSinh, GioiTinh, DanToc, NoiSinh, ChucVu, SDT_PH);
                    if (CheckImage == 2){
                        UpPhoto(hocSinh);
                    }else {
                        if (hocSinh.getmGioiTinh()){
                            UpNewHS(hocSinh, "matdinhnam");
                        }else UpNewHS(hocSinh, "matdinhnu");
                    }
                }
                break;

            case R.id.Img_NewPhoto:
                ActivityCompat.requestPermissions(
                        MainActivity_AddHs.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_IMAGE);
                break;

            case R.id.GT_Nam:
                Img_NewPhoto.setImageResource(R.drawable.nam);
                CheckImage = 1;
                GioiTinh = true;
                break;

            case R.id.GT_Nu:
                Img_NewPhoto.setImageResource(R.drawable.nu);
                CheckImage = 1;
                GioiTinh = false;
                break;
        }
    }

    //xin quyền foder
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_IMAGE && grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_IMAGE);
        }else Toast.makeText(this, "Chưa cấp quyền!", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Img_NewPhoto.setImageBitmap(bitmap);
                CheckImage = 2;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getRealPathFromURI(Uri uri){
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()){
            int Column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(Column_index);
        }
        cursor.close();
        return path;
    }

    //TODO Up Photo
    private void UpPhoto(HocSinh hocSinh){
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();

        //chỉn sửa lại tên hình
        String[] mangtenfile = file_path.split("\\.");
        //sử dụng biến thời gian milis để gán vào tên
        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //uploaded_file: key gửi lên, 2 là đường dẫn, 3 là kiểu dữ liệu của file
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        //Tạo kết nối và gửi giá trị về
        Call<String> callBack = dataClient.UpPhoto(body);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    String Photo = response.body();
                    UpNewHS(hocSinh, Photo);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity_AddHs.this, "Lỗi:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //TODO New HS
    private void UpNewHS(HocSinh hocSinh, String Photo){
        if (CheckImage == 2){
            Photo = UtilsAPI.BaseUrl + "image/" + Photo;
        }
        int Check = 0;
        if (hocSinh.getmGioiTinh()){
            Check = 1;
        }
        Call<String> callBack = dataClient.UpNewHS(MSL, hocSinh.getmHoTen(), hocSinh.getmMSHS(), hocSinh.getmNamSinh(),Check, hocSinh.getmDanToc(), hocSinh.getmNoiSinh(), hocSinh.getmChucVu(), hocSinh.getmSdtPh(), Photo);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    String s = response.body();
                    Toast.makeText(MainActivity_AddHs.this, s, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity_AddHs.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}