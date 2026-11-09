package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String contextPath = req.getContextPath();
		String servletPath = req.getServletPath();

		// Cho phép tài nguyên tĩnh (css, js, images, plugins, bootstrap)
		if (servletPath.startsWith("/assets/")) {
			chain.doFilter(req, resp);
			return;
		}

		// Cho phép các trang công khai / lỗi
		boolean isPublicPage = servletPath.equals("/login") || servletPath.equals("/403") || servletPath.equals("/404");

		// Lấy thông tin session
		HttpSession session = req.getSession(false);
		boolean isLoggedIn = session != null && session.getAttribute("currentUser") != null;

		if (isLoggedIn) {
			User currentUser = (User) session.getAttribute("currentUser");
			int roleId = currentUser.getRoleId();

			// Phân quyền theo use case trong file yêu cầu (PDF):
			// Role 3 (Member - Nhân viên): chỉ xem công việc cá nhân, thống kê cá nhân, cập nhật tiến độ
			if (roleId == 3) {
				if (servletPath.startsWith("/role-") || 
					servletPath.startsWith("/user-") || 
					servletPath.startsWith("/groupwork") || 
					servletPath.startsWith("/task")) {
					resp.sendRedirect(contextPath + "/403");
					return;
				}
			}
			// Role 2 (Leader - Quản lý dự án): xem danh sách user, quản lý dự án, quản lý công việc
			// Không được quản lý quyền (role) và không thêm/sửa/xóa user
			else if (roleId == 2) {
				if (servletPath.startsWith("/role-") || 
					servletPath.equals("/user-add") || 
					servletPath.equals("/user-edit") || 
					servletPath.equals("/user-delete")) {
					resp.sendRedirect(contextPath + "/403");
					return;
				}
			}

			// Đã đăng nhập và có quyền -> cho phép tiếp tục
			chain.doFilter(req, resp);
			return;
		}

		if (isPublicPage) {
			chain.doFilter(req, resp);
			return;
		}

		// Chưa đăng nhập -> chuyển về trang login
		resp.sendRedirect(contextPath + "/login");
	}
}
