package com.wtc.xmut.taoschool.domain;

public class Orders {
    private Integer id;

    private String shopid;

    private String buyerusername;

    private String sellerusername;

    private Integer count;

    private String time;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getBuyerusername() {
        return buyerusername;
    }

    public void setBuyerusername(String buyerusername) {
        this.buyerusername = buyerusername == null ? null : buyerusername.trim();
    }

    public String getSellerusername() {
        return sellerusername;
    }

    public void setSellerusername(String sellerusername) {
        this.sellerusername = sellerusername == null ? null : sellerusername.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Orders [id=" + id + ", shopid=" + shopid + ", buyerusername=" + buyerusername + ", sellerusername="
                + sellerusername + ", count=" + count + ", time=" + time + ", state=" + state + "]";
    }
}