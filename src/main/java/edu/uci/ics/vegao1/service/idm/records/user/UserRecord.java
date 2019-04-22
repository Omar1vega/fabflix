package edu.uci.ics.vegao1.service.idm.records.user;

public class UserRecord {
    private int id;
    private String email;
    private int status;
    private int plevel;
    private String salt;
    private String pword;

    UserRecord(int id, String email, int status, int plevel, String salt, String pword) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.plevel = plevel;
        this.salt = salt;
        this.pword = pword;
    }

    public String getPword() {
        return pword;
    }

    public String getSalt() {
        return salt;
    }
}
