package com.tk20.jtlresults;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadFile extends Action {
	 public ActionForward execute( ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException{
		 String filename=(String)request.getSession().getAttribute("filename");
		 ByteArrayOutputStream byos=new ByteArrayOutputStream();
		 OutputStream outStream = null;
		 XSSFWorkbook wb=new XSSFWorkbook();
		 try{
		 
		 String requesturl="http://localhost:8080/Open_Source_JMeter_Parser/main_report.do";
		 URL url=new URL(requesturl);
		 
		 @SuppressWarnings("resource")
		
		 XSSFSheet sheet=wb.createSheet("Aggregate Results");
		 Document doc=Jsoup.parse(url, 300000);
		 int rowCount=0;
		 Elements table=doc.select("table[id=report]");
		 
		 for(Element row:table.select("tr")){
			 Row sheetrow=sheet.createRow(++rowCount);
			 Elements ths=row.select("th");
			 int columnCount=0;
			 for(Element th:ths){
				 Cell cell=sheetrow.createCell(++columnCount);
				 cell.setCellValue(th.text());
			 }
			 Elements tds=row.select("td[class=user-name]");
			 columnCount=0;
			 for(Element td:tds){
				 Cell cell=sheetrow.createCell(++columnCount);
				 
				 cell.setCellValue(td.text());
			 }
			 
		 }
		 wb.write(byos);
		 byte[] outArray=byos.toByteArray();
		 outStream=response.getOutputStream();
		 response.setContentType("application/ms-excel");
		 response.setContentLength(outArray.length);
		 response.setHeader("Expires:", "0"); // eliminates browser caching
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");
		 
		 outStream.write(outArray);
		 outStream.flush();
		 outStream.close();
		 }
		 catch(Exception ex){
			 ex.printStackTrace();
			 return map.findForward("error");
			 
			 
		 }
		 finally{
			 byos.close();
			 wb.close();
		 }
		 return null;
	 }
}
