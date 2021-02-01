package pep.per.mint.common.data.basic.upload;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelData extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -5032460747602075050L;

	@JsonProperty
	int sheetIndex = 0;

	@JsonProperty
	String sheetName = defaultStringValue;

	@JsonProperty
	String[] header = null;

	@JsonProperty
	List<String[]> data = new LinkedList<String[]>();

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public List<String[]> getData() {
		return data;
	}

	public void setData(String[] row) {
		this.data.add(row);
	}




}
