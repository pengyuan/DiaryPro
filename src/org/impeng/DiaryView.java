package org.impeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiaryView extends Activity {
	TextView textTitle;
	TextView textContent;
	DiaryVo vo;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view); 
	    Intent intent = this.getIntent();
	    Bundle bundle = (Bundle)intent.getExtras();
	    vo = (DiaryVo)bundle.getSerializable("diary");
	    Button buttonSave = (Button)findViewById(R.id.view_btn_return);
	    textTitle = (TextView)findViewById(R.id.view_edit_title);
	    textContent = (TextView)findViewById(R.id.view_edit_content);
	    textTitle.setText(vo.getTitle());
	    textContent.setText(vo.getContent());
	    
	    textTitle.setCursorVisible(false);
	    textTitle.setFocusable(false);
	    textTitle.setFocusableInTouchMode(false);
	    textContent.setCursorVisible(false);
	    textContent.setFocusable(false);
	    textContent.setFocusableInTouchMode(false);
	    
        buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent_return = new Intent(DiaryView.this,DiaryList.class);
				startActivity(intent_return);
				finish();
			}		
		});
	 }    
}

