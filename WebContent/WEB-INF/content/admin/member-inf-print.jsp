<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>旅游关爱-打印二维码</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />  
</head>
<body>
<table border="0" width="200" height="240">
<s:iterator var="member" value="%{#request.memberList}" status="statu">	
	<tr height="175" align="center">
		<td>
			<img src="../memberQrCode.action?memberId=<s:property value='%{#member[0]}'/>" alt="二维码" width="160" height="160"/>
		</td>					
	</tr>
	<tr height="65" align="center" valign="top">
		<td><s:property value='%{#member[1]}'/>
		</td>					
	</tr>
</s:iterator>
</table>
</body>
</html>