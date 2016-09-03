package cn.com.sims.service.code;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.code.CodeDao;
import cn.com.sims.model.code.CodeGenerator;


@Service
public class CodeGeneratorManagerServiceImpl implements CodeGeneratorManagerService {
    
   @Resource
   CodeDao codeDao;
    /* log */
	private static final Logger gs_logger = Logger.getLogger(CodeGeneratorManagerServiceImpl.class);
	/**
	 * 根据业务类型,查询当前业务单号信息
	 * @param generatorType 业务类型
	 * @return 当前业务单号信息
	 * @throws Exception
	 */
	@Override
	public CodeGenerator getAndUpdateCodeGenerator(final int generatorType)throws Exception{
	    gs_logger.info("CodeGeneratorManagerServiceImpl getAndUpdateCodeGenerator方法开始");
	    CodeGenerator codegenerator = null;
		try{
		    Map<String, Integer> parameter = new HashMap<String, Integer>();
		    parameter.put("generatorid", generatorType);
		    codeDao.updsyscodegenerator(
				"code.updateCodeGenerator", parameter);
		    
		    codegenerator = codeDao.querycodegeneratorfopar(
					"code.querysyscodegeneratorbyid", parameter);
		}catch(Exception e){
			gs_logger.error("CodeGeneratorManagerServiceImpl getAndUpdateCodeGenerator方法异常",e);
			throw e;
		}
		gs_logger.info("CodeGeneratorManagerServiceImpl getAndUpdateCodeGenerator方法结束");
		return codegenerator;
	};

}
