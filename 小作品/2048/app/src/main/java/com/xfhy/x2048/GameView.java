package com.xfhy.x2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    private Card[][] cardsMap = new Card[Config.LINES][Config.LINES];  //卡片数组,记录卡片

    //Point java自己的类,表示 (x,y) 坐标空间中的位置的点，以整数精度指定。
    //List List 组件为用户提供了一个可滚动的文本项列表。可设置此 list，使其允许用户进行单项或多项选择。
    //ArrayList List 接口的大小可变数组的实现。
    private List<Point> emptyPoints = new ArrayList<>();  //空的点

    /*
     * 默认构造方法
     * @param context
     */
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    //不管从什么构造方法进来,都会执行这个方法
    private void initGameView() {
        setOrientation(GridLayout.VERTICAL);
        setColumnCount(Config.LINES);               //设置列数

        //设置游戏背景颜色,去网页上找2048再审查元素即可知道原版游戏颜色
        setBackgroundColor(0xffbbada0);

        //设置监听器,当用户触摸屏幕时,调用触发以下监听器,判断用户想要的方向
        //思路:先获取按下时的坐标,再获取离开屏幕时的坐标,通过坐标比较,即可判断用户需要的方向
        setOnTouchListener(new View.OnTouchListener() {

            //设置起始坐标,偏移量
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();   //按下屏幕时的坐标
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        //如果X方向上的偏移量比Y方向上的偏移量大,则说明用户需要的是水平方向上的改变
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX <= -5) {
                                System.out.println("左");
                                swipeLeft();
                            } else if (offsetX >= 5) {
                                System.out.println("右");
                                swipeRight();
                            }
                        } else {
                            if (offsetY <= -5) {
                                System.out.println("上");
                                swipeUp();
                            } else if (offsetY >= 5) {
                                System.out.println("下");
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;      //这里需要返回true,否则系统不会执行后续事件
            }

        });
    }

    /**
     * 当屏幕发生改变时,调用
     * 这时需要去AndroidManifest.xml  activity里添加一句
     * android:screenOrientation="portrait"  表示屏幕方向是竖直
     * 现在这个方法只会被执行一次,就是在第一次创建时
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //每张卡片的宽高
        Config.CARD_WIDTH = (Math.min(w, h) - 10) / Config.LINES;

        //添加所有的卡片到屏幕上
        addCards(Config.CARD_WIDTH, Config.CARD_WIDTH);

        //开始游戏
        startGame();
    }

    //添加卡片
    private void addCards(int cardWidth, int cardHeight) {
        Card c;
        LinearLayout line;
        LinearLayout.LayoutParams lineLp;
        //卡片是4列,4行
        for (int y = 0; y < Config.LINES; y++) {
            line = new LinearLayout(getContext());
            lineLp = new LinearLayout.LayoutParams(-1, cardHeight);
            addView(line, lineLp);
            for (int x = 0; x < Config.LINES; x++) {
                c = new Card(getContext());
                line.addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }

    //开始游戏
    void startGame() {
        //在游戏开始时,需要将分数清零
        SecondActivity aty = SecondActivity.getsecondActivity();
        aty.clearScore();                         //分数清零
        aty.showBestScore(aty.getBestScore());    //显示最高分


        //在游戏开始时,或者游戏重新开始时,需要重新将卡片置0(初始状态)
        for (int x = 0; x < Config.LINES; x++) {
            for (int y = 0; y < Config.LINES; y++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    //添加卡片(随机数)
    private void addRandomNum() {
        emptyPoints.clear();    //移除此列表(所有的空卡片的集合数组)中的所有元素。
        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {
                //如果当前卡片没有数字,值为空则可以产生数字
                if (cardsMap[x][y].getNum() <= 0) {
                    //如果当前卡片无数字则添加到 空卡片数组
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        if (emptyPoints.size() > 0) {
            //Math.random():表示生成一个0~1之间的随机数(小数)
            //在空数组里随机选择一个点 这些点需要生成随机数的
            Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));

            if (Config.type == 2) {
                cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2048 : 1024);
            } else {
                //在卡片数组里找到刚刚找到的那个点,随机生成2或者4,2:4=9:1,生成2的概率比4大,
                cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
            }

            //添加卡片时,有一个动态的缩放效果
            SecondActivity.getsecondActivity().getAnimLayer().createScaleTo1(cardsMap[p.x][p.y]);
        }
    }

    //当用户向左滑动,需要干的事情
    private void swipeLeft() {
        //记录卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        boolean swipeTrue = false;

        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {

                //当前行的当前位置的右一个元素开始,向右遍历
                for (int x1 = x + 1; x1 < Config.LINES; x1++) {
                    //如果右一个卡片有值
                    if (cardsMap[x1][y].getNum() > 0) {
                        //当前位置的卡片<=0(为空),让右一个卡片移过来
                        if (cardsMap[x][y].getNum() <= 0) {

                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            //让右一个卡片移过来
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            //把刚刚右边移过来那个数设置为0
                            cardsMap[x1][y].setNum(0);

                            //a:0111
                            //b:1110
                            //当前位置是空时,右边卡片依次移过来,但是如果不x--(再重新循环一遍这一行)就没有考虑到上面的a->b的情况
                            x--;
                            swipeTrue = true;
                        }
                        //当前位置与右边位置的卡片相等
                        else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            //动画效果
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);

                            if (Config.type == 2)  //逆向型的不同计分
                            {
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() / 2);
                                cardsMap[x1][y].setNum(0);

                                //SecondActivity.getsecondActivity().addScore(cardsMap[x][y]
                                // .getNum());
                                switch (cardsMap[x][y].getNum()) {
                                    case 1024:
                                        SecondActivity.getsecondActivity().addScore(2);
                                        break;
                                    case 512:
                                        SecondActivity.getsecondActivity().addScore(4);
                                        break;
                                    case 256:
                                        SecondActivity.getsecondActivity().addScore(8);
                                        break;
                                    case 128:
                                        SecondActivity.getsecondActivity().addScore(16);
                                        break;
                                    case 64:
                                        SecondActivity.getsecondActivity().addScore(32);
                                        break;
                                    case 32:
                                        SecondActivity.getsecondActivity().addScore(64);
                                        break;
                                    case 16:
                                        SecondActivity.getsecondActivity().addScore(128);
                                        break;
                                    case 8:
                                        SecondActivity.getsecondActivity().addScore(256);
                                        break;
                                    case 4:
                                        SecondActivity.getsecondActivity().addScore(512);
                                        break;
                                    case 2:
                                        SecondActivity.getsecondActivity().addScore(1024);
                                        break;
                                }
                                swipeTrue = true;
                            } else  //普通型计分
                            {
                                //相等则直接将当前位置的数值*2,然后将右边那张卡片置为0
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                                cardsMap[x1][y].setNum(0);

                                //增加分数,合并后是多少则添加多少分
                                SecondActivity.getsecondActivity().addScore(cardsMap[x][y].getNum
                                        ());
                                swipeTrue = true;
                            }
                        }
                        break;
                    }
                }
            }
        }

        //判断卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        if (swipeTrue) {
            addRandomNum();   //滑动一次,添加一个随机数
            checkGameOver();  //每次添加完新数,就要判断是否达到游戏结束的状态
        }
    }

    private void swipeRight() {
        //记录卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        boolean swipeTrue = false;

        for (int y = 0; y < Config.LINES; y++) {   //y轴没变
            for (int x = Config.LINES - 1; x >= 0; x--) {  //x轴变了

                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;
                            swipeTrue = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            if (Config.type == 2) {
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() / 2);
                                cardsMap[x1][y].setNum(0);
                                switch (cardsMap[x][y].getNum()) {
                                    case 1024:
                                        SecondActivity.getsecondActivity().addScore(2);
                                        break;
                                    case 512:
                                        SecondActivity.getsecondActivity().addScore(4);
                                        break;
                                    case 256:
                                        SecondActivity.getsecondActivity().addScore(8);
                                        break;
                                    case 128:
                                        SecondActivity.getsecondActivity().addScore(16);
                                        break;
                                    case 64:
                                        SecondActivity.getsecondActivity().addScore(32);
                                        break;
                                    case 32:
                                        SecondActivity.getsecondActivity().addScore(64);
                                        break;
                                    case 16:
                                        SecondActivity.getsecondActivity().addScore(128);
                                        break;
                                    case 8:
                                        SecondActivity.getsecondActivity().addScore(256);
                                        break;
                                    case 4:
                                        SecondActivity.getsecondActivity().addScore(512);
                                        break;
                                    case 2:
                                        SecondActivity.getsecondActivity().addScore(1024);
                                        break;
                                }
                                swipeTrue = true;
                            } else {
                                //相等则直接将当前位置的数值*2,然后将右边那张卡片置为0
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                                cardsMap[x1][y].setNum(0);

                                //增加分数,合并后是多少则添加多少分
                                SecondActivity.getsecondActivity().addScore(cardsMap[x][y].getNum
                                        ());
                                swipeTrue = true;
                            }


                        }
                        break;
                    }
                }
            }
        }
        //判断卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        if (swipeTrue) {
            addRandomNum();   //滑动一次,添加一个随机数
            checkGameOver();  //每次添加完新数,就要判断是否达到游戏结束的状态
        }
    }

    private void swipeDown() {
        //记录卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        boolean swipeTrue = false;

        for (int x = 0; x < Config.LINES; x++) {
            for (int y = Config.LINES - 1; y >= 0; y--) {

                //当前行的当前位置的上一个元素开始,向上遍历
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    //如果上一个卡片有值
                    if (cardsMap[x][y1].getNum() > 0) {
                        //当前位置的卡片<=0(为空),让上一个卡片移过来
                        if (cardsMap[x][y].getNum() <= 0) {
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            //让上一个卡片移过来
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            //把刚刚上边移过来那个数设置为0
                            cardsMap[x][y1].setNum(0);

                            //
                            y++;
                            swipeTrue = true;
                        }
                        //当前位置与上边位置的卡片相等
                        else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            if (Config.type == 2) {
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() / 2);
                                cardsMap[x][y1].setNum(0);
                                switch (cardsMap[x][y].getNum()) {
                                    case 1024:
                                        SecondActivity.getsecondActivity().addScore(2);
                                        break;
                                    case 512:
                                        SecondActivity.getsecondActivity().addScore(4);
                                        break;
                                    case 256:
                                        SecondActivity.getsecondActivity().addScore(8);
                                        break;
                                    case 128:
                                        SecondActivity.getsecondActivity().addScore(16);
                                        break;
                                    case 64:
                                        SecondActivity.getsecondActivity().addScore(32);
                                        break;
                                    case 32:
                                        SecondActivity.getsecondActivity().addScore(64);
                                        break;
                                    case 16:
                                        SecondActivity.getsecondActivity().addScore(128);
                                        break;
                                    case 8:
                                        SecondActivity.getsecondActivity().addScore(256);
                                        break;
                                    case 4:
                                        SecondActivity.getsecondActivity().addScore(512);
                                        break;
                                    case 2:
                                        SecondActivity.getsecondActivity().addScore(1024);
                                        break;
                                }
                                swipeTrue = true;
                            } else {
                                //相等则直接将当前位置的数值*2,然后将右边那张卡片置为0
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                                cardsMap[x][y1].setNum(0);

                                //增加分数,合并后是多少则添加多少分
                                SecondActivity.getsecondActivity().addScore(cardsMap[x][y].getNum
                                        ());
                                swipeTrue = true;
                            }
                        }
                        break;
                    }
                }
            }
        }
        //判断卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        if (swipeTrue) {
            addRandomNum();   //滑动一次,添加一个随机数
            checkGameOver();  //每次添加完新数,就要判断是否达到游戏结束的状态
        }
    }

    private void swipeUp() {
        //记录卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        boolean swipeTrue = false;

        for (int x = 0; x < Config.LINES; x++) {
            for (int y = 0; y < Config.LINES; y++) {

                //当前行的当前位置的上一个元素开始,向上遍历
                for (int y1 = y + 1; y1 < Config.LINES; y1++) {
                    //如果上一个卡片有值
                    if (cardsMap[x][y1].getNum() > 0) {
                        //当前位置的卡片<=0(为空),让上一个卡片移过来
                        if (cardsMap[x][y].getNum() <= 0) {
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            //让上一个卡片移过来
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            //把刚刚上边移过来那个数设置为0
                            cardsMap[x][y1].setNum(0);

                            //
                            y--;
                            swipeTrue = true;
                        }
                        //当前位置与上边位置的卡片相等
                        else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            SecondActivity.getsecondActivity().getAnimLayer().createMoveAnim
                                    (cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            if (Config.type == 2) {
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() / 2);
                                cardsMap[x][y1].setNum(0);
                                switch (cardsMap[x][y].getNum()) {
                                    case 1024:
                                        SecondActivity.getsecondActivity().addScore(2);
                                        break;
                                    case 512:
                                        SecondActivity.getsecondActivity().addScore(4);
                                        break;
                                    case 256:
                                        SecondActivity.getsecondActivity().addScore(8);
                                        break;
                                    case 128:
                                        SecondActivity.getsecondActivity().addScore(16);
                                        break;
                                    case 64:
                                        SecondActivity.getsecondActivity().addScore(32);
                                        break;
                                    case 32:
                                        SecondActivity.getsecondActivity().addScore(64);
                                        break;
                                    case 16:
                                        SecondActivity.getsecondActivity().addScore(128);
                                        break;
                                    case 8:
                                        SecondActivity.getsecondActivity().addScore(256);
                                        break;
                                    case 4:
                                        SecondActivity.getsecondActivity().addScore(512);
                                        break;
                                    case 2:
                                        SecondActivity.getsecondActivity().addScore(1024);
                                        break;
                                }
                                swipeTrue = true;
                            } else {
                                //相等则直接将当前位置的数值*2,然后将右边那张卡片置为0
                                cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                                cardsMap[x][y1].setNum(0);

                                //增加分数,合并后是多少则添加多少分
                                SecondActivity.getsecondActivity().addScore(cardsMap[x][y].getNum
                                        ());
                                swipeTrue = true;
                            }
                        }
                        break;
                    }
                }
            }
        }
        //判断卡片是否有动静(填补空缺,或者合并),有动静则添加新卡片
        if (swipeTrue) {
            addRandomNum();   //滑动一次,添加一个随机数
            checkGameOver();  //每次添加完新数,就要判断是否达到游戏结束的状态
        }
    }

    //判断游戏是否结束
    public void checkGameOver() {
        /*
         * a:相邻没有相同
		 * b:没有空格
		 * 若a&&b,则游戏结束
		 * */

        boolean gameOver = true;

        ALL:
        for (int x = 0; x < Config.LINES; x++) {
            for (int y = 0; y < Config.LINES; y++) {
                //判断是否有空格   判断该位置上下左右是否有相同的卡片
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y])) ||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y])) ||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1])) ||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))
                        ) {

                    //如果有空格,并且周围有一个是相同的则认定为游戏没有结束
                    gameOver = false;
                    break ALL;
                }
            }
        }

        if (gameOver) {   //当游戏结束时,弹出窗口,让用户选择是否继续

            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startGame();    //如果用户点击重新开始,则开始新游戏
                                }

                            }
                    ).show();   //显示这个弹窗
        }
    }
}

