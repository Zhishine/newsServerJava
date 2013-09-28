package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import news.modal.AppData;

import redis.clients.jedis.Jedis;
import util.ShareDataManager;

public class GetAppData extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  String type=request.getParameter("type");
	  List<String> idList=null;
	  Jedis jedis=ShareDataManager.getInstance().getJedis();
	  if(type==null||Integer.valueOf(type)==0)
	  {

	 //load from jedis
	  String APPListKey=ShareDataManager.getInstance().APPLISTKEY;
	  long length=jedis.llen(APPListKey);
	  if(!jedis.exists(APPListKey))
	     return;
	  idList=jedis.lrange(APPListKey,0,length-1);
	  }
	  else
	  {
		String id=request.getParameter("id");
		idList.add(id);
	  }
	  ArrayList<AppData> appList=new ArrayList<AppData>();
	  for(String id:idList){
		  AppData appData=new AppData();
		  appData.setId(Integer.valueOf(id));
		  String idKey=ShareDataManager.getInstance().getAppKey(Integer.valueOf(id));
		  String categoryName=jedis.get(idKey+":categoryName");
		  if(categoryName==null||categoryName.equalsIgnoreCase(""))
			  continue;
		  appData.setCategoryName(URLEncoder.encode(categoryName,"utf-8"));
		  String platform=jedis.get(idKey+":platform");
		  appData.setPlatform(platform);
		  String leftUpIconUrl=jedis.get(idKey+":leftUpIconUrl");
		  appData.setLeftUpIconUrl(leftUpIconUrl);
		  String rightUpIconUrl=jedis.get(idKey+":rightUpIconUrl");
		  appData.setRightUpIconUrl(rightUpIconUrl);
		  String leftDownIconUrl=jedis.get(idKey+":leftDownIconUrl");
		  appData.setLeftDownIconUrl(leftDownIconUrl);
		  String rightDownIconUrl=jedis.get(idKey+":rightDownIconUrl");
		  appData.setRightDownIconUrl(rightDownIconUrl);
		  String leftUpRedirectUrl=jedis.get(idKey+":leftUpRedirectUrl");
		  appData.setLeftUpRedirectUrl(leftUpRedirectUrl);
		  String rightUpRedirectUrl=jedis.get(idKey+":rightUpRedirectUrl");
		  appData.setRightUpRedirectUrl(rightUpRedirectUrl);
		  String leftDownRedirectUrl=jedis.get(idKey+":leftDownRedirectUrl");
		  appData.setLeftDownRedirectUrl(leftDownRedirectUrl);
		  String rightDownRedirectUrl=jedis.get(idKey+":rightDownRedirectUrl");
		  appData.setRightDownRedirectUrl(rightDownRedirectUrl);
		  boolean bannerIsShow=Boolean.valueOf(jedis.get(idKey+":bannerIsShow"));
		  appData.setBannerIsShow(bannerIsShow);
		  boolean taobaokeIsShow=Boolean.valueOf(jedis.get(idKey+":taobaokeIsShow"));
		  appData.setTaobaokeIsShow(taobaokeIsShow);
		  boolean adIsShow=Boolean.valueOf(jedis.get(idKey+":adIsShow"));
		  appData.setAdIsShow(adIsShow);
		  appList.add(appData);
	  }
	  if(appList.size()>0)
	    {
	    Gson gson=new Gson();
	    String jsonString= gson.toJson(appList);
	    PrintWriter out=response.getWriter();
	    out.write(jsonString);
	    }
	  ShareDataManager.getInstance().returnJedis(jedis);
	 }
}
