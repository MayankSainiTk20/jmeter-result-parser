<!DOCTYPE html>

    <%@ taglib uri="aaa" prefix="html" %>
    
    
<html>
<head><title>Result Page</title>

		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name="description" content="Sticky Table Headers Revisited: Creating functional and flexible sticky table headers" />
		<meta name="keywords" content="Sticky Table Headers Revisited" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="./css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="./css/demo.css" />
		<link rel="stylesheet" type="text/css" href="./css/component.css" />
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-throttle-debounce/1.1/jquery.ba-throttle-debounce.min.js"></script>
		<script src="./js/jquery.stickyheader.js"></script>
		<script src="./js/jquery.blockUI.js"></script>
		<script src="./js/script.js"></script>

</head>



<body>
<h1 align='center'>Result Of Load Test</h1>
<%
String fileName=(String)request.getSession().getAttribute("filename");
%>

<p align='center'>
<%
if(!(fileName==null))
{
	
%>	
JTL File name For the Load Test is <%=fileName%>

<%
}

else{
%>	
	<font color='red'>Please Upload the File</font>
<%}
%>


</p>
<div align='center'>
<html:form action="/main_report" enctype="multipart/form-data"  >
<tr><td>File Name: &nbsp; </td>
<td><html:file property="uploadfile"  /></td>
 </tr>
<br/>
<br/>
<html:checkbox property="successfull">Successes</html:checkbox> &nbsp; &nbsp;
<input id="submitbutton" class="styled-button-1" type="submit" value="Generate Report">
</html:form>



</div>		
</body></html>