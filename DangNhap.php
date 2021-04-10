<?php
require "truyvanData.php";
$mTaiKhoan  =       $_POST['pTaiKhoan'];//TaiKhoan là key nhận từ android
$mMatKhau   =      $_POST['pMatKhau1'];

$TruyVan = "SELECT * FROM user";//truy vấn vào bảng data
//chứa dữ liệu vào 1 biến
$data = mysqli_query($KetNoi, $TruyVan);
$check = 0; //tạo 1 biến để check tài khoản và mã TK có đúng không
//tánh ra từng dòng để lấy dữ liệu ở mỗi dòng
while($dong = mysqli_fetch_assoc($data)){
    if($mTaiKhoan === $dong['mTaiKhoan'] && $mMatKhau == $dong['mMatKhau']){
        $check = 1;
        break;
    }
}
if($check == 1){
    echo "Thành Công";
}else echo "Sai thông tin!";
?>