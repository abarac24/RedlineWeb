<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*,com.journaldev.spring.model.*"%>







<!DOCTYPE html>
<html lang="en">
<head>
<title>Platform Redline Test</title>
<meta charset="utf-8">

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
			<div class="col-sm-8 text-left">
				<div class="container">
					<h2>Configuration</h2>
				</div>

				<c:url var="addAction" value="/configuration/run"></c:url>
				<form:form action="${addAction}" commandName="configuration">
					<div class="btn-group">
						<table class="table" border="0">
							<tr>
								<td><form:label path="deviceIp">
										<spring:message text="Device IP AddressS" />
									</form:label></td>
								<td><form:input path="deviceIp" /></td>
								<td><form:label path="userName">
										<spring:message text="User Name" />
									</form:label></td>
								<td><form:input path="userName" value="admin" /></td>
								<td><form:label path="password">
										<spring:message text="Password" />
									</form:label></td>
								<td><form:input path="password" value="admin" /></td>

							</tr>

							<tr>
								<td><form:label path="explorer">
										<spring:message text="Internet Explorer" />
									</form:label></td>
								<td><form:select path="explorer" class="form-control">
										<form:option value="NONE" label="--- Select ---" />
										<form:option value="Internet Explorer"
											label="Internet Explorer" />
										<form:option value="Firefox" label="Firefox" />
										<form:option value="Chrome" label="Chrome" />
									</form:select></td>
							</tr>
							<tr>
								<td><input type="submit" class="btn btn-primary pull-right"
									value="<spring:message text="Save"/>" /></td>
							</tr>
						</table>

						<table class="table" id="table_id">
							<thead>
								<tr>
									<th><input type="checkbox" id="checkall"
										onclick="selectAllCheckBox();" /></th>
									<th>ID</th>
									<th>Name</th>
									<th>Project</th>
									<th>Add Step</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>ID</th>
									<th>Name</th>
									<th>Project</th>
									<th>Add Step</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${listTestCases}" var="testcase">
									<tr>
										<td><form:checkbox path="testSelected"
												value="${testcase.id}" id="testSelected" class="checkthis" /></td>
										<td>${testcase.id}</td>
										<td>${testcase.name}</td>
										<td>${testcase.project}</td>
										<c:url var="addCcUrl" value="/teststep/add?id=${testcase.id}" />
										<td><a href="${addCcUrl}">+</a></td>
										<td><a href="<c:url value='/edittest/${testcase.id}' />">Edit</a></td>
										<td><a
											href="<c:url value='/removetest/${testcase.id}' />">Delete</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>

				</form:form>
				<br>

			</div>
		</div>



		<script src="resources/js/jquery-1.12.0.min.js"></script>
		<script src="resources/js/bootstrap.min.js"></script>
		<script src="resources/js/jquery.tableCheckbox.js"></script>
		<script type="text/javascript"
			src="resources/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" charset="utf8"
			src="resources/js/datatables.js"></script>
		<script>
			$(document)
					.ready(
							function() {

								/*	$("#project").on("change", function() {
										if (this.value != "NONE") {
											$.ajax({
												type : 'GET',
												url : 'project/' + this.value,
												data : {
													boxvalue : this.value,
												},
												success : function(data) {
													if (data != "") {
														$("#table_id").html(data);
													}
												}
											});
										}

									});*/

								$("[data-toggle=tooltip]").tooltip();

								$('#table_id')
										.DataTable(
												{
													initComplete : function() {
														this
																.api()
																.columns()
																.every(
																		function() {
																			var column = this;
																			var select = $(
																					'<select><option value=""></option></select>')
																					.appendTo(
																							$(
																									column
																											.footer())
																									.empty())
																					.on(
																							'change',
																							function() {
																								var val = $.fn.dataTable.util
																										.escapeRegex($(
																												this)
																												.val());

																								column
																										.search(
																												val ? '^'
																														+ val
																														+ '$'
																														: '',
																												true,
																												false)
																										.draw();
																							});

																			column
																					.data()
																					.unique()
																					.sort()
																					.each(
																							function(
																									d,
																									j) {
																								select
																										.append('<option value="'+d+'">'
																												+ d
																												+ '</option>')
																							});
																		});
													}
												});

							});
		</script>
		<script type="text/javascript">
			function selectAllCheckBox() {

				if (document.getElementById('checkall').checked == true) {
					$('.checkthis').each(function() {
						this.checked = true;
					});
				} else {
					$('.checkthis').each(function() {
						this.checked = false;
					});
				}

			}
		</script>
		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								var table = $('#table_id').DataTable();

								$('button')
										.click(
												function() {
													var data = table.$(
															'input, select')
															.serialize();
													alert("The following data would have been submitted to the server: \n\n"
															+ data.substr(0,
																	120)
															+ '...');
													return false;
												});
							});
		</script>
</body>
</html>
