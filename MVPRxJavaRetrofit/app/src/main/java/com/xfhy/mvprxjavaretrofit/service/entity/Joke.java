package com.xfhy.mvprxjavaretrofit.service.entity;

import java.util.List;

/**
 * Created by xfhy on 2017/8/12.
 * 模型的实体类
 */

public class Joke {

    /**
     * status : 0
     * msg : ok
     * result : {"total":"51296","pagenum":"1","pagesize":"1",
     * "list":[{"content
     * ":"某吝啬鬼去酒吧喝酒，掏出事先准备好的钱叫了一杯啤酒。　　喝到一半，他觉得内急，想上洗手间。但是又怕酒被别人喝掉。　　于是，他向服务生借了笔和纸。在纸上写了：我在杯里吐了一口痰。　　然后，他放心的走了。　　一会儿，他回来，发现酒还在那里，他很高兴。　　但是，他又发现纸条上多了几个字：我也吐了一口！","addtime":"2017-08-12 03:20:09","url":"http://m.kaixinhui.com/detail-56105.html"}]}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 51296
         * pagenum : 1
         * pagesize : 1
         * list :
         * [{"content":"某吝啬鬼去酒吧喝酒，掏出事先准备好的钱叫了一杯啤酒。　　喝到一半，他觉得内急，想上洗手间。但是又怕酒被别人喝掉。　　于是，他向服务生借了笔和纸。在纸上写了：我在杯里吐了一口痰。　　然后，他放心的走了。　　一会儿，他回来，发现酒还在那里，他很高兴。　　但是，他又发现纸条上多了几个字：我也吐了一口！","addtime":"2017-08-12 03:20:09","url":"http://m.kaixinhui.com/detail-56105.html"}]
         */

        private String total;
        private String pagenum;
        private String pagesize;
        private List<ListBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPagenum() {
            return pagenum;
        }

        public void setPagenum(String pagenum) {
            this.pagenum = pagenum;
        }

        public String getPagesize() {
            return pagesize;
        }

        public void setPagesize(String pagesize) {
            this.pagesize = pagesize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * content :
             * 某吝啬鬼去酒吧喝酒，掏出事先准备好的钱叫了一杯啤酒。　　喝到一半，他觉得内急，想上洗手间。但是又怕酒被别人喝掉。　　于是，他向服务生借了笔和纸。在纸上写了：我在杯里吐了一口痰。　　然后，他放心的走了。　　一会儿，他回来，发现酒还在那里，他很高兴。　　但是，他又发现纸条上多了几个字：我也吐了一口！
             * addtime : 2017-08-12 03:20:09
             * url : http://m.kaixinhui.com/detail-56105.html
             */

            private String content;
            private String addtime;
            private String url;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "content='" + content + '\'' +
                        ", addtime='" + addtime + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "total='" + total + '\'' +
                    ", pagenum='" + pagenum + '\'' +
                    ", pagesize='" + pagesize + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Joke{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
