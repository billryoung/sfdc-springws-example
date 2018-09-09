package com.billryoung.sfdc_springws_example.query;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents the query result.
 * You'd have to customize the record type the query will return (or explore making it a template class).
 * 
 * @author billryoung
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountQueryRecords {

	private boolean done;
	
	private int totalSize;
	private String nextRecordsUrl;
	
	public String getNextRecordsUrl() {
		return nextRecordsUrl;
	}
	public void setNextRecordsUrl(String nextRecordsUrl) {
		this.nextRecordsUrl = nextRecordsUrl;
	}
	private List<AccountQueryRecord> records;
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public List<AccountQueryRecord> getRecords() {
		return records;
	}
	public void setRecords(List<AccountQueryRecord> records) {
		this.records = records;
	}
	@Override
	public String toString() {
		return "QueryRecords [done=" + done + ", totalSize=" + totalSize + ", nextRecordsUrl=" + nextRecordsUrl
				+ ", records=" + records + "]";
	}
	
	
}
