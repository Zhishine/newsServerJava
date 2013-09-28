package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import news.modal.Image;
import news.modal.News;
import redis.clients.jedis.Jedis;
import util.ShareDataManager;

import com.google.gson.Gson;

public class GetImage extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  String type=request.getParameter("type");
	  List<String> idList=null;
	  Jedis jedis=ShareDataManager.getInstance().getJedis();
	  if(type==null||Integer.valueOf(type)==0)
	  {
	  int pageNO=Integer.valueOf(request.getParameter("pageNO")) ;
	  int pageSize=Integer.valueOf(request.getParameter("pageSize")) ;
	  int categoryId=Integer.valueOf(request.getParameter("categoryId")) ;
	  if(pageNO<=0)
		  pageNO=1;
	  if(pageSize<=0)
		  pageSize=40;
	 //load from jedis
	  String categoryKey=ShareDataManager.getInstance().getCategoryImageKey(categoryId);
	 
	  if(!jedis.exists(categoryKey))
	     return;
	  idList=jedis.lrange(categoryKey, (pageNO-1)*pageSize,pageNO*pageSize-1);
	  }
	  else
	  {
	  idList=new ArrayList<String>();
	  String idStr=request.getParameter("id");
	  idList.add(idStr);
	  }
	  if(idList!=null)
	  {
		ArrayList<Image> array=new ArrayList<Image>();
	    for(String id:idList)
	    {
	    String idKey=ShareDataManager.getInstance().getImageKey(Integer.valueOf(id));
		if(jedis.exists(idKey+":imageUrl"))
		 {
			 Image image=new Image();
			 image.setId(Integer.valueOf(id));
			 image.setImageUrl(jedis.get(idKey+":imageUrl"));
			 image.setRedirectUrl(jedis.get(idKey+":redirectUrl"));
			 image.setIsNativePage(Boolean.valueOf(jedis.get(idKey+":isNativePage")));
			 image.setDescription(URLEncoder.encode(jedis.get(idKey+":description"),"utf-8"));
			 image.setExt1(jedis.get(idKey+":ext1"));
			 image.setExt2(jedis.get(idKey+":ext2"));
			 image.setExt3(jedis.get(idKey+":ext3"));
			 image.setWidth(Integer.valueOf(jedis.get(idKey+":width")));
			 image.setHeight(Integer.valueOf(jedis.get(idKey+":height")));
            array.add(image);
		 }
		 }
	    if(array.size()>0)
	    {
	    Gson gson=new Gson();
	    String jsonString= gson.toJson(array);
	    PrintWriter out=response.getWriter();
	    out.write(jsonString);
	    }
	  }
	  ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
