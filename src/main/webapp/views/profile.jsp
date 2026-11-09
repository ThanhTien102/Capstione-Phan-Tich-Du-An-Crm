<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
				<h4 class="page-title">Thông tin cá nhân</h4>
			</div>
		</div>
		<c:if test="${param.updated == '1'}">
			<div class="alert alert-success">Cập nhật tiến độ công việc thành công!</div>
		</c:if>
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
		<h4>DANH SÁCH CÔNG VIỆC CỦA TÔI</h4>
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
									<th>Ngày Bắt Đầu</th>
									<th>Ngày Kết Thúc</th>
									<th>Trạng Thái</th>
									<th>Hành Động</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${empty detail.allTasks}">
									<tr>
										<td colspan="7" class="text-center text-muted">Bạn chưa được phân công công việc nào.</td>
									</tr>
								</c:if>
								<c:forEach items="${detail.allTasks}" var="t" varStatus="loop">
									<tr>
										<td>${loop.index + 1}</td>
										<td>${t.taskName}</td>
										<td>${t.projectName}</td>
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
											<a href="profile-edit?id=${t.id}" class="btn btn-sm btn-primary">Cập nhật</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- END DANH SÁCH CÔNG VIỆC -->
	</div>
</div>
<!-- /#page-wrapper -->

