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
					<a href="../../redlinetest/testcase.html">TestCase</a>
				</p>
				<p align="left">
					<a href="../../redlinetest/configuration.html" >Configuration</a>
				</p>
				<p align="left">
					<a href="../../redlinetest/report.html">Report</a>
				</p>
			</div>
			<div class="col-sm-8 text-left">
				<div class="container">
					<h1>Add TestCase</h1>
				</div>

				<c:url var="addAction" value="/testcase/add"></c:url>
				<form:form action="${addAction}" commandName="testcase">

					<div class="btn-group">
						<table class="table" border="0">
							<c:if test="${!empty testcase.name}">
								<tr>
									<td><form:label path="id">
											<spring:message text="ID" />
										</form:label></td>
									<td><form:input path="id" readonly="true" size="8"
											disabled="true" /> <form:hidden path="id" /></td>
								</tr>
							</c:if>
							<tr>
								<td><form:label path="name">
										<spring:message text="Name" />
									</form:label></td>
								<td><form:input path="name" /></td>
							</tr>
							<tr>
								<td><form:label path="description">
										<spring:message text="Description" />
									</form:label></td>
								<td><form:textarea path="description" rows="5" cols="30" /></td>
							</tr>
							<tr>
								<td><form:label path="project">
										<spring:message text="Project" />
									</form:label></td>
								<td><form:select path="project" class="form-control">
										<form:option value="NONE" label="--- Select ---" />
										<form:option value="Connect" label="Connect" />
										<form:option value="RDL3000" label="RDL3000" />
										<form:option value="RDL3100" label="RDL3100" />
										<form:option value="ClearView" label="ClearView" />
										<form:option value="LTE" label="LTE" />
									</form:select></td>

							</tr>
							<tr>
								<td colspan="2"><c:if test="${!empty testcase.name}">
										<input type="submit" class="btn btn-primary pull-right"
											value="<spring:message text="Edit"/>" />
									</c:if> <c:if test="${empty testcase.name}">
										<input type="submit" class="btn btn-primary pull-right"
											value="<spring:message text="Create"/>" />


									</c:if></td>
							</tr>
						</table>
					</div>
				</form:form>
				<br>
				<div class="panel panel-default">
					<div class="panel-heading">Test Cases</div>

					<div class="panel-body">
						<div class="dataTable_wrapper">
							<table class="table" id="table_id">
								<thead>
									<tr>
										<th><input type="checkbox" id="checkall" /></th>
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
											<td><input type="checkbox" class="checkthis" /></td>
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
			$(document)
					.ready(
							function() {
								/* $('#table_id').DataTable({
									responsive : true,
									paging : true
								});
								$("#table_id #checkall")
										.click(
												function() {
													if ($("#table_id #checkall")
															.is(':checked')) {
														$(
																"#table_id input[type=checkbox]")
																.each(
																		function() {
																			$(
																					this)
																					.prop(
																							"checked",
																							true);
																		});

													} else {
														$(
																"#table_id input[type=checkbox]")
																.each(
																		function() {
																			$(
																					this)
																					.prop(
																							"checked",
																							false);
																		});
													}
												}); */

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
		$('button').click( function() {
        var data = table.$('input, select').serialize();
        alert(
            "The following data would have been submitted to the server: \n\n"+
            data.substr( 0, 120 )+'...'
        );
        return false;
    } );
							});
		</script>
</body>
</html>
