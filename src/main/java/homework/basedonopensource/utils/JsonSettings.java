package homework.basedonopensource.utils;

import java.util.HashMap;

public class JsonSettings {
	String filename="";
	HashMap hmap=new HashMap();
	
	public JsonSettings(String filename){
		this.filename=filename;
		String settings=StringUtils.readTxtFile(filename);
		if(settings==null) {
			settings="{}";
		}
		hmap=JsonUtils.deserialize(settings, hmap.getClass());
		
	}
	
	public Object getConfig(Object key) {
		return hmap.get(key);
	}
	
	public void writeConfig(Object key,Object val) {
		if(hmap.containsKey(key)) {
			hmap.replace(key, val);
		}else {
			hmap.put(key, val);
		}
	}
	
	public void saveConfig() {
		StringUtils.saveTxtFile(JsonUtils.serialize(hmap), filename);
	}
}
