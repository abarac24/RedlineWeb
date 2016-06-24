<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="com.journaldev.spring.model.Constants"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>



<!DOCTYPE html>
<html lang="en">
<head>
<title>Platform Redline Test</title>
<meta charset="utf-8">
<script src="resources/js/jquery-1.12.0.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="resources/css/bootstrap.css" rel="stylesheet">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="resources/css/datatables.css">

<style>
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Set height of the grid so .sidenav can be 100% (adjust as needed) */
.row.content {
	height: 450px
}

/* Set gray background color and 100% height */
.sidenav {
	padding-top: 20px;
	background-color: #f1f1f1;
	height: 100%;
}

/* Set black background color, white text and some padding */
footer {
	background-color: #555;
	color: white;
	padding: 15px;
}

/* On small screens, set height to 'auto' for sidenav and grid */
@media screen and (max-width: 767px) {
	.sidenav {
		height: auto;
		padding: 15px;
	}
	.row.content {
		height: auto;
	}
}
</style>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Logo</a>
			</div>
		</div>
	</nav>

	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-2 sidenav">
				<p align="left">
					<a href="testcase.html">TestCase</a>
				</p>
				<p align="left">
					<a href="configuration.html">Configuration</a>
				</p>
				<p align="left">
					<a href="report.html">Report</a>
				</p>
			</div>
			<%
				ApplicationContext context = new ClassPathXmlApplicationContext(
						"servlet-context.xml");

				Constants constant = (Constants) context.getBean("constantsBean");
				File[] files = new File(constant.getREPORT_PATH()).listFiles();
			%>
			<div class="col-sm-8 text-left">
				<div class="container">

					<div class="panel panel-default">
						<div class="panel-heading">Reports</div>

						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table" id="table_id">
									<thead>
										<tr>
											<th>Name</th>
											<th>Delete</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${files}" var="files">
											<tr>
												<td><a href="<c:url value='/reports/${files.getName()}' />">${files.getName()}</a></td>
												<td><a href="<c:url value='/remove/${files.getName()}' />">Delete</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>



				</div>

			</div>
		</div>

		<script src="resources/js/jquery-1.12.0.min.js"></script>
		<script src="resources/js/bootstrap.min.js"></script>
		<script src="resources/js/jquery.tableCheckbox.js"></script>
		<script type="text/javascript" charset="utf8"
			src="resources/js/datatables.js"></script>
		<script>
			$(document).ready(function() {
				$('#table_id').DataTable({
					responsive : true,
					paging : true
				});
														$("#table_id #checkall").click(function() { if
														($("#table_id #checkall").is(':checked')) { $("#table_id
														input[type=checkbox]").each(function() {
														$(this).prop("checked", true); }); } else { $("#table_id
														input[type=checkbox]").each(function() {
														$(this).prop("checked", false); }); } });

														$("[data-toggle=tooltip]").tooltip(); }); </script> <script>
															function remove(
																	fileName) {
																var myObject;
																myObject = new ActiveXObject(
																		"Scripting.FileSystemObject");
																var f = myObject
																		.GetFile(fileName);
																f.Delete();
															}
														</script>
</body>
</html>
