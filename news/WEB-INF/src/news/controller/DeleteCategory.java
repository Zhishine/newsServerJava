package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import util.DatabaseManager;
import util.ShareDataManager;

public class DeleteCategory extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  int id=Integer.valueOf(request.getParameter("id")) ;
	  //long numId=Long.valueOf(request.getParameter("numId")) ;
	  if(id>0)
	  {
		 String sql="delete from category where id="+id;
		 String deleteNewsSql="delete from categoryNewsFK where categoryId="+id;
		 try {
		 DatabaseManager.getInstance().getStatement().execute(sql);
		 DatabaseManager.getInstance().getStatement().execute(deleteNewsSql);
		 PrintWriter out=response.getWriter();
		 out.write("true");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

		  Jedis jedis=ShareDataManager.getInstance().getJedis();
		  String categoryNewsKey=ShareDataManager.getInstance().getCategoryNewsKey(id);
		  String categoryImageKey=ShareDataManager.getInstance().getCategoryImageKey(id);
		  Long length=jedis.llen(categoryNewsKey);
		  if(jedis.exists(categoryNewsKey))
		  {
		  List<String> idList=jedis.lrange(categoryNewsKey, 0,length-1);
		  for(String idKey : idList)
		  {
			  jedis.del(idKey);
		  }
		  }
		  if(jedis.exists(categoryImageKey))
		  {
		  List<String> idList=jedis.lrange(categoryImageKey, 0,length-1);
		  for(String idKey : idList)
		  {
			  jedis.del(idKey);
		  }
		  }
		  ShareDataManager.getInstance().returnJedis(jedis);
	  
	 }
}
