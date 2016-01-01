<!DOCTYPE html>


<%@page import="com.tk20.jtlresults.LabelReport"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="com.tk20.jtlresults.PerformanceReport"%>
<%@page import="org.jfree.chart.ChartUtilities"%>
<%@page import="org.jfree.chart.ChartRenderingInfo"%>
<%@page import="org.jfree.chart.entity.StandardEntityCollection"%>
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
<h1 align='center'>Load Test Results</h1>
<%
try{
	
	PerformanceReport report=(PerformanceReport)request.getSession().getAttribute("reportref");
	final JFreeChart transchart=(JFreeChart)request.getSession().getAttribute("transchartref");
	final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	String filename= (String)request.getSession().getAttribute("filename");
	String chart=filename+"transchart.png";
	synchronized(this){
		
	final File file1 = new File(getServletContext().getRealPath(".") + "/"+chart);

	ChartUtilities.saveChartAsPNG(file1, transchart, 600, 400, info);
	}
	Map<String,LabelReport> labelreport=report.getLabelReport();
	Map<String,ArrayList<String>> codes=report.getTotalCodes();
	Iterator<Entry<String, ArrayList<String>>> codesiterate = codes.entrySet().iterator();
	Iterator<Entry<String, LabelReport>> it = labelreport.entrySet().iterator();
%>
<div class="topleftcorner">
 <img src="<%= chart %>" style="border:3px solid black;" alt="image">
</div>

<br/>
<div class="topcorner">

<table style="width:100%">

<tr>
<th style="border:1px solid black; background-color: #31BBA5;">HTTP Code Value</th><th style="border:1px solid black; background-color: #31BBA5;">No of Samples</th>
</tr>


<% 	
	while (codesiterate.hasNext()) {
        Map.Entry<String, ArrayList<String>> codespair = (Map.Entry<String, ArrayList<String>>)codesiterate.next();
      ArrayList<String> values=codes.get(codespair.getKey());
    if(values.size()>0&&!(codespair.getKey().trim().equals(""))){  
%>    	
  <tr>
      <td style="border:1px solid black;" width="80%"><%= codespair.getKey() %>  </td>
      <td style="border:1px solid black;" width="20%"><%= values.size() %>  </td>
   </tr>   
<%    }}
%>  

</table>
<html:form action="/DownloadResults">
<input id="generatereport" class="styled-button-1" type="submit" value="Download Report in Excel">
</html:form>
</div>
<br/>
<table id="report" style="width:100%">

	<thead>
	<tr>
    	<th>Label</th>
    	<th>Total Samples</th>
    	<th>Average</th> 
    	<th>Median</th>
    	<th>90% Line</th>
    	<th>95% Line</th>
    	<th>99% Line</th>
    	<th>Maximum</th>
    	<th>Minimum</th>
    	
    	</tr>
	</thead>
	<tbody>

<% 	
	while (it.hasNext()) {
        Map.Entry<String, LabelReport> pair = (Map.Entry<String, LabelReport>)it.next();
      LabelReport label=labelreport.get(pair.getKey());
    if(label.size()>0&&pair.getKey()!=""){  
     
%>    
      <tr>
      <%
      String url=response.encodeURL("/detailed_report.do?label="+label.getLabel());
      %>
	<td class="user-name"><html:link action="<%=url%>"><%= label.getLabel() %></html:link> </td> 
      <td class="user-name"><%= label.size() %>  </td>
      <td class="user-name"><%= label.getAverage() %>  </td>
      <td class="user-name"><%= label.getMedian() %>  </td>
      <td class="user-name"><%=label.get90Line() %>  </td>
      <td class="user-name"><%= label.get95line() %>  </td>
      <td class="user-name"><%= label.get99line() %>  </td>
      <td class="user-name"><%= label.getMax() %>  </td>
      <td class="user-name"><%= label.getMin() %>  </td>
      </tr>
    
<%      
	}
    }

%>
<tr>
      <td class="user-name">Total</td>
      <td class="user-name"><%= report.getTotal() %>  </td>
      <td class="user-name"><%= report.getAverage() %>  </td>
       <td class="user-name"><%= report.getMedian() %>  </td>
      <td class="user-name"><%= report.get90Line() %>  </td>
      <td class="user-name"><%= report.get95line() %>  </td>
       <td class="user-name"><%= report.get99line() %>  </td>
       <td class="user-name"><%= report.getMax() %>  </td>
      <td class="user-name"><%= report.getMin() %>  </td>
      </tr>
     


<%	

}
catch(Exception ex){
	ex.printStackTrace();
	out.print(ex.getMessage());
}

%>
 </tbody>
</table>




	
</body></html>