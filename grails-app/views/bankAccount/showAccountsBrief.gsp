<%@ page import="com.aclExample.BankAccount" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Bank account brief</title>
</head>

<body>
<a href="#list-bankAccount" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>

<div id="list-bankAccount" class="content scaffold-list" role="main">
    <h1>My bank accounts brief</h1>
    <table>
        <thead>
        <tr>
            <th>Bank Account Number</th>
            <th>Balance</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${accountsDetails}" status="i" var="accountsDetail">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td>${accountsDetail[0]}</td>
                <td>${accountsDetail[1]}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
