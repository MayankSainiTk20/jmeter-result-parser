package com.tk20.jtlresults;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HttpSample implements Serializable, Comparable<HttpSample> {

  private static final long serialVersionUID = -3531990216789038711L;
  DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
  private long duration;

	private boolean successful;

	private boolean errorObtained;

	private Date date;

	private String name;

	private String uri;

	private String httpCode = "";

	private double sizeInKb;

	// Summarizer fields
	private long summarizerMin;

	private long summarizerMax;

	private float summarizerErrors;

	private long summarizerSamples;

	public long getDuration() {
		return duration;
	}

	public Date getDate() {
		return date;
	}
	

	public String getName() {
		return name;
	}
	public String getUri() {
		return uri;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public long getSummarizerSamples() {
		return summarizerSamples;
	}

	public long getSummarizerMin() {
		return summarizerMin;
	}

	public long getSummarizerMax() {
		return summarizerMax;
	}

	public float getSummarizerErrors() {
		return summarizerErrors;
	}

	public boolean isFailed() {
		return !isSuccessful();
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public void setErrorObtained(boolean errorObtained) {
		this.errorObtained = errorObtained;
	}

	public boolean hasError() {
		return errorObtained;
	}

	public void setDate(Date time) {
		this.date = time;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	public void setSummarizerSamples(long summarizerSamples) {
		this.summarizerSamples = summarizerSamples;
	}

	public void setSummarizerMin(long summarizerMin) {
		this.summarizerMin = summarizerMin;
	}

	public void setSummarizerMax(long summarizerMax) {
		this.summarizerMax = summarizerMax;
	}

	public void setSummarizerErrors(float summarizerErrors) {
		this.summarizerErrors = summarizerErrors;
	}

	public int compareTo(HttpSample o) {
		return (int) (getDuration() - o.getDuration());
	}

	public double getSizeInKb() {
		return sizeInKb;
	}

	public void setSizeInKb(double d) {
		this.sizeInKb = d;
	}

	public boolean isErrorObtained() {
		return errorObtained;
	}
}
