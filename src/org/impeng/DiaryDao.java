package org.impeng;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDao {
	private final String DATABASE_NAME="IMPENG_DIARY.DB";
	private final String DIARY_TABLE_NAME = "IMPENG_T_DIARY";
	private final String USER_TABLE_NAME = "IMPENG_T_USER";
	private Context ctx;
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	
	public DiaryDao(Context ctx) {
		this.ctx = ctx;
	}
	
	public void open() {
		this.dbHelper = new DbHelper(ctx,this.DATABASE_NAME,this.DIARY_TABLE_NAME,this.USER_TABLE_NAME);
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
    
    public void insert(DiaryVo vo) {
		ContentValues initValues = new ContentValues();
		initValues.put("icon", vo.getIcon());
		initValues.put("title", vo.getTitle());
		initValues.put("content", vo.getContent());
		initValues.put("date", vo.getDate());
		db.insert(DIARY_TABLE_NAME, null, initValues);
    }

    public void delete(DiaryVo vo) {
    	String sql = "delete from "+ DIARY_TABLE_NAME +" where _id='"+ vo.getId() +"';";
    	db.execSQL(sql);
    }
    
	public void update(DiaryVo vo) {
		String sql = "update "+ DIARY_TABLE_NAME +" set icon='"+ vo.getIcon() +"',title='"+ vo.getTitle() +"',content='"+ vo.getContent() +"' where _id='"+ vo.getId() +"';";
		db.execSQL(sql);
    }
    
	public ArrayList<DiaryVo> getAllDiary() {
		ArrayList<DiaryVo> diary_list = new ArrayList<DiaryVo>();
		//select * from table where title=?   String[] as = {arg1}
		Cursor c = db.rawQuery("select * from "+ this.DIARY_TABLE_NAME, null);
        while(c.moveToNext()) {
        	DiaryVo vo = new DiaryVo();
        	vo.setId(c.getInt(0));
        	vo.setIcon(c.getInt(1));
        	vo.setTitle(c.getString(2));
        	vo.setDate(c.getString(3));
        	vo.setContent(c.getString(4));
        	diary_list.add(vo);
        }
        return diary_list;   
	}

	public DiaryVo getDiaryById(int id) {
		String sql = "select * frome "+ this.DIARY_TABLE_NAME +" where _id='"+ id +"';";
		Cursor c = db.rawQuery(sql, null);
    	DiaryVo vo = new DiaryVo();
    	vo.setId(c.getInt(0));
    	vo.setIcon(c.getInt(1));
    	vo.setTitle(c.getString(2));
    	vo.setDate(c.getString(3));
    	vo.setContent(c.getString(4));
		return vo;
	}
	
	public ArrayList<DiaryVo> search(String key){
		ArrayList<DiaryVo> diary_list = new ArrayList<DiaryVo>();
		Cursor c = db.rawQuery("select * from "+ this.DIARY_TABLE_NAME +" where title like '%"+ key +"%' or content like '%"+ key +"%';", null);
        while(c.moveToNext()) {
        	DiaryVo vo = new DiaryVo();
        	vo.setId(c.getInt(0));
        	vo.setIcon(c.getInt(1));
        	vo.setTitle(c.getString(2));
        	vo.setDate(c.getString(3));
        	vo.setContent(c.getString(4));
        	diary_list.add(vo);
        }
        return diary_list;   
	}
	
	public boolean loginCheck(String acc,String psw){
		String sql = "select * from "+ this.USER_TABLE_NAME +" where account='" + acc +"';";
		Cursor c = db.rawQuery(sql, null);
		if(c.moveToFirst()){
			if(c.getString(2).equals(psw)){
				return true;
			}	
		}
		return false;
	}
	
	public boolean register(String acc,String psw){
		String sql = "select * from "+ this.USER_TABLE_NAME +" where account='" + acc +"';";
		Cursor c = db.rawQuery(sql, null);
		if(!c.moveToFirst()){
			ContentValues initValues = new ContentValues();
			initValues.put("account", acc);
			initValues.put("password", psw);
			db.insert(USER_TABLE_NAME, null, initValues);
			return true;
		}else {
			return false;
		}
	}
}

class DbHelper extends SQLiteOpenHelper {
	String DIARY_TABLE_NAME;
	String USER_TABLE_NAME;
	
	public DbHelper(Context context,String databaseName,String diaryTable,String userTable) {
		super(context,databaseName, null, 1);
		this.DIARY_TABLE_NAME = diaryTable;
		this.USER_TABLE_NAME = userTable;
	}

	public void onCreate(SQLiteDatabase db) {//在db创建的时候执行一次，而在再次创建表格的时候不再执行
		String createSql = "" + "create table " + DIARY_TABLE_NAME + "(_id integer primary key AUTOINCREMENT, icon integer, title text, date text, content text)";
		String createSql2 = "" + "create table " + USER_TABLE_NAME + "(_id integer primary key AUTOINCREMENT, account text, password text)";
		db.execSQL(createSql);
		db.execSQL(createSql2);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
		db.execSQL("drop table if exists " + DIARY_TABLE_NAME);
		db.execSQL("drop table if exists " + USER_TABLE_NAME);
		onCreate(db);
	}	
}