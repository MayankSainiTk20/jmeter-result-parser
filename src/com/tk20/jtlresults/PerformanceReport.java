package com.tk20.jtlresults;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


public class PerformanceReport {
	Map<String, ArrayList<Integer>> response=new  LinkedHashMap<String, ArrayList<Integer>>();
	Map<String, ArrayList<String>> totalcodes=new  LinkedHashMap<String, ArrayList<String>>();
	TimeSeries successGraph = new TimeSeries("Success", Minute.class);
	 TimeSeries failureGraph = new TimeSeries("Failed", Minute.class);
	 DateFormat format=new SimpleDateFormat("HH:mm:ss");
	List<Integer> values=new ArrayList<Integer>();
	 private long totalDuration = 0;
	 private long max = Long.MIN_VALUE;
	 private String success =null;
	 private int nbError = 0;
	 private int nbSuccess = 0;
	  private transient List<Long> durationsSortedBySize = null;
	 private long size;
	 private long min = Long.MAX_VALUE;
	 private final Map<String, LabelReport> labelReportMap = new LinkedHashMap<String, LabelReport>();
	 public void addSample(HttpSample pHttpSample,String success){
		 String name=pHttpSample.getName();
		 
		 this.success=success;
		 String Uri=pHttpSample.getUri();
		 LabelReport report=labelReportMap.get(name);
		 if(report==null){
			 report=new LabelReport(Uri,name);
			 labelReportMap.put(name, report);
		 }
		
		 if(this.success!=null){
			 if(pHttpSample.isSuccessful())
			 {
			 report.addHttpSample(pHttpSample);
			 size++;
		     totalDuration += pHttpSample.getDuration();
		    
		    max = Math.max(pHttpSample.getDuration(), max);
		    min = Math.min(pHttpSample.getDuration(), min);
		 }
			 
		 }
		 else
		 {
			 report.addHttpSample(pHttpSample);
			 if (!pHttpSample.isSuccessful()) {
			      nbError++;
			    }
			 size++;
		     totalDuration += pHttpSample.getDuration();
		    
		    max = Math.max(pHttpSample.getDuration(), max);
		    min = Math.min(pHttpSample.getDuration(), min);
		   
		 }
		 
		 ArrayList<String> l1=totalcodes.get(pHttpSample.getHttpCode());
		    if(l1==null)
		    {
		    	l1=new ArrayList<String>();
		    	totalcodes.put(pHttpSample.getHttpCode(), l1);
		    }
		    l1.add(pHttpSample.getHttpCode()); 
		    String time= format.format(pHttpSample.getDate());
		    if (!pHttpSample.isSuccessful()) {
			      nbError++;
			      failureGraph.addOrUpdate(new Minute(Integer.parseInt(time.split(":")[1]), Integer.parseInt(time.split(":")[0]), 1, 1,2001), nbError);
			    }
		    if (pHttpSample.isSuccessful()) {
			      nbSuccess++;
			      successGraph.addOrUpdate(new Minute(Integer.parseInt(time.split(":")[1]), Integer.parseInt(time.split(":")[0]), 1, 1,2001), nbSuccess);
			    }
	 }
	 public long getAverage() {
		    if (size == 0) {
		      return 0;
		    }
		    
		    return totalDuration / size;
		  }
	 public int countErrors() {
		    return nbError;
		  }
	 public long getMax() {
		    return max;
		  }
	 public long getMin() {
		    return min;
		  }
	 public long getTotal() {
		    return size;
		  }
	 public Map<String, ArrayList<String>>  getTotalCodes() {
		    return totalcodes;
		}
	  private long getDurationAt(double percentage) {
		    if (percentage < 0 || percentage > 1) {
		      throw new IllegalArgumentException("Argument 'percentage' must be a value between 0 and 1 (inclusive)");
		    }

		    if (size == 0) {
		      return 0;
		    }
		    
		    synchronized (labelReportMap) {
		      if (durationsSortedBySize == null) {
		        durationsSortedBySize = new ArrayList<Long>();
		        for (LabelReport currentReport : labelReportMap.values()) {
		          durationsSortedBySize.addAll(currentReport.getDurations());
		        }
		        Collections.sort(durationsSortedBySize);
		      }
		      return durationsSortedBySize.get((int) (durationsSortedBySize.size() * percentage));
		    }
		  }
	  public long get90Line() {
		    return getDurationAt(.9);
		  }

		  public long getMedian() {
		    return getDurationAt(.5);
		  }
		  public long get95line() {
			    return getDurationAt(.95);
			  }
		  public long get99line() {
			    return getDurationAt(.99);
			  }
		  public double errorPercent() {
		  
		      return getTotal() == 0 ? 0 : ((double) countErrors()) / getTotal() * 100;
		    
		  }
		  public Map<String, LabelReport> getLabelReport(){
			return labelReportMap;
			  
		  }
		  public XYDataset  doTransactionTrendGraph() throws IOException {    
			   
			    TimeSeriesCollection resp = new TimeSeriesCollection();
			    resp.setDomainIsPointsInTime(true);
			    resp.addSeries(successGraph);
			    resp.addSeries(failureGraph);
			    List<XYDataset> dataset = new ArrayList<XYDataset>();
			    dataset.add(resp);

			   return resp;
			  }
	 
}
