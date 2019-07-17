<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="Survey" value="Park Details Page" />
</c:import>



<div class="container">
	<h1>Favorite National Park Survey</h1>
	<p>Welcome to our survey! Please let us know your favorite national
		park, as well as some demographic information so we can best serve you
		in the future. We swear we won't sell your data to Cambridge
		Analytica...</p>
	<c:url var="surveyURL" value="/survey" />
	<form:form action="${surveyURL}" method="POST" modelAttribute="survey">
		<div class="form-group row">
			<label for="park" class="col-md-3 col-form-label">Favorite
				National Park:</label>
			<form:select class="form-control col-md-4" path="parkCode" required="true">
				<form:errors class="alert alert-danger" path="parkCode" />
				<c:forEach var="park" items="${parks}">
					<c:set var="parkCode" value="${park.code}" />
					<form:option value="${parkCode}">
						<c:out value="${park.name}" />
					</form:option>
				</c:forEach>
			</form:select>
			<div class="invalid-feedback text-center">Please select your
				favorite park</div>
		</div>
		<div class="form-group row">
			<c:set var="emailErrors">
				<form:errors path="email" />
			</c:set>
			<c:if test="${not empty emailErrors}">
				<c:set var="hasErrorEmail" value="is-invalid" />
			</c:if>
			<label for="email" class="col-md-3 col-form-label">Your
				Email:</label>
			<form:input path="email"
				class="${hasErrorEmail} form-control col-md-4" required="true"/>
			<div class="invalid-feedback text-center">Please enter a valid
				email</div>
		</div>
		<div class="form-group row">
			<c:set var="stateErrors">
				<form:errors path="state" />
			</c:set>
			<c:if test="${not empty stateErrors}">
				<c:set var="hasErrorState" value="is-invalid" />
			</c:if>
			<label for="state" class="col-md-3 col-form-label">State of
				Residence:</label>
			<form:select path="state" class="col-md-1 form-control" required="true">
				<c:forEach var="state" items="${states}">
					<form:option value="${state}" class="text-center">
						<c:out value="${state}" />
					</form:option>
				</c:forEach>
			</form:select>
			<div class="invalid-feedback">Please choose a state</div>
		</div>
		<c:set var="activityErrors">
			<form:errors path="activityLevel" />
		</c:set>
		<c:if test="${not empty activityErrors}">
			<c:set var="hasErrorActivity" value="is-invalid" />
		</c:if>
		<div class="row form-group">
			<label for="" class="col-md-3 col-form-label">Activity Level</label>
			<div class="col-md-9">
				<div class="form-check form-check-inline">
					<form:radiobutton class="${hasErrorActivity} form-check-input"
						path="activityLevel" value="inactive" required="true"/>
					<label class="form-check-label" for="inactive">Inactive</label>
				</div>
				<div class="form-check form-check-inline">
					<form:radiobutton class="${hasErrorActivity} form-check-input"
						path="activityLevel" value="sedentary" required="true"/>
					<label class="form-check-label" for="sedentary">Sedentary</label>
				</div>
				<div class="form-check form-check-inline">
					<form:radiobutton class="${hasErrorActivity} form-check-input"
						path="activityLevel" value="active" required="true"/>
					<label class="form-check-label" for="active">Active</label>
				</div>
				<div class="form-check form-check-inline">
					<form:radiobutton class="${hasErrorActivity} form-check-input"
						path="activityLevel" value="extremely active" required="true"/>
					<label class="form-check-label" for="extremely active">Extremely
						Active</label>
				<div class="invalid-feedback">
					<p>Please enter your activity level</p>
				</div>
				</div>
			</div>
		</div>
		<div>
			<button type="submit" class="btn btn-primary submit">Submit
				Survey</button>
		</div>
	</form:form>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />