<%@ page import="com.aclExample.BankAccount" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bankAccount.label', default: 'BankAccount')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-bankAccount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-bankAccount" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="accountBalance" title="${message(code: 'bankAccount.accountBalance.label', default: 'Account Balance')}"/>

            <g:sortableColumn property="accountNumber" title="${message(code: 'bankAccount.accountNumber.label', default: 'Account Number')}"/>

            <g:sortableColumn property="branch" title="${message(code: 'bankAccount.branch.label', default: 'Branch')}"/>

            <th><g:message code="bankAccount.user.label" default="User"/></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${bankAccountInstanceList}" status="i" var="bankAccountInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${bankAccountInstance.id}">${fieldValue(bean: bankAccountInstance, field: "accountBalance")}</g:link></td>

                <td>${fieldValue(bean: bankAccountInstance, field: "accountNumber")}</td>

                <td>${fieldValue(bean: bankAccountInstance, field: "branch")}</td>

                <td>${fieldValue(bean: bankAccountInstance, field: "user")}</td>

                <td><g:link action="addAmountForm" id="${bankAccountInstance.id}">Add Amount</g:link></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${bankAccountInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
