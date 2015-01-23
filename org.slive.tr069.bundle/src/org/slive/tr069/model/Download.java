package org.slive.tr069.model;

import org.w3c.dom.Element;

/**
 * 本方法可以由服务器使用来指示CPE在指定的位置下载特定的文件
 * @author Slive
 */
public class Download extends AbstractMethod
{
	private static final long serialVersionUID = -2157612232445139644L;
	
	// FileType类型全局变量
	public static final String FT_FIRMWARE = "1 Firmware Upgrade Image";
    public static final String FT_WEBCONTENT = "2 Web Content";
    public static final String FT_CONFIG = "3 Vendor Configuration File";
    
    // 所有模型字段全局变量
    private static final String CommandKey = "CommandKey";
    private static final String FileType = "FileType";
    private static final String URL = "URL";
    private static final String Username = "Username";
    private static final String Password = "Password";
    private static final String FileSize = "FileSize";
    private static final String TargetFileName = "TargetFileName";
    private static final String DelaySeconds = "DelaySeconds";
    private static final String SuccessURL = "SuccessURL";
    private static final String FailureURL = "FailureURL";
    
    private String commandKey;
    private String fileType;
    private String url;
    private String userName;
    private String passWord;
    private String targetFileName;
    private String successURL;
    private String failureURL;
    private long fileSize;
    private int delaySeconds;

    public Download()
    {
        methodName = "Download";
        commandKey = "";
        fileType = "";
        url = "";
        userName = "";
        passWord = "";
        targetFileName = "";
        successURL = "";
        failureURL = "";
        fileSize = 0;
        delaySeconds = 0;
    }

    public Download(String commandKey, String url, String fileType)
    {
        methodName = "Download";
        this.commandKey = commandKey;
        this.url = url;
        this.fileType = fileType;
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
        Element fileSizItem = soapMessageModel.createElement(FileSize);
        fileSizItem.setTextContent(String.valueOf(fileSize));
        Element targetItem = soapMessageModel.createElement(TargetFileName);
        targetItem.setTextContent(targetFileName);
        Element delayItem = soapMessageModel.createElement(DelaySeconds);
        delayItem.setTextContent(String.valueOf(delaySeconds));
        Element succurlItem = soapMessageModel.createElement(SuccessURL);
        succurlItem.setTextContent(successURL);
        Element failurlItem = soapMessageModel.createElement(FailureURL);
        failurlItem.setTextContent(failureURL);
        body.appendChild(comItem);
        body.appendChild(fileTypeItem);
        body.appendChild(urlItem);
        body.appendChild(userItem);
        body.appendChild(passItem);
        body.appendChild(fileSizItem);
        body.appendChild(targetItem);
        body.appendChild(delayItem);
        body.appendChild(succurlItem);
        body.appendChild(failurlItem);
    }

    @Override
    protected void parseBody2Filed(Element body, SoapMessageModel soapMessageModel)
    {
    	commandKey = getRequestChildElement(body, CommandKey).getTextContent();
    	fileType = getRequestChildElement(body, FileType).getTextContent();
    	url = getRequestChildElement(body, URL).getTextContent();
    	userName = getRequestChildElement(body, Username).getTextContent();
    	passWord = getRequestChildElement(body, Password).getTextContent();
    	fileSize = Long.valueOf(getRequestChildElement(body, FileSize).getTextContent());
    	targetFileName = getRequestChildElement(body, TargetFileName).getTextContent();
    	delaySeconds = Integer.valueOf(getRequestChildElement(body, DelaySeconds).getTextContent());
    	successURL = getRequestChildElement(body, SuccessURL).getTextContent();
    	failureURL = getRequestChildElement(body, FailureURL).getTextContent();
    }

    @Override
    public String toString()
    {
    	StringBuilder sbd = new StringBuilder();
    	sbd.append(" CommandKey:" + commandKey);
    	sbd.append(" FileType:" + fileType);
    	sbd.append(" Url:" + url);
    	sbd.append(" UserName:" + userName);
    	sbd.append(" PassWord:" + passWord);
    	sbd.append(" FileSize:" + fileSize);
    	sbd.append(" TargetFileName:" + targetFileName);
    	sbd.append(" DelaySeconds:" + delaySeconds);
    	sbd.append(" SuccessURL:" + successURL);
    	sbd.append(" FailureURL:" + failureURL);
    	return super.toString() + sbd.toString();
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

    public long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getTargetFileName()
    {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName)
    {
        this.targetFileName = targetFileName;
    }

    public int getDelaySeconds()
    {
        return delaySeconds;
    }

    public void setDelaySeconds(int delaySeconds)
    {
        this.delaySeconds = delaySeconds;
    }

    public String getSuccessURL()
    {
        return successURL;
    }

    public void setSuccessURL(String successURL)
    {
        this.successURL = successURL;
    }

    public String getFailureURL()
    {
        return failureURL;
    }

    public void setFailureURL(String failureURL)
    {
        this.failureURL = failureURL;
    }

}
