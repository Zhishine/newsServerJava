package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

import util.DatabaseManager;
import util.ShareDataManager;
import news.modal.Product;

public class GetProduct extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  String type=request.getParameter("type");
	  List<String> numIdList=null;
	  Jedis jedis=ShareDataManager.getInstance().getJedis();
	  if(type==null||Integer.valueOf(type)==0)
	  {
	  int pageNO=Integer.valueOf(request.getParameter("pageNO")) ;
	  int pageSize=Integer.valueOf(request.getParameter("pageSize")) ;
	  int categoryId=Integer.valueOf(request.getParameter("categoryId"));
	  if(pageNO<=0)
		  pageNO=1;
	  if(pageSize<=0)
		  pageSize=40;
	 //load from jedis
	  String categoryKey=ShareDataManager.getInstance().getCategoryProductKey(categoryId);
		 
	  if(!jedis.exists(categoryKey))
	     return;
	  numIdList=jedis.lrange(categoryKey, (pageNO-1)*pageSize,pageNO*pageSize-1);
	  }
	  else
	  {
	  numIdList=new ArrayList<String>();
	  String numIdStr=request.getParameter("numId");
	  numIdList.add(numIdStr);
	  }
	  if(numIdList!=null)
	  {
		ArrayList array=new ArrayList();
	    for(String numId:numIdList)
	    {
	    String numIdKey=ShareDataManager.getInstance().getProductKey(Long.valueOf(numId));
		if(jedis.exists(numIdKey+":title"))
		 {
			String oldPrice=jedis.get(numIdKey+":oldPrice");
			if(oldPrice!=null&&oldPrice!="")
			{
		Product product=new Product();
	    product.setNumId(Long.valueOf(numId));
	    if(type!=null&&Integer.valueOf(type)==1)
		  {
	    product.setTitle(URLEncoder.encode(jedis.get(numIdKey+":title"),"utf-8"));
	    product.setPCUrl(jedis.get(numIdKey+":pcClickUrl"));
	    product.setMobileUrl(jedis.get(numIdKey+":mobileClickUrl"));
		  }
        product.setImageUrl(jedis.get(numIdKey+":imageUrl"));
        product.setOldPrice(Float.valueOf(jedis.get(numIdKey+":oldPrice")));
        product.setNewPrice(Float.valueOf(jedis.get(numIdKey+":newPrice")));
        product.setImageRate(Float.valueOf(jedis.get(numIdKey+":imageRate")));
        array.add(product);
		 }
		 }
	    }
	    if(array.size()>0)
	    {
	    Gson gson=new Gson();
	    String jsonString= gson.toJson(array);
	    PrintWriter out=response.getWriter();
	    out.write(jsonString);
	    }
	  }
	  ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
