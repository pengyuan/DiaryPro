package org.impeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiaryPro extends Activity {
	EditText edit_psw;
	EditText edit_acc;
	Button btn_log;
	Button btn_reg;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.init();
        
        btn_reg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DiaryPro.this,DiaryReg.class);
				startActivity(intent);
				finish();
			}
		});
        
        btn_log.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String acc = edit_acc.getText().toString();
				String psw = edit_psw.getText().toString();
				DiaryDao dao = new DiaryDao(DiaryPro.this);
				dao.open();
				if(!acc.equals("")&&!psw.equals("")&&acc!=null&&psw!=null){
					if(dao.loginCheck(acc, psw)) {
						Intent intent = new Intent(DiaryPro.this,DiaryList.class);
						startActivity(intent);
						dao.close();
						finish();
					}
					else {
						dao.close();
						Toast.makeText(DiaryPro.this, "’À∫≈ªÚ√‹¬Î¥ÌŒÛ", Toast.LENGTH_LONG).show();
					}
				} else {
					dao.close();
					Toast.makeText(DiaryPro.this, "«Î ‰»Î’À∫≈ªÚ√‹¬Î", Toast.LENGTH_LONG).show();
				}
			}
        });
    }
    
    private void init(){
        edit_psw = (EditText)this.findViewById(R.id.edit_psw);
        edit_acc = (EditText)this.findViewById(R.id.edit_acc);
        btn_log = (Button)this.findViewById(R.id.btn_log);
        btn_reg = (Button)this.findViewById(R.id.btn_reg_new);
    }
}