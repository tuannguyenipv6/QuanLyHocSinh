package com.example.qlhcsinh.Object;

public class User1 {
    private int mHinh;
    private String mTen;
    private String mNoiO;

    public int getmHinh() {
        return mHinh;
    }

    public void setmHinh(int mHinh) {
        this.mHinh = mHinh;
    }

    public String getmTen() {
        return mTen;
    }

    public void setmTen(String mTen) {
        this.mTen = mTen;
    }

    public String getmNoiO() {
        return mNoiO;
    }

    public void setmNoiO(String mNoiO) {
        this.mNoiO = mNoiO;
    }

    public User1(int mHinh, String mTen, String mNoiO) {
        this.mHinh = mHinh;
        this.mTen = mTen;
        this.mNoiO = mNoiO;
    }
}
