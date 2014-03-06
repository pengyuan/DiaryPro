package org.impeng;

import java.util.ArrayList;
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

public class DiaryEdit extends Activity {
	EditText textTitle;
	EditText textContent;
	Spinner spinner;
	DiaryVo vo;
	DiaryDao dao;
	String weather;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit);
	    Intent intent = this.getIntent();
	    Bundle bundle = (Bundle)intent.getExtras();
	    dao = new DiaryDao(DiaryEdit.this);
	    vo = (DiaryVo)bundle.getSerializable("diary");
	    Button buttonSave = (Button)findViewById(R.id.edit_btn_save);
	    textTitle = (EditText)findViewById(R.id.edit_edit_title);
	    textContent = (EditText)findViewById(R.id.edit_edit_content);
	    spinner = (Spinner) findViewById(R.id.spinner);	    
	    textTitle.setText(vo.getTitle());
	    textContent.setText(vo.getContent());
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
	    spinner.setPrompt("ѡ������");
	    spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());
	    
        buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String title = textTitle.getText().toString();
				String content = textContent.getText().toString();
				dao.open();
				if(weather.equals("����")) {
					vo.setIcon(R.drawable.sun);
				}else if(weather.equals("����")) {
					vo.setIcon(R.drawable.cloud);
				}else if(weather.equals("����")) {
					vo.setIcon(R.drawable.rain);
				}else if(weather.equals("��ѩ")) {
					vo.setIcon(R.drawable.snow);
				}else {
					vo.setIcon(R.drawable.sun);
				}
				vo.setTitle(title);
				vo.setContent(content);
				dao.update(vo);
				dao.close();
				Intent intent_logout = new Intent(DiaryEdit.this,DiaryList.class);
				startActivity(intent_logout);
				finish();
			}		
		});
	}
	class SpinnerOnSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,long id) {
			if(position == 0){
				weather = "����";
			}else if(position == 1){
				weather = "����";
			}else if(position ==2){
				weather = "����";
			}else {
				weather = "��ѩ";
			}
		}
		 
		public void onNothingSelected(AdapterView<?> adapterView) {
			weather = "δѡ��";
		}
	}  
}

