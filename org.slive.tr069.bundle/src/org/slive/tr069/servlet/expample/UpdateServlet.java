package org.slive.tr069.servlet.expample;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新用servlet
 * @author Slive
 * @date 2013-5-30
 */
public class UpdateServlet extends HttpServlet
{
	private static final long serialVersionUID = -7745237078260092153L;
	private File updateFile;
	private String url;
	public UpdateServlet()
	{
		
	}
	
	public File getUpdateFile()
	{
		return updateFile;
	}

	/**
	 * 更新文件
	 * @param updateFile
	 */
	public void setUpdateFile(File updateFile)
	{
		this.updateFile = updateFile;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException
	{
		update(req,response);
	}

	private void update(HttpServletRequest req,HttpServletResponse response) throws IOException,
			FileNotFoundException
	{
		response.setContentType("text/xml;charset=UTF-8");
		ServletOutputStream outStream = response.getOutputStream();
		
		int fileSize = 0;
		if(updateFile != null)
		{
			FileInputStream fileOut = new FileInputStream(updateFile);
			BufferedInputStream bis = new BufferedInputStream(fileOut);
			fileSize = bis.available();
			byte[] bys = new byte[fileSize];
			bis.read(bys);
			outStream.write(bys);
		}
		response.setContentLength(fileSize);
		outStream.flush();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException
	{
		update(req,response);
	}
}
