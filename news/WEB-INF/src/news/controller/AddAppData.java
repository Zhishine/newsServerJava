package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import util.DatabaseManager;
import util.ShareDataManager;

public class AddAppData extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		   PrintWriter out=response.getWriter();
		   Jedis jedis=ShareDataManager.getInstance().getJedis();
		  //URLDecoder.decode(request.getParameter("参数名"),"UTF-8");
		  boolean isEdit=Boolean.valueOf(request.getParameter("isEdit"));
		  int id;
		  id=0;
		  if(isEdit)
			  id=Integer.valueOf(request.getParameter("id"));
		  String leftUpIconUrl=request.getParameter("leftUpIconUrl");
		  String rightUpIconUrl=request.getParameter("rightUpIconUrl");
		  String leftDownIconUrl=request.getParameter("leftDownIconUrl");
		  String rightDownIconUrl=request.getParameter("rightDownIconUrl");
		  String leftUpRedirectUrl=request.getParameter("leftUpRedirectUrl");
		  String rightUpRedirectUrl=request.getParameter("rightUpRedirectUrl");
		  String leftDownRedirectUrl=request.getParameter("leftDownRedirectUrl");
		  String rightDownRedirectUrl=request.getParameter("rightDownRedirectUrl");
		  boolean bannerIsShow=Boolean.valueOf(request.getParameter("bannerIsShow"));
		  boolean taobaokeIsShow=Boolean.valueOf(request.getParameter("taobaokeIsShow"));
		  boolean adIsShow=Boolean.valueOf(request.getParameter("adIsShow"));
		  String platform=request.getParameter("platform");
		  String categoryName=new String(request.getParameter("categoryName").getBytes("ISO-8859-1"), "UTF-8");
		 
		  String redisKey="";
	      if(!isEdit)
	      {
	    	  long idLong=jedis.incr(ShareDataManager.APPIDKEY);
	    	 id=Integer.valueOf(String.valueOf(idLong));
	      }
	         redisKey=ShareDataManager.getInstance().getAppKey(id);
	    	 jedis.set(redisKey+":id",String.valueOf(id));
	    	 jedis.set(redisKey+":platform",platform);
	    	 jedis.set(redisKey+":categoryName",categoryName);
	    	 jedis.set(redisKey+":leftUpIconUrl",leftUpIconUrl);
	    	 jedis.set(redisKey+":rightUpIconUrl",rightUpIconUrl);
	    	 jedis.set(redisKey+":leftDownIconUrl",leftDownIconUrl);
	    	 jedis.set(redisKey+":rightDownIconUrl",rightDownIconUrl);
	    	 jedis.set(redisKey+":leftUpRedirectUrl",leftUpRedirectUrl);
	    	 jedis.set(redisKey+":rightUpRedirectUrl",rightUpRedirectUrl);
	    	 jedis.set(redisKey+":leftDownRedirectUrl",leftDownRedirectUrl);
	    	 jedis.set(redisKey+":rightDownRedirectUrl",rightDownRedirectUrl);
	    	 jedis.set(redisKey+":bannerIsShow",String.valueOf(bannerIsShow));
	    	 jedis.set(redisKey+":taobaokeIsShow",String.valueOf(taobaokeIsShow));
	    	 jedis.set(redisKey+":adIsShow",String.valueOf(adIsShow));
	    	 if(!isEdit)
	    		 jedis.lpush(ShareDataManager.APPLISTKEY,String.valueOf(id));
	    	 ShareDataManager.getInstance().returnJedis(jedis);
			 out.write("1");
	 }
}
