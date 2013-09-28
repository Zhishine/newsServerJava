package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import util.ShareDataManager;
import news.modal.Ad;

public class GetAd extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		Jedis jedis=ShareDataManager.getInstance().getJedis();
 		ArrayList<Ad> adList=new ArrayList<Ad>();
		for(int i=1;i<5;i++)
		{
			if(jedis.exists("xingmeitao:ad:imageUrl:"+i))
			{
				Ad ad=new Ad();
				ad.setImageUrl(jedis.get("xingmeitao:ad:imageUrl:"+i));
				ad.setInfo(jedis.get("xingmeitao:ad:info:"+i));
				ad.setOrder(i);
				adList.add(ad);
			}
		}
		if(adList.size()>0)
		{
			 Gson gson=new Gson();
			 String jsonString= gson.toJson(adList);
			 PrintWriter out=response.getWriter();
			 out.write(jsonString);
		}
		ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
