<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
				<h4 class="page-title">
					<c:choose>
						<c:when test="${isEdit}">Sửa công việc</c:when>
						<c:otherwise>Thêm mới công việc</c:otherwise>
					</c:choose>
				</h4>
			</div>
		</div>
		<!-- /.row -->
		<!-- .row -->
		<div class="row">
			<div class="col-md-2 col-12"></div>
			<div class="col-md-8 col-xs-12">
				<div class="white-box">
					<c:if test="${param.created == '1'}">
						<div class="alert alert-success">Thêm công việc thành công.</div>
					</c:if>
					<c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<c:choose>
						<c:when test="${isEdit}">
							<c:url var="actionForm" value="task-edit"></c:url>
						</c:when>
						<c:otherwise>
							<c:url var="actionForm" value="task-add"></c:url>
						</c:otherwise>
					</c:choose>
					<form class="form-horizontal form-material" action="${actionForm}" method="post">
						<c:if test="${isEdit}">
							<input name="id" type="hidden" value="${taskEdit.id}">
						</c:if>
						<div class="form-group">
							<label class="col-md-12">Dự án</label>
							<div class="col-md-12">
								<select name="projectId" class="form-control form-control-line" required>
									<option value="">-- Chọn dự án --</option>
									<c:forEach items="${projects}" var="p">
										<option value="${p.id}"
											<c:if test="${(isEdit ? taskEdit.projectId : param.projectId) == p.id}">selected</c:if>>
											${p.projectName}
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Tên công việc</label>
							<div class="col-md-12">
								<input type="text" placeholder="Tên công việc" name="taskName"
									class="form-control form-control-line"
									value="${isEdit ? taskEdit.taskName : param.taskName}" required>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Người thực hiện</label>
							<div class="col-md-12">
								<select name="userId" class="form-control form-control-line" required>
									<option value="">-- Chọn người thực hiện --</option>
									<c:forEach items="${users}" var="u">
										<option value="${u.id}"
											<c:if test="${(isEdit ? taskEdit.userId : param.userId) == u.id}">selected</c:if>>
											${u.fullname} (${u.email})
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Ngày bắt đầu</label>
							<div class="col-md-12">
								<input type="date" name="startDate"
									class="form-control form-control-line"
									value="${isEdit ? taskEdit.startDate : param.startDate}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Ngày kết thúc</label>
							<div class="col-md-12">
								<input type="date" name="endDate"
									class="form-control form-control-line"
									value="${isEdit ? taskEdit.endDate : param.endDate}">
							</div>
						</div>
						<c:if test="${isEdit}">
							<div class="form-group">
								<label class="col-md-12">Trạng thái</label>
								<div class="col-md-12">
									<select name="statusId" class="form-control form-control-line">
										<c:forEach items="${statuses}" var="s">
											<option value="${s.id}"
												<c:if test="${taskEdit.statusId == s.id}">selected</c:if>>
												${s.nameStatus}
											</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<div class="col-sm-12">
								<button type="submit" class="btn btn-success">
									<c:choose>
										<c:when test="${isEdit}">Cập nhật</c:when>
										<c:otherwise>Lưu lại</c:otherwise>
									</c:choose>
								</button>
								<a href="task" class="btn btn-primary">Quay lại</a>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-2 col-12"></div>
		</div>
		<!-- /.row -->
	</div>
</div>
<!-- /#page-wrapper -->

