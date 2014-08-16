<%@ page import="com.aclExample.BankAccount" %>



<div class="fieldcontain ${hasErrors(bean: bankAccountInstance, field: 'accountBalance', 'error')} required">
    <label for="accountBalance">
        <g:message code="bankAccount.accountBalance.label" default="Account Balance"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="accountBalance" type="number" value="${bankAccountInstance.accountBalance}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: bankAccountInstance, field: 'accountNumber', 'error')} required">
    <label for="accountNumber">
        <g:message code="bankAccount.accountNumber.label" default="Account Number"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="accountNumber" type="number" value="${bankAccountInstance.accountNumber}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: bankAccountInstance, field: 'branch', 'error')} ">
    <label for="branch">
        <g:message code="bankAccount.branch.label" default="Branch"/>

    </label>
    <g:textField name="branch" value="${bankAccountInstance?.branch}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bankAccountInstance, field: 'user', 'error')} required">
    <label for="user">
        <g:message code="bankAccount.user.label" default="User"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="user" name="user.id" from="${com.aclExample.User.list()}" optionKey="id" required="" value="${bankAccountInstance?.user?.id}" class="many-to-one"/>

</div>

