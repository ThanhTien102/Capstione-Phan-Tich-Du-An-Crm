<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<h4 class="page-title">Chi tiết thành viên</h4>
			</div>
			<div class="col-lg-6 col-sm-6 col-md-6 col-xs-12 text-right">
				<a href="user-table" class="btn btn-sm btn-primary">Quay lại danh sách</a>
			</div>
		</div>
		<!-- /.row -->
		<!-- .row -->
		<div class="row">
			<div class="col-md-4 col-xs-12">
				<div class="white-box">
					<div class="user-bg">
						<img width="100%" alt="user" src="${pageContext.request.contextPath}/assets/plugins/images/large/img1.jpg">
						<div class="overlay-box">
							<div class="user-content">
								<a href="javascript:void(0)">
									<img src="${pageContext.request.contextPath}/assets/plugins/images/users/genu.jpg"
										class="thumb-lg img-circle" alt="img">
								</a>
								<h4 class="text-white">${detail.user.fullname}</h4>
								<h5 class="text-white">${detail.user.email}</h5>
								<p class="text-white">SĐT: ${detail.user.phone} | Quốc gia: ${detail.user.country}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-8 col-xs-12">
				<!-- BEGIN THỐNG KÊ -->
				<div class="row">
					<!--col -->
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="white-box">
							<div class="col-in row">
								<div class="col-xs-12">
									<h3 class="counter text-right m-t-15 text-danger">${detail.stats.notStartedPercent}%</h3>
								</div>
								<div class="col-xs-12">
									<i data-icon="E" class="linea-icon linea-basic"></i>
									<h5 class="text-muted vb text-center">CHƯA BẮT ĐẦU</h5>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="progress">
										<div class="progress-bar progress-bar-danger" role="progressbar"
											aria-valuenow="${detail.stats.notStartedPercent}" aria-valuemin="0" aria-valuemax="100"
											style="width: ${detail.stats.notStartedPercent}%">
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
								<div class="col-xs-12">
									<h3 class="counter text-right m-t-15 text-megna">${detail.stats.inProgressPercent}%</h3>
								</div>
								<div class="col-xs-12">
									<i class="linea-icon linea-basic" data-icon="&#xe01b;"></i>
									<h5 class="text-muted vb text-center">ĐANG THỰC HIỆN</h5>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="progress">
										<div class="progress-bar progress-bar-megna" role="progressbar"
											aria-valuenow="${detail.stats.inProgressPercent}" aria-valuemin="0" aria-valuemax="100"
											style="width: ${detail.stats.inProgressPercent}%">
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
								<div class="col-xs-12">
									<h3 class="counter text-right m-t-15 text-primary">${detail.stats.completedPercent}%</h3>
								</div>
								<div class="col-xs-12">
									<i class="linea-icon linea-basic" data-icon="&#xe00b;"></i>
									<h5 class="text-muted vb text-center">HOÀN THÀNH</h5>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="progress">
										<div class="progress-bar progress-bar-primary" role="progressbar"
											aria-valuenow="${detail.stats.completedPercent}" aria-valuemin="0" aria-valuemax="100"
											style="width: ${detail.stats.completedPercent}%">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /.col -->
				</div>
				<!-- END THỐNG KÊ -->
			</div>
		</div>
		<br />
		<!-- /.row -->
		<!-- BEGIN DANH SÁCH CÔNG VIỆC -->
		<h4>DANH SÁCH CÔNG VIỆC</h4>
		<div class="row">
			<div class="col-md-4">
				<div class="white-box">
					<h3 class="box-title text-danger">Chưa thực hiện</h3>
					<div class="message-center">
						<c:if test="${empty detail.notStartedTasks}">
							<p class="text-muted">Không có công việc nào</p>
						</c:if>
						<c:forEach items="${detail.notStartedTasks}" var="t">
							<a href="task-edit?id=${t.id}">
								<div class="mail-contnet">
									<h5>${t.taskName}</h5>
									<span class="mail-desc">Dự án: ${t.projectName}</span>
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
						<c:if test="${empty detail.inProgressTasks}">
							<p class="text-muted">Không có công việc nào</p>
						</c:if>
						<c:forEach items="${detail.inProgressTasks}" var="t">
							<a href="task-edit?id=${t.id}">
								<div class="mail-contnet">
									<h5>${t.taskName}</h5>
									<span class="mail-desc">Dự án: ${t.projectName}</span>
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
						<c:if test="${empty detail.completedTasks}">
							<p class="text-muted">Không có công việc nào</p>
						</c:if>
						<c:forEach items="${detail.completedTasks}" var="t">
							<a href="task-edit?id=${t.id}">
								<div class="mail-contnet">
									<h5>${t.taskName}</h5>
									<span class="mail-desc">Dự án: ${t.projectName}</span>
									<span class="time">Bắt đầu: <fmt:formatDate value="${t.startDate}" pattern="dd/MM/yyyy" /></span>
									<span class="time">Kết thúc: <fmt:formatDate value="${t.endDate}" pattern="dd/MM/yyyy" /></span>
								</div>
							</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<!-- END DANH SÁCH CÔNG VIỆC -->
	</div>
</div>
<!-- /#page-wrapper -->

