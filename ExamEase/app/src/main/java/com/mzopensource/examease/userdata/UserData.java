package com.mzopensource.examease.userdata;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserData {
    private static FirebaseUser mUser;

    public static void setUserData(FirebaseUser user) {
        mUser = user;
    }

    public static FirebaseUser getUserData() {
        return mUser;
    }

}
