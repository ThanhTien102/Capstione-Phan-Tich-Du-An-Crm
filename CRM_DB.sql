-- CRM Database Setup Script
CREATE DATABASE IF NOT EXISTS CRM_DB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE CRM_DB;

-- 1. Bảng Role (Quyền)
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Status;
DROP TABLE IF EXISTS Role;

CREATE TABLE Role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name_role VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

-- 2. Bảng User (Người dùng)
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    country VARCHAR(50),
    role_id INT NOT NULL,
    CONSTRAINT FK_User_Role FOREIGN KEY (role_id) REFERENCES Role(id) ON DELETE RESTRICT
);

-- 3. Bảng Project (Dự án / Nhóm việc)
CREATE TABLE Project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name_project VARCHAR(150) NOT NULL,
    start_date DATE,
    end_date DATE
);

-- 4. Bảng Status (Trạng thái công việc)
CREATE TABLE Status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name_status VARCHAR(50) NOT NULL
);

-- 5. Bảng Task (Công việc)
CREATE TABLE Task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    task_name VARCHAR(150) NOT NULL,
    start_date DATE,
    end_date DATE,
    status_id INT NOT NULL DEFAULT 1,
    project_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT FK_Task_Status FOREIGN KEY (status_id) REFERENCES Status(id),
    CONSTRAINT FK_Task_Project FOREIGN KEY (project_id) REFERENCES Project(id) ON DELETE CASCADE,
    CONSTRAINT FK_Task_User FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

-- ====================================================
-- SEED DATA
-- Mật khẩu mặc định cho các tài khoản là: 123456
-- (Mã hóa bằng BCrypt với cost factor 12)
-- ====================================================

-- Thêm quyền
INSERT INTO Role (id, name_role, description) VALUES
(1, 'ROLE_ADMIN', 'Quản trị hệ thống - Toàn quyền'),
(2, 'ROLE_LEADER', 'Quản lý dự án'),
(3, 'ROLE_MEMBER', 'Nhân viên thực hiện công việc');

-- Thêm trạng thái công việc
INSERT INTO Status (id, name_status) VALUES
(1, 'Chưa bắt đầu'),
(2, 'Đang thực hiện'),
(3, 'Đã hoàn thành');

-- Thêm người dùng (Mật khẩu: 123456)
INSERT INTO User (id, email, password, fullname, phone, country, role_id) VALUES
(1, 'admin@gmail.com', '$2a$12$4DOSlzmRURa0TcYdU18q9ei/8/dVddXttyCoANLQ6z/tQseOTtlVG', 'Quản Trị Viên (Admin)', '0901234567', 'Việt Nam', 1),
(2, 'leader@gmail.com', '$2a$12$4DOSlzmRURa0TcYdU18q9ei/8/dVddXttyCoANLQ6z/tQseOTtlVG', 'Trần Văn Leader', '0912345678', 'Việt Nam', 2),
(3, 'member@gmail.com', '$2a$12$4DOSlzmRURa0TcYdU18q9ei/8/dVddXttyCoANLQ6z/tQseOTtlVG', 'Nguyễn Văn Member', '0923456789', 'Việt Nam', 3),
(4, 'teo@gmail.com', '$2a$12$4DOSlzmRURa0TcYdU18q9ei/8/dVddXttyCoANLQ6z/tQseOTtlVG', 'Nguyễn Văn Tèo', '0934567890', 'Việt Nam', 3),
(5, 'lan@gmail.com', '$2a$12$4DOSlzmRURa0TcYdU18q9ei/8/dVddXttyCoANLQ6z/tQseOTtlVG', 'Trần Thị Lan', '0945678901', 'Việt Nam', 3),
(6, 'pavan@gmail.com', '$2a$12$4DOSlzmRURa0TcYdU18q9ei/8/dVddXttyCoANLQ6z/tQseOTtlVG', 'Pavan Kumar', '0956789012', 'India', 3);

-- Thêm dự án mẫu
INSERT INTO Project (id, name_project, start_date, end_date) VALUES
(1, 'Dự án CRM', '2026-05-01', '2026-12-31'),
(2, 'Dự án Elearning', '2026-06-15', '2026-11-30'),
(3, 'Dự án Rạp chiếu phim', '2026-07-01', '2026-10-31');

-- Thêm công việc mẫu
INSERT INTO Task (id, task_name, start_date, end_date, status_id, project_id, user_id) VALUES
(1, 'Phân tích hệ thống CRM', '2026-05-05', '2026-05-20', 3, 1, 4),
(2, 'Thiết kế Database CRM', '2026-05-21', '2026-06-05', 2, 1, 5),
(3, 'Xây dựng giao diện CRM', '2026-06-06', '2026-06-25', 1, 1, 6),
(4, 'Phân tích yêu cầu Elearning', '2026-06-16', '2026-07-01', 3, 2, 4),
(5, 'Xây dựng API Elearning', '2026-07-02', '2026-08-15', 2, 2, 3),
(6, 'Thiết kế UI Rạp chiếu phim', '2026-07-05', '2026-08-01', 1, 3, 5);

