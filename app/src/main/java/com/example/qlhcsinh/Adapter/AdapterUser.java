package com.example.qlhcsinh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlhcsinh.Object.User1;
import com.example.qlhcsinh.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolderUser> {
    private List<User1> mUsers;
    public void setmUsers(List<User1> mUsers) {
        this.mUsers = mUsers;
        notifyDataSetChanged();
    }
    public AdapterUser(List<User1> mUsers) {
        this.mUsers = mUsers;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolderUser(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser holder, int position) {
        User1 user = mUsers.get(position);
        if (user == null){
            return;
        }
        holder.txNoiO.setText(user.getmNoiO());
        holder.txTen.setText(user.getmTen());
        holder.civHinh.setImageResource(user.getmHinh());
    }
    @Override
    public int getItemCount() {
        if (mUsers != null){
            return mUsers.size();
        }
        return 0;
    }
    public class ViewHolderUser extends RecyclerView.ViewHolder {
        CircleImageView civHinh;
        TextView txTen, txNoiO;
        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            civHinh = (CircleImageView) itemView.findViewById(R.id.itHinh);
            txTen = (TextView) itemView.findViewById(R.id.itTen);
            txNoiO = (TextView) itemView.findViewById(R.id.itNoiO);
        }
    }
}
