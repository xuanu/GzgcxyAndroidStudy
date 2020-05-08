package cn.zeffect.apk.jump.myapplication2.unit37;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.zeffect.apk.jump.myapplication2.R;
import cn.zeffect.apk.jump.myapplication2.unit31.PermissionUtils;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findViewById(R.id.read_contact_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_contact_btn) {
            //读取通讯录需要权限！！
//            Manifest.permission.READ_CONTACTS 通讯录的权限
            if (PermissionUtils.checkPermission(this, Manifest.permission.READ_CONTACTS)) {
                //读取
                readContact();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1001);
                } else {
                    //读取
                    readContact();
                }
            }
        }
    }

    private void readContact() {
        //现在就要开始读取通讯录了。
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI; //读取通讯录的Uri
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                //
                String contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Log.e("zeffect", "用户名:" + cursor.getString(cursor.getColumnIndex("display_name")));
                Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                while (phoneCursor.moveToNext()) {
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phone = phone.replace("-", "");
                    phone = phone.replace(" ", "");
                    Log.e("zeffect", "phone:" + phone);
                }
            }
        }
    }
}
