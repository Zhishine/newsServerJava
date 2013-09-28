package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import util.DatabaseManager;
import util.ShareDataManager;

public class UpdateAd extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		Jedis jedis=ShareDataManager.getInstance().getJedis();
		String imageUrl=request.getParameter("imageUrl");
		String info=request.getParameter("info");
		int order=Integer.valueOf(request.getParameter("order"));
	    jedis.set("xingmeitao:ad:imageUrl:"+order,imageUrl);
	    jedis.set("xingmeitao:ad:info:"+order,info);
	    PrintWriter out=response.getWriter();
	    out.write("true");
	    ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
