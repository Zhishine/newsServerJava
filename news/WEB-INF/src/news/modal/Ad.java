package news.modal;

public class Ad {
private int mOrder;

private String mImageUrl;
private String mInfo;
public void setOrder(int order)
{
this.mOrder=order;	
}
public int getOrder()
{
return this.mOrder;	
}

public void setImageUrl(String imageUrl)
{
this.mImageUrl=imageUrl;
}
public String getImageUrl()
{
return this.mImageUrl;	
}
public void setInfo(String info)
{
this.mInfo=info;	
}
public String getInfo()
{
return this.mInfo;	
}
}
