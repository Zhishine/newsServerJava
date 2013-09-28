package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import util.ShareDataManager;

public class PublicAd extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		Date date=new Date();
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
		jedis.set("news:publicadlasttime",String.valueOf(date.getTime()));
	    PrintWriter out=response.getWriter();
	    out.write("true");
	    ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
