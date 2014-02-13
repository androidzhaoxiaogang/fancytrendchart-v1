package xg.fancytrendchart;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


public class TrendView extends View {

	public TrendView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
	}

	public TrendView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrendView(Context context) {
		super(context, null);
	}
	
	/**
	 * Initialize the data to be used.
	 */
	private void initialize() {
		
	}

}
