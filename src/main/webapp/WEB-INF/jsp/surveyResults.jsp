<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="parkDetails" value="Park Details Page" />
</c:import>

<h1 class="survey-results-header"> Survey Results</h1>



<br> 

<div class="container survey-page">

<p> Thank for taking our survey. Here are other parks in our system with reviews and the number of reviews they've received. </p>
	
		<c:forEach var="park" items="${sortedParks}">
		
		<div class="row">
		
				<div class="col-md-4">
					<img class="image-surveyResults-page"src="<c:url value="/img/parks/${park.key.codeAsLowerCase}.jpg" />" />
				</div>
				
				<div class="col-md-4 m-auto">
					<p class="resultsParkName"> <strong><c:out value="${park.key.name}" /></strong></p>
				</div>
			
				<div class="col-md-4 m-auto">
					<p class="resultsNumberOfSurveys">Number of Surveys: <c:out value="${park.value}" /></p>
				</div>
		</div>
					
		</c:forEach>
	</>
</div>



<c:import url="/WEB-INF/jsp/common/footer.jsp" />