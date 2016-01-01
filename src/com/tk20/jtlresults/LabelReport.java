package com.tk20.jtlresults;



import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;




public class LabelReport implements Serializable,Comparable<LabelReport>{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String label;
private String Uri;
private int nbError = 0;
private float summarizerErrors = 0;
private long totalDuration = 0;
private long summarizerSize = 0;
private transient boolean isSorted = false;
private final List<Sample> samples = new ArrayList<Sample>();

private transient List<Long> durationsIO = new ArrayList<Long>();
private transient List<Long> durationsSortedBySize = new ArrayList<Long>();
private Date start = null;
SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
private Date end = null;
Map<String, ArrayList<String>> codes=new  LinkedHashMap<String, ArrayList<String>>();
public LabelReport(String Uri, String label){
	this.label=label;
	this.Uri=Uri;
	
}
public void addHttpSample(HttpSample sample) {
	
	 if (!sample.isSuccessful()) {
	      nbError++;
	    }
	 synchronized (samples) {
	      if (samples.add(new Sample(sample.getDate(), sample.getDuration(),sample.isSuccessful()))) {
	        isSorted = false;
	      } 
	    }
	
	 
	 totalDuration += sample.getDuration();
	 summarizerSize += sample.getSummarizerSamples();
	    summarizerErrors += sample.getSummarizerErrors();
	    synchronized (samples) {
	    	  ArrayList<String> l1=codes.get(sample.getHttpCode());
	  	    if(l1==null)
	  	    {
	  	    	l1=new ArrayList<String>();
	  	    	codes.put(sample.getHttpCode(), l1);
	  	    }
	  	    l1.add(sample.getHttpCode());
		}		
	  	    if (start == null || sample.getDate().before( start )) {
	  	        start = sample.getDate();
	  	      }
	
	  
	      Date finish = new Date(sample.getDate().getTime() + sample.getDuration());
	      if (end == null || finish.after(end)) {
	          end = finish;
	      }
}
public String getUri() {
    return Uri;
  }
public Map<String, ArrayList<String>>  getCodes() {
    return codes;
}
public String getLabel() {
    return label;
  }
public int countErrors() {
    return nbError;
  }
public List<Sample> getSamples() {
    return samples;
  }
public float getSummarizerErrors() {    
    return summarizerErrors/summarizerSize*100;     
  }
public int size() {
    synchronized (samples) {
      return samples.size();
    }
}
public long getAverage() {
    return totalDuration / size();
  }
public double errorPercent() {
    return ((double) countErrors()) / size() * 100;
  }
public XYDataset  doSummarizerTrendGraph(String label) throws IOException {    
    TimeSeries responseTimes = new TimeSeries(label, FixedMillisecond.class);
    synchronized (samples) {
      for (Sample sample : samples) {
    	 
        responseTimes.addOrUpdate(new FixedMillisecond(sample.date), sample.duration);
      }
    }

    TimeSeriesCollection resp = new TimeSeriesCollection();
    resp.setDomainIsPointsInTime(true);
    resp.addSeries(responseTimes);
  
    List<XYDataset> dataset = new ArrayList<XYDataset>();
    dataset.add(resp);

   return resp;
  }
protected List<Long> getSortedDuration() {
    synchronized (samples) {
      if (!isSorted || durationsSortedBySize == null || durationsSortedBySize.size() != samples.size()) {
        
        durationsSortedBySize = new ArrayList<Long>(samples.size());
        for (Sample sample : samples) {
          durationsSortedBySize.add(sample.duration);
        }
        Collections.sort(durationsSortedBySize);
        isSorted = true;
      }

      return durationsSortedBySize;
    }
  }
public List<Long> getDurations() {
    synchronized (samples) {
      if (durationsIO == null || durationsIO.size() != samples.size()) {        
        durationsIO = new ArrayList<Long>(samples.size());
        for (Sample sample : samples) {
          durationsIO.add(sample.duration);
        }
      }
      return durationsIO;
    }
  }
private long getDurationAt(double percentage) {
    if (percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("Argument 'percentage' must be a value between 0 and 1 (inclusive)");
    }
    
    synchronized (samples) {
      final List<Long> durations = getSortedDuration();

      if (durations.isEmpty()) {
        return 0;
      }
      
      return durations.get((int) (samples.size() * percentage));
    }    
  }
public long get90Line() {
    return getDurationAt(0.9);
  }
public Date getStart() {
    return start;
  }
  
  public Date getEnd() {
    return end;
  }
public long getMedian() {
    return getDurationAt(0.5);
  }
public long get95line() {
    return getDurationAt(.95);
  }
public long get99line() {
    return getDurationAt(.99);
  }
  public long getMax() {
	    final List<Long> durations = getSortedDuration();
	    if (durations.isEmpty()) {
	      return 0;
	    }
	    return durations.get(durations.size()-1);
	  }

	  public long getMin() {
	    final List<Long> durations = getSortedDuration();
	    if (durations.isEmpty()) {
	      return 0;
	    }
	    return durations.get(0);
	  }


public int compareTo(LabelReport report) {
	 if (report == this) {
	      return 0;
	    }
	    return report.getLabel().compareTo(this.getLabel());
}
 

}
