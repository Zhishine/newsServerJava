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

public class AddImage extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		PrintWriter out=response.getWriter();
	  //URLDecoder.decode(request.getParameter("参数名"),"UTF-8");
	  boolean isEdit=Boolean.valueOf(request.getParameter("isEdit"));
	  String description=new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
	  String redirectUrl=request.getParameter("redirectUrl");
	  int nativePage=0;
	  if(request.getParameter("isNativePage").equalsIgnoreCase("false"))
		  nativePage=1;
	  String imageUrl=request.getParameter("imageUrl");
	  String ext1=request.getParameter("ext1");
	  String ext2=request.getParameter("ext2");
	  String ext3=request.getParameter("ext3");
	  int width=Integer.valueOf(request.getParameter("width"));
	  int height=Integer.valueOf(request.getParameter("height"));
	  int id=0;
	  if(request.getParameter("id")!=null)
	  id=Integer.valueOf(request.getParameter("id"));
	 
	  int categoryId=Integer.valueOf(request.getParameter("categoryId"));

		 try {
		 if(!isEdit)
		 {
	     String existSql="select count(*) from image where imageUrl='"+imageUrl+"'";
	     ResultSet existResult=DatabaseManager.getInstance().getStatement().executeQuery(existSql);
	     existResult.next();
	     if(existResult.getInt(1)>0)
	     {
	    	 out.write("2");
			 return;
	     }
	     String insertSql="insert into image(description,redirectUrl,imageUrl,isNativePage,width,height,ext1,ext2,ext3)" +
				 		" values('"+description+"','"+redirectUrl+"','"+imageUrl+"',"+nativePage+","+width+","+height+",'"+ext1+"','"+ext2+"','"+ext3+"')";
		 DatabaseManager.getInstance().getStatement().execute(insertSql);
		 String sql="select max(id) from image";
		 ResultSet result=DatabaseManager.getInstance().getStatement().executeQuery(sql);
		 result.next();
		 int idInt=result.getInt(1);
			 String insertCategorySql="insert into categoryImageFK(categoryId,imageId) values("+categoryId+","+idInt+")";
			 DatabaseManager.getInstance().getStatement().execute(insertCategorySql);
	         Jedis jedis=ShareDataManager.getInstance().getJedis();
			 String idKey=ShareDataManager.getInstance().getImageKey(idInt);
			 
			 jedis.set(idKey+":description",description);
			 jedis.set(idKey+":redirectUrl",redirectUrl);
			 jedis.set(idKey+":imageUrl",imageUrl);
			 if(nativePage==0)
			 jedis.set(idKey+":isNativePage","true");
			 else
			 jedis.set(idKey+":isNativePage","false");
			 jedis.set(idKey+":width",String.valueOf(width));
			 jedis.set(idKey+":height",String.valueOf(height));
			 jedis.set(idKey+":ext1",ext1);
			 jedis.set(idKey+":ext2",ext2);
			 jedis.set(idKey+":ext3",ext3);
			 String categoryIdKey=ShareDataManager.getInstance().getCategoryImageKey(categoryId);
			 jedis.lpush(categoryIdKey,String.valueOf(idInt));
			 ShareDataManager.getInstance().returnJedis(jedis);
		
		 out.write("1");
		 return;
		 }
		 else
		 {
	     String updateSql="update image set  description='"+description+"'," +
	     		" redirectUrl='"+redirectUrl+"', imageUrl='"+imageUrl+"', isNativePage="+nativePage+", width="+width+", height="+height+", ext1='"+ext1+"', " +
	     				"ext2='"+ext2+"', ext3='"+ext3+"' where id="+id;
		 DatabaseManager.getInstance().getStatement().execute(updateSql);
		 Jedis jedis=ShareDataManager.getInstance().getJedis();
		 String idKey=ShareDataManager.getInstance().getImageKey(id);
		 
		 jedis.set(idKey+":description",description);
		 jedis.set(idKey+":redirectUrl",redirectUrl);
		 jedis.set(idKey+":imageUrl",imageUrl);
		 if(nativePage==0)
		 jedis.set(idKey+":isNativePage","true");
		 else
		 jedis.set(idKey+":isNativePage","false");
		 jedis.set(idKey+":width",String.valueOf(width));
		 jedis.set(idKey+":height",String.valueOf(height));
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
