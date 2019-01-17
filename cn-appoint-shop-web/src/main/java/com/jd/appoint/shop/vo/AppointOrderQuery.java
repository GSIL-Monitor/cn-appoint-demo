package com.jd.appoint.shop.vo;

/**
 * Created by bjliuyong on 2018/5/24.
 */
public class AppointOrderQuery {

    private int page = 1 ;
    private int pageSize = 20;

    private Long id = null ;
    private String customerName ;
    private String customerPhone ;
    private String serverType ;
    private String staffName ;
    private String appointStartTime ;
    private String appointEndTime ;
    private Integer appointStatus ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(String appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public String getAppointEndTime() {
        return appointEndTime;
    }

    public void setAppointEndTime(String appointEndTime) {
        this.appointEndTime = appointEndTime;
    }

    public Integer getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(Integer appointStatus) {
        this.appointStatus = appointStatus;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("AppointOrderQuery{");
        sb.append("page=").append(page);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", id=").append(id);
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", customerPhone='").append(customerPhone).append('\'');
        sb.append(", serverType='").append(serverType).append('\'');
        sb.append(", staffName='").append(staffName).append('\'');
        sb.append(", appointStartTime='").append(appointStartTime).append('\'');
        sb.append(", appointEndTime='").append(appointEndTime).append('\'');
        sb.append(", appointStatus=").append(appointStatus);
        sb.append('}');
        return sb.toString();
    }
}
