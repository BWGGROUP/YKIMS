package cn.com.sims.common.commonUtil;

/**
 * 
 * @author duwenjie
 *
 */
public class Page {
	private int pageSize=Integer.parseInt(CommonUtil.findNoteTxtOfXML("PAGESIZE"));//一页显示条数
	private int pageCount=1;//当前页数
	private int totalPage;//总页数
	private int totalCount;//总条数
	private int startCount=0;//查询开始条数
	
	public int getStartCount() {
		return startCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize>0?pageSize:1;//至少显示一条数据
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount>0?pageCount:1;//至少第一页
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount<0?0:totalCount;
		//
		if(this.totalCount%this.pageSize>0){
			//判断总条数与一页显示条数取余,若大于0则相除结果所得页数加1
			this.totalPage=this.totalCount/this.pageSize+1;
		}else{
			this.totalPage=this.totalCount/this.pageSize;
		}
		if(this.pageCount>this.totalPage){
			//判断当前页数大于总页数,则将当前页数改为总页数
			this.pageCount=this.totalPage;
		}
		//计算查询开始条数
		this.startCount=((this.pageCount-1)>0?this.pageCount-1:0)*this.pageSize;
	}
	
}