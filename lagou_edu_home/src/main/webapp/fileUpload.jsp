<%--
  Created by IntelliJ IDEA.
  User: oqugh
  Date: 2022/3/24
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<%--
    文件上传三要素
    1.表单提交方式必须是Post
    2.表单的enctype属性必须是multipart/form-data
    3.表单中必须有文件上传项
--%>
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
        <input type="file" name="upload">
        <br>
        <input type="text" name="name">
        <input type="text" name="password">
        <input type="submit" value="文件上传">
    </form>

</body>
</html>
