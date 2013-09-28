package news.modal;

import java.io.Serializable;

public class Product implements Serializable {
	private long mNumId;
	private String mTitle;
	private String mMobileClickUrl;
	private String mPcClickUrl;
	private float mNewPrice;
	private float mOldPrice;
	private String mImageUrl;
    private float mImageRate;
	public Product()
	{
		
	}
	public long getNumId()
	{
	     return mNumId;	
	}

	public void setNumId(long numId)
	{
		this.mNumId=numId;
	}

	public String getTitle()
	{
	     return mTitle;	
	}

	public void setTitle(String title)
	{
	     this.mTitle=title;	
	}

	public String getMobileUrl()
	{
	     return mMobileClickUrl;	
	}

	public void setMobileUrl(String mobileUrl)
	{
	     this.mMobileClickUrl=mobileUrl;	
	}

	public String getPCUrl()
	{
	      return mPcClickUrl;	
	}

	public void setPCUrl(String pcUrl)
	{
	      this.mPcClickUrl=pcUrl;
	}

	public float getNewPrice()
	{
	       return mNewPrice;	
	}

	public void setNewPrice(float newPrice)
	{
	       this.mNewPrice=newPrice;	
	}

	public float getOldPrice()
	{
	       return mOldPrice;
	}

	public void setOldPrice(float oldPrice)
	{
	        this.mOldPrice=oldPrice;	
	}

	public String getImageUrl()
	{
	        return mImageUrl;	
	}

	public void setImageUrl(String imageUrl)
	{
	        this.mImageUrl=imageUrl;	
	}
	
	public float getImageRate()
	{
		return mImageRate;
	}
	
	public void setImageRate(float imageRate)
	{
		this.mImageRate=imageRate;
	}
}
