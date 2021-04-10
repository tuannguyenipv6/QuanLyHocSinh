<?php
require "truyvanData.php";
$mTaiKhoan  =       $_POST['pTaiKhoan'];//TaiKhoan là key nhận từ android
$mQMK       =       $_POST['pQMK'];
$mMatKhau1   =      $_POST['pMatKhau1'];

//kiểm tra tk và mã tk đã có đúng hay không
$TruyVan = "SELECT * FROM user";//truy vấn vào bảng data
//chứa dữ liệu vào 1 biến
$data = mysqli_query($KetNoi, $TruyVan);
$check = 0; //tạo 1 biến để check tài khoản và mã TK có đúng không
//tánh ra từng dòng để lấy dữ liệu ở mỗi dòng
while($dong = mysqli_fetch_assoc($data)){
    if($mTaiKhoan === $dong['mTaiKhoan'] && $mQMK == $dong['mQMK']){
        $check = 1;
        break;
    }
}

if($check == 1){
    //Lệnh Update bảng trên sever
    $Chen = "UPDATE user SET mMatKhau = '$mMatKhau1' WHERE mTaiKhoan = '$mTaiKhoan'";
    //tương tự add ta kiểm tra thành công hay không
    if(mysqli_query($KetNoi, $Chen)){
        echo "Thành Công";
    }else echo "Thất Bại";
}else echo "Tài khoản hoặc mã tài khoản không khớp";
?>