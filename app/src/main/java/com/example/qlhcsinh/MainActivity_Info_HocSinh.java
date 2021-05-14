package com.example.qlhcsinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qlhcsinh.Fragment.FragmentHocTap;
import com.example.qlhcsinh.Fragment.FragmentListHS;
import com.example.qlhcsinh.Fragment.FragmentTKB;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity_Info_HocSinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private static final int FRAGMENT_LIST_HS = 1;
    private static final int FRAGMENT_TKB = 2;
    private static final int FRAGMENT_HOCTAP = 3;

    private static int CURENT_FRAGMENT = FRAGMENT_LIST_HS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_hoc_sinh);
        Anhxa();
        ActionToolBar();

        //sự kiện click item menu Navigation
        mNavigationView.setNavigationItemSelectedListener(this);

        //set fragment mặt định là FragmentListHS
        ReplaceFragment(new FragmentListHS());
    }
    private void Anhxa(){
        mToolbar = findViewById(R.id.mToolbar);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        mNavigationView = findViewById(R.id.mNavigationView);
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

    //set Fragment
    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Conten_Fragment, fragment);
        fragmentTransaction.commit();
    }

    //implements sự kiện onclick menu Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_DS_HS:
                //kiểm tra nếu không phải đang đứng ở FragmentListHS thì chuyển về FragmentListHS
                if (FRAGMENT_LIST_HS != CURENT_FRAGMENT){
                    ReplaceFragment(new FragmentListHS());
                    CURENT_FRAGMENT = FRAGMENT_LIST_HS;
                }
                break;
            case R.id.nav_TKB:
                if (FRAGMENT_TKB != CURENT_FRAGMENT){
                    ReplaceFragment(new FragmentTKB());
                    CURENT_FRAGMENT = FRAGMENT_TKB;
                }
                break;
            case R.id.nav_HocTap:
                if (FRAGMENT_HOCTAP != CURENT_FRAGMENT){
                    ReplaceFragment(new FragmentHocTap());
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
}