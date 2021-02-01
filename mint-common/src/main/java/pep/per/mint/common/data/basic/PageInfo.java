package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageInfo extends CacheableObject{

	/*
    TOTAL_CNT
   ,100                  as PER_CNT
   ,trunc(TOTAL_CNT/100) as PAGE_CNT
   ,mod(TOTAL_CNT,100)   as TAIL_CNT
   ,1                    as PAGE
   ,IDX
	*/
	@JsonProperty
	int totalCount = 0;

	@JsonProperty
	int perCount = 100;

	int pageCount = 0;

	@JsonProperty
	int tailCount = 0;

	@JsonProperty
	int page = 1;

	@JsonProperty
	int index = 1;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPerCount() {
		return perCount;
	}

	public void setPerCount(int perCount) {
		this.perCount = perCount;
	}

	public int getTailCount() {
		return tailCount;
	}

	public void setTailCount(int tailCount) {
		this.tailCount = tailCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


}
