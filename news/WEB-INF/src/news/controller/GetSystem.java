package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

import util.DatabaseManager;
import util.ShareDataManager;
import news.modal.SystemInfo;

public class GetSystem extends HttpServlet {
	 String version="1.0.0";
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
		    if(!jedis.exists("news:version"))
		    	jedis.set("news:version",version);
		    
		    if(!jedis.exists("xingmeitao:publicadlasttime"))
		        jedis.set("xingmeitao:publicadlasttime","0");
            SystemInfo sys=new SystemInfo();
            sys.setVersion(jedis.get("xingmeitao:version"));
            sys.setVersionUrl(jedis.get("xingmeitao:versionurl"));
            sys.setPublicAdLastTime(Long.valueOf(jedis.get("xingmeitao:publicadlasttime")));
            sys.setVersionDescription(URLEncoder.encode(jedis.get("xingmeitao:versiondescription"),"utf-8"));
            Gson gson=new Gson();
    	    String jsonString= gson.toJson(sys);
    	    PrintWriter out=response.getWriter();
    	    out.write(jsonString);
    	    ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
