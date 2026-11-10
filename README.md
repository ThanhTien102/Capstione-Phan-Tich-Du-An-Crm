# CAPSTONE: Hệ Thống Quản Lý Dự Án & Công Việc CRM (Java Servlet - JSP)

Dự án **Quản lý Dự án & Công việc CRM** được xây dựng trên nền tảng **Java Servlet, JSP, JSTL, SiteMesh** kết nối cơ sở dữ liệu **MySQL** thông qua JDBC, thiết kế theo mô hình phân lớp rõ ràng và phân quyền chặt chẽ cho 3 vai trò (**Admin – Leader – Member**).

- **Video Demo trên YouTube**: https://youtu.be/0r_TGtUksJU
- **Repository GitHub**: https://github.com/ThanhTien102/Capstione-Phan-Tich-Du-An-Crm

---

## 🚀 Các Tính Năng Chính Theo Phân Quyền (RBAC)

Hệ thống xác thực phiên đăng nhập (`HttpSession`) và kiểm soát phân quyền tập trung tại [`AuthenticationFilter`](src/main/java/filters/AuthenticationFilter.java):

### 1. Quản trị viên (`ROLE_ADMIN` - `role_id = 1`)
- **Dashboard (`/`)**: Thống kê tổng số lượng và tỷ lệ phần trăm công việc toàn hệ thống theo 3 trạng thái (*Chưa bắt đầu*, *Đang thực hiện*, *Đã hoàn thành*).
- **Quản lý Thành viên (`/user-table`, `/user-add`, `/user-edit`, `/user-delete`, `/user-details`)**:
  - Tìm kiếm theo họ tên/email, phân trang danh sách.
  - Thêm mới (mật khẩu tự động mã hóa bằng BCrypt), chỉnh sửa, xóa thành viên.
  - Xem chi tiết hồ sơ, biểu đồ tiến độ cá nhân và công việc phân theo trạng thái của từng thành viên.
- **Quản lý Quyền (`/role-table`, `/role-add`, `/role-edit`, `/role-delete`)**: Xem danh sách, thêm, sửa, xóa vai trò trong hệ thống.
- **Quản lý Dự án (`/groupwork`, `/groupwork-add`, `/groupwork-edit`, `/groupwork-delete`, `/groupwork-details`)**: Tạo mới, chỉnh sửa, xóa dự án và xem báo cáo chi tiết tiến độ dự án gom nhóm theo từng thành viên tham gia.
- **Quản lý Công việc (`/task`, `/task-add`, `/task-edit`, `/task-delete`)**: Tạo mới, phân công công việc cho thành viên vào từng dự án, cập nhật thông tin và trạng thái.

### 2. Quản lý dự án (`ROLE_LEADER` - `role_id = 2`)
- Quản lý **Dự án** (`/groupwork*`) và xem chi tiết tiến độ dự án.
- Quản lý, phân công và theo dõi **Công việc** (`/task*`).
- Xem danh sách & chi tiết **Thành viên** (`/user-table`, `/user-details`).
- Menu quản lý Quyền tự động ẩn trên Sidebar; nếu truy cập trực tiếp `/role-*` hoặc `/user-add`, `/user-edit`, `/user-delete` sẽ bị chặn và chuyển hướng sang trang `/403`.

### 3. Nhân viên (`ROLE_MEMBER` - `role_id = 3`)
- **Dashboard (`/`)**: Hiển thị thống kê công việc của **chính cá nhân** đang đăng nhập.
- **Công việc của tôi (`/profile`, `/profile-edit`)**: Xem thông tin cá nhân, thống kê tỷ lệ % hoàn thành công việc cá nhân, theo dõi danh sách việc được giao và cập nhật trạng thái tiến độ (*Chưa bắt đầu*, *Đang thực hiện*, *Đã hoàn thành*).
- Nếu truy cập trái phép vào các đường dẫn quản trị (`/role-*`, `/user-*`, `/groupwork*`, `/task*`), hệ thống tự động chặn và chuyển hướng về trang `/403`.

---

## 🏗 Kiến Trúc & Cấu Trúc Dự Án

Source code trong `src/main/java` được tổ chức theo mô hình phân lớp:

```text
src/main/
├── java/
│   ├── connection/     # DBConnection - Cấu hình kết nối MySQL JDBC (UTF-8)
│   ├── controllers/    # AuthController, HomeServlet, UserController, RoleServlet,
│   │                   # ProjectController, TaskController, ProfileController, ErrorController
│   ├── services/       # AuthService, UserService, RoleService, ProjectService, TaskService
│   ├── repositories/   # UserRepository, ProjectRepository, TaskRepository
│   ├── dao/            # RoleDAO, StatusDAO
│   ├── models/         # User, Role, Project, Status, Task
│   ├── dto/            # CreateUserDTO, UserListDTO, TaskListDTO, TaskStatsDTO,
│   │                   # UserTaskDetailDTO, MemberProjectTasksDTO
│   └── filters/        # AuthenticationFilter - Xác thực, phân quyền & ép UTF-8
└── webapp/
    ├── assets/         # Giao diện Pixel Admin (Bootstrap, CSS, JS, Plugins, Images)
    ├── decorators/     # Layout khung chính SiteMesh (layout.jsp)
    ├── layout/         # header.jsp, sidebar.jsp, footer.jsp
    ├── views/          # Các trang JSP (home, login, user-*, role-*, groupwork*, task*, profile*, 403, 404, blank)
    └── WEB-INF/
        ├── lib/        # jstl-1.2.jar, sitemesh-2.4.2.jar, mysql-connector-java-8.0.13.jar, jbcrypt-0.4.jar
        ├── decorators.xml
        └── web.xml
```

---

## 🗄 Thiết Kế Cơ Sở Dữ Liệu (`CRM_DB`)

Script khởi tạo và dữ liệu mẫu nằm tại [`CRM_DB.sql`](CRM_DB.sql) (mã hóa `utf8mb4_unicode_ci`), gồm 5 bảng chính:

| Bảng | Mô tả | Khóa ngoại |
| :--- | :--- | :--- |
| `Role` | Lưu danh sách quyền (`ROLE_ADMIN`, `ROLE_LEADER`, `ROLE_MEMBER`) | — |
| `User` | Lưu thông tin người dùng và mật khẩu đã băm BCrypt | `role_id` → `Role(id)` |
| `Project` | Lưu thông tin các dự án và ngày bắt đầu / kết thúc | — |
| `Status` | Lưu trạng thái công việc (`Chưa bắt đầu`, `Đang thực hiện`, `Đã hoàn thành`) | — |
| `Task` | Lưu danh sách công việc gắn với dự án, người thực hiện và trạng thái | `status_id` → `Status(id)`, `project_id` → `Project(id)`, `user_id` → `User(id)` |

---

## 🛠 Công Nghệ Sử Dụng
- **Ngôn ngữ**: Java (JDK 21 / Java 8+)
- **Nền tảng Web**: Java Servlet, JSP, JSTL 1.2
- **Layout Decorator**: SiteMesh 2.4.2
- **Giao diện**: Pixel Admin Template, Bootstrap 3/4, HTML5, CSS3, JavaScript
- **Cơ sở dữ liệu**: MySQL 8.0 (`mysql-connector-java-8.0.13`)
- **Bảo mật**: BCrypt Password Hashing (`jbcrypt-0.4`), Session Authentication & Filter Authorization
- **Web Server**: Apache Tomcat 9.0

---

## 📦 Cài Đặt và Khởi Chạy Cục Bộ (Local)
1. **Khởi tạo Database**: Import file `CRM_DB.sql` vào MySQL (hỗ trợ đầy đủ tiếng Việt `utf8mb4`).
2. **Cấu hình kết nối**: Kiểm tra URL, cổng, tài khoản MySQL trong `src/main/java/connection/DBConnection.java` (mặc định `jdbc:mysql://localhost:3307/CRM_DB`).
3. **Triển khai Server**: Deploy project lên **Apache Tomcat 9.0** (ví dụ qua Eclipse Dynamic Web Project hoặc IntelliJ IDEA + Smart Tomcat với context path `/java14crmproject`).
4. **Truy cập ứng dụng**: Mở trình duyệt vào `http://localhost:8080/java14crmproject/`.

---

## 🔑 Tài Khoản Kiểm Thử Mẫu
*(Tất cả tài khoản mẫu trong `CRM_DB.sql` sử dụng chung mật khẩu **`123456`** đã mã hóa BCrypt)*

| Vai trò | Email đăng nhập | Mật khẩu |
| :--- | :--- | :--- |
| **Admin** (`ROLE_ADMIN`) | `admin@gmail.com` | `123456` |
| **Leader** (`ROLE_LEADER`) | `leader@gmail.com` | `123456` |
| **Member** (`ROLE_MEMBER`) | `member@gmail.com` | `123456` |
