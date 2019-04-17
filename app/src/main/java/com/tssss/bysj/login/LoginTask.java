package com.tssss.bysj.login;

import android.os.AsyncTask;

import com.tssss.bysj.http.HttpClient;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;

public class LoginTask extends AsyncTask<User, Void, String> {

    @Override
    protected String doInBackground(User... users) {
        return HttpClient.getSync(Constant.BASE_URL);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
