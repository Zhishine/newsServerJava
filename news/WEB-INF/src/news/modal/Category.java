package news.modal;

import java.io.Serializable;

public class Category implements Serializable {
private int mId;
private String mName;

public int getId()
{
return mId;	
}
public void setId(int id)
{
this.mId=id;
}
public String getName()
{
return mName;	
}
public void setName(String name)
{
this.mName=name;
}

}
