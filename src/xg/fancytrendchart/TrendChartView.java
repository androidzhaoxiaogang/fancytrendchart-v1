package xg.fancytrendchart;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


public class TrendChartView extends View {
	private boolean drawGradient = false;
	
	private int xTitleColor = 0xFF129FCD;
	private int xAxisColor = 0xFF129FCD;
	private int yAxisColor = 0xFF129FCD;
	
	private int xAxisTextColor = 0xFF129FCD;
	private int yAxisTextColor = 0xFF129FCD;
	
	private int xAxisGridColor = 0xFF129FCD;
	private int xAxisGridEndColor = Color.WHITE;
	private int yAxisGridColor = 0xFF129FCD;
	
	private int lineColor = 0xFF129FCD;
	
	private int titleTextSize = 18;
	private int xAxisTextSize = 12;
	
	private int lineStrokeWidth = 2;
	private int titleStrokeWidth = 2;
	private int xAxisStrokeWidth = 2;
	
	private int xGridCount = 7;
	
	private final Rect textBounds = new Rect();
	
	private int xAxispadding = 20;
	private int yAxispadding = 20;
	
	private float width;
	private float height;
	private float titleHeight;
	private float startXaxis;
	
	private Paint paint;
	private Paint xGridPaint;
	
	private boolean disableTouch;
	
	private Bitmap chartBitmap = null;
	
	private String title;
	private List<String> xValueList;
	private List<String> yValueList;

	public TrendChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initTrendChart(context, attrs);
	}

	public TrendChartView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrendChartView(Context context, String title) {
		super(context, null);
		this.title = title;
	}
	
	/**
	 * Initialize chart data.
	 */
	private void initTrendChart(Context context, AttributeSet attrs) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		xAxispadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, xAxispadding, dm);
		yAxispadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, yAxispadding, dm);
		titleTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, titleTextSize, dm);
		xAxisTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, xAxisTextSize, dm);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FancyTrendChart);
		drawGradient = a.getBoolean(R.styleable.FancyTrendChart_drawGradient, drawGradient);
		xAxisColor = a.getInt(R.styleable.FancyTrendChart_xAxisColor, xAxisColor);
		yAxisColor = a.getInt(R.styleable.FancyTrendChart_yAxisColor, yAxisColor);
		xAxisTextColor = a.getInt(R.styleable.FancyTrendChart_xAxisTextColor, xAxisTextColor);
		xAxisTextColor = a.getInt(R.styleable.FancyTrendChart_xAxisTextColor, yAxisTextColor);
		xAxisGridColor = a.getInt(R.styleable.FancyTrendChart_xGridColor, xAxisGridColor);
		yAxisGridColor = a.getInt(R.styleable.FancyTrendChart_yGridColor, yAxisGridColor);
		lineColor = a.getInt(R.styleable.FancyTrendChart_lineColor, lineColor);
		xAxispadding = a.getInt(R.styleable.FancyTrendChart_xAxisPadding, xAxispadding);
		yAxispadding = a.getInt(R.styleable.FancyTrendChart_yAxisPadding, yAxispadding);
		a.recycle();
		
		xValueList = new ArrayList<String>();
		yValueList = new ArrayList<String>();
		
		paint = new Paint();
		paint.setAntiAlias(true);
		
		xGridPaint = new Paint();
		xGridPaint.setAntiAlias(true);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if(disableTouch) {
			return super.dispatchTouchEvent(event);
		}
		
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		width = this.getWidth();
		height = this.getHeight();
		
//		if (chartBitmap == null) {
//			initChartBitmap();
//		}
		
		startDrawChart(canvas);
		//canvas.drawBitmap(chartBitmap, 0, 0, paint);
	}

	/**
	 * Sets the x axis color.
	 *
	 * @param color the new x axis color
	 */
	public void setXaxisColor(int color) {
		xAxisColor = color;
	}

	/**
	 * Sets the y axis color.
	 *
	 * @param color the new y axis color
	 */
	public void setYaxisColor(int color) {
		yAxisColor = color;
	}
	
	public void setLineStrokeWidth(int width) {
		lineStrokeWidth = width;
	}
	
	public void setTopTile(String title) {
		this.title = title;
	}
	
	public int getTitleTextSize() {
		return titleTextSize;
	}

	public void setTitleTextSize(int titleTextSize) {
		this.titleTextSize = titleTextSize;
	}
	
	public int getTitleStrokeWidth() {
		return titleStrokeWidth;
	}

	public void setTitleStrokeWidth(int titleStrokeWidth) {
		this.titleStrokeWidth = titleStrokeWidth;
	}
	
	public int getxGridCount() {
		return xGridCount;
	}

	public void setxGridCount(int xGridCount) {
		this.xGridCount = xGridCount;
	}
	
	public boolean isDisableTouch() {
		return disableTouch;
	}

	public void setDisableTouch(boolean disableTouch) {
		this.disableTouch = disableTouch;
	}
	
	public List<String> getxValueList() {
		return xValueList;
	}

	public void setxValueList(List<String> xValueList) {
		this.xValueList.clear();
		this.xValueList.addAll(xValueList);
	}
	
	public List<String> getyValueList() {
		return yValueList;
	}

	public void setyValueList(List<String> yValueList) {
		this.yValueList.clear();
		this.yValueList.addAll(yValueList);
	}
	
	private void startDrawChart(Canvas canvas){
		//chartBitmap = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
		//Canvas canvas = new Canvas(chartBitmap);
		drawTopTitle(canvas);
		drawXYAxises(canvas);
		drawTrendLine(canvas);
		//canvas.save();
	}
	
	private void drawTopTitle(Canvas canvas) {
		paint.setColor(xTitleColor);
		paint.setStrokeWidth(titleStrokeWidth);
		paint.setTextSize(titleTextSize);
		paint.getTextBounds(title, 0, 1, textBounds);
		
		titleHeight = textBounds.height();
		canvas.drawText(title, xAxispadding / 2, yAxispadding / 2 + titleHeight, paint);
	}
	
	private void drawXYAxises(Canvas canvas) {
		drawXaxis(canvas);
	}
	
	private void drawXaxis(Canvas canvas) {
		paint.getTextBounds("11.11", 0, 1, textBounds);
		startXaxis = textBounds.width() + xAxispadding / 2;
		paint.setColor(xAxisColor);
		paint.setStrokeWidth(xAxisStrokeWidth);
		paint.setTextSize(xAxisTextSize);
		canvas.drawLine(startXaxis, height - yAxispadding, width - xAxispadding/2, 
				height - yAxispadding, paint); //bottom line
		
		paint.setColor(xAxisColor);
		paint.setStrokeWidth(xAxisStrokeWidth);
		paint.setTextSize(xAxisTextSize);
		
		float xGridSpace = (width - xAxispadding / 2 - startXaxis) / 6;
		float xGridStart = startXaxis;
		
		for (int i = 0; i < xGridCount; i++) {
			if (xValueList.size() >= xGridCount) {
				if (i == 0) {
					canvas.drawText(xValueList.get(i), startXaxis, height - yAxispadding, paint);
				} else if (i == xGridCount - 1) {
					canvas.drawText(xValueList.get(i), xGridStart, height - yAxispadding, paint);
				}
			}
			
			LinearGradient gradient = new LinearGradient(xGridStart, 
					height - yAxispadding, xGridStart, yAxispadding, 
					xAxisColor, xAxisGridEndColor, Shader.TileMode.MIRROR);
			xGridPaint.setShader(gradient);
			canvas.drawLine(xGridStart, height - yAxispadding, xGridStart, 
					yAxispadding + titleHeight / 2, paint);
			xGridStart += xGridSpace;
		}
	}
	
	private void drawTrendLine(Canvas canvas) {
		
	}


}
