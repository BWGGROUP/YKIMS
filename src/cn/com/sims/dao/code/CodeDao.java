package cn.com.sims.dao.code;

import cn.com.sims.model.code.CodeGenerator;



public interface CodeDao {

	//更新id编号生成表，参数obj
	public void updsyscodegenerator(String str, Object obj) throws Exception;
	// 查询id编号生成表，参数obj
	public CodeGenerator querycodegeneratorfopar(String str, Object obj) throws Exception;
	
}
