package server;

class Password {
    private String hash;
    private String salt;

    Password(String hash, String salt){
        this.hash = hash;
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}
