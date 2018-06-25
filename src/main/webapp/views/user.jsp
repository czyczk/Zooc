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
<div>
  <form action="<%=contextPath%>/api/v1/user" method="post">
    <input type="text" name="username" placeholder="username" />
    <input type="text" name="password" placeholder="password" />
    <input type="text" name="email" placeholder="email" />
    <input type="text" name="mobile" placeholder="mobile" />
    <button type="submit">Create</button>
  </form>
</div>
<div>
  <form action="<%=contextPath%>/api/v1/user/1" method="post">
    <input type="text" name="password" placeholder="password" />
    <button type="submit">Update</button>
  </form>
</div>
</body>
</html>
