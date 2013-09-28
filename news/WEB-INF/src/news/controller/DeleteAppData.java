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

public class DeleteAppData extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  int id=Integer.valueOf(request.getParameter("id")) ;
	  //long numId=Long.valueOf(request.getParameter("numId")) ;
		  String idKey=ShareDataManager.getInstance().getAppKey(id);
		  String appListKey=ShareDataManager.APPLISTKEY;
	      Jedis jedis=ShareDataManager.getInstance().getJedis();
	      jedis.del(idKey+":id");
	      jedis.del(idKey+":categoryName");
	      jedis.del(idKey+":platform");
	      jedis.del(idKey+":leftUpIconUrl");
	      jedis.del(idKey+":rightUpIconUrl");
	      jedis.del(idKey+":leftDownIconUrl");
	      jedis.del(idKey+":rightDownIconUrl");
	      jedis.del(idKey+":leftUpRedirectUrl");
	      jedis.del(idKey+":rightUpRedirectUrl");
	      jedis.del(idKey+":leftDownRedirectUrl");
	      jedis.del(idKey+":rightDownRedirectUrl");
	      jedis.del(idKey+":bannerIsShow");
	      jedis.del(idKey+":taobaokeIsShow");
	      jedis.del(idKey+":adIsShow");
	      ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
