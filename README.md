# Dự Án Quản Lý Công Việc CRM (Java Servlet - JSP)

Dự án Capstone Quản lý Công việc CRM được xây dựng trên nền tảng Java Servlet, JSP, JSTL, SiteMesh, kết nối MySQL và băm mật khẩu bảo mật BCrypt.

## 🚀 Các Tính Năng Chính Theo Phân Quyền (RBAC)

1. **Quản trị viên (Admin - Role 1)**:
   - Thống kê toàn hệ thống: Tỷ lệ phần trăm công việc chưa bắt đầu, đang thực hiện, đã hoàn thành.
   - Quản lý Thành viên (User): Xem danh sách, thêm mới, sửa, xóa, xem chi tiết tiến độ công việc của từng thành viên.
   - Quản lý Quyền (Role): Xem danh sách, thêm, sửa, xóa quyền.
   - Quản lý Dự án: Xem danh sách dự án, tạo dự án mới, xóa dự án, xem tiến độ chi tiết theo từng thành viên trong dự án.
   - Quản lý Công việc: Phân công công việc, chỉ định người thực hiện, cập nhật trạng thái.

2. **Quản lý (Leader - Role 2)**:
   - Quản lý dự án được phân công và thành viên trong dự án.
   - Phân công và theo dõi tiến độ công việc của thành viên.
   - Xem thống kê chi tiết tiến độ dự án.

3. **Nhân viên (Member - Role 3)**:
   - Xem trang cá nhân, thông tin tài khoản.
   - Theo dõi danh sách công việc được phân công.
   - Cập nhật trạng thái tiến độ công việc (Chưa bắt đầu, Đang thực hiện, Đã hoàn thành).
   - Thống kê cá nhân trực quan.

## 🛠 Công Nghệ Sử Dụng
- **Ngôn ngữ**: Java
- **Nền tảng Web**: Java Servlet, JSP, JSTL
- **Layout Decorator**: SiteMesh 2.4.2
- **Giao diện**: Pixel Admin Template, Bootstrap, HTML5, CSS3, JavaScript
- **Cơ sở dữ liệu**: MySQL 8.0, JDBC
- **Bảo mật**: BCrypt Password Hashing, Authentication & Authorization Filter

## 📦 Cài Đặt và Khởi Chạy
1. Import cơ sở dữ liệu từ file `CRM_DB.sql` vào MySQL.
2. Cấu hình thông tin kết nối trong `src/main/java/connection/DBConnection.java`.
3. Triển khai (Deploy) dự án lên Apache Tomcat 9.
4. Truy cập hệ thống tại: `http://localhost:8080/java14crmproject/`.

---
**Tác giả**: Ngô Thanh Tiến (@ThanhTien102)
