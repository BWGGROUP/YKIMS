package cn.com.sims.service.code;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.com.sims.model.code.CodeGenerator;


@Scope("singleton")
@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
   @Resource
   CodeGeneratorManagerService codeGeneratorManagerService;
    /* log */
	private static final Logger gs_logger = Logger.getLogger(CodeGeneratorServiceImpl.class);
	
	
	private volatile static Map<Integer,AtomicLong> typeCounter = new ConcurrentHashMap<Integer,AtomicLong>(20);
	private volatile static Map<Integer,Long> numbers = new ConcurrentHashMap<Integer,Long>(20);
	
	/**
	 * 根据单号间距和业务类型，获取业务单号
	 * @param num 单号间距
	 * @param generatorType 业务类型
	 * @return 业务单号
	 * @throws Exception 执行过程异常
	 * @author rqq
	 */
	@Override
	public long getCodeGenerator(int num, int generatorType) throws Exception {
	    gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator 增量num："+num +"业务类型："+generatorType +" 生成id号开始"); 
		long result;
		if (num <= 0) {
			num = 1;
			gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator 增量num为null，赋默认值为1，现在增量num为："+num ); 
		}
		try{
			AtomicLong counter;
			Long number;
			synchronized (typeCounter) {
				counter = typeCounter.get(generatorType);
				
				if(counter == null) {
					counter =  new AtomicLong(0);
					typeCounter.put(generatorType, counter);
				}
				
				
			}
			
			synchronized(counter){
				result = counter.addAndGet(num);
				gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator 生成号result："+result);
				number = numbers.get(generatorType);
				gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator  目前最大值number："+number);
				if(number == null) {
					number = 0L;
				}
				
				// 判断现在的号是否已经达到数据中的上限
				if (result >= number) {
				    gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator 生成号result大于或等于目前最大值number");
					CodeGenerator codegenerator = codeGeneratorManagerService.getAndUpdateCodeGenerator(generatorType);
					gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator 更新数据库并获取目前的起始值："+codegenerator.toString());
					number = codegenerator.getStartNum() + codegenerator.getStepNum();
					gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator更新数据库后最大值number："+number);
					numbers.put(generatorType, number);
					result = codegenerator.getStartNum();
					gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator更新数据库后的生成号为："+result);
					counter.set(codegenerator.getStartNum());
				}
			}
			gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator增量num："+num +"业务类型："+generatorType +" 生成id号结束"); 	
		}catch(Exception e){
		    gs_logger.error("CodeGeneratorServiceImpl getCodeGenerator方法异常",e);
			throw e;
		}
		gs_logger.info("CodeGeneratorServiceImpl getCodeGenerator方法结束");
		return result;
	}

}
