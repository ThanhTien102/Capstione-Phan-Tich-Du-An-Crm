<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse slimscrollsidebar">
		<ul class="nav" id="side-menu">
			<li style="padding: 10px 0 0;"><a
				href="${pageContext.request.contextPath}/" class="waves-effect"><i
					class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span
					class="hide-menu">Dashboard</span></a></li>

			<c:if test="${sessionScope.currentUser.roleId == 1 || sessionScope.currentUser.roleId == 2}">
				<li><a href="${pageContext.request.contextPath}/user-table"
					class="waves-effect"><i class="fa fa-user fa-fw"
						aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a></li>
			</c:if>

			<c:if test="${sessionScope.currentUser.roleId == 1}">
				<li><a href="${pageContext.request.contextPath}/role-table"
					class="waves-effect"><i class="fa fa-modx fa-fw"
						aria-hidden="true"></i><span class="hide-menu">Quyền</span></a></li>
			</c:if>

			<c:if test="${sessionScope.currentUser.roleId == 1 || sessionScope.currentUser.roleId == 2}">
				<li><a href="${pageContext.request.contextPath}/groupwork"
					class="waves-effect"><i class="fa fa-table fa-fw"
						aria-hidden="true"></i><span class="hide-menu">Dự án</span></a></li>
				<li><a href="${pageContext.request.contextPath}/task"
					class="waves-effect"><i class="fa fa-tasks fa-fw"
						aria-hidden="true"></i><span class="hide-menu">Công việc</span></a></li>
			</c:if>

			<c:if test="${sessionScope.currentUser.roleId == 3}">
				<li><a href="${pageContext.request.contextPath}/profile"
					class="waves-effect"><i class="fa fa-tasks fa-fw"
						aria-hidden="true"></i><span class="hide-menu">Công việc của tôi</span></a></li>
			</c:if>

			<li><a href="${pageContext.request.contextPath}/blank" class="waves-effect"><i
					class="fa fa-columns fa-fw" aria-hidden="true"></i><span
					class="hide-menu">Blank Page</span></a></li>
			<li><a href="${pageContext.request.contextPath}/403" class="waves-effect"><i
					class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span
					class="hide-menu">Error 403</span></a></li>
		</ul>
	</div>
</div>
