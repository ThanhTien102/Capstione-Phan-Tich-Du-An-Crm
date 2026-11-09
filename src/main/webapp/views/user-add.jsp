<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row bg-title">
			<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
				<h4 class="page-title">Thêm mới thành viên</h4>
			</div>
		</div>
		<!-- /.row -->
		<!-- .row -->
		<div class="row">
			<div class="col-md-2 col-12"></div>
			<div class="col-md-8 col-xs-12">
				<div class="white-box">
					<c:if test="${param.created == '1'}">
						<div class="alert alert-success">Thêm thành viên thành công.</div>
					</c:if>
					<c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<form class="form-horizontal form-material" action="user-add"
						method="post">
						<div class="form-group">
							<label class="col-md-12">Full Name</label>
							<div class="col-md-12">
								<input type="text" placeholder="Johnathan Doe" name="fullname"
									class="form-control form-control-line"
									value="${param.fullname}">
							</div>
						</div>
						<div class="form-group">
							<label for="example-email" class="col-md-12">Email</label>
							<div class="col-md-12">
								<input type="email" placeholder="johnathan@admin.com"
									class="form-control form-control-line" name="email"
									value="${param.email}" id="example-email">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Password</label>
							<div class="col-md-12">
								<input type="password" name="password"
									class="form-control form-control-line">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Phone No</label>
							<div class="col-md-12">
								<input type="text" placeholder="123 456 7890" name="phone"
									value="${param.phone}" class="form-control form-control-line">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-12">Select Country</label>
							<div class="col-sm-12">
								<select class="form-control form-control-line" name="country">

									<option
										<c:if test="${param.country == 'London'}"> selected</c:if>
										value="London">London</option>
									<option
										<c:if test="${param.country == 'India'}"> selected</c:if>
										value="India">India</option>
									<option <c:if test="${param.country == 'Usa'}"> selected</c:if>
										value="Usa">Usa</option>
									<option
										<c:if test="${param.country == 'Canada'}"> selected</c:if>
										value="Canada">Canada</option>
									<option
										<c:if test="${param.country == 'Thailand'}"> selected</c:if>
										value="Thailand">Thailand</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-12">Role</label>
							<div class="col-md-12">
								<select name="roleId">
									<c:forEach items="${roles}" var="role">
										<option
											<c:if test="${param.roleId == role.id}"> 
												selected
											</c:if>
											value="${role.id}">${role.nameRole}
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<button type="submit" class="btn btn-success">Add User</button>
								<a href="user-table.html" class="btn btn-primary">Quay lại</a>
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