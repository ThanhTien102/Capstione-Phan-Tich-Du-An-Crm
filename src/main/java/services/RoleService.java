package services;

import java.sql.SQLException;
import java.util.List;

import dao.RoleDAO;
import models.Role;

public class RoleService {
	private final RoleDAO roleDAO;

	public RoleService() {
		this.roleDAO = new RoleDAO();
	}

	public List<Role> getAll() {
		return roleDAO.getAll();
	}

	public Role getById(int id) {
		return roleDAO.getById(id);
	}

	public String create(String nameRole, String description) {
		if (nameRole == null || nameRole.trim().isEmpty()) {
			return "Tên quyền không được để trống";
		}
		Role role = new Role(nameRole.trim(), description != null ? description.trim() : "");
		boolean success = roleDAO.create(role);
		return success ? null : "Không thể tạo quyền mới";
	}

	public String update(Role role) {
		if (role.getNameRole() == null || role.getNameRole().trim().isEmpty()) {
			return "Tên quyền không được để trống";
		}
		boolean success = roleDAO.update(role);
		return success ? null : "Không thể cập nhật quyền";
	}

	public String deleteById(int id) {
		try {
			boolean success = roleDAO.deleteById(id);
			return success ? null : "Không tìm thấy quyền";
		} catch (SQLException e) {
			return "Quyền đang được gán cho người dùng, không thể xóa";
		}
	}
}

