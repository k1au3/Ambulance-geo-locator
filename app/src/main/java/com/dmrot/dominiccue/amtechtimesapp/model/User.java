package com.dmrot.dominiccue.amtechtimesapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dominiccue on 10/7/2017.
 */

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("role")
    private String role;
    @SerializedName("email")
    private String email;
    @SerializedName("design")
    private String design;
    @SerializedName("branch")
    private String branch;
    @SerializedName("unique_id")
    private String unique_id;
    @SerializedName("password")
    private String password;
    @SerializedName("old_password")
    private String old_password;
    @SerializedName("new_password")
    private String new_password;
    @SerializedName("code")
    private String code;

    public User(String name, String country, String role, String email, String design, String branch, String unique_id, String password, String old_password, String new_password, String code) {
        this.name = name;
        this.country = country;
        this.role = role;
        this.email = email;
        this.design = design;
        this.branch = branch;
        this.unique_id = unique_id;
        this.password = password;
        this.old_password = old_password;
        this.new_password = new_password;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
