package cn.com.sims.dao.baseinvestorlabelinfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/** 
 * @author  yl
 * @date ：2015年10月22日 
 * 类说明 
 */
@Service
public class BaseInvestorlabelInfoDaoImpl  implements IBaseInvestorlabelInfoDao{
	@Resource SqlMapClient sqlMapClient;

	@Override
	public void insertInveslabelInfo(String str, BaseInvestorlabelInfo info)
			throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, info);		
	}

	@Override
	public void tranDeleteInvestorlabel(String str, BaseInvestorlabelInfo info)
			throws Exception {
		// TODO Auto-generated method stub
			sqlMapClient.delete(str, info);
	}

	@Override
	public void insertBaseInvestorlabelInfoforit(String str,
		BaseInvestorlabelInfo baseinvestorlabelinfo) throws Exception {
	    sqlMapClient.insert(str, baseinvestorlabelinfo);	
	    
	}
	
}
