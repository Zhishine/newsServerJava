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

public class UpdateVersion extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
	     String version=request.getParameter("version");
	     String url=request.getParameter("url");
	     String description=new String(request.getParameter("versionDescription").getBytes("ISO-8859-1"), "UTF-8");;
	     jedis.set("news:version", version);
	     jedis.set("news:versionurl", url);
	     jedis.set("news:versiondescription", description);
	     ShareDataManager.getInstance().returnJedis(jedis);
	 }
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
	     String version=request.getParameter("version");
	     String url=request.getParameter("url");
	     String description=new String(request.getParameter("versionDescription").getBytes("ISO-8859-1"), "UTF-8");;
	     jedis.set("news:version", version);
	     jedis.set("news:versionurl", url);
	     jedis.set("news:versiondescription", description);
	     PrintWriter out=response.getWriter();
	     out.write("true");
	     ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
