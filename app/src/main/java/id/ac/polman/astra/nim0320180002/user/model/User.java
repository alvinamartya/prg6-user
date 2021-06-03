package id.ac.polman.astra.nim0320180002.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("username")
    @Expose
    private String username;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.username = "";
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
