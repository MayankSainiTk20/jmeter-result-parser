<!DOCTYPE html>

<%@page import="com.tk20.jtlresults.HttpSample"%>
<%@page import="com.tk20.jtlresults.Sample"%>
<%@page import="com.tk20.jtlresults.LabelReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.tk20.jtlresults.PerformanceReport"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="java.io.*"%>
<%@page import="org.jfree.chart.ChartUtilities"%>
<%@page import="org.jfree.chart.ChartRenderingInfo"%>
<%@page import="org.jfree.chart.entity.StandardEntityCollection"%>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
<head><title>Result Page</title>

			<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Sticky Table Headers Revisited | Demo 1</title>
		<meta name="description" content="Sticky Table Headers Revisited: Creating functional and flexible sticky table headers" />
		<meta name="keywords" content="Sticky Table Headers Revisited" />
		<meta name="author" content="Codrops" />
		
		<link rel="stylesheet" type="text/css" href="css/dataTables.jqueryui.css" />
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		
		   <style type="text/css">

.paging-nav {
  text-align: right;
  padding-top: 2px;
}

.paging-nav a {
  margin: auto 1px;
  text-decoration: none;
  display: inline-block;
  padding: 1px 7px;
  background: #91b9e6;
  color: white;
  border-radius: 3px;
}

.paging-nav .selected-page {
  background: #187ed5;
  font-weight: bold;
}

.paging-nav,
#tableData {
  width: 400px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}
</style>
</head>





<body>
<h1 align='center'>Load Test Results</h1>

<div class="topleftcorner">
<%
try{
final JFreeChart timechart=(JFreeChart)request.getSession().getAttribute("chartref");
final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
String filename=null;
synchronized(this){
	filename=System.currentTimeMillis()+"timeseries.png";
final File file1 = new File(getServletContext().getRealPath(".") + "/"+filename);

ChartUtilities.saveChartAsPNG(file1, timechart, 600, 400, info);
}

%>
 <img src="<%= filename %>" style="border:3px solid black;" alt="image">
</div>

<div class="topcorner">
<table id="myreport" style="width:100%">

<tr>
<th style="border:1px solid black; background-color: #31BBA5;">HTTP Code Value</th><th style="border:1px solid black; background-color: #31BBA5;">No of Samples</th>
</tr>
<%


final LabelReport report=(LabelReport)request.getSession().getAttribute("labelref");
DateFormat df = new SimpleDateFormat("HH:mm:ss");
List<Sample> samplesView=report.getSamples();

Map<String,ArrayList<String>> codes=report.getCodes();
Iterator<Entry<String, ArrayList<String>>> codesiterate = codes.entrySet().iterator();
while (codesiterate.hasNext()) {
    Map.Entry<String, ArrayList<String>> codespair = (Map.Entry<String, ArrayList<String>>)codesiterate.next();
  ArrayList<String> values=codes.get(codespair.getKey());
if(values.size()>0&&!(codespair.getKey().trim().equals(""))){  
	%>    	
	  <tr>
	      <td style="border:1px solid black;" width="80%"><%= codespair.getKey() %>  </td>
	      <td style="border:1px solid black;" width="20%"><%= values.size() %>  </td>
	  </tr>   
	<%    }}%>  

	</table>

	</div>
	
	
	<table id="report" style="width:100%">

	<thead>
	<tr>
		<th>Start Time</th> 
    	<th>Duration Of Sample</th>
    	<th>Success Status</th>
	</tr>

	</thead>
	<tbody>
	<%
	for(int i=0;i<samplesView.size();i++){
	Sample sample=samplesView.get(i);
	String time=df.format(sample.date);
	%>
	<tr>
		<td class="user-name"><%= time%>  </td>
      <td class="user-name"><%=sample.duration %>  </td>
      
       <td class="user-name"><%=sample.successful %>  </td>
	</tr>
	
	<%}
}
catch(Exception ex){
ex.printStackTrace();	
}

	%>
	</tbody>
	</table>


			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
				<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
		<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-throttle-debounce/1.1/jquery.ba-throttle-debounce.min.js"></script>
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.dataTables.js" type="text/javascript"></script>
 <script type="text/javascript">
        $(document).ready(function () {
            $("#report").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true               
            });
        });
 </script>



</body></html>