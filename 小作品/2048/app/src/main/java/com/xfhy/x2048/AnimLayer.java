package com.xfhy.x2048;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 卡片移动的特效,生成卡片特效
 *
 * @author XFHY
 */
public class AnimLayer extends FrameLayout {

    public AnimLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayer();
    }

    public AnimLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayer();
    }

    public AnimLayer(Context context) {
        super(context);
        initLayer();
    }

    private void initLayer() {
    }

    //从一个点到另一个点,在其间产生划痕的效果   这里是从当前位置移到上下左右的其中一个
    public void createMoveAnim(final Card from, final Card to, int fromX, int toX, int fromY, int
            toY) {

        //动画结束之后   将from的那个格子消失，将to的那个格子设为可见
        final Card c = getCard(from.getNum());

        LayoutParams lp = new LayoutParams(Config.CARD_WIDTH, Config.CARD_WIDTH);
        lp.leftMargin = fromX * Config.CARD_WIDTH;   //左边距
        lp.topMargin = fromY * Config.CARD_WIDTH;
        c.setLayoutParams(lp);

        //要到达的那个点是空的,则把要到达的那个点设置为不可见
        if (to.getNum() <= 0) {
            to.getLabel().setVisibility(View.INVISIBLE);
        }

		/*
         *TranslateAnimation 位移动画效果
		 *TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
		 *  float fromXDelta 动画开始的点离当前View X坐标上的差值
			float toXDelta 动画结束的点离当前View X坐标上的差值
			float fromYDelta 动画开始的点离当前View Y坐标上的差值
			float toYDelta 动画开始的点离当前View Y坐标上的差值

			常见方法:
			animation.setDuration(long durationMillis);//设置动画持续时间
			animation.setRepeatCount(int i);//设置重复次数
			animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
		 * */
        TranslateAnimation ta = new TranslateAnimation(0, Config.CARD_WIDTH * (toX - fromX), 0,
                Config.CARD_WIDTH * (toY - fromY));
        ta.setDuration(100);   //设置动画持续时间
        ta.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //将卡片上的文本控件设置为可见
                to.getLabel().setVisibility(View.VISIBLE);

                //不可见
                recycleCard(c);
            }
        });
        c.startAnimation(ta);    //开始动画
    }

    private Card getCard(int num) {
        Card c;
        if (cards.size() > 0)    //卡片有数值
        {
            c = cards.remove(0);   //移除0部(首部)
        } else                   //卡片无数值
        {
            c = new Card(getContext());
            addView(c);        //添加视图
        }
        c.setVisibility(View.VISIBLE);
        c.setNum(num);
        return c;
    }

    //回收卡片,将卡片设置为不可见
    private void recycleCard(Card c) {
        c.setVisibility(View.INVISIBLE);
        c.setAnimation(null);
        cards.add(c);
    }

    private List<Card> cards = new ArrayList<>();

    //生成卡片时的缩放效果
    public void createScaleTo1(Card target) {
        /*
		 * android中提供了4中动画：
		AlphaAnimation 透明度动画效果
		ScaleAnimation 缩放动画效果
		TranslateAnimation 位移动画效果
		RotateAnimation 旋转动画效果

		ScaleAnimation(float fromX, float toX, float fromY, float toY,int pivotXType, float
		pivotXValue, int pivotYType, float pivotYValue)
		float fromX 动画起始时 X坐标上的伸缩尺寸
		float toX 动画结束时 X坐标上的伸缩尺寸
		float fromY 动画起始时Y坐标上的伸缩尺寸
		float toY 动画结束时Y坐标上的伸缩尺寸
		int pivotXType 动画在X轴相对于物件位置类型
		float pivotXValue 动画相对于物件的X坐标的开始位置
		int pivotYType 动画在Y轴相对于物件位置类型
		float pivotYValue 动画相对于物件的Y坐标的开始位置
		 * */
        ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(100);        //设置动画持续时间
        target.setAnimation(null);  //动画执行条件
        target.getLabel().startAnimation(sa);   //开始动画
        //startAnimation是立即执行一个指定的动画，而setAnimation则是有条件
    }

}

