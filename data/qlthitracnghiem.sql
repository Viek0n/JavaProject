-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 19, 2025 at 10:07 PM
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
  `MaBaiKT` varchar(50) NOT NULL,
  `MaCT` varchar(50) NOT NULL,
  `MaND` varchar(50) NOT NULL,
  `Diem` double NOT NULL,
  `ThoiGianLam` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `baikiemtra`
--

INSERT INTO `baikiemtra` (`MaBaiKT`, `MaCT`, `MaND`, `Diem`, `ThoiGianLam`) VALUES
('KT1', 'CT1', 'Sys', 10, '00:09:21'),
('KT2', 'CT1', 'Sys', 5.289999961853027, '00:09:25'),
('KT3', 'CT4', 'Sys', 10, '00:09:49'),
('KT4', 'CT4', 'Sys', 5, '00:09:55');

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
(1, 'CH01'),
(1, 'CH02'),
(1, 'CH03'),
(1, 'CH04'),
(1, 'KT01'),
(1, 'KT02'),
(1, 'KT03'),
(1, 'KT04'),
(1, 'TG'),
(1, 'QT'),
(3, 'TG'),
(2, 'CH01'),
(2, 'CH02'),
(2, 'CH03'),
(2, 'CH04'),
(2, 'KT01'),
(2, 'KT02'),
(2, 'KT03'),
(2, 'KT04'),
(4, 'QT');

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
('Sys', '841107'),
('3123560096', '841107');

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
('CH1', 'Entry point của một chương trình Java là gì?', 'DE', '841107C1', 'Sys'),
('CH10', 'Từ khóa nào để tạo một đối tượng trong Java?', 'DE', '841107C1', 'Sys'),
('CH11', 'Sự khác nhau giữa == và .equals() trong Java là gì?', 'TRUNGBINH', '841107C1', 'Sys'),
('CH12', 'Constructor trong Java có gì đặc biệt?', 'TRUNGBINH', '841107C1', 'Sys'),
('CH13', 'Từ khóa static trong Java dùng để làm gì?', 'TRUNGBINH', '841107C1', 'Sys'),
('CH14', 'Hàm main() có thể có tham số khác String[] args không?', 'TRUNGBINH', '841107C1', 'Sys'),
('CH15', 'Khác biệt giữa ArrayList và LinkedList trong Java?', 'TRUNGBINH', '841107C1', 'Sys'),
('CH16', 'Làm sao để xử lý ngoại lệ một cách tùy chỉnh trong Java?', 'KHO', '841107C1', 'Sys'),
('CH17', 'Inner class trong Java là gì và có mấy loại?', 'KHO', '841107C1', 'Sys'),
('CH18', 'Lập trình hướng đối tượng (OOP) trong Java là gì?', 'DE', '841107C2', 'Sys'),
('CH19', 'Từ khóa nào dùng để khai báo một lớp trong Java?', 'DE', '841107C2', 'Sys'),
('CH2', 'Tập tin Java có phần mở rộng là gì?', 'DE', '841107C1', 'Sys'),
('CH20', 'Phương thức nào được gọi khi tạo một đối tượng trong Java?', 'DE', '841107C2', 'Sys'),
('CH21', 'Từ khóa nào dùng để tạo một đối tượng trong Java?', 'DE', '841107C2', 'Sys'),
('CH22', 'Thuộc tính của một lớp trong Java thường được khai báo với từ khóa nào?', 'DE', '841107C2', 'Sys'),
('CH23', 'Phương thức nào có cùng tên với lớp và không có giá trị trả về?', 'DE', '841107C2', 'Sys'),
('CH24', 'Từ khóa nào dùng để kế thừa một lớp trong Java?', 'DE', '841107C2', 'Sys'),
('CH25', 'Từ khóa nào dùng để khai báo biến toàn cục trong lớp?', 'DE', '841107C2', 'Sys'),
('CH26', 'Phương thức nào có thể được gọi mà không cần tạo đối tượng?', 'DE', '841107C2', 'Sys'),
('CH27', 'Truy cập private trong Java có nghĩa là gì?', 'DE', '841107C2', 'Sys'),
('CH28', 'Sự khác biệt giữa kế thừa và đa hình trong Java là gì?', 'TRUNGBINH', '841107C2', 'Sys'),
('CH29', 'Sự khác biệt giữa overloading và overriding trong Java là gì?', 'TRUNGBINH', '841107C2', 'Sys'),
('CH3', 'Để biên dịch một file Java, ta dùng lệnh gì?', 'DE', '841107C1', 'Sys'),
('CH30', 'Từ khóa super trong Java được dùng để làm gì?', 'TRUNGBINH', '841107C2', 'Sys'),
('CH31', 'Từ khóa this trong Java được dùng để làm gì?', 'TRUNGBINH', '841107C2', 'Sys'),
('CH32', 'Tại sao nên sử dụng getter và setter trong Java?', 'TRUNGBINH', '841107C2', 'Sys'),
('CH33', 'Làm sao để triển khai tính đóng gói (encapsulation) trong Java?', 'KHO', '841107C2', 'Sys'),
('CH34', 'Làm sao để ngăn chặn một lớp bị kế thừa trong Java?', 'KHO', '841107C2', 'Sys'),
('CH35', 'JDBC là gì trong Java?', 'DE', '841107C3', 'Sys'),
('CH36', 'Lớp nào trong JDBC dùng để kết nối với cơ sở dữ liệu?', 'DE', '841107C3', 'Sys'),
('CH37', 'Phương thức nào dùng để tải driver JDBC?', 'DE', '841107C3', 'Sys'),
('CH38', 'Đối tượng nào được dùng để thực thi câu lệnh SQL trong JDBC?', 'DE', '841107C3', 'Sys'),
('CH39', 'Phương thức nào dùng để đóng kết nối trong JDBC?', 'DE', '841107C3', 'Sys'),
('CH4', 'Lệnh nào dùng để chạy chương trình Java?', 'DE', '841107C1', 'Sys'),
('CH40', 'Câu lệnh SQL nào dùng để lấy dữ liệu từ bảng?', 'DE', '841107C3', 'Sys'),
('CH41', 'Phương thức nào dùng để lấy số cột trong ResultSet?', 'DE', '841107C3', 'Sys'),
('CH42', 'Gói nào chứa các lớp JDBC trong Java?', 'DE', '841107C3', 'Sys'),
('CH43', 'Đối tượng nào dùng để chứa kết quả truy vấn trong JDBC?', 'DE', '841107C3', 'Sys'),
('CH44', 'Phương thức nào dùng để di chuyển con trỏ trong ResultSet?', 'DE', '841107C3', 'Sys'),
('CH45', 'Sự khác biệt giữa Statement và PreparedStatement trong JDBC là gì?', 'TRUNGBINH', '841107C3', 'Sys'),
('CH46', 'Làm sao để xử lý ngoại lệ trong JDBC?', 'TRUNGBINH', '841107C3', 'Sys'),
('CH47', 'CallableStatement trong JDBC dùng để làm gì?', 'TRUNGBINH', '841107C3', 'Sys'),
('CH48', 'ResultSet Type nào cho phép cuộn ngược trong JDBC?', 'TRUNGBINH', '841107C3', 'Sys'),
('CH49', 'Tại sao nên sử dụng connection pooling trong JDBC?', 'TRUNGBINH', '841107C3', 'Sys'),
('CH5', 'Java có phải là ngôn ngữ lập trình hướng đối tượng không?', 'DE', '841107C1', 'Sys'),
('CH50', 'Làm sao để thực thi một thủ tục lưu trữ (stored procedure) trong JDBC?', 'KHO', '841107C3', 'Sys'),
('CH51', 'Làm sao để xử lý batch update trong JDBC?', 'KHO', '841107C3', 'Sys'),
('CH52', 'Swing trong Java là gì?', 'DE', '841107C4', 'Sys'),
('CH53', 'Lớp nào đại diện cho cửa sổ chính trong Swing?', 'DE', '841107C4', 'Sys'),
('CH54', 'Thành phần nào dùng để tạo nút nhấn trong Swing?', 'DE', '841107C4', 'Sys'),
('CH55', 'Phương thức nào dùng để hiển thị JFrame trong Swing?', 'DE', '841107C4', 'Sys'),
('CH56', 'Thành phần nào dùng để nhập văn bản một dòng trong Swing?', 'DE', '841107C4', 'Sys'),
('CH57', 'Gói nào chứa các thành phần của Swing?', 'DE', '841107C4', 'Sys'),
('CH58', 'Thành phần nào dùng để hiển thị danh sách chọn trong Swing?', 'DE', '841107C4', 'Sys'),
('CH59', 'JLabel trong Swing dùng để làm gì?', 'DE', '841107C4', 'Sys'),
('CH6', 'Từ khóa nào dùng để khai báo biến trong Java?', 'DE', '841107C1', 'Sys'),
('CH60', 'Thành phần nào dùng để tạo hộp kiểm trong Swing?', 'DE', '841107C4', 'Sys'),
('CH61', 'Phương thức nào dùng để đặt layout cho JFrame?', 'DE', '841107C4', 'Sys'),
('CH62', 'Sự khác biệt giữa JFrame và JPanel trong Swing là gì?', 'TRUNGBINH', '841107C4', 'Sys'),
('CH63', 'FlowLayout trong Swing sắp xếp các thành phần như thế nào?', 'TRUNGBINH', '841107C4', 'Sys'),
('CH64', 'BorderLayout chia vùng trong JFrame thành bao nhiêu khu vực?', 'TRUNGBINH', '841107C4', 'Sys'),
('CH65', 'Sự kiện nhấn nút trong Swing được xử lý qua interface nào?', 'TRUNGBINH', '841107C4', 'Sys'),
('CH66', 'CardLayout trong Swing phù hợp cho tình huống nào?', 'TRUNGBINH', '841107C4', 'Sys'),
('CH67', 'Làm sao để tạo giao diện nhiều tab trong Swing?', 'KHO', '841107C4', 'Sys'),
('CH68', 'Làm sao để xử lý sự kiện cho nhiều nút trong cùng một lớp ở Swing?', 'KHO', '841107C4', 'Sys'),
('CH7', 'Java có hỗ trợ overload hàm không?', 'DE', '841107C1', 'Sys'),
('CH8', 'Lệnh nào dùng để in ra màn hình trong Java?', 'DE', '841107C1', 'Sys'),
('CH9', 'Biến nào có phạm vi toàn cục trong lớp Java?', 'DE', '841107C1', 'Sys');

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
('CT1', 'CH10'),
('CT1', 'CH12'),
('CT1', 'CH16'),
('CT1', 'CH14'),
('CT1', 'CH30');

-- --------------------------------------------------------

--
-- Table structure for table `cautrucde`
--

CREATE TABLE `cautrucde` (
  `MaCT` varchar(50) NOT NULL,
  `TenCT` text NOT NULL,
  `MoTa` text NOT NULL,
  `XemDapAn` tinyint(1) NOT NULL,
  `ThoiGianBD` datetime NOT NULL,
  `ThoiGianKT` datetime NOT NULL,
  `ThoiGianLamBai` time NOT NULL,
  `MonHoc` varchar(50) NOT NULL,
  `MaND` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `cautrucde`
--

INSERT INTO `cautrucde` (`MaCT`, `TenCT`, `MoTa`, `XemDapAn`, `ThoiGianBD`, `ThoiGianKT`, `ThoiGianLamBai`, `MonHoc`, `MaND`) VALUES
('CT1', 'Test Exam Struct', 'test thử chức năng', 1, '2025-05-01 00:00:00', '2025-05-21 00:00:00', '00:10:00', '841107', 'Sys'),
('CT2', 'Test 2', 'DAL testing', 0, '2025-05-17 00:00:00', '2025-05-17 00:00:00', '00:10:00', '841107', 'Sys'),
('CT3', 'Test3', 'DAL testing 2', 0, '2025-04-29 00:00:00', '2025-04-30 00:00:00', '00:10:00', '841107', 'Sys'),
('CT4', 'Testing', 'test', 0, '2025-05-20 00:00:00', '2025-05-20 00:00:00', '00:10:00', '841107', 'Sys');

-- --------------------------------------------------------

--
-- Table structure for table `chitietbai`
--

CREATE TABLE `chitietbai` (
  `MaCH` varchar(50) NOT NULL,
  `MaBaiKT` varchar(50) NOT NULL,
  `LuaChon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `chitietbai`
--

INSERT INTO `chitietbai` (`MaCH`, `MaBaiKT`, `LuaChon`) VALUES
('CH64', 'KT1', 0),
('CH30', 'KT1', 0),
('CH28', 'KT1', 0),
('CH18', 'KT1', 0),
('CH14', 'KT1', 0),
('CH12', 'KT1', 0),
('CH62', 'KT1', 0),
('CH65', 'KT1', 0),
('CH24', 'KT1', 0),
('CH27', 'KT1', 0),
('CH16', 'KT1', 0),
('CH25', 'KT1', 0),
('CH63', 'KT1', 0),
('CH29', 'KT1', 0),
('CH66', 'KT1', 0),
('CH19', 'KT1', 0),
('CH10', 'KT1', 0),
('CH62', 'KT2', 0),
('CH32', 'KT2', 0),
('CH23', 'KT2', 0),
('CH14', 'KT2', 3),
('CH25', 'KT2', 0),
('CH16', 'KT2', 3),
('CH30', 'KT2', 2),
('CH20', 'KT2', 0),
('CH64', 'KT2', 2),
('CH10', 'KT2', 0),
('CH12', 'KT2', 0),
('CH66', 'KT2', 3),
('CH65', 'KT2', 0),
('CH29', 'KT2', 2),
('CH63', 'KT2', 0),
('CH26', 'KT2', 1),
('CH21', 'KT2', 1),
('CH51', 'KT3', 0),
('CH50', 'KT3', 0),
('CH50', 'KT4', 1),
('CH51', 'KT4', 0);

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
('CT2', '841107C4', 'DE', 6),
('CT2', '841107C1', 'DE', 6),
('CT2', '841107C2', 'DE', 6),
('CT2', '841107C3', 'DE', 6),
('CT1', '841107C2', 'DE', 5),
('CT1', '841107C4', 'TRUNGBINH', 5),
('CT1', '841107C2', 'TRUNGBINH', 2),
('CT4', '841107C3', 'KHO', 2);

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
('841108C1', 'INTRODUCTION', '841108'),
('841108C2', 'SOFTWARE PROCESSES', '841108');

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
('CH2', '.java', 1),
('CH2', '.class', 0),
('CH2', '.jar', 0),
('CH2', '.txt', 0),
('CH3', 'javac', 1),
('CH3', 'java', 0),
('CH3', 'compile', 0),
('CH3', 'run', 0),
('CH4', 'java', 1),
('CH4', 'javac', 0),
('CH4', 'run', 0),
('CH4', 'execute', 0),
('CH5', 'Có', 1),
('CH5', 'Không', 0),
('CH5', 'Chỉ một phần', 0),
('CH5', 'Chỉ hỗ trợ hướng hàm', 0),
('CH6', 'int', 1),
('CH6', 'var', 0),
('CH6', 'let', 0),
('CH6', 'def', 0),
('CH7', 'Có', 1),
('CH7', 'Không', 0),
('CH7', 'Chỉ với constructor', 0),
('CH7', 'Chỉ với static', 0),
('CH8', 'System.out.println()', 1),
('CH8', 'System.out.print()', 0),
('CH8', 'console.log()', 0),
('CH8', 'print()', 0),
('CH9', 'Biến static', 1),
('CH9', 'Biến local', 0),
('CH9', 'Biến instance', 0),
('CH9', 'Biến private', 0),
('CH10', 'new', 1),
('CH10', 'create', 0),
('CH10', 'make', 0),
('CH10', 'build', 0),
('CH11', '== so sánh địa chỉ, .equals() so sánh nội dung', 1),
('CH11', 'Cả hai đều so sánh nội dung', 0),
('CH11', '== so sánh nội dung, .equals() so sánh địa chỉ', 0),
('CH11', 'Cả hai giống nhau', 0),
('CH12', 'Không có giá trị trả về và trùng tên với lớp', 1),
('CH12', 'Luôn là static', 0),
('CH12', 'Có giá trị trả về là void', 0),
('CH12', 'Chỉ chạy một lần', 0),
('CH13', 'Chia sẻ giữa các đối tượng', 1),
('CH13', 'Tạo biến local', 0),
('CH13', 'Gán giá trị mặc định', 0),
('CH13', 'Chỉ dùng cho main', 0),
('CH14', 'Không, chỉ chấp nhận String[] args', 1),
('CH14', 'Có, chấp nhận mọi kiểu', 0),
('CH14', 'Chỉ chấp nhận int[]', 0),
('CH14', 'Không cần tham số', 0),
('CH15', 'LinkedList nhanh hơn khi thêm ở đầu', 1),
('CH15', 'ArrayList nhanh hơn khi thêm ở đầu', 0),
('CH15', 'Không có khác biệt', 0),
('CH15', 'ArrayList không hỗ trợ xóa', 0),
('CH16', 'Kế thừa lớp Exception', 1),
('CH16', 'Sử dụng try mà không catch', 0),
('CH16', 'Tạo một lớp Object', 0),
('CH16', 'Không thể tùy chỉnh', 0),
('CH17', 'Lớp bên trong lớp khác, gồm 2 loại: static và non-static', 1),
('CH17', 'Chỉ là biến nội bộ', 0),
('CH17', 'Chỉ dùng cho interface', 0),
('CH17', 'Không tồn tại', 0),
('CH18', 'Một phương pháp lập trình dựa trên các đối tượng và lớp', 1),
('CH18', 'Một phương pháp lập trình dựa trên hàm', 0),
('CH18', 'Một ngôn ngữ lập trình', 0),
('CH18', 'Một công cụ biên dịch', 0),
('CH19', 'class', 1),
('CH19', 'object', 0),
('CH19', 'struct', 0),
('CH19', 'type', 0),
('CH20', 'Constructor', 1),
('CH20', 'Destructor', 0),
('CH20', 'Main', 0),
('CH20', 'Static', 0),
('CH21', 'new', 1),
('CH21', 'create', 0),
('CH21', 'make', 0),
('CH21', 'build', 0),
('CH22', 'private', 1),
('CH22', 'public', 0),
('CH22', 'static', 0),
('CH22', 'void', 0),
('CH23', 'Constructor', 1),
('CH23', 'Destructor', 0),
('CH23', 'Getter', 0),
('CH23', 'Setter', 0),
('CH24', 'extends', 1),
('CH24', 'implements', 0),
('CH24', 'inherits', 0),
('CH24', 'super', 0),
('CH25', 'static', 1),
('CH25', 'local', 0),
('CH25', 'global', 0),
('CH25', 'instance', 0),
('CH26', 'Phương thức static', 1),
('CH26', 'Phương thức instance', 0),
('CH26', 'Phương thức private', 0),
('CH26', 'Phương thức public', 0),
('CH27', 'Chỉ có thể truy cập trong cùng một lớp', 1),
('CH27', 'Có thể truy cập từ bất kỳ đâu', 0),
('CH27', 'Chỉ có thể truy cập trong cùng một gói', 0),
('CH27', 'Chỉ có thể truy cập trong cùng một phương thức', 0),
('CH28', 'Kế thừa cho phép tái sử dụng mã, đa hình cho phép một hành vi có nhiều dạng', 1),
('CH28', 'Cả hai đều giống nhau', 0),
('CH28', 'Kế thừa cho phép đa hình, nhưng đa hình không cần kế thừa', 0),
('CH28', 'Kế thừa và đa hình không liên quan đến nhau', 0),
('CH29', 'Overloading là nhiều phương thức cùng tên khác tham số, overriding là ghi đè phương thức', 1),
('CH29', 'Overloading ghi đè phương thức, overriding tạo nhiều phương thức cùng tên', 0),
('CH29', 'Cả hai đều giống nhau', 0),
('CH29', 'Overloading chỉ dùng cho constructor', 0),
('CH30', 'Gọi constructor hoặc phương thức của lớp cha', 1),
('CH30', 'Gọi constructor của lớp hiện tại', 0),
('CH30', 'Tạo đối tượng mới', 0),
('CH30', 'Khai báo biến static', 0),
('CH31', 'Tham chiếu đến đối tượng hiện tại', 1),
('CH31', 'Tham chiếu đến đối tượng cha', 0),
('CH31', 'Tham chiếu đến biến static', 0),
('CH31', 'Tham chiếu đến phương thức main', 0),
('CH32', 'Để kiểm soát truy cập và bảo vệ dữ liệu', 1),
('CH32', 'Để tăng tốc độ chương trình', 0),
('CH32', 'Để thay thế constructor', 0),
('CH32', 'Để tạo biến static', 0),
('CH33', 'Sử dụng thuộc tính private và getter/setter', 1),
('CH33', 'Sử dụng thuộc tính public', 0),
('CH33', 'Sử dụng thuộc tính static', 0),
('CH33', 'Không cần đóng gói trong Java', 0),
('CH34', 'Sử dụng từ khóa final', 1),
('CH34', 'Sử dụng từ khóa private', 0),
('CH34', 'Sử dụng từ khóa static', 0),
('CH34', 'Không thể ngăn chặn', 0),
('CH35', 'API để kết nối và thao tác với cơ sở dữ liệu', 1),
('CH35', 'API để lập trình giao diện', 0),
('CH35', 'API để xử lý tập tin', 0),
('CH35', 'API để biên dịch mã', 0),
('CH36', 'DriverManager', 1),
('CH36', 'Statement', 0),
('CH36', 'ResultSet', 0),
('CH36', 'Connection', 0),
('CH37', 'Class.forName()', 1),
('CH37', 'Driver.load()', 0),
('CH37', 'Connection.getDriver()', 0),
('CH37', 'Statement.loadDriver()', 0),
('CH38', 'Statement', 1),
('CH38', 'ResultSet', 0),
('CH38', 'Connection', 0),
('CH38', 'DriverManager', 0),
('CH39', 'close()', 1),
('CH39', 'disconnect()', 0),
('CH39', 'terminate()', 0),
('CH39', 'shutdown()', 0),
('CH40', 'SELECT', 1),
('CH40', 'INSERT', 0),
('CH40', 'UPDATE', 0),
('CH40', 'DELETE', 0),
('CH41', 'getColumnCount()', 0),
('CH41', 'getMetaData().getColumnCount()', 1),
('CH41', 'getColumns()', 0),
('CH41', 'countColumns()', 0),
('CH42', 'java.sql', 1),
('CH42', 'javax.swing', 0),
('CH42', 'java.util', 0),
('CH42', 'java.io', 0),
('CH43', 'ResultSet', 1),
('CH43', 'Statement', 0),
('CH43', 'Connection', 0),
('CH43', 'Driver', 0),
('CH44', 'next()', 1),
('CH44', 'move()', 0),
('CH44', 'shift()', 0),
('CH44', 'jump()', 0),
('CH45', 'PreparedStatement chống SQL injection, Statement không', 1),
('CH45', 'Statement nhanh hơn PreparedStatement', 0),
('CH45', 'Cả hai giống nhau', 0),
('CH45', 'PreparedStatement chỉ dùng cho SELECT', 0),
('CH46', 'Sử dụng try-catch', 1),
('CH46', 'Sử dụng throw', 0),
('CH46', 'Sử dụng finally', 0),
('CH46', 'Không cần xử lý', 0),
('CH47', 'Thực thi thủ tục lưu trữ (stored procedure)', 1),
('CH47', 'Thực thi truy vấn SELECT', 0),
('CH47', 'Tạo kết nối', 0),
('CH47', 'Đóng kết nối', 0),
('CH48', 'TYPE_SCROLL_INSENSITIVE', 1),
('CH48', 'TYPE_FORWARD_ONLY', 0),
('CH48', 'TYPE_SCROLL_SENSITIVE', 0),
('CH48', 'TYPE_READ_ONLY', 0),
('CH49', 'Để tăng hiệu suất và tái sử dụng kết nối', 1),
('CH49', 'Để giảm số lượng driver', 0),
('CH49', 'Để đơn giản hóa mã nguồn', 0),
('CH49', 'Để tránh ngoại lệ', 0),
('CH50', 'Sử dụng CallableStatement với call syntax', 1),
('CH50', 'Sử dụng Statement với EXEC', 0),
('CH50', 'Sử dụng PreparedStatement với SELECT', 0),
('CH50', 'Không thể thực thi', 0),
('CH51', 'Sử dụng addBatch() và executeBatch()', 1),
('CH51', 'Sử dụng executeUpdate() riêng lẻ', 0),
('CH51', 'Sử dụng PreparedStatement mà không batch', 0),
('CH51', 'Sử dụng Statement mà không batch', 0),
('CH52', 'Thư viện để tạo giao diện người dùng đồ họa', 1),
('CH52', 'Thư viện để kết nối cơ sở dữ liệu', 0),
('CH52', 'Thư viện để xử lý tập tin', 0),
('CH52', 'Thư viện để lập trình mạng', 0),
('CH53', 'JFrame', 1),
('CH53', 'JPanel', 0),
('CH53', 'JLabel', 0),
('CH53', 'JButton', 0),
('CH54', 'JButton', 1),
('CH54', 'JLabel', 0),
('CH54', 'JTextField', 0),
('CH54', 'JCheckBox', 0),
('CH55', 'setVisible(true)', 1),
('CH55', 'show()', 0),
('CH55', 'display()', 0),
('CH55', 'setDisplay(true)', 0),
('CH56', 'JTextField', 1),
('CH56', 'JTextArea', 0),
('CH56', 'JLabel', 0),
('CH56', 'JComboBox', 0),
('CH57', 'javax.swing', 1),
('CH57', 'java.awt', 0),
('CH57', 'java.util', 0),
('CH57', 'java.io', 0),
('CH58', 'JList', 1),
('CH58', 'JTextField', 0),
('CH58', 'JButton', 0),
('CH58', 'JLabel', 0),
('CH59', 'Hiển thị văn bản hoặc hình ảnh', 1),
('CH59', 'Nhập văn bản', 0),
('CH59', 'Tạo nút nhấn', 0),
('CH59', 'Hiển thị danh sách', 0),
('CH60', 'JCheckBox', 1),
('CH60', 'JRadioButton', 0),
('CH60', 'JButton', 0),
('CH60', 'JTextField', 0),
('CH61', 'setLayout()', 1),
('CH61', 'changeLayout()', 0),
('CH61', 'applyLayout()', 0),
('CH61', 'setDesign()', 0),
('CH62', 'JFrame là container cấp cao, JPanel là container trung gian', 1),
('CH62', 'JPanel là container cấp cao, JFrame là container trung gian', 0),
('CH62', 'Cả hai đều giống nhau', 0),
('CH62', 'JFrame không hỗ trợ layout', 0),
('CH63', 'Theo dòng từ trái sang phải', 1),
('CH63', 'Theo lưới cố định', 0),
('CH63', 'Theo cột từ trên xuống dưới', 0),
('CH63', 'Xếp chồng lên nhau', 0),
('CH64', '5 khu vực', 1),
('CH64', '3 khu vực', 0),
('CH64', '4 khu vực', 0),
('CH64', '6 khu vực', 0),
('CH65', 'ActionListener', 1),
('CH65', 'MouseListener', 0),
('CH65', 'KeyListener', 0),
('CH65', 'WindowListener', 0),
('CH66', 'Chuyển đổi giữa các giao diện trên cùng một container', 1),
('CH66', 'Hiển thị các thành phần theo dòng', 0),
('CH66', 'Sắp xếp theo lưới', 0),
('CH66', 'Hiển thị danh sách chọn', 0),
('CH67', 'Dùng JTabbedPane', 1),
('CH67', 'Dùng JTable', 0),
('CH67', 'Dùng JList', 0),
('CH67', 'Dùng CardLayout', 0),
('CH68', 'Dùng ActionListener và kiểm tra nguồn sự kiện', 1),
('CH68', 'Dùng nhiều lớp khác nhau', 0),
('CH68', 'Không thể xử lý nhiều nút', 0),
('CH68', 'Dùng MouseListener cho tất cả', 0),
('CH1', 'Phương thức main() với cú pháp chuẩn public static void', 1),
('CH1', 'Phương thức begin()', 0),
('CH1', 'Phương thức execute()', 0),
('CH1', 'Phương thức launch()', 0);

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
('841107', 'Lập trình Java'),
('841108', 'Công nghệ phần mềm');

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
('3123560066', 'Trọng Phúc', '12345', 'HOATDONG', 3),
('3123560069', 'Thanh Quang', '12345', 'HOATDONG', 3),
('3123560096', 'NgVi', '12345', 'HOATDONG', 2),
('3123560097', 'Hoàng Vũ', '12345', 'HOATDONG', 3),
('Sus', 'Testing', 'test123', 'HOATDONG', 4),
('Sys', 'System', '69420', 'HOATDONG', 1);

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
(3, 'Sinh viên'),
(4, 'Quản trị');

-- --------------------------------------------------------

--
-- Table structure for table `phanhoi`
--

CREATE TABLE `phanhoi` (
  `MaBaiKT` varchar(50) NOT NULL,
  `MaND` varchar(50) NOT NULL,
  `NoiDung` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

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
('QT', 'Quản trị'),
('TG', 'Tham gia bài kiểm tra');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `baikiemtra`
--
ALTER TABLE `baikiemtra`
  ADD PRIMARY KEY (`MaBaiKT`),
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
  ADD KEY `fk_cautrucde_monhoc` (`MonHoc`),
  ADD KEY `fk_cautrucde_nguoidung` (`MaND`);

--
-- Indexes for table `chitietbai`
--
ALTER TABLE `chitietbai`
  ADD KEY `fk_chitietbai_baikiemtra` (`MaBaiKT`),
  ADD KEY `fk_chitietbai_cauhoi` (`MaCH`);

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
-- Indexes for table `phanhoi`
--
ALTER TABLE `phanhoi`
  ADD KEY `fk_phanhoi_baikiemtra` (`MaBaiKT`),
  ADD KEY `fk_phanhoi_nguoidung` (`MaND`);

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
  ADD CONSTRAINT `fk_cautrucde_monhoc` FOREIGN KEY (`MonHoc`) REFERENCES `monhoc` (`MaMH`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cautrucde_nguoidung` FOREIGN KEY (`MaND`) REFERENCES `nguoidung` (`MaND`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chitietbai`
--
ALTER TABLE `chitietbai`
  ADD CONSTRAINT `fk_chitietbai_baikiemtra` FOREIGN KEY (`MaBaiKT`) REFERENCES `baikiemtra` (`MaBaiKT`),
  ADD CONSTRAINT `fk_chitietbai_cauhoi` FOREIGN KEY (`MaCH`) REFERENCES `cauhoi` (`MaCH`);

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

--
-- Constraints for table `phanhoi`
--
ALTER TABLE `phanhoi`
  ADD CONSTRAINT `fk_phanhoi_baikiemtra` FOREIGN KEY (`MaBaiKT`) REFERENCES `baikiemtra` (`MaBaiKT`),
  ADD CONSTRAINT `fk_phanhoi_nguoidung` FOREIGN KEY (`MaND`) REFERENCES `nguoidung` (`MaND`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
