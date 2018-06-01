package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;



public class ceshiFile {

	public static void main(String[] args) {
		File fileDaiZuo = new File("C:\\Users\\Administrator\\Desktop\\zhiyuan"+"\\daizuo");//待做文件

		File fileDaiZuoDate = new File("C:\\Users\\Administrator\\Desktop\\zhiyuan"+"\\daizuo\\date");//待做文件数据

		if ((fileDaiZuo.length()-1)!= (fileDaiZuoDate.length())) {//判断是否相等，不相等说明有新的文件加入
			//查找新文件件名字
			Map<String,String> fileDaiZuoMap=new HashMap<>();//创建两个map用来查找两个待做文集加里面多余数据文件夹里面的文件名字
			Map<String,String> fileDaiZuoDateMap=new HashMap<>();//用map的 removeAll方法查找多余的文件
			System.out.println("  长度：     "+fileDaiZuo.listFiles().length);
			for(File file: fileDaiZuo.listFiles()){  
				//System.out.println(""+file);  
				fileDaiZuoMap.put(file.getName(),file.getPath());  
			}  
			for(File file: fileDaiZuoDate.listFiles()){  
				//System.out.println(""+file);  
				fileDaiZuoDateMap.put(file.getName(),file.getPath());  
			}  
			fileDaiZuoMap.keySet().removeAll(fileDaiZuoDateMap.keySet());
			fileDaiZuoMap.keySet().remove("date");
         for(String mm:fileDaiZuoMap.keySet()){
        	 System.out.println("    =====  :"+mm);
        	 String path="C:\\Users\\Administrator\\Desktop\\zhiyuan"+"\\daizuo\\date\\"+mm+"\\"+mm+"date.json";
        	  File   fname = new File  (path);
        	  
        	  try {
        		  File dir = new File(fname.getParent());  
                  dir.mkdirs();  
                  fname.createNewFile(); 
        		  fname.createNewFile();
				FileWriter	filexj = new FileWriter(fname);
				BufferedWriter bufw =new BufferedWriter(filexj);
					bufw.write(template(mm,path));
					bufw.flush();
					bufw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 
        	
         }
		}

	}
	
	public static String template(String name,String address)  {
		 
		JSONObject jo= new JSONObject();
		 Map<String, Object> map = new HashMap<String, Object>();  
		 JSONArray array = new JSONArray(); 
		try {
			jo.put("name", name);
			jo.put("address", address);
			map.put("wall", "");
			map.put("wallArea", "");
			map.put("kArea", "kArea");
			jo.put("info", map);
			jo.put("add", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String st=jo.toString();
		System.out.println("  json  : "+st);
		return st;
	}

}
