<%@ page import="com.aclExample.BankAccount" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Search Account</title>
</head>

<body>
<a href="#edit-bankAccount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>

<div id="edit-bankAccount" class="content scaffold-edit" role="main">
    <h1>Search account</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:form url="[controller: 'bankAccount', action: 'searchAccount']" method="PUT">
        <fieldset class="form">

            <div class="fieldcontain">
                <label>Branch
                </label>
                <g:field name="branch" type="text"/>

            </div>

            <div class="fieldcontain">
                <label>
                    User
                </label>
                <g:field name="user" type="text"/>
            </div>

        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit action="searchAccount" value="Search Account"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
