package com.billryoung.sfdc_springws_example.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Account row from my query.
 * You'd have to customize the fields the query will return.
 * And here, you may need to annotate the get/set methods with @JsonProperty as the fields are case senstive.
 * 
 * @author billryoung
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountQueryRecord {
	
	private QueryAttributes attributes;
	private String id;
	private String name;

	//we need to use JsonProperty b/c of case differences
	//getId/setId is treated as property "id", but we get back property "Id"
	//you could also use this if you want to have a better Java name than the default api name
	@JsonProperty("Id")
	public String getId() {
		return id;
	}
	
	@JsonProperty("Id")
	public void setId(String id) {
		this.id = id;
	}
	
	//must use to get property "Name" instead of property "name" (upper/lower case)
	@JsonProperty("Name")
	public String getName() {
		return name;
	}
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
	
	public QueryAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(QueryAttributes attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		return "QueryRecord [attributes=" + attributes + ", id=" + id + ", name=" + name + "]";
	}
	
}
