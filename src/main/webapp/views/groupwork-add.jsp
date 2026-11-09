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
						<c:when test="${isEdit}">Sửa dự án</c:when>
						<c:otherwise>Thêm mới dự án</c:otherwise>
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
						<div class="alert alert-success">Thêm dự án thành công.</div>
					</c:if>
					<c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<c:choose>
						<c:when test="${isEdit}">
							<c:url var="actionForm" value="groupwork-edit"></c:url>
						</c:when>
						<c:otherwise>
							<c:url var="actionForm" value="groupwork-add"></c:url>
						</c:otherwise>
					</c:choose>
					<form class="form-horizontal form-material" action="${actionForm}" method="post">
						<c:if test="${isEdit}">
							<input name="id" type="hidden" value="${projectEdit.id}">
						</c:if>
						<div class="form-group">
							<label class="col-md-12">Tên dự án</label>
							<div class="col-md-12">
								<input type="text" placeholder="Tên dự án" name="projectName"
									class="form-control form-control-line"
									value="${isEdit ? projectEdit.projectName : param.projectName}" required>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Ngày bắt đầu</label>
							<div class="col-md-12">
								<input type="date" name="startDate"
									class="form-control form-control-line"
									value="${isEdit ? projectEdit.startDate : param.startDate}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Ngày kết thúc</label>
							<div class="col-md-12">
								<input type="date" name="endDate"
									class="form-control form-control-line"
									value="${isEdit ? projectEdit.endDate : param.endDate}">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<button type="submit" class="btn btn-success">
									<c:choose>
										<c:when test="${isEdit}">Cập nhật</c:when>
										<c:otherwise>Lưu lại</c:otherwise>
									</c:choose>
								</button>
								<a href="groupwork" class="btn btn-primary">Quay lại</a>
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

