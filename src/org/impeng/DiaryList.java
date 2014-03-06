package org.impeng;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DiaryList extends Activity{
	private final int NEW = 1;
	private final int SEARCH = 2;
	private final int LOGOUT = 3;
	private final int VIEW = 4;
	private final int EDIT = 5;
	private final int DELETE =6;
	private ArrayList<DiaryVo> diary_list;
	private DiaryDao dao;
	ListView lv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		lv = (ListView)this.findViewById(R.id.diary_list);
		dao = new DiaryDao(DiaryList.this);
		this.listViewRefresh();
		this.registerForContextMenu(lv);
		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
	}	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item1 = menu.add(0, NEW, 1, "写日记");
		item1.setIcon(R.drawable.diary_new);
		MenuItem item2 = menu.add(0, SEARCH, 2, "查找");
		item2.setIcon(R.drawable.diary_search);
		MenuItem item3 = menu.add(0, LOGOUT, 3, "注销");
		item3.setIcon(R.drawable.diary_logout);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case NEW:
				Intent intent_new = new Intent(DiaryList.this, DiaryNew.class);
				this.startActivity(intent_new);
				this.finish();
				break;
			case SEARCH:
				Intent intent_search = new Intent(DiaryList.this, DiarySearch.class);
				this.startActivity(intent_search);
				this.finish();
				break;
			case LOGOUT:
				Intent intent_logout = new Intent(DiaryList.this,DiaryPro.class);
				startActivity(intent_logout);
				this.finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		menu.add(0, VIEW, 1, "查看");
		menu.add(0, EDIT, 2, "修改");
		menu.add(0, DELETE, 3, "删除");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		DiaryVo vo = diary_list.get(info.position);
		Bundle bundle;
		switch(item.getItemId()) {
			case VIEW:
				Intent intent_view = new Intent(DiaryList.this,DiaryView.class);
				bundle = new Bundle();
				bundle.putSerializable("diary", vo);
				intent_view.putExtras(bundle);
				startActivity(intent_view);
				this.finish();
				break;
			case EDIT:
				Intent intent_edit = new Intent(DiaryList.this,DiaryEdit.class);
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