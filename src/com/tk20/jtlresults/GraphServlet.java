package com.tk20.jtlresults;




import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

/** Simple servlet for testing. Generates HTML instead of plain
 *  text as with the HelloWorld servlet.
 */

@WebServlet("/detailed1")
public class GraphServlet extends Action {
	
	 /**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OutputStream outstream=null;
	

    public ActionForward execute( ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException{
    	try{
  	      String labelName=request.getParameter("label");
  	      
  	      PerformanceReport report=(PerformanceReport)request.getSession().getAttribute("reportref");
  	      Map<String,LabelReport> labelreport=report.getLabelReport();
  	      LabelReport label=labelreport.get(labelName);
  	      final XYDataset dataset= label.doSummarizerTrendGraph(labelName);
  	      
  	      final JFreeChart timechart  = ChartFactory.createTimeSeriesChart("Response Time Graph Of "+labelName,"Elapsed Time","Response Time",dataset,true,true,false);
  	    
  	      
  	      request.getSession().setAttribute("chartref", timechart);
  	      request.getSession().setAttribute("labelref", label);
  	      Thread.sleep(5000);
  	      
  	 }
  		catch(Exception ex){
  			System.out.println(ex.getLocalizedMessage());
  			return map.findForward("error");
  			
  		}
    	return map.findForward("detailed_report");
    }

	
}
