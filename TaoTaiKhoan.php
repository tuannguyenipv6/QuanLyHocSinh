<?php
require "truyvanData.php";
//để add thêm vào bảng cần nhận 3 tham số
//post để có thể gửi từ app lên web
$mTaiKhoan  =     $_POST['pTaiKhoan'];//TaiKhoan là key nhận từ android
$mMatKhau   =     $_POST['pMatKhau'];
$mMSL       =     $_POST['pMSL'];
$mGVPH      =     $_POST['pGVPH'];
$mQMK       =     $_POST['pQMK'];

//kiểm tra tk và mã tk đã tồn tại hay chưa
$TruyVan = "SELECT * FROM user";//truy vấn vào bảng data
//chứa dữ liệu vào 1 biến
$data = mysqli_query($KetNoi, $TruyVan);
//tánh ra từng dòng để lấy dữ liệu ở mỗi dòng
$check = 0; //tạo 1 biến để check tài khoản đã tồn tại hay chưa.
while($dong = mysqli_fetch_assoc($data)){
    if($mTaiKhoan === $dong['mTaiKhoan']){
        echo "Tài khoản đã tồn tại!";
        $check = 1;
        break;
    }
}
if($check == 0){
    //kiểm tra mã gửi lên là gv hay ph
    $GVPH = false;
    if($mGVPH == 1){
        $GVPH = true;
    }else $GVPH = false;

    //lênh intert (chèn) dữ liệu vào bảng (id mình để null)
    $Chen = "INSERT INTO user VALUE('$mTaiKhoan', '$mMatKhau', '$mMSL', '$GVPH', '$mQMK')";
    //kiểm tra xem có insert vào đc k
    if(mysqli_query($KetNoi, $Chen)){//nếu thành công (trả về true)
        //$KetNoi đã được require ở trên từ file truyvanData.php
        echo "Thành Công";
    }else echo "Thất Bại";
}
?>