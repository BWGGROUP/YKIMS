package cn.com.sims.common.commonUtil;

import java.io.File;

public class DeleteDirectory {
    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    private static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录 customDate long型数据格式
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir,long customDate) {
    	
    	if(!dir.exists()){
    		return false;
    	}
    	if( customDate == 0){
    		return false;
    	}
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]),customDate);
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        if(System.currentTimeMillis()-dir.lastModified()>customDate*24*60*60*1000){
        	if(!dir.isDirectory()){
        		if(dir.getName().indexOf(".xls")>0){
        			return dir.delete();
        		}else{
        			return true;
        		}
        	}else{
        		if(CommonUtil.findNoteTxtOfXML("INVESTMENT_DOWN").indexOf(dir.getAbsolutePath())<0){
        			return dir.delete();
        		}else{
        			return true;
        		}
        	}
        }else{
        	return true;
        }
       
    }
    /**
     *测试
     */
    public static void main(String[] args) {
    	System.out.println(new File("/home/skl/git/iims_document/apache-tomcat-7.0.64.tar.gz").lastModified());
//        doDeleteEmptyDir("new_dir1");
//        String newDir2 = "new_dir2";
//        boolean success = deleteDir(new File(newDir2));
//        if (success) {
//            System.out.println("Successfully deleted populated directory: " + newDir2);
//        } else {
//            System.out.println("Failed to delete populated directory: " + newDir2);
//        }     
    }
}
