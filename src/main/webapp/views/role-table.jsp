<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
				<h4 class="page-title">Danh sách quyền</h4>
			</div>
			<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
				<a href="role-add" class="btn btn-sm btn-success">Thêm mới</a>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- KẾT QUẢ CỦA THAO TÁC -->
		<c:if test="${param.delete == '1'}">
			<div class="alert alert-success">Xóa quyền thành công!</div>
		</c:if>
		<c:if test="${param.updated == '1'}">
			<div class="alert alert-success">Cập nhật quyền thành công!</div>
		</c:if>
		<c:if test="${param.err == '1'}">
			<div class="alert alert-danger">Xóa quyền thất bại (quyền đang được sử dụng)!</div>
		</c:if>
		<!-- /row -->
		<div class="row">
			<div class="col-sm-12">
				<div class="white-box">
					<div class="table-responsive">
						<table class="table" id="example">
							<thead>
								<tr>
									<th>STT</th>
									<th>Tên Quyền</th>
									<th>Mô Tả</th>
									<th>Hành Động</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="role">
									<tr>
										<td>${role.id}</td>
										<td>${role.nameRole}</td>
										<td>${role.description}</td>
										<td>
											<a href="role-edit?id=${role.id}" class="btn btn-sm btn-primary">Sửa</a>
											<form style="display: inline;" action="role-delete" method="post"
												onsubmit="return confirm('Bạn chắc chắn muốn xóa quyền này?')">
												<input type="hidden" name="id" value="${role.id}">
												<button type="submit" class="btn btn-sm btn-danger">Xóa</button>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->
