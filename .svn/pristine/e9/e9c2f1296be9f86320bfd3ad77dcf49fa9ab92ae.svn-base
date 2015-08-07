package com.common.mybatis.page;

/**
 * 分页参数类
 * 
 */
public class PageParameter {

	public static final int DEFAULT_PAGE_SIZE = 10;
	/** 页大小 */
	private int pageSize;
	/** 当前页 */
	private int currentPage;
	/** 当前位置 */
	private int position;
	/** 后一页 */
	private int prePage;
	/** 前一页 */
	private int nextPage;
	/** 总页数 */
	private int totalPage;
	/** 总大小 */
	private int totalCount;

	public PageParameter() {
		this.currentPage = 1;
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.nextPage = currentPage + 1;
		this.prePage = currentPage - 1;
	}

	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public PageParameter(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.nextPage = currentPage + 1;
		this.prePage = currentPage - 1;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.nextPage = currentPage + 1;
		this.prePage = currentPage - 1;
	}

	public int getPosition() {
		position = (this.currentPage - 1) * this.pageSize;
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
