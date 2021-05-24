package com.example.qlhcsinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlhcsinh.Adapter.AdapterUser;
import com.example.qlhcsinh.Fragment.FragmentHocTap;
import com.example.qlhcsinh.Fragment.FragmentTKB;
import com.example.qlhcsinh.Object.InfoGV;
import com.example.qlhcsinh.Object.User;
import com.example.qlhcsinh.Object.User1;
import com.example.qlhcsinh.Retrofit.DataClient;
import com.example.qlhcsinh.Retrofit.UtilsAPI;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Info_HocSinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    //AppBarLayout: bố cục thanh ứng dụng
    AppBarLayout mAppBarLayout;
    //CollapsingToolbarLayout: thu gọn bố cục thanh công cụ
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    //của tk androidx,Toolbar: thanh công cụ
    Toolbar mToolbar;
    //là nút add, FloatingActionButton: nút hành động nỗi
    FloatingActionButton mFloatingActionButton;
    RecyclerView mRecyclerView;
    AdapterUser adapter;
    Menu mMenu;
    List<User1> mUsers;
    boolean isExpanded = true;//trạng thái của FloatingActionButton

    private static final int FRAGMENT_TKB = 2;
    private static final int FRAGMENT_HOCTAP = 3;
    private static int CURENT_FRAGMENT = 1;

    int REQUEST_CODE_IMAGE = 123;
    String realPath = "";
    String In = "";

    TextView txtName_GV, txtGmail_GV;
    ImageView img_GV1, SetImg, Img_GV2;
    EditText edtValue;

    User userLogin;
    DataClient dataClient = UtilsAPI.getData();

    Dialog dialogSetImg, dialogSetString;

    int Check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_hoc_sinh);
        Anhxa();
        ActionToolBar();

        initToolbar();
        initRecyclerView();
        initToolbarAnimations();
        onClickBtnAdd();
        //sự kiện click item menu Navigation
        mNavigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        userLogin = (User) intent.getSerializableExtra("Info_TKMK");

        setInfoGV();




    }
    private void Anhxa(){
        mToolbar = findViewById(R.id.mToolbar);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        mNavigationView = findViewById(R.id.mNavigationView);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mCollapsingToolbarLayout);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvUser);
        Img_GV2 = findViewById(R.id.Img_GV2);

        //ánh xạ headerLayout của NavigationView
        View headerLayout  = mNavigationView.getHeaderView(0);
        txtGmail_GV = headerLayout .findViewById(R.id.txtGmail_GV);
        txtName_GV = headerLayout .findViewById(R.id.txtName_GV);
        img_GV1 = headerLayout .findViewById(R.id.img_GV1);

    }

    private void ActionToolBar(){
        //gọi đến hàm hỗ trợ tool bar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //gán icon
        mToolbar.setNavigationIcon(R.drawable.ic_menu);

        //sự kiện onClick hiện menu
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //hàm hiện nút add
    private void initToolbar(){
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//để hiện thị nút add
        }
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mUsers = getmUsers();
        adapter = new AdapterUser(mUsers);
        mRecyclerView.setAdapter(adapter);
    }
    private List<User1> getmUsers(){
        List<User1> list = new ArrayList<>();
        for (int i = 0 ; i <= 5 ; i ++){
            User1 user = new User1(R.drawable.mtuannguyen, "Tuấn Nguyễn", "Tp. Hồ Chí Minh");
            User1 user1 = new User1(R.drawable.mtuannguyen_3, "Quốc Tuấn", "Eahiao - Đắk Lắk");
            User1 user2 = new User1(R.drawable.mtuannguyen_5, "Nguyễn Quốc Tuấn", "Gò Vấp - Sài Gòn");
            User1 user3 = new User1(R.drawable.mtuannguyen_6, "Quốc Tuấn Nguyễn", "Q12. Tp. HCM");
            list.add(user);     list.add(user1);    list.add(user2);    list.add(user3);
        }
        return list;
    }

    private void initToolbarAnimations(){
        mCollapsingToolbarLayout.setTitle("Nguyễn Quốc Tuấn");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mtuannguyen_3);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.color_toolbar));
                mCollapsingToolbarLayout.setContentScrimColor(myColor);
                mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.black_trans));
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200){
                    isExpanded = false;
                }else isExpanded = true;
                invalidateOptionsMenu();
            }
        });
    }

    //sự kiện onclick nút add
    private void onClickBtnAdd(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override//hàm bắt sự kiện Button add
            public void onClick(View v) {
                if (userLogin.ismGV_PH()){
                    Intent intent = new Intent(MainActivity_Info_HocSinh.this, MainActivity_AddHs.class);
                    intent.putExtra("Key_MSL", userLogin.getmMSL());
                    startActivity(intent);
                }else
                    Toast.makeText(MainActivity_Info_HocSinh.this, "Phụ Huynh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //bắt sự kiện nút add (FloatingActionButton) khi đc gán vào menu
        if (item.getTitle() == "Add"){
            Toast.makeText(MainActivity_Info_HocSinh.this, "Add menu", Toast.LENGTH_SHORT).show();
        }
        switch (item.getItemId()){
            //sự kiện oncick hiện menu drawable
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;//gán menu ở phần khai báo bằng với menu ta tạo trong resoucre
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mMenu != null && (!isExpanded || mMenu.size() != 1)){
            //gán nút add (FloatingActionButton) vào menu
            mMenu.add("Add").setIcon(R.drawable.ic_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return super.onPrepareOptionsMenu(mMenu);
    }

    //set Fragment
    private void ReplaceFragment(Fragment fragment, String s){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Conten_Fragment, fragment, s);
        fragmentTransaction.commit();
    }

    //sự kiện onclick menu Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_DS_HS:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("tkb");
                Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("hoctap");
                if(fragment != null){
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    CURENT_FRAGMENT = 1;
                }
                if (fragment1 != null){
                    getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                    CURENT_FRAGMENT = 1;
                }
                break;
            case R.id.nav_TKB:
                if (FRAGMENT_TKB != CURENT_FRAGMENT){
                    ReplaceFragment(new FragmentTKB(), "tkb");
                    CURENT_FRAGMENT = FRAGMENT_TKB;
                }
                break;
            case R.id.nav_HocTap:
                if (FRAGMENT_HOCTAP != CURENT_FRAGMENT){
                    ReplaceFragment(new FragmentHocTap(), "hoctap");
                    CURENT_FRAGMENT = FRAGMENT_HOCTAP;
                }
                break;
            case R.id.nav_call:
                Toast.makeText(MainActivity_Info_HocSinh.this, "call", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_sms:
                Toast.makeText(MainActivity_Info_HocSinh.this, "sms", Toast.LENGTH_LONG).show();
                break;
        }
        //đóng menu
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void OnClick_Info(View view){
        switch (view.getId()){
            case R.id.Infor_GV:
                if (userLogin.ismGV_PH()){
                    DialogDefault();
                }
                break;
            case R.id.txtSetPhoto1:
                DialogIMG();
                In = "dPhoto1";
                break;
            case R.id.txtSetName:
                DialogString();
                In = "dName";
                break;
            case R.id.txtSetGmail:
                In = "dMail";
                DialogString();
                break;
            case R.id.txtSetPhoto2:
                DialogIMG();
                In = "dPhoto2";
                break;
            case R.id.txtSetSDT:
                In = "dSDT";
                DialogString();
                break;

            case R.id.Img_GV2:
                if (userLogin.ismGV_PH()){
                    DialogDefault();
                }
                break;

                //sự kiện dialog IMG
            case R.id.SetImg:
                ActivityCompat.requestPermissions(
                        MainActivity_Info_HocSinh.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_IMAGE);
                break;
            case R.id.HuyImg:
                dialogSetImg.cancel();
                if (Check == 1){
                    Check = 0;
                }
                break;
            case R.id.OKImg:
                if (Check == 1){
                    UpPhoto();
                    Check = 0;
                }
                break;

                //dialog set String
            case R.id.OKSetString:
                String value = edtValue.getText().toString().trim();
                if (value.length() > 0){
                    UpChuoi(value, In);
                    dialogSetString.cancel();
                }
                break;
        }
    }



    //Dialog set Default
    private void DialogDefault(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_default);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        //Bo tròn và set vị trí hiển thị dialog
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.gravity = Gravity.CENTER; //hiển thị ở giữa
        window.setAttributes(windowLayoutParams);
        dialog.show();
    }

    private void DialogIMG(){
        dialogSetImg = new Dialog(this);
        dialogSetImg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSetImg.setContentView(R.layout.dialog_set_image);

        //ánh xạ view
        SetImg = dialogSetImg.findViewById(R.id.SetImg);

        Window window = dialogSetImg.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSetImg.show();
    }


    //kết nối tới đường dẫn uri và duyệt xem bên trong có gì hay không nếu có thì get những giá trị đó ra
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

    //xin quyền foder
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_IMAGE && grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
            //Intent.ACTION_PICK: chọn 1 cái gì đó
            Intent intent = new Intent(Intent.ACTION_PICK);
            //chỉ chọn hình ảnh thôi
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_IMAGE);
        }else Toast.makeText(this, "Chưa cấp quyền!", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            //uri: đường dẫn tới hình ảnh đó
            Uri uri = data.getData();
            //gán đường dẫn vào realPath
            realPath = getRealPathFromURI(uri);
            try {
                //sau khi có đường dẫn thì dừng InputStream kết nối và mỡ đường dẫn đó ra
                //bắt lôi try catch
                InputStream inputStream = getContentResolver().openInputStream(uri);
                //convert về dạng Bitmap với BitmapFactory
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //set hình cho image
                SetImg.setImageBitmap(bitmap);
                Check = 1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //up ảnh lên sever
    private void UpPhoto(){
        File file = new File(realPath);//lấy đường dẫn file
        String file_path = file.getAbsolutePath(); //tên file

        //chỉn sửa lại tên hình
        String[] mangtenfile = file_path.split("\\.");
        //sử dụng biến thời gian milis để gán vào tên
        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
        Log.d("BBB-TênHình", file_path);

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
                    UpChuoi(Photo, In);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity_Info_HocSinh.this, "Lỗi:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpChuoi(String Photo, String In){
        if(In.equals("dPhoto1") || In.equals("dPhoto2")){
            Photo = UtilsAPI.BaseUrl + "image/" + Photo;
        }
        Call<String> callBack = dataClient.UpValue(userLogin.getmMSL(), Photo, In);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    if (response.body().equals("Success")){
                        Toast.makeText(MainActivity_Info_HocSinh.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        if (In.equals("dPhoto1") || In.equals("dPhoto2")){
                            dialogSetImg.cancel();
                        }
                        setInfoGV();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity_Info_HocSinh.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //dialog set String
    private void DialogString(){
        dialogSetString = new Dialog(this);
        dialogSetString.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSetString.setContentView(R.layout.dialog_set_string);

        edtValue = dialogSetString.findViewById(R.id.edtValue);
        Window window = dialogSetString.getWindow();
        if (window == null){
            return;
        }
        //Bo tròn và set vị trí hiển thị dialog
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.gravity = Gravity.CENTER; //hiển thị ở giữa
        window.setAttributes(windowLayoutParams);

        dialogSetString.show();
    }

    //set Info GV
    private void setInfoGV(){
        Call<InfoGV> callBack = dataClient.GetInfoGV(userLogin.getmMSL());
        callBack.enqueue(new Callback<InfoGV>() {
            @Override
            public void onResponse(Call<InfoGV> call, Response<InfoGV> response) {
                if (response != null){
                    InfoGV infoGV = response.body();
                    txtName_GV.setText(infoGV.getmName());
                    txtGmail_GV.setText(infoGV.getmGmail());
                    Picasso.get().load(infoGV.getmPhoto1()).placeholder(R.drawable.ic_hoa).error(R.drawable.ic_hoa).into(img_GV1);
                    Picasso.get().load(infoGV.getmPhoto2()).placeholder(R.drawable.ic_hoa).error(R.drawable.ic_hoa).into(Img_GV2);
                }
            }

            @Override
            public void onFailure(Call<InfoGV> call, Throwable t) {
                Toast.makeText(MainActivity_Info_HocSinh.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("BBB-Check-Error-Info", t.getMessage());
            }
        });
    }
}