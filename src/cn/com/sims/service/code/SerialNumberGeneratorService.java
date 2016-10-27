package cn.com.sims.service.code;


public interface SerialNumberGeneratorService {
	
	/**
	 * 根据单号间距和业务类型，获取业务单号
	 * @param num 单号间距
	 * @param generatorType 业务类型
	 * @return 业务单号
	 * @throws Exception 执行过程异常
	 * @author rqq
	 */
	public String getCodeGeneratorstr(int num, int generatorType) throws Exception ;
}