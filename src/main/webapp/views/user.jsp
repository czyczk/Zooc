<%--
  User: Zenas Chen
  Date: 2018-6-23
  Time: 15:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath(); %>
<html>
<head>
  <title>User Test</title>
</head>
<body>
<form action="<%=contextPath%>/api/v1/user/create" method="post">
  <input type="text" name="username" placeholder="username" />
  <input type="text" name="password" placeholder="password" />
  <input type="text" name="aaa" placeholder="whatever" />
  <button type="submit">Submit</button>
</form>
</body>
</html>
