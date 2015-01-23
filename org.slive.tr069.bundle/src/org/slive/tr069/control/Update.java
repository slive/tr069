package org.slive.tr069.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slive.tr069.model.Download;
import org.slive.tr069.servlet.expample.UpdateServlet;


/**
 * @author Slive
 * @date 2013-5-30
 */
public class Update
{
	/**
	 * 通过http方式进行更新，
	 * @throws IOException 
	 * return {@link Download} 相对CPE来说是下载，相对ACS来说是上传
	 */
	public static Download httpUpdate(String fileType,UpdateServlet servlet,File updateFile) throws IOException
	{
		Download dld = new Download();
		dld.setFileType(fileType);
		dld.setUrl(servlet.getUrl());
		servlet.setUpdateFile(updateFile);
		dld.setTargetFileName(updateFile.getName());
		FileInputStream fileOut = new FileInputStream(updateFile);
		BufferedInputStream bis = new BufferedInputStream(fileOut);
		int available = bis.available();
		dld.setFileSize(available);
		return dld;
	}
}
