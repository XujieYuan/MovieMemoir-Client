package com.m.moviememoir.Bean;

public class Credential {

    /**
     * credentialid : 1
     * password : ln998
     * signupdate : 2000-02-14T00:00:00+08:00
     * username : leon@gmail.com
     */

    private int credentialid;
    private String password;
    private String signupdate;
    private String username;

    public int getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
