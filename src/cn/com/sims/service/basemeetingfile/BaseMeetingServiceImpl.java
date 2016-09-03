package cn.com.sims.service.basemeetingfile;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.basemeetingfile.IBaseMeetingFileDao;
import cn.com.sims.model.basemeetingfile.BaseMeetingFile;

/**
 * 会议附件
 * @author shbs-tp004
 * @date 2016-5-5
 */
@Service
public class BaseMeetingServiceImpl implements IBaseMeetingFileService {

	@Resource
	private IBaseMeetingFileDao fileDao;
	
	private static final Logger gs_logger = Logger
	.getLogger(BaseMeetingServiceImpl.class);
	
	/**
	 * 添加会议附件
	 * @param info
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	@Override
	public void insertMeetingFileInfo(BaseMeetingFile info) throws Exception {
		gs_logger.info("insertMeetingFileInfo 方法开始");
		try {
			if(info!=null){
				fileDao.insertMeetingFile("BaseMeetingFile.insertMeetingFileInfo", info);
			}
		} catch (Exception e) {
			gs_logger.error("insertMeetingFileInfo 方法异常",e);
			throw e;
		}
		gs_logger.info("insertMeetingFileInfo 方法结束");
	}
	
	/**
	 * 查询会议附件信息
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	@Override
	public BaseMeetingFile findMeetingFileByMeetingFilecode(String filecode)
			throws Exception {
		gs_logger.info("findMeetingFileByMeetingFilecode 方法开始");
		BaseMeetingFile info=null;

		try {
			info=fileDao.findMeetingFileByMeetingFilecode("BaseMeetingFile.findMeetingFileByMeetingFilecode", filecode);
		} catch (Exception e) {
			gs_logger.error("findMeetingFileByMeetingFilecode 方法异常",e);
			
			throw e;
		}
		
		gs_logger.info("findMeetingFileByMeetingFilecode 方法结束");
		return info;
	}

	/**
	 * 查询会议附件信息
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	@Override
	public List<BaseMeetingFile> findMeetingFileByMeetingcode(String meetingcode)
			throws Exception {
		gs_logger.info("findMeetingFileByMeetingcode 方法开始");
		List<BaseMeetingFile> info=null;

		try {
			info=fileDao.findMeetingFileByMeetingcode("BaseMeetingFile.findMeetingFileByMeetingcode", meetingcode);
		} catch (Exception e) {
			gs_logger.error("findMeetingFileByMeetingcode 方法异常",e);
			
			throw e;
		}
		
		gs_logger.info("findMeetingFileByMeetingcode 方法结束");
		return info;
	}

	/**
	 * 删除会议附件信息
	 * @param filecode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-10
	 */
	@Override
	public int deleteMeetingfileByfilecode(String filecode) throws Exception {
		gs_logger.info("deleteMeetingfileByfilecode 方法开始");
		int data=0;
		try {
			data=fileDao.deleteMeetingfileByfilecode("BaseMeetingFile.deleteMeetingfileByfilecode", filecode);
		} catch (Exception e) {
			gs_logger.error("deleteMeetingfileByfilecode 方法异常",e);
			throw e;
		}
		gs_logger.info("deleteMeetingfileByfilecode 方法结束");
		return data;
	}

	@Override
	public int deleteMeetingfileByMeetingcode(String meetcode) throws Exception {
		gs_logger.info("deleteMeetingfileByMeetingcode 方法开始");
		int data=0;
		try {
			data=fileDao.deleteMeetingfileByMeetingcode("BaseMeetingFile.deleteMeetingfileByMeetingcode", meetcode);
		} catch (Exception e) {
			gs_logger.error("deleteMeetingfileByMeetingcode 方法异常",e);
			throw e;
		}
		gs_logger.info("deleteMeetingfileByMeetingcode 方法结束");
		return data;
	}

}
