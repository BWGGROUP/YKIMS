package cn.com.sims.service.code;

import cn.com.sims.model.code.CodeGenerator;



public interface CodeGeneratorManagerService {
	
    /**
	 * 根据业务类型,查询当前业务单号信息
	 * @param generatorType 业务类型
	 * @return 当前业务单号信息
	 * @throws Exception
	 */
	public CodeGenerator getAndUpdateCodeGenerator(final int generatorType)throws Exception ;
}