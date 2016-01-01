package com.tk20.jtlresults;

import java.io.Serializable;
import java.util.Date;



public class Sample implements Serializable, Comparable<Sample> {
    
    private static final long serialVersionUID = 4458431861223813407L;
    
    public final Date date;
    public final long duration;
    public final boolean successful;
    
    public Sample(Date date, long duration,boolean successful) {
      this.date = date;
      this.duration = duration;
      this.successful=successful;
    }

    /** Compare first based on duration, next on date. */
    public int compareTo(Sample other) {
      if (this == other) return 0;
      if (this.duration < other.duration) return -1; 
      if (this.duration > other.duration) return 1;
      if (this.date == null || other.date == null) return 0;
      if (this.date.before(other.date)) return -1;
      if (this.date.after(other.date)) return 1;
      return 0;
    }}