

<script>
function redirecttostart()
{
<% 
	String url = request.getRequestURL().toString();
	String fileName=request.getParameter("file");
	request.getSession().setAttribute("filename", fileName);
	int index = url.lastIndexOf("/");
	url = url.substring(0,index) + "/start.do";	
	%>
	window.location.href = "<%=url%>";
}


</script>
<body onload="javascript:redirecttostart()">
