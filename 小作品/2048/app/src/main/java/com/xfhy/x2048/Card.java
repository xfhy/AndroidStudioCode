package com.xfhy.x2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xfhy.x2048.Config;


/*
 * Ⅰlabel显示相应得分情况，background是相应背景图片。
　　Ⅱ在构造函数初始化情况，我们设置相应文字，字体大小，以及相应的对齐方式了，
                 这些都是在数据初始化中做的动作了。
　　Ⅲ根据不同分值卡片，来显示不同颜色的卡片，就是在这个setnumber中实现的。
　　Ⅳ相应的卡片拷贝,是一种必然，我们就在这个clone方法中完成了相应值传递。
　　这样，利用了面向对象的原则，就把一个卡片的类模拟出来了。

     config类，主要是一些配置信息，我们记录了每行的长度，和卡片的宽度。
 * */
public class Card extends FrameLayout
{

	private int num = 0;      //卡片数值
	private TextView label;   //卡片数值显示
	private View background;  //卡片

	public Card(Context context)
	{
		super(context);
		LayoutParams lp = null;    //一张卡片就是一个LayoutParams

		background = new View(getContext());
		lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		background.setBackgroundColor(0x33ffffff);
		addView(background, lp);              //先在LayoutParams里添加背景

		label = new TextView(getContext());   //初始化文本控件
		label.setTextSize(20);                //文本控件大小:28
		label.setGravity(Gravity.CENTER);     //文本放在中间
		//TextPaint tp = label.getPaint();
		//tp.setFakeBoldText(true);


		lp = new LayoutParams(-1, -1);        //填充满整个父级容器
		lp.setMargins(10, 10, 0, 0);          //上面,右面的边距
		addView(label, lp);                   //再在LayoutParams添加文本控件

		setNum(0);                            //最初时,卡片上的数值是0,不显示文本
	}

	//获取该Card的数值
	public int getNum()
	{
		return num;
	}

	//设置该Card的数值
	public void setNum(int num)
	{
		this.num = num;
		if( num<=0 )
		{
			label.setText("");
		}
		else
		{
			//这里参数不能是单一的int类型,这里加了""后,就变成字符串了,就可以了
			//label.setText(num+"");

			if(Config.type==0)    //  经典版2048
			{
				label.setText(num+"");
			}
			else if(Config.type==1)    //  情侣版2048
			{
				switch(num)
				{
					case 0:
						label.setText("");
						break;
					case 2:
						label.setText("搭讪");
						break;
					case 4:
						label.setText("暧昧");
						break;
					case 8:
						label.setText("约会");
						break;
					case 16:
						label.setText("表白");
						break;
					case 32:
						label.setText("恋爱");
						break;
					case 64:
						label.setText("牵手");
						break;
					case 128:
						label.setText("拥抱");
						break;
					case 256:
						label.setText("接吻");
						break;
					case 512:
						label.setText("XXOO");
						break;
					case 1024:
						label.setText("求婚");
						break;
					case 2048:
						label.setText("小恩爱");
						break;
					case 4096:
						label.setText("相濡以沫");
						break;
				}
			}
			else if(Config.type==2) //逆向版
			{
				switch(num)
				{
					case 0:
						label.setText("");
						break;
					case 2048:
						label.setText("2048");
						break;
					case 1024:
						label.setText("1024");
						break;
					case 512:
						label.setText("512");
						break;
					case 256:
						label.setText("256");
						break;
					case 128:
						label.setText("128");
						break;
					case 64:
						label.setText("64");
						break;
					case 32:
						label.setText("32");
						break;
					case 16:
						label.setText("16");
						break;
					case 8:
						label.setText("8");
						break;
					case 4:
						label.setText("4");
						break;
					case 2:
						label.setText("2");
						break;
				}
			}
			else if(Config.type==3)   //丧病版
			{
				label.setText(num+"");
			}
			else if(Config.type==4)  //朝代版
			{
				switch(num)
				{
					case 0:
						label.setText("");
						break;
					case 2:
						label.setText("夏");
						break;
					case 4:
						label.setText("商");
						break;
					case 8:
						label.setText("周");
						break;
					case 16:
						label.setText("秦");
						break;
					case 32:
						label.setText("汉");
						break;
					case 64:
						label.setText("魏");
						break;
					case 128:
						label.setText("晋");
						break;
					case 256:
						label.setText("南北");
						break;
					case 512:
						label.setText("隋");
						break;
					case 1024:
						label.setText("唐");
						break;
					case 2048:
						label.setText("宋");
						break;
					case 4096:
						label.setText("元");
						break;
				}
			}
			else if(Config.type==5)
			{
				/*
				 * 甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戌亥为天干地支
				 * */
				switch(num)
				{
					case 0:
						label.setText("");
						break;
					case 2:
						label.setText("甲");
						break;
					case 4:
						label.setText("乙");
						break;
					case 8:
						label.setText("丙");
						break;
					case 16:
						label.setText("丁");
						break;
					case 32:
						label.setText("戊");
						break;
					case 64:
						label.setText("己");
						break;
					case 128:
						label.setText("庚");
						break;
					case 256:
						label.setText("辛");
						break;
					case 512:
						label.setText("壬");
						break;
					case 1024:
						label.setText("癸");
						break;
					case 2048:
						label.setText("子");
						break;
					case 4096:
						label.setText("丑");
						break;
				}
			}

		}


		//设置不同数字颜色
		switch (num)
		{
			case 0:
				//label.setBackgroundColor(0x33ffffff);
				label.setBackgroundColor(0x00000000);
				break;
			case 2:
				label.setBackgroundColor(0xeee4da);
				break;
			case 4:
				label.setBackgroundColor(0xede0c8);
				break;
			case 8:
				label.setBackgroundColor(0xf2b179);
				break;
			case 16:
				label.setBackgroundColor(0xf59563);
				break;
			case 32:
				label.setBackgroundColor(0xf67c5f);
				break;
			case 64:
				label.setBackgroundColor(0xf65e3b);
				break;
			case 128:
				label.setBackgroundColor(0xedcf72);
				break;
			case 256:
				label.setBackgroundColor(0xedcc61);
				break;
			case 512:
				label.setBackgroundColor(0xedc850);
				break;
			case 1024:
				label.setBackgroundColor(0xedc53f);
				break;
			case 2048:
				label.setBackgroundColor(0xedc22e);
				break;
			case 4096:
				label.setBackgroundColor(0x3c3a32);
				break;
			default:
				label.setBackgroundColor(0x3c3a32);
				break;
		}

	}

	//判断2张卡片是否相同
	public boolean equals(Card o) {
		return getNum()==o.num;
	}

	//卡片克隆(复制)
	protected Card clone(){
		Card c= new Card(getContext());
		c.setNum(getNum());
		return c;
	}

	//获取卡片上的TextView控件
	public TextView getLabel() {
		return label;
	}
}
