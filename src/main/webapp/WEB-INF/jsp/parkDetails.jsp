<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="parkDetails" value="Park Details Page" />
</c:import>

<div class="text-center">
	<img class="rounded w-100"
		src="<c:url value="/img/parks/${park.codeAsLowerCase}.jpg" />" />
</div>


<div class="row mt-4">

	<div class="col-lg-8">
		<h2 class="mb-4">${park.name}-${park.state}</h2>

		<div>${park.description}</div>
		<blockquote class="blockquote mt-5 mb-2 text-right mr-3">
			<p class="mb-0">${park.inspirationalQuote}</p>
			<footer class="blockquote-footer">
				<cite>${park.inspirationalQuoteSource}</cite>
			</footer>
		</blockquote>
		<div class="row ml-3">
			<c:set var="classC" value="secondary" />
			<c:if test="${tempFormat == 'C'}">
				<c:set var="classC" value="primary" />
			</c:if>
			<c:set var="classF" value="primary" />
			<c:if test="${tempFormat == 'C'}">
				<c:set var="classF" value="secondary" />
			</c:if>
			<p class="my-auto">Change temperature format:</p>
			<c:url var="parkDetailsURL" value="/parkDetails" />
			<form class="mx-2" action="${parkDetailsURL}" method="POST">
				<input type="hidden" value="${park.code}" name="currentParkId" /> <input
					type="hidden" value="F" name="tempFormat" /> <input type="submit"
					class="btn btn-${classF}" value="Fahrenheit" />
			</form>
			<form action="${parkDetailsURL}" method="POST">
				<input type="hidden" value="${park.code}" name="currentParkId" /> <input
					type="hidden" value="C" name="tempFormat" /> <input type="submit"
					class="btn btn-${classC}" value="Celsius" />
			</form>
		</div>
	</div>

	<div class="card col-lg-4">
		<ul class="list-group list-group-flush">
			<li class="list-group-item"><strong>Acreage: </strong>
			<fmt:formatNumber type="number" maxFractionDigits="3"
					value="${park.acreage}" /></li>
			<li class="list-group-item"><strong>Elevation(ft): </strong>
			<fmt:formatNumber type="number" maxFractionDigits="3"
					value="${park.elevationInFeet}" /></li>
			<li class="list-group-item"><strong>Miles of Trail: </strong>
			<fmt:formatNumber type="number" maxFractionDigits="3"
					value="${park.milesOfTrail}" /></li>
			<li class="list-group-item"><strong>Annual Visitors: </strong>
			<fmt:formatNumber type="number" maxFractionDigits="3"
					value="${park.annualVisitorCount}" /></li>
			<li class="list-group-item"><strong>Climate:</strong> <c:out
					value="${park.climate}" /></li>
			<li class="list-group-item"><strong>Number of
					Campsites: </strong>
			<fmt:formatNumber type="number" maxFractionDigits="3"
					value="${park.numberOfCampsites}" /></li>
			<li class="list-group-item"><strong>Founded in:</strong> <c:out
					value="${park.yearFounded}" /></li>
			<li class="list-group-item"><strong>Entry Fee: </strong>
			<fmt:formatNumber value="${park.entryFee}" type="currency" /></li>
			<li class="list-group-item"><strong>Animal Species: </strong>
			<fmt:formatNumber type="number" maxFractionDigits="3"
					value="${park.numberOfAnimalSpecies}" /></li>
		</ul>
	</div>
</div>


<div class="row" id="weather">
	<div class="col-md-4 col-12 justify-content-center">
		<p class="text-center mt-3">Today</p>
		<div class="mx-auto my-3 img-center">
			<img class="img-fluid text-center"
				src="<c:url value="/img/weather/${forecast[0].imageName}.png" />" />
		</div>
		<c:forEach var="suggestion" items="${weatherSuggestions}">
			<p class="text-center">${suggestion}</p>
		</c:forEach>
	</div>
	<c:set var="today" value="<%=new Date()%>" />
	<%
		int count = 1;
	%>
	<c:forEach var="dailyForecast" items="${forecast}" begin="1"
		varStatus="counter">
		<c:set var="day"
			value="<%=new Date(new Date().getTime() + 60 * 60 * 24 * 1000 * count)%>" />
		<%
			count++;
		%>
		<div class="col-md-2 col-6 mt-5">
			<p class="text-center">
				<fmt:formatDate type="date" value="${day}" pattern="E" />
			</p>
			<img class="img-fluid"
				src="<c:url value="/img/weather/${dailyForecast.imageName}.png" />" />
			<ul class="list-group list-group-flush mt-0">
				<c:choose>
					<c:when test="${tempFormat == 'C'}">
						<li class="list-group-item"><strong>High:</strong> <fmt:formatNumber
								type="number" maxFractionDigits="0"
								value="${(dailyForecast.high - 32) * 5 / 9}" />C</li>
						<li class="list-group-item"><strong>Low:</strong> <fmt:formatNumber
								type="number" maxFractionDigits="0"
								value="${(dailyForecast.low - 32) * 5 / 9}" />C</li>
					</c:when>
					<c:otherwise>
						<li class="list-group-item"><strong>High:</strong> <c:out
								value="${dailyForecast.high}F" /></li>
						<li class="list-group-item"><strong>Low:</strong> <c:out
								value="${dailyForecast.low}F" /></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</c:forEach>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />