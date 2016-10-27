package cn.com.sims.service.investment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.Investment.InvestmentDao;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.viewInvestmentInfo.investmentInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;

/**
 * @author yanglian
 *  @version
 */
@Service
public class InvestmentServiceImpl implements IInvestmentService{
	@Resource
	InvestmentDao investmentDao;
	private static final Logger gs_logger = Logger.getLogger(InvestmentServiceImpl.class);
	/**
	 * 模糊查询投资机构名称或投资人名称
	 * 
	 * @param name
	 * @return
	 * @version 2015-09-24
	 */
 @Override
	public List findInvestment(String name) throws Exception{
		gs_logger.info("InvestmentServiceImpl findInvestment方法开始");
		List list = new ArrayList();
		try{
		list=investmentDao.findInvestment("viewInvestmentInfo.search", name);
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findInvestment方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findInvestment方法结束");
		return list;
	}
	/**
	 * 模糊查询投资机构名称或投资人名称
	 * 
	 * @param paraMap
	 * @return
	 * @version 2015-09-24
	 */
	@Override
public List findInvestmentByName(Map<String, Object> paraMap)
		throws Exception {
	gs_logger.info("InvestmentServiceImpl findInvestmentByName方法开始");
	List list = new ArrayList(50);
	try{
	list=investmentDao.findInvestmentByName("viewInvestmentInfo.findInvestmentByName", paraMap);
	//2015-12-14 yl add start
	if(list!=null && list.size()>0){
		for(int i=0;i<list.size();i++){
			Map map=(Map<String,String>)list.get(i);
			paraMap.remove("base_investment_code");
			paraMap.put("base_investment_code", map.get("base_investment_code"));
			String companyName = investmentDao.findCompanyRe("viewInvestmentInfo.findCompanyRe",paraMap);
			((Map<String,String>)list.get(i)).put("companyName", companyName);
		}
	}
	//2015-12-14 yl add end
	}catch(Exception e){
		gs_logger.error("InvestmentServiceImpl findInvestmentByName方法异常",e);
		throw e;
	}
	gs_logger.info("InvestmentServiceImpl findInvestmentByName方法结束");
	return list;
}
	@Override
	public List gotoSearchByName(Map<String, Object> paraMap) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestmentServiceImpl gotoSearchByName方法开始");
		List list = new ArrayList<viewInvestmentInfo>();
		try{
		list=investmentDao.gotoSearchByName("viewInvestmentInfo.gotoSearchByName",paraMap);
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map=(Map<String,String>)list.get(i);
				paraMap.remove("base_investment_code");
				paraMap.put("base_investment_code", map.get("base_investment_code"));
				String companyName = investmentDao.findCompanyRe("viewInvestmentInfo.findCompanyRe",paraMap);
				((Map<String,String>)list.get(i)).put("companyName", companyName);
			}
		}
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl gotoSearchByName方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl gotoSearchByName方法结束");
		return list;
	}
	@Override
	public int findCountSizeByName(Map<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestmentServiceImpl findCountSizeByName方法开始");
		int countSzie=0;

		try{
			
			countSzie=investmentDao.findCountSizeByName("viewInvestmentInfo.findCountSizeByName",paraMap);
			
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findCountSizeByName方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findCountSizeByName方法结束");
		return countSzie;
//		return pageCount;
	}
	@Override
	public List findLable(String type) throws Exception {
		gs_logger.info("InvestmentServiceImpl findLable方法开始");
		List list = null;
		try{
		list=investmentDao.findLable("SysLabelelementInfo.findLable",type);
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findLable方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findLable方法结束");
		return list;
	}
	@Override
	public List findCompany(String name) throws Exception {
		// TODO Auto-generated method stub
			gs_logger.info("InvestmentServiceImpl findCompany方法开始");
			List list = null;
			try{
			list=investmentDao.findCompany("viewCompInfo.findCompany", name);
			}catch(Exception e){
				gs_logger.error("InvestmentServiceImpl findCompany方法异常",e);
				throw e;
			}
			gs_logger.info("InvestmentServiceImpl findCompany方法结束");
			return list;
		}
	@Override
	public List findInvestmentByMoreCon(Map<String, Object> paraMap,List comlist)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestmentServiceImpl findInvestmentByMoreCon方法开始");
		
		List investmentList = null;
		List list = null;
		String companyName="";
		try{
			
			list=investmentDao.findInvestmentByMoreCon("viewInvestmentInfo.findMoreList",paraMap);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Map map=(Map<String,String>)list.get(i);
					if(map.get("view_investment_compcode")!=null){
					if(comlist!=null&&comlist.size()>0){
						for(int j=0;j<comlist.size();j++){
								if(map.get("view_investment_compcode").toString().contains(comlist.get(j).toString())){
									((Map<String,String>)list.get(i)).remove("view_investment_compcode");
									((Map<String,String>)list.get(i)).put("view_investment_compcode",  "是");
								}
							}
						}
					}else{
						((Map<String,String>)list.get(i)).remove("view_investment_compcode");
						((Map<String,String>)list.get(i)).put("view_investment_compcode",  "否");
					}
					if(!((Map<String,String>)list.get(i)).get("view_investment_compcode").toString().equals("是")){
						((Map<String,String>)list.get(i)).remove("view_investment_compcode");
						((Map<String,String>)list.get(i)).put("view_investment_compcode",  "否");
					}
					paraMap.remove("base_investment_code");
					paraMap.put("base_investment_code", map.get("base_investment_code"));
					
					companyName = investmentDao.findCompanyRe("viewInvestmentInfo.findCompanyRe",paraMap);
					if(companyName!=null && !"".equals(companyName.trim())){
						companyName=companyName.replaceAll(",", ", ");
					}
					((Map<String,String>)list.get(i)).put("companyName", companyName);
				}
			}
			
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findInvestmentByMoreCon方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findInvestmentByMoreCon方法结束");
		return list;
	}
	@Override
	public int findInvestmentByMoreCount(Map<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestmentServiceImpl findInvestmentByMoreCount方法开始");
		int countSzie=0;

		try{
			
			countSzie=investmentDao.findInvestmentByMoreCount("viewInvestmentInfo.findInvestmentByMoreCount",paraMap);
			
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findInvestmentByMoreCount方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findInvestmentByMoreCount方法结束");
		return countSzie;
	}
	
	@Override
	public List<investmentInfo> findInvestmentByMoreConExport(Map<String, Object> paraMap,List competitionList)
			throws Exception {
		// TODO Auto-generated method stub
gs_logger.info("InvestmentServiceImpl findInvestmentByMoreConExport方法开始");

		List list = new ArrayList<investmentInfo>();
		try{
			
			list=investmentDao.findInvestmentByMoreConExport("viewInvestmentInfo.findInvestmentByMoreConExport",paraMap);
			String nowdate = CommonUtil.getDayTime_tamp().toString();
			String s=Integer.parseInt(nowdate.substring(0,4))-1+"";
			String oldyear=nowdate.replaceAll(nowdate.substring(0,4), s);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					investmentInfo investmentinfo = (cn.com.sims.model.viewInvestmentInfo.investmentInfo) list
							.get(i);
					paraMap.remove("base_investment_code");
					paraMap.put("base_investment_code",
							investmentinfo.getBase_investment_code());
					paraMap.put("nowdate", nowdate);
					paraMap.put("oldyear", oldyear);
					List<BaseInvesfundInfo> Inlist=investmentDao.findInvesfund("BaseInvesfundInfo.findInvesfund",paraMap);
					String str="";
					if(Inlist!=null&&Inlist.size()>0){
						for(int j=0;j<Inlist.size();j++){
								str=str+Inlist.get(j).getBase_invesfund_name()+"("+Inlist.get(j).getBase_invesfund_scale()+")\n";
								
						}
					}
					investmentinfo.setInvesfund(str);
					String companyName = investmentDao.findCompanyRe(
							"viewInvestmentInfo.findCompanyRece", paraMap);
					investmentinfo.setCompanyname(companyName);
					String competition = "";
					if (competitionList == null) {
						investmentinfo.setView_investment_compcode(competition);
					} else {
						for (int j = 0; j < competitionList.size(); j++) {
							if (investmentinfo.getView_investment_compcode() != null) {
								if (investmentinfo.getView_investment_compcode().contains(competitionList.get(j).toString())) {
									investmentinfo.setView_investment_compcode("是");
									break;
								}
							} else {
								investmentinfo.setView_investment_compcode("否");
							}
						}
						if (!investmentinfo.getView_investment_compcode()
								.equals("是")) {
							investmentinfo.setView_investment_compcode("否");
						}
					}
				}
			}
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findInvestmentByMoreConExport方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findInvestmentByMoreConExport方法结束");
		return list;
	}
	@Override
	public List findLableScle() throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestmentServiceImpl findLable方法开始");
		List list = null;
		try{
		list=investmentDao.findLableScale("SysLabelelementInfo.findLableScale");
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findLable方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findLable方法结束");
		return list;
	}
	@Override
	public List<investmentInfo> findInvestmentByNameExport(
			Map<String, Object> paraMap) throws Exception {
		// TODO Auto-generated method stub
gs_logger.info("InvestmentServiceImpl findInvestmentByNameExport方法开始");
		
		List investmentList = null;
		List list = new ArrayList<investmentInfo>();
		try{
			
			list=investmentDao.findInvestmentByNameExport("viewInvestmentInfo.findInvestmentByNameExport",paraMap);
			String nowdate = CommonUtil.getDayTime_tamp().toString();
			String s=Integer.parseInt(nowdate.substring(0,4))-1+"";
			String oldyear=nowdate.replaceAll(nowdate.substring(0,4), s);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					investmentInfo investmentinfo = (cn.com.sims.model.viewInvestmentInfo.investmentInfo) list
							.get(i);
					paraMap.remove("base_investment_code");
					paraMap.put("base_investment_code",
							investmentinfo.getBase_investment_code());
					paraMap.put("nowdate", nowdate);
					paraMap.put("oldyear", oldyear);
					List<BaseInvesfundInfo> Inlist=investmentDao.findInvesfund("BaseInvesfundInfo.findInvesfund",paraMap);
					String str="";
					if(Inlist!=null&&Inlist.size()>0){
						for(int j=0;j<Inlist.size();j++){
								str=str+Inlist.get(j).getBase_invesfund_name()+"("+Inlist.get(j).getBase_invesfund_scale()+")\n";
						}
					}
					investmentinfo.setInvesfund(str);
					String companyName = investmentDao.findCompanyRe(
							"viewInvestmentInfo.findCompanyRece", paraMap);
					investmentinfo.setCompanyname(companyName);
				}
			}
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findInvestmentByNameExport方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findInvestmentByNameExport方法结束");
		return list;
	}
//	@Override
//	public List findInvestmentByMoreCon(Map<String, Object> paraMap)
//			throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public int findInvestmentByMoreCount(Map<String, Object> paraMap, List list)
//			throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
	
	/**
	 * 通过名称，拼音，首字母模糊查询投资机构信息
	 * @param name
	 * @return 机构名称集合
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-12-15
	 */
	@Override
	public List findInvestmentNameListByName(Map<String, Object> paraMap)
			throws Exception {
		gs_logger.info("InvestmentServiceImpl findInvestmentByName方法开始");
		List list = new ArrayList(50);
		try{
			list=investmentDao.findInvestmentByName("viewInvestmentInfo.findInvestmentByName", paraMap);
		}catch(Exception e){
			gs_logger.error("InvestmentServiceImpl findInvestmentByName方法异常",e);
			throw e;
		}
		gs_logger.info("InvestmentServiceImpl findInvestmentByName方法结束");
		return list;

	}
}
