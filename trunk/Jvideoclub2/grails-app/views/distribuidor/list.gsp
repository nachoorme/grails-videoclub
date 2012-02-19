
<%@ page import="modelodatos.Distribuidor" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'distribuidor.label', default: 'Distribuidor')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-distribuidor" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-distribuidor" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="nombre" title="${message(code: 'distribuidor.nombre.label', default: 'Nombre')}" />
					
						<g:sortableColumn property="cif" title="${message(code: 'distribuidor.cif.label', default: 'Cif')}" />
					
						<g:sortableColumn property="direccion" title="${message(code: 'distribuidor.direccion.label', default: 'Direccion')}" />
					
						<g:sortableColumn property="poblacion" title="${message(code: 'distribuidor.poblacion.label', default: 'Poblacion')}" />
					
						<g:sortableColumn property="provincia" title="${message(code: 'distribuidor.provincia.label', default: 'Provincia')}" />
					
						<g:sortableColumn property="distritoPostal" title="${message(code: 'distribuidor.distritoPostal.label', default: 'Distrito Postal')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${distribuidorInstanceList}" status="i" var="distribuidorInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${distribuidorInstance.id}">${fieldValue(bean: distribuidorInstance, field: "nombre")}</g:link></td>
					
						<td>${fieldValue(bean: distribuidorInstance, field: "cif")}</td>
					
						<td>${fieldValue(bean: distribuidorInstance, field: "direccion")}</td>
					
						<td>${fieldValue(bean: distribuidorInstance, field: "poblacion")}</td>
					
						<td>${fieldValue(bean: distribuidorInstance, field: "provincia")}</td>
					
						<td>${fieldValue(bean: distribuidorInstance, field: "distritoPostal")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${distribuidorInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
