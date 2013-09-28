package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import news.modal.Image;
import news.modal.News;
import util.DatabaseManager;

import com.google.gson.Gson;

public class SelectImage extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 int type=Integer.valueOf(request.getParameter("type"));
		 String sql=null;
		 switch(type)
		 {
		 case 0:
			 int pageIndex=Integer.valueOf(request.getParameter("pageIndex"));
			 int pageSize=Integer.valueOf(request.getParameter("pageSize"));
			 int categoryId=Integer.valueOf(request.getParameter("categoryId"));
			 int start=pageIndex*pageSize;
			 sql="select * from image i,categoryImageFK f where f.imageId=i.id and f.categoryId="+categoryId+" limit "+start+","+pageSize;
			 break;
		 case 1:
			 int id=Integer.valueOf(request.getParameter("id"));
		     sql="select * from image where id="+id;	
			 break;
		 case 2:
			  String key=new String(request.getParameter("key").getBytes("ISO-8859-1"), "UTF-8");
		     sql="select * from image where description like '%"+key+"%'";	
			 break;
		 }
	
		 try {
		 ResultSet result=DatabaseManager.getInstance().getStatement().executeQuery(sql);
		 ArrayList<Image> list=new ArrayList<Image>();
		 while(result.next())
		 {
			 Image image=new Image();
			 image.setId(result.getInt("id"));
			 image.setImageUrl(result.getString("imageUrl"));
			 image.setRedirectUrl(result.getString("redirectUrl"));
			 if(result.getInt("isNativePage")==0)
				 image.setIsNativePage(true);
			 else
				 image.setIsNativePage(false);
			 image.setDescription(URLEncoder.encode(result.getString("description"),"utf-8"));
			 image.setWidth(result.getInt("width"));
			 image.setHeight(result.getInt("height"));
			 image.setExt1(result.getString("ext1"));
			 image.setExt2(result.getString("ext2"));
			 image.setExt3(result.getString("ext3"));
			 list.add(image);
		 }
		 if(list.size()>0)
		 {
			 Gson gson=new Gson();
			 String gsonStr=gson.toJson(list);
			 int length=gsonStr.length();
			 PrintWriter out=response.getWriter();
			 response.addHeader("content-length",String.valueOf(length));
			 out.write(gsonStr);
		 }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
