package com.xfhy.jsoupdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    for (int k = 0; k < 5; k++) {
                        Document doc = Jsoup.connect("http://www.qiushibaike.com/8hr/page/" + k +
                                "/").get();  //因为是分页的  并且1页就是序号k变了

                        /*
                        * 原文:
                        * <a href="/article/119453408" target="_blank" class="contentHerf" onclick="_hmt.push(['_trackEvent','web-list-content','chick'])">
<div class="content">
<span>


这就很过分了！

</span>

</div>
</a>
<!-- 图片或gif -->



<div class="thumb">

<a href="/article/119453408" target="_blank">
<img src="//pic.qiushibaike.com/system/pictures/11945/119453408/medium/app119453408.jpg" alt="糗事#119453408" class="illustration" width="100%" height="auto">
</a>
</div>


<div class="stats">
<!-- 笑脸、评论数等 -->


<span class="stats-vote"><i class="number">1835</i> 好笑</span>
<span class="stats-comments">
<span class="dash"> · </span>
<a href="/article/119453408" data-share="/article/119453408" id="c-119453408" class="qiushi_comments" target="_blank" onclick="_hmt.push(['_trackEvent','web-list-comment','chick'])">
<i class="number">38</i> 评论
</a>
</span>
</div>
<div id="qiushi_counts_119453408" class="stats-buttons bar clearfix">
<ul class="clearfix">
<li id="vote-up-119453408" class="up">
<a href="javascript:voting(119453408,1)" class="voting" data-article="119453408" id="up-119453408" rel="nofollow" onclick="_hmt.push(['_trackEvent','web-list-funny','chick'])">
<i></i>
<span class="number hidden">1856</span>
</a>
                        * */

                        //a标签  class=contentHerf
                        Elements els = doc.select("a.contentHerf");
                        Log.e("一、HTML內容", els.toString());

                        for (int i = 0; i < els.size(); i++) {
                            Element el = els.get(i);
                            Log.e("1.标题", el.text());  //取出里面所有的文本  <p> <b>

                            String href = el.attr("href"); //选择href
                            Log.e("2.链接", href);

                            //爬里面的详情页
                            Document doc_detail = Jsoup.connect("http://www.qiushibaike.com" +
                                    href).get();
                            Elements els_detail = doc_detail.select(".content");
                            Log.e("3.內容", els_detail.text());

                            Elements els_pic = doc_detail.select(".thumb img[src$=jpg]");
                            if (!els_pic.isEmpty()) {
                                String pic = els_pic.attr("src");
                                Log.e("4.图片连接", "" + pic);
                            } else {
                                Log.e("4.图片连接", "无");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
