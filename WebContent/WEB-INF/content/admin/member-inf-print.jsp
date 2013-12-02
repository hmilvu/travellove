<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>旅游关爱-打印二维码</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />  
</head>
<body>
<table border="1" width="100%" height="100%">
	<%
		int row = Integer.valueOf((String)request.getAttribute("row"));
		int column = Integer.valueOf((String)request.getAttribute("column"));
		List<Object []>memberList = (List<Object []>)request.getAttribute("memberList");
		for(int i = 0; i < row; i++){
	%>
	<tr>
		<%
			for(int j = 0; j < 3; j++){
				if(i * 3 + j < memberList.size()){
					Object[] member = (Object[])memberList.get(i * 3 + j);
					Long memberId = (Long)member[0];
					String memberName = (String)member[1];					
		 %>
		<td>
			<table width="300" height="250">
				<tr height="200" align="center"">
					<td>
						<img src="../memberQrCode.action?memberId=<%=memberId %>" alt="二维码" width="200" height="200"/>
					</td>					
				</tr>
				<tr align="center"">
					<td><%=memberName %>
					</td>					
				</tr>
			</table>
		</td>
		<%
				} else{
		%>
		<td>
			<table width="300" height="250">
				<tr height="200">
					<td>&nbsp;
					</td>					
				</tr>
				<tr>
					<td>&nbsp;
					</td>					
				</tr>
			</table>
		</td>
		<%
				}//else
			}// for j
		 %>
	</tr>
	<%
		}// for i
	 %>
</table>
</body>
</html>