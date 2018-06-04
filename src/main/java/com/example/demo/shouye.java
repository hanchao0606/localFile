package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;



@Controller
public class shouye {

	@Value("${address}")
	private String dizhi;
	
	@Value("${daizuo}")
    private String daizuo;
	
	@RequestMapping("/shouye")
	public String test(Model model ){
		return "shouye.html";
	}
	
	 @ModelAttribute("main")
	 @ResponseBody
	    public List<Object> populateSeedStarters(Model model) {


			File fileDaiZuo = new File(dizhi+"\\"+daizuo);//待做文件
			File fileDaiZuoDate = new File(dizhi+"\\"+daizuo+"\\date");//待做文件数据
			/////////////////////////////////判断是否相等，不相等说明有新的文件加入///////////////////////////////////////////
			//查找新文件件名字
			Map<String,String> fileDaiZuoMap=new HashMap<>();//创建两个map用来查找两个待做文集加里面多余数据文件夹里面的文件名字
			Map<String,String> fileDaiZuoDateMap=new HashMap<>();//用map的 removeAll方法查找多余的文件
			if ((fileDaiZuo.length()-1)!= (fileDaiZuoDate.length())) {//判断是否相等，不相等说明有新的文件加入

				for(File file: fileDaiZuo.listFiles()){  
					System.out.println(""+file);  
					fileDaiZuoMap.put(file.getName(),file.getPath());  
				}  
				for(File file: fileDaiZuoDate.listFiles()){  
					System.out.println(""+file);  
					fileDaiZuoDateMap.put(file.getName(),file.getPath());  
				}  
				fileDaiZuoMap.keySet().removeAll(fileDaiZuoDateMap.keySet());
				fileDaiZuoMap.keySet().remove("date");

			}
			//////////////////////////新创建的文件创建数据json文件/////////////////////////////////
			for(String mm:fileDaiZuoMap.keySet()){
				System.out.println("    =====  :"+mm);
				String path=dizhi+"\\"+daizuo+"\\date\\"+mm+"\\"+mm+"date.json";//文件全路径
				File   fname = new File  (path);//创建file
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
			/////////////////////////////判断是否相等，不相等说明有新的文件加入功能结束//////添加的新文件的数据完成///////////下面读取json文件/////////////////////////////////////	

			/*Map< String, String>  map = new HashMap<> ();*/
			List<Object> list=new ArrayList<>();
			for(File file: fileDaiZuoDate.listFiles()){  
				System.out.println(file.getPath());
				list.add( JSON.parseObject(readFileByLines(file.getPath()+"\\"+file.getName()+"date.json")));
			}

			//Map< String, Object>  resmap = new HashMap<> ();
			model.addAttribute("main", list);

	        return list;
	    }
	
	@RequestMapping("/test2")
	public String tem() {
		
		return "seedstartermng";
		
	}

	//新建数据的模板
	public  String template(String name,String address)  {
		JSONObject jo= new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();  
		JSONArray array = new JSONArray(); 
		try {
			jo.put("name", name);
			jo.put("address", address);
			map.put("wall", "");//墙
			map.put("wallArea", "");//墙面积
			map.put("kArea", "");//k面积
			map.put("wallBack", "");//墙背楞长度
			map.put("beam", "");//梁
			map.put("beamArea", "");//梁面积
			map.put("beamBack", "");//梁背楞
			map.put("top", "");//顶
			map.put("topArea", "");//顶板面积
			map.put("hanging", "");//吊	
			map.put("hangingArea", "");//吊模面积
			map.put("stairs", "");//楼梯
			map.put("stairsArea", "");//楼梯面积
			map.put("stairsBack", "");//楼梯背楞
			map.put("TemplateAreaCombined", "");//模板面积合计
			map.put("BackLength", "");//背楞长度合计
			map.put("state","1");//状态
			map.put("forecastArea", "");//预估面积
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



	/**
	 * 读取文件内容
	 * 返回string
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public  String readFileByLines(String fileName) {
		StringBuilder sb=new StringBuilder();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				//                System.out.println("line " + line + ": " + tempString);
				//                line++;
				sb.append(tempString);

			}
			reader.close();
			//System.out.println("sb11111111111111111:"+sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return sb.toString();
	}
}
