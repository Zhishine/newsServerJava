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

public class DeleteKey extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  String name=request.getParameter("key") ;
	  Jedis jedis=ShareDataManager.getInstance().getJedis();
	  jedis.del(name);
	  ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
