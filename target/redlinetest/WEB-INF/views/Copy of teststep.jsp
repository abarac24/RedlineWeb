<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*,com.journaldev.spring.model.*"%>

<jsp:useBean id="testBean" class="com.journaldev.spring.model.TestStep"
	scope="session" />

<jsp:setProperty name="testBean" property="*" />





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
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Projects</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Login</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-2 sidenav">
				<p align="left">
					<a href="testsuite.html">TestSuite</a>
				</p>
				<p align="left">
					<a href="testcase.html">TestCase</a>
				</p>
				<p align="left">
					<a href="#">Reports</a>
				</p>
				<p align="left">
					<a href="#">Failed Tests</a>
				</p>
				<p align="left">
					<a href="#">Passed Tests</a>
				</p>
			</div>
			<div class="col-sm-8 text-left">
				<div class="container">
					<h1>Add TestCase</h1>
				</div>

				<c:url var="addAction" value="/teststep/add"></c:url>
				<form:form action="${addAction}" commandName="teststep">

					<div class="btn-group">
						<table class="table" border="0">
							<c:if test="${!empty teststep.fieldType}">
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
								<td><form:input path="name" value="<%=testBean.getName()%>" /></td>
							</tr>
							<tr>
								<td><form:label path="suiteName">
										<spring:message text="Suite" />
									</form:label></td>
								<td><form:select path="suiteName" class="form-control">
										<c:forEach items="${listNameSuite}" var="listNameSuite">
											<form:option value='${listNameSuite.suiteName}'
												label='${listNameSuite.suiteName}' />
										</c:forEach>
									</form:select></td>
							</tr>

							<tr>
								<td><form:label path="action">
										<spring:message text="Action" />
									</form:label></td>
								<td><form:select path="action" class="form-control">
										<form:option value="NONE" label="--- Select ---" />
										<form:option value="NAV" label="NAV" />
										<form:option value="SET" label="SET" />
										<form:option value="GET" label="GET" />
										<form:option value="CLICK" label="CLICK" />
									</form:select></td>
							</tr>
							<tr>
								<td><form:label path="fieldId">
										<spring:message text="FieldId" />
									</form:label></td>
								<td><form:input path="fieldId" /></td>
							</tr>
							<tr>
								<td><form:label path="fieldType">
										<spring:message text="FieldType" />
									</form:label></td>
								<td><form:select path="fieldType" class="form-control">
										<form:option value="NONE" label="--- Select ---" />
										<form:option value="DDL" label="DDL" />
										<form:option value="text" label="text" />
										<form:option value="checkbox" label="checkbox" />
										<form:option value="button" label="button" />
										<form:option value="null" label="null" />
									</form:select></td>
							</tr>
							<tr>
								<td><form:label path="fieldValue">
										<spring:message text="FieldValue" />
									</form:label></td>
								<td><form:input path="fieldValue" /></td>
							</tr>
							<tr>
								<td><form:label path="result">
										<spring:message text="Result" />
									</form:label></td>
								<td><form:select path="result" class="form-control">
										<form:option value="Pass" label="Pass" />
										<form:option value="Fail" label="Fail" />
									</form:select></td>
							</tr>
							<tr>
								<td colspan="2"><c:if test="${!empty teststep.fieldType}">
										<input type="submit" class="btn btn-primary pull-right"
											value="<spring:message text="Edit Step"/>" />
									</c:if> <c:if test="${empty teststep.fieldType}">
										<input type="submit" class="btn btn-primary pull-right"
											value="<spring:message text="Add Step"/>" />


									</c:if></td>
							</tr>
						</table>
					</div>
				</form:form>
				<br>
				<div class="panel panel-default">
					<div class="panel-heading">DataTables Advanced Tables</div>

					<div class="panel-body">
						<div class="dataTable_wrapper">
							<table class="table"
								id="table_id">
								<thead>
									<tr>
										<th>ID</th>
										<th>Suite</th>
										<th>Action</th>
										<th>Field ID</th>
										<th>Field Type</th>
										<th>Field Value</th>
										<th>Result</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listTestSteps}" var="teststep">
										<tr>
											<td>${teststep.id}</td>
											<td>${teststep.suiteName}</td>
											<td>${teststep.action}</td>
											<td>${teststep.fieldId}</td>
											<td>${teststep.fieldType}</td>
											<td>${teststep.fieldValue}</td>
											<td>${teststep.result}</td>
											<td><a href="<c:url value='/edit/${teststep.id}' />">Edit</a></td>
											<td><a href="<c:url value='/remove/${teststep.id}' />">Delete</a></td>
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
		<script type="text/javascript" charset="utf8"
			src="resources/js/datatables.js"></script>
		<script>
			$(document).ready(function() {
				$('#table_id').DataTable({
					responsive : true,
					paging : true
				});
			});
		</script>
</body>
</html>
