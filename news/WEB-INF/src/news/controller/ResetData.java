package news.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import util.ShareDataManager;


public class ResetData extends HttpServlet {
	 String version="1.0.0";
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
		    	jedis.set("news:version",version);
		        jedis.set("news:publicadlasttime","0");
		        ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
