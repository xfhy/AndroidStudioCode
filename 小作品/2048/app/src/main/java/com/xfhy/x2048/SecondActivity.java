package com.xfhy.x2048;


import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

	private TextView tvScore;                          //记录分数
	private static SecondActivity mainActivity = null;   //让外界得到这个Activity
	private int score = 0;                             //分数
	private TextView tvBestScore;                      //最高分
	private LinearLayout root = null;                  //最底层的布局文件(LinearLayout)
	private Button btnNewGame;                         //开始新游戏的按钮
	private GameView gameView;                         //获取自定义游戏逻辑布局控件
	private AnimLayer animLayer = null;                //获取移动特效的布局控件
	public static final String SP_KEY_BEST_SCORE = "bestScore";

	//在外界可以访问到MainActivity实例的
	public SecondActivity() {      //构造函数
		mainActivity = this;    //外界可访问
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		tvScore = (TextView)findViewById(R.id.tvScore);   //找到id里面的分数TextView控件

		root = (LinearLayout) findViewById(R.id.container);
		//root.setBackgroundColor(0xfffaf8ef);

		tvBestScore = (TextView) findViewById(R.id.tvBestScore);

		gameView = (GameView) findViewById(R.id.gameView);

		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				gameView.startGame();
			}
		});

		animLayer = (AnimLayer)findViewById(R.id.animLayer);
	}

	//获取当前Activity
	public static SecondActivity getsecondActivity() {
		return mainActivity;
	}

	//把分数归零
	public void clearScore()
	{
		score=0;
		showScore();   //分数归零后,分数应重新显示
	}

	//呈现分数
	public void showScore()
	{
		tvScore.setText(score+"");
	}

	//分数累加
	public void addScore(int s)
	{
		score += s;  //分数累加
		showScore(); //及时显示当前分数

		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}

	//保存最高分
	public void saveBestScore(int s){
		//getPreferences:保存最高分要用到    这里是保存到文件中去了
		//Shared Preferences:
		//主要用于存储key-value对格式的数据，是轻量级的存储机制，轻到只能存储基本数据类型
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, s);
		e.commit();
	}

	//得到最高分
	public int getBestScore(){
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}

	//显示最高分
	public void showBestScore(int s){
		tvBestScore.setText(s+"");
	}

	//得到控制移动动画的那个类
	public AnimLayer getAnimLayer() {
		return animLayer;
	}
}

