package com.hgw.baseframe.bean.main;

import java.util.List;

/**
 * 描述：广告对象
 * @author hgw
 */
public class BannerBean {
    /** 响应结果码. */
    private int errorCode;
    /** 提示信息. */
    private String errorMsg;
    /** 具体的内容. */
    private List<Data> data;

    public class Data{
        private String desc;
        private String id;
        private String imagePath;
        private String isVisible;
        private String order;
        private String title;
        private String type;
        private String url;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(String isVisible) {
            this.isVisible = isVisible;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
