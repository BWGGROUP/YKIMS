package cn.com.sims.dao.basemeetingfile;

import java.util.List;

import cn.com.sims.model.basemeetingfile.BaseMeetingFile;

/**
 * 会议附件
 * @author dwj
 * @date 2016-5-5
 */
public interface IBaseMeetingFileDao {
	
	/**
	 * 添加会议附件信息
	 * @param str
	 * @param info
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	public void insertMeetingFile(String str,BaseMeetingFile info) throws Exception;
	
	/**
	 * 根据附件code查询会议附件信息
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-5
	 */
	public BaseMeetingFile findMeetingFileByMeetingFilecode(String str,String filecode)throws Exception;
	
	/**
	 * 查询会议附件信息
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-10
	 */
	public List<BaseMeetingFile> findMeetingFileByMeetingcode(String str,String meetingcode)throws Exception;
	
	/**
	 * 删除会议附件信息
	 * @param str
	 * @param filecode
	 * @return
	 * @throws Exception
	 */
	public int deleteMeetingfileByfilecode(String str,String filecode)throws Exception;
	
	/**
	 * 根据会议code删除会议附件信息
	 * @param str
	 * @param meetcode
	 * @return
	 * @throws Exception
	 */
	public int deleteMeetingfileByMeetingcode(String str,String meetcode)throws Exception;
}
