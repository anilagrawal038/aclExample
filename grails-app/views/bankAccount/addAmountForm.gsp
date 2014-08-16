<%@ page import="com.aclExample.BankAccount" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Add Amount</title>
</head>

<body>
<a href="#edit-bankAccount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>

<div id="edit-bankAccount" class="content scaffold-edit" role="main">
    <h1>Add amount to account</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${bankAccountInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${bankAccountInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form url="[resource: bankAccountInstance, action: 'addAmount']" method="PUT">
        <g:hiddenField name="version" value="${bankAccountInstance?.version}"/>
        <fieldset class="form">

            <div class="fieldcontain ${hasErrors(bean: bankAccountInstance, field: 'accountNumber', 'error')} required">
                <label for="accountNumber">
                    <g:message code="bankAccount.accountNumber.label" default="Account Number"/>
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="accountNumber" type="number" value="${bankAccountInstance.accountNumber}" required="" disabled="disabled"/>

            </div>

            <div class="fieldcontain required">
                <label for="accountBalance">
                    Add balance
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="balance" type="number" value="0" required=""/>

            </div>

        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="addAmount" value="Add Amount"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
