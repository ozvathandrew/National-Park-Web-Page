<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Nation Park Geek</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<c:url var="cssUrl" value="/css/site.css" />
<link rel="stylesheet" href="${cssUrl}" />
</head>

<body class="bg-light album py-5">
	<div class="logo">
		<img src="<c:url value="/img/logo.png" />" />
	</div>
	<div class="border-bottom border-dark border-top mb-5">
		<nav class="navbar navbar-expand navbar-light bg-light">
			<div class="navbar-nav pl-5">
				<a class="nav-item nav-link active" href="<c:url value="/" />">Home</a>
				<a class="nav-item nav-link" href="<c:url value="/survey" />">Survey</a>
			</div>
		</nav>
	</div>



	<div class="container">