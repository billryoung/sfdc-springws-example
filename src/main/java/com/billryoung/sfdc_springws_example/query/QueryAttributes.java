package com.billryoung.sfdc_springws_example.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents the attributes on each record returned in the query.
 * This is generic for any query.
 * 
 * @author billryoung
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryAttributes {
	private String type;
	private String url;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "QueryAttributes [type=" + type + ", url=" + url + "]";
	}
	
}
