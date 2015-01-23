package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * 服务器用本方法使CPE向指定的位置上载一个特定的文件
 * @author Slive
 *
 */
public class Upload extends AbstractMethod
{
	private static final long serialVersionUID = -2580059480719208408L;
	
	public static final String FT_CONFIG = "1 Vendor Configuration File";
	public static final String FT_LOG = "2 Vendor Log File";
	
    // 所有模型字段全局变量
    private static final String CommandKey = "CommandKey";
    private static final String FileType = "FileType";
    private static final String URL = "URL";
    private static final String Username = "Username";
    private static final String Password = "Password";
    private static final String DelaySeconds = "DelaySeconds";
	
	private String commandKey;
    private String fileType;
    private String url;
    private String userName;
    private String passWord;
    private int delaySeconds;

    public Upload()
    {
        methodName = "Upload";
        userName = "";
        passWord = "";
        delaySeconds = 0;
        fileType = "";
        url = "";
        commandKey = "default.command.key";
    }

    @Override
    protected void addField2Body(Element body, SoapMessageModel soapMessageModel)
    {
        Element comItem = soapMessageModel.createElement(CommandKey);
        comItem.setTextContent(commandKey);
        Element fileTypeItem = soapMessageModel.createElement(FileType);
        fileTypeItem.setTextContent(fileType);
        Element urlItem = soapMessageModel.createElement(URL);
        urlItem.setTextContent(url);
        Element userItem = soapMessageModel.createElement(Username);
        userItem.setTextContent(userName);
        Element passItem = soapMessageModel.createElement(Password);
        passItem.setTextContent(passWord);
        Element delayItem = soapMessageModel.createElement(DelaySeconds);
        delayItem.setTextContent(String.valueOf(delaySeconds));

        body.appendChild(comItem);
        body.appendChild(fileTypeItem);
        body.appendChild(urlItem);
        body.appendChild(userItem);
        body.appendChild(passItem);
        body.appendChild(delayItem);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	commandKey = getRequestChildElement(body, CommandKey).getTextContent();
    	fileType = getRequestChildElement(body, FileType).getTextContent();
    	url = getRequestChildElement(body, URL).getTextContent();
    	userName = getRequestChildElement(body, Username).getTextContent();
    	passWord = getRequestChildElement(body, Password).getTextContent();
    	delaySeconds = Integer.valueOf(getRequestChildElement(body, DelaySeconds).getTextContent());
    }

	public String getCommandKey()
	{
		return commandKey;
	}

	public void setCommandKey(String commandKey)
	{
		this.commandKey = commandKey;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

	public int getDelaySeconds()
	{
		return delaySeconds;
	}

	public void setDelaySeconds(int delaySeconds)
	{
		this.delaySeconds = delaySeconds;
	}
}
