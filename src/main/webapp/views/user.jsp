<%--
  User: Zenas Chen
  Date: 2018-6-23
  Time: 15:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath(); %>
<html>
<head>
  <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.js"></script>
  <title>User Test</title>
</head>
<body>
<div>
  <form id="create-form">
    <input type="text" name="username" placeholder="username" />
    <input type="text" name="password" placeholder="password" />
    <input type="text" name="email" placeholder="email" />
    <input type="text" name="mobile" placeholder="mobile" />
    <button id="create-button" type="button">Create</button>
  </form>
</div>
<div>
  <form>
    <input type="text" name="username" placeholder="username" />
    <input type="text" name="password" placeholder="password" />
    <button type="button" id="update-button">Update</button>
  </form>
</div>
</body>
<script>
  $(() => {
    $("#create-button").click(() => {
      const user = {
        "username": "zenas",
        "password": "zzzz",
        "email": "czyczk@qq.com",
        "mobile": "12345678901"
      };
      $.ajax({
        url: '<%=contextPath%>/api/v1/user',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(user),
        dataType: 'json'
      })
    });

    $("#update-button").click(() => {
      const user = {
        password: 'dddd'
      };
      $.ajax({
        url: '<%=contextPath%>/api/v1/user/1',
        type: 'put',
        contentType: 'application/json',
        data: JSON.stringify(user),
        dataType: 'json'
      })
    });
  });
</script>
</html>
