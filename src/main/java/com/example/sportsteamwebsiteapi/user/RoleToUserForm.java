package com.example.sportsteamwebsiteapi.user;

class RoleToUserForm {
    private String username;
    private String roleName;
    
    public RoleToUserForm() {
    }
    
    public RoleToUserForm(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}