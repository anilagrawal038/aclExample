<%@ page import="com.aclExample.BankAccount" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bankAccount.label', default: 'BankAccount')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-bankAccount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-bankAccount" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list bankAccount">

        <g:if test="${bankAccountInstance?.accountBalance}">
            <li class="fieldcontain">
                <span id="accountBalance-label" class="property-label"><g:message code="bankAccount.accountBalance.label" default="Account Balance"/></span>

                <span class="property-value" aria-labelledby="accountBalance-label"><g:fieldValue bean="${bankAccountInstance}" field="accountBalance"/></span>

            </li>
        </g:if>

        <g:if test="${bankAccountInstance?.accountNumber}">
            <li class="fieldcontain">
                <span id="accountNumber-label" class="property-label"><g:message code="bankAccount.accountNumber.label" default="Account Number"/></span>

                <span class="property-value" aria-labelledby="accountNumber-label"><g:fieldValue bean="${bankAccountInstance}" field="accountNumber"/></span>

            </li>
        </g:if>

        <g:if test="${bankAccountInstance?.branch}">
            <li class="fieldcontain">
                <span id="branch-label" class="property-label"><g:message code="bankAccount.branch.label" default="Branch"/></span>

                <span class="property-value" aria-labelledby="branch-label"><g:fieldValue bean="${bankAccountInstance}" field="branch"/></span>

            </li>
        </g:if>

        <g:if test="${bankAccountInstance?.user}">
            <li class="fieldcontain">
                <span id="user-label" class="property-label"><g:message code="bankAccount.user.label" default="User"/></span>

                <span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show"
                                                                                  id="${bankAccountInstance?.user?.id}">${bankAccountInstance?.user?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource: bankAccountInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${bankAccountInstance}"><g:message code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>