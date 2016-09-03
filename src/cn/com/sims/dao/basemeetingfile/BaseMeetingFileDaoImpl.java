package cn.com.sims.dao.basemeetingfile;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basemeetingfile.BaseMeetingFile;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 会议附件
 * @author dwj
 * @date 2016-5-5
 */
@Service
public class BaseMeetingFileDaoImpl implements IBaseMeetingFileDao {
	
	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void insertMeetingFile(String str, BaseMeetingFile info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	@Override
	public BaseMeetingFile findMeetingFileByMeetingFilecode(String str,
			String filecode) throws Exception {
		return (BaseMeetingFile) sqlMapClient.queryForObject(str,filecode);
	}

	@Override
	public List<BaseMeetingFile> findMeetingFileByMeetingcode(String str,
			String meetingcode) throws Exception {
		return sqlMapClient.queryForList(str,meetingcode);
	}

	@Override
	public int deleteMeetingfileByfilecode(String str, String filecode)
			throws Exception {
		return sqlMapClient.delete(str, filecode);
	}

	@Override
	public int deleteMeetingfileByMeetingcode(String str, String meetcode)
			throws Exception {
		return sqlMapClient.delete(str, meetcode);
	}
	

}
