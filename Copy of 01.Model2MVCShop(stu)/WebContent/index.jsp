<%@ page contentType="text/html; charset=euc-kr" %>

<html>
<head>
<title>Model2 MVC Shop</title>
</head>

<!-- 화면분할 상하로 두 구역으로 나누고 좌우로는 나누지 않음 -->
<frameset rows="80,*" cols="*" frameborder="NO" border=" " framespacing="0">
  <!-- 상단 frame, 하단 freamset 부분 -->
  <frame src="/layout/top.jsp" name="topFrame" scrolling="NO" noresize >
  <!-- 하단 frameSet 부분에서는 다시 두 구역으로 나누고 있음 -->
  <frameset rows="*" cols="160,*" framespacing="0" frameborder="NO" border="0">
    <frame src="/layout/left.jsp" name="leftFrame" scrolling="NO" noresize>
    <frame src="" name="rightFrame"  scrolling="auto">
  </frameset>

</frameset>

<noframes>
<body>
</body>
</noframes>

</html>