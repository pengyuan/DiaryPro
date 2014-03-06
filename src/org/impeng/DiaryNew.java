package org.impeng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class DiaryNew extends Activity {
	EditText textTitle;
	EditText textContent;
	Spinner spinner;
	DiaryVo vo;
	DiaryDao dao;
	String weather;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit);
	    Button buttonSave = (Button)findViewById(R.id.edit_btn_save);
	    textTitle = (EditText)findViewById(R.id.edit_edit_title);
	    textContent = (EditText)findViewById(R.id.edit_edit_content);
	    spinner = (Spinner) findViewById(R.id.spinner);	  
	    List<Integer> list = new ArrayList<Integer>();
	    list.add(R.drawable.sun);
	    list.add(R.drawable.cloud);
	    list.add(R.drawable.rain);
	    list.add(R.drawable.snow);
	    
		ArrayList<HashMap<String,Integer>> myList = new ArrayList<HashMap<String,Integer>>();
		HashMap<String,Integer> map1 = new HashMap<String,Integer>();
		HashMap<String,Integer> map2 = new HashMap<String,Integer>();
		HashMap<String,Integer> map3 = new HashMap<String,Integer>();
		HashMap<String,Integer> map4 = new HashMap<String,Integer>();
		map1.put("weather", R.drawable.sun);
		map2.put("weather", R.drawable.cloud);
		map3.put("weather", R.drawable.rain);
		map4.put("weather", R.drawable.snow);
		myList.add(map1);myList.add(map2);myList.add(map3);myList.add(map4);

		String[] from = {"weather"};
		int[] to = new int[]{R.id.item_img_view};
		SimpleAdapter adapter = new SimpleAdapter(this,myList,R.layout.item,from,to);
	    spinner.setAdapter(adapter);
	    spinner.setPrompt("选择天气");
	    spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());
	    
		dao = new DiaryDao(DiaryNew.this);
        buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String title = textTitle.getText().toString();
				String content = textContent.getText().toString();
				dao.open();
				vo = new DiaryVo();
				if(weather.equals("天晴")) {
					vo.setIcon(R.drawable.sun);
				}else if(weather.equals("多云")) {
					vo.setIcon(R.drawable.cloud);
				}else if(weather.equals("下雨")) {
					vo.setIcon(R.drawable.rain);
				}else if(weather.equals("下雪")) {
					vo.setIcon(R.drawable.snow);
				}else {
					vo.setIcon(R.drawable.sun);
				}
				vo.setTitle(title);
				vo.setContent(content);
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");     
				Date curDate = new Date(System.currentTimeMillis()+8*60*60*1000); 
				String str = formatter.format(curDate); 
				vo.setDate(str);
				dao.insert(vo);
				dao.close();
				Intent intent = new Intent(DiaryNew.this, DiaryList.class);
				startActivity(intent);
				finish();
			}		
		});
	}
	
	class SpinnerOnSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,long id) {
			if(position == 0){
				weather = "天晴";
			}else if(position == 1){
				weather = "多云";
			}else if(position ==2){
				weather = "下雨";
			}else {
				weather = "下雪";
			}
		}
		 
		public void onNothingSelected(AdapterView<?> adapterView) {
			weather = "未选择";
		}
	}    
}
