<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<h4 class="page-title">Chi tiết dự án: ${project.projectName}</h4>
			</div>
			<div class="col-lg-6 col-sm-6 col-md-6 col-xs-12 text-right">
				<a href="groupwork" class="btn btn-sm btn-primary">Quay lại danh sách</a>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- BEGIN THỐNG KÊ -->
		<div class="row">
			<!--col -->
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="white-box">
					<div class="col-in row">
						<div class="col-md-6 col-sm-6 col-xs-6">
							<i data-icon="E" class="linea-icon linea-basic"></i>
							<h5 class="text-muted vb">CHƯA BẮT ĐẦU</h5>
						</div>
						<div class="col-md-6 col-sm-6 col-xs-6">
							<h3 class="counter text-right m-t-15 text-danger">${stats.notStartedPercent}%</h3>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="progress">
								<div class="progress-bar progress-bar-danger" role="progressbar"
									aria-valuenow="${stats.notStartedPercent}" aria-valuemin="0" aria-valuemax="100"
									style="width: ${stats.notStartedPercent}%">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col -->
			<!--col -->
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="white-box">
					<div class="col-in row">
						<div class="col-md-6 col-sm-6 col-xs-6">
							<i class="linea-icon linea-basic" data-icon="&#xe01b;"></i>
							<h5 class="text-muted vb">ĐANG THỰC HIỆN</h5>
						</div>
						<div class="col-md-6 col-sm-6 col-xs-6">
							<h3 class="counter text-right m-t-15 text-megna">${stats.inProgressPercent}%</h3>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="progress">
								<div class="progress-bar progress-bar-megna" role="progressbar"
									aria-valuenow="${stats.inProgressPercent}" aria-valuemin="0" aria-valuemax="100"
									style="width: ${stats.inProgressPercent}%">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col -->
			<!--col -->
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="white-box">
					<div class="col-in row">
						<div class="col-md-6 col-sm-6 col-xs-6">
							<i class="linea-icon linea-basic" data-icon="&#xe00b;"></i>
							<h5 class="text-muted vb">HOÀN THÀNH</h5>
						</div>
						<div class="col-md-6 col-sm-6 col-xs-6">
							<h3 class="counter text-right m-t-15 text-primary">${stats.completedPercent}%</h3>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="progress">
								<div class="progress-bar progress-bar-primary" role="progressbar"
									aria-valuenow="${stats.completedPercent}" aria-valuemin="0" aria-valuemax="100"
									style="width: ${stats.completedPercent}%">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col -->
		</div>
		<!-- END THỐNG KÊ -->

		<!-- BEGIN DANH SÁCH CÔNG VIỆC THEO THÀNH VIÊN -->
		<c:if test="${empty membersWithTasks}">
			<div class="white-box text-center">
				<p class="text-muted">Chưa có thành viên hoặc công việc nào trong dự án này.</p>
			</div>
		</c:if>

		<c:forEach items="${membersWithTasks}" var="m">
			<div class="row m-b-20">
				<div class="col-xs-12">
					<a href="user-details?id=${m.user.id}" class="group-title">
						<img width="36" src="${pageContext.request.contextPath}/assets/plugins/images/users/pawandeep.jpg" class="img-circle" />
						<span style="font-size: 16px; font-weight: bold; margin-left: 10px;">${m.user.fullname} (${m.user.email})</span>
					</a>
				</div>
				<div class="col-md-4">
					<div class="white-box">
						<h3 class="box-title text-danger">Chưa thực hiện</h3>
						<div class="message-center">
							<c:if test="${empty m.notStartedTasks}">
								<p class="text-muted">Không có công việc nào</p>
							</c:if>
							<c:forEach items="${m.notStartedTasks}" var="t">
								<a href="task-edit?id=${t.id}">
									<div class="mail-contnet">
										<h5>${t.taskName}</h5>
										<span class="time">Bắt đầu: <fmt:formatDate value="${t.startDate}" pattern="dd/MM/yyyy" /></span>
										<span class="time">Kết thúc: <fmt:formatDate value="${t.endDate}" pattern="dd/MM/yyyy" /></span>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="white-box">
						<h3 class="box-title text-megna">Đang thực hiện</h3>
						<div class="message-center">
							<c:if test="${empty m.inProgressTasks}">
								<p class="text-muted">Không có công việc nào</p>
							</c:if>
							<c:forEach items="${m.inProgressTasks}" var="t">
								<a href="task-edit?id=${t.id}">
									<div class="mail-contnet">
										<h5>${t.taskName}</h5>
										<span class="time">Bắt đầu: <fmt:formatDate value="${t.startDate}" pattern="dd/MM/yyyy" /></span>
										<span class="time">Kết thúc: <fmt:formatDate value="${t.endDate}" pattern="dd/MM/yyyy" /></span>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="white-box">
						<h3 class="box-title text-primary">Đã hoàn thành</h3>
						<div class="message-center">
							<c:if test="${empty m.completedTasks}">
								<p class="text-muted">Không có công việc nào</p>
							</c:if>
							<c:forEach items="${m.completedTasks}" var="t">
								<a href="task-edit?id=${t.id}">
									<div class="mail-contnet">
										<h5>${t.taskName}</h5>
										<span class="time">Bắt đầu: <fmt:formatDate value="${t.startDate}" pattern="dd/MM/yyyy" /></span>
										<span class="time">Kết thúc: <fmt:formatDate value="${t.endDate}" pattern="dd/MM/yyyy" /></span>
									</div>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		<!-- END DANH SÁCH CÔNG VIỆC -->
	</div>
</div>
<!-- /#page-wrapper -->

