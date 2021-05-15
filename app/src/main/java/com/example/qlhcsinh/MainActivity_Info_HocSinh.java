package com.example.qlhcsinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qlhcsinh.Adapter.AdapterUser;
import com.example.qlhcsinh.Fragment.FragmentHocTap;
import com.example.qlhcsinh.Fragment.FragmentTKB;
import com.example.qlhcsinh.Object.User1;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    }
    private void Anhxa(){
        mToolbar = findViewById(R.id.mToolbar);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        mNavigationView = findViewById(R.id.mNavigationView);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mCollapsingToolbarLayout);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvUser);
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
                //bắt sự kiện nút add khi chưa add vào menu của resoucre
                Toast.makeText(MainActivity_Info_HocSinh.this, "add!", Toast.LENGTH_SHORT).show();
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
    }}