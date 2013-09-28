package news.modal;

public class Image {
	/* `id` int(11) NOT NULL AUTO_INCREMENT,
	  `imageUrl` varchar(512) NOT NULL,
	  `width` int(11) NOT NULL,
	  `height` int(11) NOT NULL,
	  `isNativePage` int(11) NOT NULL DEFAULT '0',
	  `redirectUrl` varchar(512),
	  `description` varchar(512),
	  `ext1` varchar(512),
	  `ext2` varchar(512),
	  `ext3` varchar(512),*/
	private int mId;
	private String mImageUrl;
	private int mHeight;
	private int mWidth;
	private boolean mIsNativePage;
	private String mRedirectUrl;
	private String mDescription;
	private String mExt1;
	private String mExt2;
	private String mExt3;
	
	public void setId(int id){
		this.mId=id;
	}
	public int getId(){
		return this.mId;
	}
	public void setImageUrl(String imageUrl){
		this.mImageUrl=imageUrl;
	}
	public String getImageUrl(){
		return this.mImageUrl;
	}
	public void setHeight(int height){
		this.mHeight=height;
	}
	public int getHeight(){
		return this.mHeight;
	}
	public void setWidth(int width){
		this.mWidth=width;
	}
	public int getWidth(){
		return this.mWidth;
	}
    public void setIsNativePage(boolean isNativePage){
    	this.mIsNativePage=isNativePage;
    }
public boolean getIsNativePage(){
    	return this.mIsNativePage;
    }
    public void setRedirectUrl(String redirectUrl){
    	this.mRedirectUrl=redirectUrl;
    }
    public String getRedirectUrl(){
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
