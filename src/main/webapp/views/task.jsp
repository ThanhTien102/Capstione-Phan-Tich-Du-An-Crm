<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
				<h4 class="page-title">Danh sách công việc</h4>
			</div>
			<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
				<a href="task-add" class="btn btn-sm btn-success">Thêm mới</a>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- KẾT QUẢ CỦA THAO TÁC -->
		<c:if test="${param.delete == '1'}">
			<div class="alert alert-success">Xóa công việc thành công!</div>
		</c:if>
		<c:if test="${param.updated == '1'}">
			<div class="alert alert-success">Cập nhật công việc thành công!</div>
		</c:if>
		<c:if test="${param.err == '1'}">
			<div class="alert alert-danger">Thao tác thất bại!</div>
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
									<th>Tên Công Việc</th>
									<th>Dự Án</th>
									<th>Người Thực Hiện</th>
									<th>Ngày Bắt Đầu</th>
									<th>Ngày Kết Thúc</th>
									<th>Trạng Thái</th>
									<th>Hành Động</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${tasks}" var="t" varStatus="loop">
									<tr>
										<td>${loop.index + 1}</td>
										<td>${t.taskName}</td>
										<td>${t.projectName}</td>
										<td>${t.userName}</td>
										<td><fmt:formatDate value="${t.startDate}" pattern="dd/MM/yyyy" /></td>
										<td><fmt:formatDate value="${t.endDate}" pattern="dd/MM/yyyy" /></td>
										<td>
											<c:choose>
												<c:when test="${t.statusId == 1}">
													<span class="label label-danger">${t.statusName}</span>
												</c:when>
												<c:when test="${t.statusId == 2}">
													<span class="label label-info">${t.statusName}</span>
												</c:when>
												<c:when test="${t.statusId == 3}">
													<span class="label label-success">${t.statusName}</span>
												</c:when>
												<c:otherwise>
													<span class="label label-default">${t.statusName}</span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<a href="task-edit?id=${t.id}" class="btn btn-sm btn-primary">Sửa</a>
											<form style="display: inline;" action="task-delete" method="post"
												onsubmit="return confirm('Bạn chắc chắn muốn xóa công việc này?')">
												<input type="hidden" name="id" value="${t.id}">
												<button type="submit" class="btn btn-sm btn-danger">Xóa</button>
											</form>
											<a href="profile-edit?id=${t.id}" class="btn btn-sm btn-info">Xem</a>
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
</div>
<!-- /#page-wrapper -->

