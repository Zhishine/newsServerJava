package news.modal;

public class News {

private int mId;
private String mTitle;
private String mRedirectUrl;
private String mDescription;
private String mTitleImageUrl;
private boolean mIsWebPage;
private String mExt1;
private String mExt2;
private String mExt3;

public void setId(int id)
{
this.mId=id;	
}
public int getId()
{
return this.mId;	
}

public void setTitleImageUrl(String titleImageUrl){
	this.mTitleImageUrl=titleImageUrl;
}
public String getTitleImageUrl(){
	return this.mTitleImageUrl;
}
public void setTitle(String title)
{
this.mTitle=title;	
}
public String getTitle()
{
return this.mTitle;	
}

public void setRedirectUrl(String redirectUrl)
{
this.mRedirectUrl=redirectUrl;	
}
public String getRedirectUrl()
{
return this.mRedirectUrl;	
}

public void setDescription(String description)
{
this.mDescription=description;	
}
public String getDescription()
{
return this.mDescription;	
}

public void setIsWebPage(boolean isWebPage){
	this.mIsWebPage=isWebPage;
}
public boolean getIsWebPage()
{
return this.mIsWebPage;	
}

public void setExt1(String ext1){
	this.mExt1=ext1;
}
public String getExt1(){
	return this.mExt1;
}

public void setExt2(String ext2){
	this.mExt2=ext2;
}
public String getExt2(){
	return this.mExt2;
}

public void setExt3(String ext3){
	this.mExt3=ext3;
}
public String getExt3()
{
return this.mExt3;	
}
}
