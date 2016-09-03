package cn.com.sims.service.basemeetingfile;

import java.util.List;

import cn.com.sims.model.basemeetingfile.BaseMeetingFile;

/**
 * 会议附件
 * @author shbs-tp004
 * @date 2016-5-5
 */
public interface IBaseMeetingFileService {

	/**
	 * 添加会议附件
	 * @param info
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	public void insertMeetingFileInfo(BaseMeetingFile info)throws Exception;
	
	/**
	 * 根据附件code查询会议附件信息
	 * @param str
	 * @param filecode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	public BaseMeetingFile findMeetingFileByMeetingFilecode(String filecode)throws Exception;
	
	/**
	 * 查询会议附件信息
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	public List<BaseMeetingFile> findMeetingFileByMeetingcode(String meetingcode)throws Exception;
	
	/**
	 * 根据会议filecode删除会议附件信息
	 * @param filecode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-10
	 */
	public int deleteMeetingfileByfilecode(String filecode)throws Exception;
	
	/**
	 *  根据会议code删除会议附件信息
	 * @param meetcode
	 * @return
	 * @throws Exception
	 */
	public int deleteMeetingfileByMeetingcode(String meetcode)throws Exception;
}
