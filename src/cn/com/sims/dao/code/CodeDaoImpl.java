package cn.com.sims.dao.code;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.code.CodeGenerator;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service
public class CodeDaoImpl implements CodeDao{

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void updsyscodegenerator(String str, Object obj) throws Exception {
		sqlMapClient.update(str, obj);
	}
	@Override
	public CodeGenerator querycodegeneratorfopar(String str, Object obj) throws Exception {
		return (CodeGenerator) sqlMapClient.queryForObject(str, obj);
	}
}
