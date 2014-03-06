package org.impeng;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DiarySearch extends Activity{
	private final int VIEW = 1;
	private final int EDIT = 2;
	private final int DELETE =3 ;
	private ArrayList<DiaryVo> diary_list;
	private DiaryDao dao;
	private ListView lv;
	private Button btn_search;
	private Button search_return_btn;
	private EditText edit_search;
	private String key;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		lv = (ListView)this.findViewById(R.id.search_diary_list);
		btn_search = (Button)this.findViewById(R.id.search_btn);
		edit_search = (EditText)DiarySearch.this.findViewById(R.id.search_edit);
		search_return_btn = (Button)this.findViewById(R.id.search_return_btn);
		dao = new DiaryDao(DiarySearch.this);
		this.listViewRefresh();
		this.registerForContextMenu(lv);
		btn_search.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				key = edit_search.getText().toString();
				searchDiary(key);
			}
		});
		search_return_btn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent_return = new Intent(DiarySearch.this,DiaryList.class);
				startActivity(intent_return);
				finish();
			}
		});
	}

	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		menu.add(0, VIEW, 1, "²é¿´");
		menu.add(0, EDIT, 2, "ÐÞ¸Ä");
		menu.add(0, DELETE, 3, "É¾³ý");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		DiaryVo vo = diary_list.get(info.position);
		Bundle bundle;
		switch(item.getItemId()) {
			case VIEW:
				Intent intent_view = new Intent(DiarySearch.this,DiaryView.class);
				bundle = new Bundle();
				bundle.putSerializable("diary", vo);
				intent_view.putExtras(bundle);
				startActivity(intent_view);
				this.finish();
				break;
			case EDIT:
				Intent intent_edit = new Intent(DiarySearch.this,DiaryEdit.class);
				bundle = new Bundle();
				bundle.putSerializable("diary", vo);
				intent_edit.putExtras(bundle);
				startActivity(intent_edit);
				this.finish();
				break;
			case DELETE:
				dao.open();
				dao.delete(vo);
				dao.close();
				listViewRefresh();
				break;
		}
		return super.onContextItemSelected(item);
	}

	private void searchDiary(String key) {
		dao.open();
		diary_list = dao.search(key);
		dao.close();
		ArrayList<HashMap<String,Object>> myList = new ArrayList<HashMap<String,Object>>();
		for(int i = 0; i < diary_list.size(); i++) {
			DiaryVo vo = new DiaryVo();
			vo = diary_list.get(i);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("icon", vo.getIcon());
			map.put("title", vo.getTitle());
			map.put("date", vo.getDate());
			myList.add(map);
		}
		String[] from = {"icon","title","date"};
		int[] to = new int[]{R.id.img_view,R.id.txt_title,R.id.txt_date};
		SimpleAdapter adapter = new SimpleAdapter(this,myList,R.layout.diary,from,to);
		lv.setAdapter(adapter);
	}
	
	private void listViewRefresh() {
		dao.open();
		diary_list = dao.getAllDiary();
		dao.close();
		ArrayList<HashMap<String,Object>> myList = new ArrayList<HashMap<String,Object>>();
		for(int i = 0; i < diary_list.size(); i++) {
			DiaryVo vo = new DiaryVo();
			vo = diary_list.get(i);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("icon", vo.getIcon());
			map.put("title", vo.getTitle());
			map.put("date", vo.getDate());
			myList.add(map);
		}
		String[] from = {"icon","title","date"};
		int[] to = new int[]{R.id.img_view,R.id.txt_title,R.id.txt_date};
		SimpleAdapter adapter = new SimpleAdapter(this,myList,R.layout.diary,from,to);
		lv.setAdapter(adapter);
	}
}