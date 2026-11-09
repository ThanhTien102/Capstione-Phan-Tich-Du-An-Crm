<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
				<h4 class="page-title">Cập nhật tiến độ công việc</h4>
			</div>
		</div>
		<!-- /.row -->
		<!-- .row -->
		<div class="row">
			<div class="col-md-2 col-12"></div>
			<div class="col-md-8 col-xs-12">
				<div class="white-box">
					<c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<form class="form-horizontal form-material" action="profile-edit" method="post">
						<input type="hidden" name="id" value="${task.id}">
						<div class="form-group">
							<label class="col-md-12">Tên dự án</label>
							<div class="col-md-12">
								<input type="text" readonly value="${task.projectName}" class="form-control form-control-line">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Tên công việc</label>
							<div class="col-md-12">
								<input type="text" readonly value="${task.taskName}" class="form-control form-control-line">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Ngày bắt đầu</label>
							<div class="col-md-12">
								<input type="text" readonly value="<fmt:formatDate value="${task.startDate}" pattern="dd/MM/yyyy" />" class="form-control form-control-line">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Ngày kết thúc</label>
							<div class="col-md-12">
								<input type="text" readonly value="<fmt:formatDate value="${task.endDate}" pattern="dd/MM/yyyy" />" class="form-control form-control-line">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Trạng thái</label>
							<div class="col-md-12">
								<select name="statusId" class="form-control form-control-line">
									<c:forEach items="${statuses}" var="s">
										<option value="${s.id}" <c:if test="${task.statusId == s.id}">selected</c:if>>
											${s.nameStatus}
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<button type="submit" class="btn btn-success">Lưu lại</button>
								<a href="profile" class="btn btn-primary">Quay lại</a>
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

