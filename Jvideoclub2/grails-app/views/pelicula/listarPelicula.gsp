
<%@ page import="modelodatos.Pelicula" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pelicula.label', default: 'Pelicula')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-pelicula" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>				
			</ul>
		</div>
		<div id="list-pelicula" class="content scaffold-list" role="main">
			<h1>Mostrar listado de películas</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:render template="lista" model="[accion:'lista']" />
			
			<div class="pagination">
				<g:paginate total="${peliculaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
