package dto.ViewModels.SubModels;

import org.springframework.beans.factory.annotation.Autowired;

public class VmManager {
    private String name;
    private String avatar;
    private String role;

    @Autowired
    public VmManager(String name, String avatar, String role) {
        this.name = name;
        this.avatar = avatar;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
