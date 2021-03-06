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

public class DeleteImage extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  int id=Integer.valueOf(request.getParameter("id")) ;
	  //long numId=Long.valueOf(request.getParameter("numId")) ;
	  if(id>0)
	  {
		 String sql="delete from image where id="+id;
		 String categoryImageFKSql="delete from categoryImageFK where imageId="+id;
		 try {
		 DatabaseManager.getInstance().getStatement().execute(sql);
		 DatabaseManager.getInstance().getStatement().execute(categoryImageFKSql);
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

		  String idKey=ShareDataManager.getInstance().getImageKey(Integer.valueOf(id));
	      Jedis jedis=ShareDataManager.getInstance().getJedis();
	 
	      jedis.del(idKey+":description");
	      jedis.del(idKey+":redirectUrl");
	      jedis.del(idKey+":imageUrl");
	      jedis.del(idKey+":isNativePage");
	      jedis.del(idKey+":width");
	      jedis.del(idKey+":height");
	      jedis.del(idKey+":ext1");
	      jedis.del(idKey+":ext2");
	      jedis.del(idKey+":ext3");
	      ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
