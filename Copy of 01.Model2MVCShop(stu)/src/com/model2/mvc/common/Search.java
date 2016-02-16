package com.model2.mvc.common;


public class Search {
	
	private int curruntPage;
	private String searchCondition;
	private String searchKeyword;
	private int pageSize;
	
	public Search(){
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageUnit) {
		this.pageSize = pageUnit;
	}
	
	public int getCurrentPage() {
		return curruntPage;
	}
	public void setCurrentPage(int page) {
		this.curruntPage = page;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
}