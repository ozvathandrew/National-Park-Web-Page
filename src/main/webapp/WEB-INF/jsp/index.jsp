<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="homeView" value="Park Home View" />
</c:import>

<div class="container">
	
		<c:forEach var="park" items="${parks}">
		<div class="row pb-5">
			<div class="col-lg-6">
				<a href="<c:url value="/parkDetails?parkId=${park.code}" />"> 
				<img class="image-home-page"
					src="<c:url value="/img/parks/${park.codeAsLowerCase}.jpg" />" />
				</a>
			</div>
				<div class="col-lg-6">
					<a href="<c:url value="/parkDetails?parkId=${park.code}" />"> 
						<strong><c:out value="${park.name}" /></strong>
					</a>
					<p><c:out value="${park.description}" /> </p>
				</div>
			</div>
		</c:forEach>
	
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />