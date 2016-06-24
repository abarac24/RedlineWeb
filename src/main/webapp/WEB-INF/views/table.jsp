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



	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-8 text-left">
				<c:url var="addAction" value="/configuration/run"></c:url>
				<form:form action="${addAction}" commandName="configuration">
					<div class="btn-group">
						<table class="table" id="table_id">
							<thead>
								<tr>
									<th><input type="checkbox" id="checkall"
										onclick="selectAllCheckBox();" /></th>
									<th>ID</th>
									<th>Name</th>
									<th>Description</th>
									<th>Add Step</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listTestCases}" var="testcase">
									<tr>
										<td><form:checkbox path="testSelected"
												value="${testcase.id}" id="testSelected" class="checkthis" /></td>
										<td>${testcase.id}</td>
										<td>${testcase.name}</td>
										<td>${testcase.description}</td>
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
		<script type="text/javascript" charset="utf8"
			src="resources/js/datatables.js"></script>
		<script>
		
			$(document)
					.ready(
							function() {
							
								$("#project").on("change", function()  {
									if(this.value!="NONE"){
										$.ajax({
		   									type:'GET',
		   									url:'project/'+this.value,
		   									data:{boxvalue:this.value,},
		   									success:function(data){
			 							        if(data!=""){
	           										$("#table_id").html(data);
	        									}
		   									}
	   									});
   									}
   									
								});
								
								$('#table_id').DataTable({
									responsive : true,
									paging : true
								});
								$("#table_id #checkall")
										.click(
												function() {
													if ($("#table_id #checkall")
															.is(':checked')) {
														$(
																"#table_id input[path=testSelected]")
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
																"#table_id input[path=testSelected]")
																.each(
																		function() {
																			$(
																					this)
																					.prop(
																							"checked",
																							false);
																		});
													}
												});

								$("[data-toggle=tooltip]").tooltip();
								
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
</body>
</html>
