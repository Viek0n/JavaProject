-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 29, 2025 at 08:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qlthitracnghiem`
--

-- --------------------------------------------------------

--
-- Table structure for table `baikiemtra`
--

CREATE TABLE `baikiemtra` (
  `MaCT` varchar(50) NOT NULL,
  `MaND` varchar(50) NOT NULL,
  `BaiLam` text NOT NULL,
  `Diem` double NOT NULL,
  `ThoiGianLam` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `baikiemtra`
--

INSERT INTO `baikiemtra` (`MaCT`, `MaND`, `BaiLam`, `Diem`, `ThoiGianLam`) VALUES
('CT1', 'Test', 'GG', 0, '00:45:00');

-- --------------------------------------------------------

--
-- Table structure for table `bangchiaquyen`
--

CREATE TABLE `bangchiaquyen` (
  `MaNQ` int(11) NOT NULL,
  `MaQuyen` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `bangchiaquyen`
--

INSERT INTO `bangchiaquyen` (`MaNQ`, `MaQuyen`) VALUES
(1, 'CH02'),
(1, 'CH03'),
(1, 'CH04'),
(1, 'KT01'),
(1, 'KT02'),
(1, 'KT03'),
(1, 'KT04'),
(1, 'ND01'),
(1, 'ND02'),
(1, 'ND03'),
(1, 'ND04'),
(1, 'NQ01'),
(1, 'NQ02'),
(1, 'NQ03'),
(1, 'NQ04'),
(1, 'CH01'),
(1, 'TG01');

-- --------------------------------------------------------

--
-- Table structure for table `bangphancong`
--

CREATE TABLE `bangphancong` (
  `MaND` varchar(50) NOT NULL,
  `MaMH` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `bangphancong`
--

INSERT INTO `bangphancong` (`MaND`, `MaMH`) VALUES
('Sys', '841048'),
('Sys', '841107');

-- --------------------------------------------------------

--
-- Table structure for table `cauhoi`
--

CREATE TABLE `cauhoi` (
  `MaCH` varchar(50) NOT NULL,
  `NoiDung` text NOT NULL,
  `DoKho` enum('DE','TRUNGBINH','KHO') NOT NULL,
  `MaChuong` varchar(50) NOT NULL,
  `MaND` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `cauhoi`
--

INSERT INTO `cauhoi` (`MaCH`, `NoiDung`, `DoKho`, `MaChuong`, `MaND`) VALUES
('CH1', 'Entry point của 1 chương trình java là gì?', 'DE', '841107C1', 'Sys'),
('CH2', 'test', 'KHO', '841107C2', 'Sys');

-- --------------------------------------------------------

--
-- Table structure for table `cauhoituychon`
--

CREATE TABLE `cauhoituychon` (
  `MaCT` varchar(50) NOT NULL,
  `MaCH` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `cauhoituychon`
--

INSERT INTO `cauhoituychon` (`MaCT`, `MaCH`) VALUES
('CT1', 'CH1'),
('CT1', 'CH2');

-- --------------------------------------------------------

--
-- Table structure for table `cautrucde`
--

CREATE TABLE `cautrucde` (
  `MaCT` varchar(50) NOT NULL,
  `TenCT` text NOT NULL,
  `MoTa` text NOT NULL,
  `ThoiGianBD` datetime NOT NULL,
  `ThoiGianKT` datetime NOT NULL,
  `ThoiGianLamBai` time NOT NULL,
  `MonHoc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `cautrucde`
--

INSERT INTO `cautrucde` (`MaCT`, `TenCT`, `MoTa`, `ThoiGianBD`, `ThoiGianKT`, `ThoiGianLamBai`, `MonHoc`) VALUES
('CT1', 'Testing', 'test', '2025-04-22 12:36:31', '2025-04-24 12:36:31', '01:00:00', '841048'),
('CT2', 'Test2', 'DAL testing', '3925-05-29 00:00:00', '3925-05-30 00:00:00', '01:00:00', '841107'),
('CT3', 'Test3', 'DAL testing 2', '2025-04-29 00:00:00', '2025-04-30 00:00:00', '01:00:00', '841107');

-- --------------------------------------------------------

--
-- Table structure for table `chitietde`
--

CREATE TABLE `chitietde` (
  `MaCT` varchar(50) NOT NULL,
  `MaChuong` varchar(50) NOT NULL,
  `DoKho` enum('DE','TRUNGBINH','KHO','') NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `chitietde`
--

INSERT INTO `chitietde` (`MaCT`, `MaChuong`, `DoKho`, `SoLuong`) VALUES
('CT1', '841107C1', 'DE', 10),
('CT1', '841107C2', 'DE', 5);

-- --------------------------------------------------------

--
-- Table structure for table `chuong`
--

CREATE TABLE `chuong` (
  `MaChuong` varchar(50) NOT NULL,
  `TenChuong` text NOT NULL,
  `MonHoc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `chuong`
--

INSERT INTO `chuong` (`MaChuong`, `TenChuong`, `MonHoc`) VALUES
('841107C1', 'Basic Java Programming', '841107'),
('841107C2', 'Lập trình hướng đối tượng', '841107'),
('841107C3', 'JDBC', '841107'),
('841107C4', 'SWING', '841107'),
('841107C5', 'SWING and 2 layer', '841107'),
('841107C6', 'GUI', '841107'),
('841107C7', 'Event', '841107'),
('841107C8', 'Component-Basic', '841107');

-- --------------------------------------------------------

--
-- Table structure for table `dapan`
--

CREATE TABLE `dapan` (
  `MaCH` varchar(50) NOT NULL,
  `NoiDung` text NOT NULL,
  `Dung` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `dapan`
--

INSERT INTO `dapan` (`MaCH`, `NoiDung`, `Dung`) VALUES
('CH1', 'Một phương thức bất kỳ có trong class đầu tiên', 0),
('CH1', 'Phương thức có tên là start()', 0),
('CH1', 'Phương thức main() với cú pháp chuẩn public static void main(String[] args)', 1),
('CH1', 'Bất kỳ phương thức nào có từ khóa static', 0),
('CH2', 'test A', 1),
('CH2', 'test B', 0),
('CH2', 'test C', 0),
('CH2', 'test D', 0);

-- --------------------------------------------------------

--
-- Table structure for table `monhoc`
--

CREATE TABLE `monhoc` (
  `MaMH` varchar(50) NOT NULL,
  `TenMH` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `monhoc`
--

INSERT INTO `monhoc` (`MaMH`, `TenMH`) VALUES
('841048', 'Phân tích thiết kế hệ thống thông tin'),
('841107', 'Lập trình Java');

-- --------------------------------------------------------

--
-- Table structure for table `nguoidung`
--

CREATE TABLE `nguoidung` (
  `MaND` varchar(50) NOT NULL,
  `Ten` text NOT NULL,
  `MatKhau` text NOT NULL,
  `TrangThai` enum('HOATDONG','KHOA') NOT NULL,
  `MaNQ` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `nguoidung`
--

INSERT INTO `nguoidung` (`MaND`, `Ten`, `MatKhau`, `TrangThai`, `MaNQ`) VALUES
('3123560069', 'Nguyễn Thanh Quang', '12345', 'HOATDONG', 2),
('3123560096', 'NgVi', '12345', 'HOATDONG', 1),
('3123560097', 'Hoàng Vũ', '12345', 'HOATDONG', 3),
('Sys', 'System', '69420', 'HOATDONG', 1),
('Test', 'Tester', 'abc', 'HOATDONG', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `MaNQ` int(11) NOT NULL,
  `TenNhom` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `nhomquyen`
--

INSERT INTO `nhomquyen` (`MaNQ`, `TenNhom`) VALUES
(1, 'Admin'),
(2, 'Giảng viên'),
(3, 'Sinh viên');

-- --------------------------------------------------------

--
-- Table structure for table `quyen`
--

CREATE TABLE `quyen` (
  `MaQuyen` varchar(50) NOT NULL,
  `TenQuyen` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `quyen`
--

INSERT INTO `quyen` (`MaQuyen`, `TenQuyen`) VALUES
('CH01', 'Xem câu hỏi'),
('CH02', 'Thêm câu hỏi'),
('CH03', 'Cập nhật câu hỏi'),
('CH04', 'Xóa câu hỏi'),
('KT01', 'Xem bài kiểm tra'),
('KT02', 'Thêm bài kiểm tra'),
('KT03', 'Cập nhật bài kiểm tra'),
('KT04', 'Xóa bài kiểm tra'),
('ND01', 'Xem người dùng'),
('ND02', 'Thêm người dùng'),
('ND03', 'Cập nhật người dùng'),
('ND04', 'Xóa người dùng'),
('NQ01', 'Xem nhóm quyền'),
('NQ02', 'Thêm nhóm quyền'),
('NQ03', 'Cập nhật nhóm quyền'),
('NQ04', 'Xóa nhóm quyền'),
('TG01', 'Tham gia bài kiểm tra');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `baikiemtra`
--
ALTER TABLE `baikiemtra`
  ADD KEY `fk_baikiemtra_cautrucde` (`MaCT`),
  ADD KEY `fk_baikiemtra_nguoidung` (`MaND`);

--
-- Indexes for table `bangchiaquyen`
--
ALTER TABLE `bangchiaquyen`
  ADD KEY `fk_bangchiaquyen_nhomquyen` (`MaNQ`),
  ADD KEY `fk_bangchiaquyen_quyen` (`MaQuyen`);

--
-- Indexes for table `bangphancong`
--
ALTER TABLE `bangphancong`
  ADD KEY `fk_bangphancong_nguoidung` (`MaND`),
  ADD KEY `fk_bangphancong_monhoc` (`MaMH`);

--
-- Indexes for table `cauhoi`
--
ALTER TABLE `cauhoi`
  ADD PRIMARY KEY (`MaCH`),
  ADD KEY `fk_cauhoi_chuong` (`MaChuong`),
  ADD KEY `fk_cauhoi_nguoidung` (`MaND`);

--
-- Indexes for table `cauhoituychon`
--
ALTER TABLE `cauhoituychon`
  ADD KEY `fk_cauhoituychon_cauhoi` (`MaCH`),
  ADD KEY `fk_cauhoituychon_cautrucde` (`MaCT`);

--
-- Indexes for table `cautrucde`
--
ALTER TABLE `cautrucde`
  ADD PRIMARY KEY (`MaCT`),
  ADD KEY `fk_cautrucde_monhoc` (`MonHoc`);

--
-- Indexes for table `chitietde`
--
ALTER TABLE `chitietde`
  ADD KEY `fk_chitietde_cautrucde` (`MaCT`),
  ADD KEY `fk_chitietde_chuong` (`MaChuong`);

--
-- Indexes for table `chuong`
--
ALTER TABLE `chuong`
  ADD PRIMARY KEY (`MaChuong`),
  ADD KEY `fk_chuong_monhoc` (`MonHoc`);

--
-- Indexes for table `dapan`
--
ALTER TABLE `dapan`
  ADD KEY `fk_dapan_cauhoi` (`MaCH`);

--
-- Indexes for table `monhoc`
--
ALTER TABLE `monhoc`
  ADD PRIMARY KEY (`MaMH`);

--
-- Indexes for table `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`MaND`),
  ADD KEY `fk_nguoidung_nhomquyen` (`MaNQ`);

--
-- Indexes for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`MaNQ`);

--
-- Indexes for table `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`MaQuyen`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `MaNQ` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `baikiemtra`
--
ALTER TABLE `baikiemtra`
  ADD CONSTRAINT `fk_baikiemtra_cautrucde` FOREIGN KEY (`MaCT`) REFERENCES `cautrucde` (`MaCT`),
  ADD CONSTRAINT `fk_baikiemtra_nguoidung` FOREIGN KEY (`MaND`) REFERENCES `nguoidung` (`MaND`);

--
-- Constraints for table `bangchiaquyen`
--
ALTER TABLE `bangchiaquyen`
  ADD CONSTRAINT `fk_bangchiaquyen_nhomquyen` FOREIGN KEY (`MaNQ`) REFERENCES `nhomquyen` (`MaNQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_bangchiaquyen_quyen` FOREIGN KEY (`MaQuyen`) REFERENCES `quyen` (`MaQuyen`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bangphancong`
--
ALTER TABLE `bangphancong`
  ADD CONSTRAINT `fk_bangphancong_monhoc` FOREIGN KEY (`MaMH`) REFERENCES `monhoc` (`MaMH`),
  ADD CONSTRAINT `fk_bangphancong_nguoidung` FOREIGN KEY (`MaND`) REFERENCES `nguoidung` (`MaND`);

--
-- Constraints for table `cauhoi`
--
ALTER TABLE `cauhoi`
  ADD CONSTRAINT `fk_cauhoi_chuong` FOREIGN KEY (`MaChuong`) REFERENCES `chuong` (`MaChuong`),
  ADD CONSTRAINT `fk_cauhoi_nguoidung` FOREIGN KEY (`MaND`) REFERENCES `nguoidung` (`MaND`);

--
-- Constraints for table `cauhoituychon`
--
ALTER TABLE `cauhoituychon`
  ADD CONSTRAINT `fk_cauhoituychon_cauhoi` FOREIGN KEY (`MaCH`) REFERENCES `cauhoi` (`MaCH`),
  ADD CONSTRAINT `fk_cauhoituychon_cautrucde` FOREIGN KEY (`MaCT`) REFERENCES `cautrucde` (`MaCT`);

--
-- Constraints for table `cautrucde`
--
ALTER TABLE `cautrucde`
  ADD CONSTRAINT `fk_cautrucde_monhoc` FOREIGN KEY (`MonHoc`) REFERENCES `monhoc` (`MaMH`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chitietde`
--
ALTER TABLE `chitietde`
  ADD CONSTRAINT `fk_chitietde_cautrucde` FOREIGN KEY (`MaCT`) REFERENCES `cautrucde` (`MaCT`),
  ADD CONSTRAINT `fk_chitietde_chuong` FOREIGN KEY (`MaChuong`) REFERENCES `chuong` (`MaChuong`);

--
-- Constraints for table `chuong`
--
ALTER TABLE `chuong`
  ADD CONSTRAINT `fk_chuong_monhoc` FOREIGN KEY (`MonHoc`) REFERENCES `monhoc` (`MaMH`);

--
-- Constraints for table `dapan`
--
ALTER TABLE `dapan`
  ADD CONSTRAINT `fk_dapan_cauhoi` FOREIGN KEY (`MaCH`) REFERENCES `cauhoi` (`MaCH`);

--
-- Constraints for table `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD CONSTRAINT `fk_nguoidung_nhomquyen` FOREIGN KEY (`MaNQ`) REFERENCES `nhomquyen` (`MaNQ`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
