<?php
//khai báo các tham số để truy vấn
$severname = "localhost";
$username = "root";
$password = "";
$database = "qlhs";
//kết nối tới Database
//truyền vào 4 tham số: sever (localhost), tên user (root), mật khẩu (rỗng), tên database
$KetNoi = mysqli_connect($severname, $username, $password, $database);
//khi kết nối data trả đúng dữ liệu có dấu ("SET NAMES 'utf8'")
mysqli_query($KetNoi, "SET NAMES 'utf8'");//mysqli_query truy vấn
?>