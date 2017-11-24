<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap-grid.css">
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap-reboot.css">
<script src="resources/js/jquery-3.2.1.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://unpkg.com/tooltip.js"></script>
<script src="resources/js/bootstrap.bundle.js"></script>
<script src="resources/js/bootstrap.js"></script>
<script src="resources/js/core.js"></script>
</head>
<body>
	<!-- navi -->
	<jsp:include page="/WEB-INF/views/structure/navi.jsp" />
	<!-- content -->
	<div style="text-align: center; min-width: 100%; max-width: 600px; margin: 0px auto;">
		<div style="min-width: 30%; min-height: 600px; background-color: red; display: inline-block;">
			<jsp:include page="${head}" />
		</div>
		<div style="min-width: 68%; min-height: 600px; background-color: red; display: inline-block;">
			<jsp:include page="${detail}" />
		</div>
	</div>
	<!-- footer -->
</body>
</html>
