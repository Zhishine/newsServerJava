package news.modal;

public class SystemInfo {
private String mVersion;
private String mVersionUrl;
private long mPublicAdLastTime;
private String mVersionDescription;
public void setVersion(String version)
{
this.mVersion=version;
}
public String getVersion()
{
return this.mVersion;	
}

public void setPublicAdLastTime(long tick)
{
this.mPublicAdLastTime=tick;	
}
public long getPublicAdLastTime()
{
return this.mPublicAdLastTime;	
}
public void setVersionUrl(String versionUrl)
{
this.mVersionUrl=versionUrl;	
}
public String getVersionUrl()
{
return this.mVersionUrl;	
}
public void setVersionDescription(String versionDescription)
{
	this.mVersionDescription=versionDescription;
}
public String getVersionDescription()
{
return this.mVersionDescription;	
}
}
