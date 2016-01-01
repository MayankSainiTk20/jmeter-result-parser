package com.tk20.jtlresults;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

/** Simple servlet for testing. Generates HTML instead of plain
 *  text as with the HelloWorld servlet.
 */

public class TestServlet extends Action {
	private String delimeter=",";
	private int timestampIdx = -1;
	private int elapsedIdx = -1;
	private int responseCodeIdx = -1;
	private int successIdx = -1;
	private int label = -1;
	private int uriIdx=-1;

    public ActionForward execute( ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException{
    	 
	      FileUploadForm obj=(FileUploadForm) form;
	      String success=obj.getSuccessfull();
	      String fileName=null;
	      FormFile dataFile=null;
	      try{
	       dataFile = obj.getUploadfile();
	       fileName = dataFile.getFileName();
	      }
	      catch(Exception ex)
	      {
	    	  return map.findForward("error");
	      }
	      if(request.getSession().getAttribute("filename")==null)
	      {
	    	  request.getSession().setAttribute("filename", fileName);
	      }
	      else
	      {
	       fileName=(String) request.getSession().getAttribute("filename");
	      }
	      storeFile(dataFile,fileName);
	       PerformanceReport report = new PerformanceReport();
	       BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+ fileName));
	         try{
	        	 readHeader(reader);
	        	 String line = reader.readLine();
	    		 while (line != null) {
	    			 final HttpSample sample = getSample(line);
	    			 if (sample != null){
	    				report.addSample(sample,success); 
	    				
	    			 }
	    			 line = reader.readLine();
	    		 }
	         } catch (ParseException e) {
				
				e.printStackTrace();
			}
	         finally{
	        	 if (reader != null) {
	     	        reader.close();
	     	      }
	        	
	         }
	         
        	 final XYDataset dataset= report.doTransactionTrendGraph();
   	      final JFreeChart transactionChart  = ChartFactory.createTimeSeriesChart("Success Vs Failure","Elapsed Time","Value",dataset,true,true,false);
   	     
   	      
   	      		 request.getSession().setAttribute("transchartref", transactionChart);
        	 	 request.getSession().setAttribute("reportref", report);
        	 	return map.findForward("success");
	       
	         
        }
	
	
	
	private void storeFile(FormFile dataFile,String fileName) throws FileNotFoundException, IOException {
		int  fileSize = dataFile.getFileSize();
        byte[] fileData = dataFile.getFileData();
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"/"+ fileName);
        fileOut.write(fileData, 0, fileSize);
        fileOut.flush();
         fileOut.close();
		
	}



	public void readHeader(BufferedReader reader) throws IOException{
		String line=reader.readLine();
		String[] fields=line.split(delimeter);
		
		for(int i=0;i<fields.length;i++){
			String field=fields[i];
			 if ("timeStamp".equals(field)) {
			        timestampIdx = i;
			      } else if ("elapsed".equals(field)) {
			        elapsedIdx = i;
			      } else if ("responseCode".equals(field)) {
			        responseCodeIdx = i;
			      } else if ("success".equals(field)) {
			        successIdx = i;
			      } else if ("label".equals(field)) {
			        label = i;
			      }
			      else if ("URL".equals(field)) {
			    	  uriIdx = i;
				      }
		}
		
		
	}
	private  HttpSample getSample(String line) throws ParseException {

		  final HttpSample sample = new HttpSample();
			try{
		  final String commasNotInsideQuotes = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		    final String[] values = line.split(commasNotInsideQuotes);
		    DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		    Date date = format.parse(values[timestampIdx]);
		    sample.setDate(date);
		    sample.setDuration(Long.valueOf(values[elapsedIdx]));
		    sample.setHttpCode(values[responseCodeIdx]);
		    sample.setSuccessful(Boolean.valueOf(values[successIdx]));
		    sample.setName(values[label]);
		    sample.setUri(values[uriIdx]);
		    return sample;
	}
			catch(ArrayIndexOutOfBoundsException ex){
				return null;
			}
			
		
	
	}
}
