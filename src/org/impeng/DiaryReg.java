package org.impeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiaryReg extends Activity {
	EditText edit_psw;
	EditText edit_acc;
	Button btn_log;
	Button btn_reg;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        this.init();
        
        btn_reg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String acc = edit_acc.getText().toString();
				String psw = edit_psw.getText().toString();
				DiaryDao dao = new DiaryDao(DiaryReg.this);
				dao.open();
				System.out.println("acc"+acc);
				if(acc.equals("")||psw.equals("")||acc==null||psw==null){
					Toast.makeText(DiaryReg.this, "«Î ‰»Î’À∫≈ªÚ√‹¬Î", Toast.LENGTH_LONG).show();
				}
				else{
					if(dao.register(acc, psw)) {
						Intent intent = new Intent(DiaryReg.this,DiaryList.class);
						startActivity(intent);
						dao.close();
						finish();
					}
					else {
						dao.close();
						Toast.makeText(DiaryReg.this, "’À∫≈“—±ª◊¢≤·£¨«Î÷ÿ–¬◊¢≤·", Toast.LENGTH_LONG).show();
					}					
				}
			}
		});
    }
    
    private void init(){
        edit_psw = (EditText)this.findViewById(R.id.edit_reg_psw);
        edit_acc = (EditText)this.findViewById(R.id.edit_reg_acc);
        btn_reg = (Button)this.findViewById(R.id.btn_reg);
    }
}