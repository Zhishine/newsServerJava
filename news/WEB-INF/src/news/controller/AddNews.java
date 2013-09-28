package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import util.DatabaseManager;
import util.ShareDataManager;

public class AddNews extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		PrintWriter out=response.getWriter();
	  //URLDecoder.decode(request.getParameter("参数名"),"UTF-8");
	  boolean isEdit=Boolean.valueOf(request.getParameter("isEdit"));
	  String title=new String(request.getParameter("title").getBytes("ISO-8859-1"), "UTF-8");
	  String description=new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
	  String redirectUrl=request.getParameter("redirectUrl");
	  int webPage=0;
	  if(request.getParameter("isWebPage").equalsIgnoreCase("false"))
		  webPage=1;
	  
	  String titleImageUrl=request.getParameter("titleImageUrl");
	  String ext1=request.getParameter("ext1");
	  String ext2=request.getParameter("ext2");
	  String ext3=request.getParameter("ext3");
	  int id=0;
	  if(request.getParameter("id")!=null)
	  id=Integer.valueOf(request.getParameter("id"));
	 
	  int categoryId=Integer.valueOf(request.getParameter("categoryId"));

		 try {
		 if(!isEdit)
		 {
			   String existSql="select count(*) from news where redirectUrl='"+redirectUrl+"'";
			     ResultSet existResult=DatabaseManager.getInstance().getStatement().executeQuery(existSql);
			     existResult.next();
			     if(existResult.getInt(1)>0)
			     {
			    	 out.write("2");
					 return;
			     }
	     String insertSql="insert into news(title,description,redirectUrl,titleImageUrl,isWebPage,ext1,ext2,ext3)" +
				 		" values('"+title+"','"+description+"','"+redirectUrl+"','"+titleImageUrl+"',"+webPage+",'"+ext1+"','"+ext2+"','"+ext3+"')";
		 DatabaseManager.getInstance().getStatement().execute(insertSql);
		 String sql="select max(id) from news";
		 ResultSet result=DatabaseManager.getInstance().getStatement().executeQuery(sql);
		 result.next();
		 int idInt=result.getInt(1);
			 String insertCategorySql="insert into categoryNewsFK(categoryId,newsId) values("+categoryId+","+idInt+")";
			 DatabaseManager.getInstance().getStatement().execute(insertCategorySql);
	         Jedis jedis=ShareDataManager.getInstance().getJedis();
			 String idKey=ShareDataManager.getInstance().getNewsKey(idInt);
			 jedis.set(idKey+":title",title);
			 jedis.set(idKey+":description",description);
			 jedis.set(idKey+":redirectUrl",redirectUrl);
			 jedis.set(idKey+":titleImageUrl",titleImageUrl);
			 if(webPage==0)
			 jedis.set(idKey+":isWebPage","true");
			 else
			 jedis.set(idKey+":isWebPage","false");
			 jedis.set(idKey+":ext1",ext1);
			 jedis.set(idKey+":ext2",ext2);
			 jedis.set(idKey+":ext3",ext3);
			 String categoryIdKey=ShareDataManager.getInstance().getCategoryNewsKey(categoryId);
			 jedis.lpush(categoryIdKey,String.valueOf(idInt));
			 ShareDataManager.getInstance().returnJedis(jedis);
		
		 out.write("1");
		 return;
		 }
		 else
		 {
	     String updateSql="update news set title='"+title+"', description='"+description+"'," +
	     		" redirectUrl='"+redirectUrl+"', titleImageUrl='"+titleImageUrl+"', isWebPage="+webPage+", ext1='"+ext1+"', " +
	     				"ext2='"+ext2+"', ext3='"+ext3+"' where id="+id;
		 DatabaseManager.getInstance().getStatement().execute(updateSql);
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
		 String idKey=ShareDataManager.getInstance().getNewsKey(id);
		 jedis.set(idKey+":title",title);
		 jedis.set(idKey+":description",description);
		 jedis.set(idKey+":redirectUrl",redirectUrl);
		 jedis.set(idKey+":titleImageUrl",titleImageUrl);
		 if(webPage==0)
		 jedis.set(idKey+":isWebPage","true");
		 else
		 jedis.set(idKey+":isWebPage","false");
		 jedis.set(idKey+":ext1",ext1);
		 jedis.set(idKey+":ext2",ext2);
		 jedis.set(idKey+":ext3",ext3);
		 ShareDataManager.getInstance().returnJedis(jedis);
		 out.write("1");
		 return;
		 }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			out.write("3");
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.write("3");
			e.printStackTrace();
			return;
		}
	  }
	 
}
