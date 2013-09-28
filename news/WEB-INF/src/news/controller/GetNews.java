package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import news.modal.News;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

import util.DatabaseManager;
import util.ShareDataManager;


public class GetNews extends HttpServlet {
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
	  String categoryKey=ShareDataManager.getInstance().getCategoryNewsKey(categoryId);
	 
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
		ArrayList<News> array=new ArrayList<News>();
	    for(String id:idList)
	    {
	    String idKey=ShareDataManager.getInstance().getNewsKey(Integer.valueOf(id));
		if(jedis.exists(idKey+":title"))
		 {
			 News news=new News();
			 news.setId(Integer.valueOf(id));
			 news.setTitle(URLEncoder.encode(jedis.get(idKey+":title"),"utf-8"));
			 news.setTitleImageUrl(jedis.get(idKey+":titleImageUrl"));
			 news.setRedirectUrl(jedis.get(idKey+":redirectUrl"));
			 news.setIsWebPage(Boolean.valueOf(jedis.get(idKey+":isWebPage")));
			 news.setDescription(URLEncoder.encode(jedis.get(idKey+":description"),"utf-8"));
			 news.setExt1(jedis.get(idKey+":ext1"));
			 news.setExt2(jedis.get(idKey+":ext2"));
			 news.setExt3(jedis.get(idKey+":ext3"));

             array.add(news);
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
