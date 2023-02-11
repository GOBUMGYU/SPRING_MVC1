<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입 폼</title>
</head>
<body>

<!-- 여기서 form의 action을 보면 절대경로(/로 시작)이 아니라 상대경로 (/로)시작하는 하지 않는 것을 볼 수 있다.
     이렇게 상대경로를 사용하면 폼 전송시 현재 URL이 속한 계층 경로 + /save가 호출된다.
     상대경로 호출
     현재 계층 경로 : localhost:9000/servlet-mvc/members/
     결과         : localhost:9000/servlet-mvc/members/save
     절대 경로로 호출 시 : localhost:9000/save-->
<form action="save" method="post">
  username: <input type="text" name="username">
  age:      <input type="text" name="age">
  <button type="submit">전송</button>
</form>

</body>
</html>
