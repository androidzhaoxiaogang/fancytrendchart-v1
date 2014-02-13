package xg.fancytrendchart;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class TrendChart extends View {
	private boolean drawGradient = false;
	
	private int xAxisColor = 0xFF129FCD;
	private int yAxisColor = 0xFF129FCD;
	
	private int xAxisTextColor = 0xFF129FCD;
	private int yAxisTextColor = 0xFF129FCD;
	
	private int xAxisGridColor = 0xFF129FCD;
	private int yAxisGridColor = 0xFF129FCD;
	
	private int lineColor = 0xFF129FCD;
	private int lineWidth = 5;
	
	private float xAxispadding = 20f;
	private float yAxispadding = 20f;
	
	private float width;
	private float height;
	
	private Paint paint;
	
	private boolean disableTouch;
	
	private Bitmap chartBitmap = null;

	public TrendChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initTrendChart(context, attrs);
	}

	public TrendChart(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrendChart(Context context) {
		super(context, null);
	}
	
	public TrendChart(Context context, List<Float> list) {
		super(context);
		
	}
	
	/**
	 * Initialize chart data.
	 */
	private void initTrendChart(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FancyTrendChart);
		drawGradient = a.getBoolean(R.styleable.FancyTrendChart_drawGradient, drawGradient);
		xAxisColor = a.getInt(R.styleable.FancyTrendChart_xAxisColor, xAxisColor);
		yAxisColor = a.getInt(R.styleable.FancyTrendChart_yAxisColor, yAxisColor);
		xAxisTextColor = a.getInt(R.styleable.FancyTrendChart_xAxisTextColor, xAxisTextColor);
		xAxisTextColor = a.getInt(R.styleable.FancyTrendChart_xAxisTextColor, yAxisTextColor);
		xAxisGridColor = a.getInt(R.styleable.FancyTrendChart_xGridColor, xAxisGridColor);
		yAxisGridColor = a.getInt(R.styleable.FancyTrendChart_yGridColor, yAxisGridColor);
		lineColor = a.getInt(R.styleable.FancyTrendChart_lineColor, lineColor);
		xAxispadding = a.getFloat(R.styleable.FancyTrendChart_xAxisPadding, xAxispadding);
		yAxispadding = a.getFloat(R.styleable.FancyTrendChart_yAxisPadding, yAxispadding);
		a.recycle();
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
		
		if (chartBitmap == null) {
			initChartBitmap();
		}
		canvas.drawBitmap(chartBitmap, 0, 0, paint);
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
	
	public void setStrokeWidth(int width) {
		lineWidth = width;
	}
	
	public boolean isDisableTouch() {
		return disableTouch;
	}

	public void setDisableTouch(boolean disableTouch) {
		this.disableTouch = disableTouch;
	}
	
	private void initChartBitmap(){
		chartBitmap = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
		Canvas canvas = new Canvas(chartBitmap);
		drawXYAxises(canvas);
		drawTrendLine(canvas);
		canvas.save();
	}
	
	private void drawXYAxises(Canvas canvas) {
		
	}
	
	private void drawTrendLine(Canvas canvas) {
		
	}
}
