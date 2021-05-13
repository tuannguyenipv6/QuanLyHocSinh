package com.example.qlhcsinh;

public class User {
    private String mTaiKhoan;
    private int mMatKhau;
    private int mMSL;
    private boolean mGV_PH;
    private int mQ_MK;


    public User(String mTaiKhoan, int mMatKhau, int mMSL, boolean mGV_PH, int mQ_MK) {
        this.mTaiKhoan = mTaiKhoan;
        this.mMatKhau = mMatKhau;
        this.mMSL = mMSL;
        this.mGV_PH = mGV_PH;
        this.mQ_MK = mQ_MK;
    }

    public User(String mTaiKhoan, int mMatKhau, int mQ_MK) {
        this.mTaiKhoan = mTaiKhoan;
        this.mMatKhau = mMatKhau;
        this.mQ_MK = mQ_MK;
    }

    public User(String mTaiKhoan, int mMatKhau) {
        this.mTaiKhoan = mTaiKhoan;
        this.mMatKhau = mMatKhau;
    }

    public String getmTaiKhoan() {
        return mTaiKhoan;
    }

    public void setmTaiKhoan(String mTaiKhoan) {
        this.mTaiKhoan = mTaiKhoan;
    }

    public int getmMatKhau() {
        return mMatKhau;
    }

    public void setmMatKhau(int mMatKhau) {
        this.mMatKhau = mMatKhau;
    }

    public int getmMSL() {
        return mMSL;
    }

    public void setmMSL(int mMSL) {
        this.mMSL = mMSL;
    }

    public boolean ismGV_PH() {
        return mGV_PH;
    }

    public void setmGV_PH(boolean mGV_PH) {
        this.mGV_PH = mGV_PH;
    }

    public int getmQ_MK() {
        return mQ_MK;
    }

    public void setmQ_MK(int mQ_MK) {
        this.mQ_MK = mQ_MK;
    }
}
