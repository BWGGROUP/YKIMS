package cn.com.sims.service.code;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.sims.common.RandomId;

@Service
public class SerialNumberGeneratorServiceImpl implements SerialNumberGeneratorService {
   @Resource
   CodeGeneratorService codeGeneratorService;
    /* log */
	private static final Logger gs_logger = Logger.getLogger(SerialNumberGeneratorServiceImpl.class);
	/**
	 * 根据单号间距和业务类型，获取业务单号
	 * @param num 单号间距
	 * @param generatorType 业务类型
	 * @return 业务单号
	 * @throws Exception 执行过程异常
	 * @author rqq
	 */
	@Override
	public String getCodeGeneratorstr(int num, int generatorType) throws Exception {
	    gs_logger.info("SerialNumberGeneratorServiceImpl getCodeGeneratorstr （凯撒加密）增量num："+num +"业务类型："+generatorType +" 生成id号开始"); 
	    String codeId=null;
		try{
		    long result;
		    result=codeGeneratorService.getCodeGenerator(num, generatorType);
		    String id= String.valueOf(result);
			gs_logger.info("SerialNumberGeneratorServiceImpl getCodeGeneratorstr（凯撒加密前）id："+id ); 	
		    RandomId r = new RandomId();
		    codeId=r.randomId(id);
			gs_logger.info("SerialNumberGeneratorServiceImpl getCodeGeneratorstr（凯撒加密后id）id："+id+"codeId:"+codeId+"增量num："+num +"业务类型："+generatorType +" 生成id号结束"); 	
		}catch(Exception e){
		    gs_logger.error("SerialNumberGeneratorServiceImpl getCodeGeneratorstr（凯撒加密后id）方法异常",e);
			throw e;
		}
		gs_logger.info("SerialNumberGeneratorServiceImpl getCodeGeneratorstr（凯撒加密后id）方法结束");
		return codeId;
	}
}
