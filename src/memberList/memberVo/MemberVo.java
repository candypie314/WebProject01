package memberList.memberVo;

import java.util.Date;

public class MemberVo {
    private String id;
    private String password;
    private String name;
    private Date regDate;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getRegDate() {
        return regDate;
    }
    public void setRegDate(Date setRegDate) {
        this.regDate = regDate;
    }
    
    
}
