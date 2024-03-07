package com.linln.admin.system.domain;

public class SLogItem {
    private DeepInfo deepInfo;
    private ShallowInfo shallowInfo;
    private UserInfo userInfo;

    // 构造函数
    public SLogItem(DeepInfo di, ShallowInfo si, UserInfo ui) {
        this.deepInfo = di;
        this.shallowInfo = si;
        this.userInfo = ui;
    }

    // getters and setters
    public DeepInfo getDeepInfo() { return deepInfo; }
    public void setDeepInfo(DeepInfo deepInfo) { this.deepInfo = deepInfo; }

    public ShallowInfo getShallowInfo() { return shallowInfo; }
    public void setShallowInfo(ShallowInfo shallowInfo) { this.shallowInfo = shallowInfo; }

    public UserInfo getUsersInfo() { return userInfo; }
    public void setUsersInfo(UserInfo userInfo) { this.userInfo = userInfo; }
}
