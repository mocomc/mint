package pep.per.mint.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class FieldPath implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9218769635211701619L;

	public static final String DELIMITERS = "/.:,";
	
	public static final String DEFAULT_DELIMITER = ".";

	public static final FieldPath ROOT = new FieldPath();
	
	@JsonProperty
	String delimiter = ".";
	
	@JsonProperty
	List<String> pathList = new ArrayList<String>();
	
	@JsonProperty
	StringBuffer pathBuffer = new StringBuffer();
	
	public FieldPath() {
		super();
	}
	
	public FieldPath(String path) {
		this(path,DEFAULT_DELIMITER);
	}
	
	public FieldPath(String path, String delim) {
		
		this.delimiter = delim;
		this.pathBuffer.append(path);
		List<String> splitPathList = Arrays.asList(Util.split(path, delim));
		pathList.addAll(splitPathList);
		
	}
	
	
	public List<String> getPathList(){
		return pathList;
	}
	
	public FieldPath addPath(String path){
		if(pathList.size() > 0) pathBuffer.append(delimiter).append(path);
		else pathBuffer.append(path);
		
		//pathList.add(path);
		List<String> splitPathList = Arrays.asList(Util.split(path, delimiter));
		pathList.addAll(splitPathList);
		return this;
	}
	
	public FieldPath(List<String> pathList) {
		this.pathList = pathList;
		 
		int i = 0;
		for(  ; i < pathList.size() - 1 ; i ++){
			pathBuffer.append(pathList.get(i)).append(delimiter);
		}
		pathBuffer.append(pathList.get(i));
	}
	
	
	@Override
	public String toString() {
		if(pathBuffer.length() == 0 &&  pathList.size() > 0){
			int i = 0;
			for(  ; i < pathList.size() - 1 ; i ++){
				pathBuffer.append(pathList.get(i)).append(delimiter);
			}
			pathBuffer.append(pathList.get(i));
		}
		return pathBuffer.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new FieldPath("a.b.c.e").addPath("f"));
	}

	/**
	 * @return
	 */
	public int size() {
		return pathList.size();
	}
	
}
