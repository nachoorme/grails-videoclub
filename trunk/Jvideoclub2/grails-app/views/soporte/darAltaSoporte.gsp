<%@ page import="modelodatos.Soporte" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'soporte.label', default: 'Soporte')}" />
		<title><g:message code="Alta soporte" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-soporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-soporte" class="content scaffold-create" role="main">
			<h1><g:message code="Alta soporte" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${soporteInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${soporteInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'Crear Soporte', default: 'Crear Soporte')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>