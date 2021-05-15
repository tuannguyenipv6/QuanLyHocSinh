package com.example.qlhcsinh.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlhcsinh.Adapter.AdapterUser;
import com.example.qlhcsinh.Object.User1;
import com.example.qlhcsinh.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentListHS extends Fragment {
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

    View view;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_hs, container, false);
        Anhxa();
        initToolbar();//gọi đến hàm hiện nút add
        initRecyclerView();
        initToolbarAnimations();
        onClickBtnAdd();
        return view;
    }
    private void Anhxa(){
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.FmAppBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.FmCollapsingToolbarLayout);
//        mToolbar = (Toolbar) view.findViewById(R.id.mToolbar);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.FmFloatingActionButton);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.FmRecyclerView);
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mUsers = getmUsers();
        adapter = new AdapterUser(mUsers);
        mRecyclerView.setAdapter(adapter);
    }

    //set List user
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
                getActivity().invalidateOptionsMenu();
            }
        });
    }

    private void initToolbar(){

    }

    private void onClickBtnAdd(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override//hàm bắt sự kiện Button add
            public void onClick(View v) {
                //bắt sự kiện nút add khi chưa add vào menu của resoucre
                Toast.makeText(getContext(), "Tuấn Nguyễn!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
