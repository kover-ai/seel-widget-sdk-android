package com.seel.widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.seel.widget.R;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.utils.DpPxUtils;

/**
 * Coverage Details View
 */
public class CoverageDetailsView extends LinearLayout {
    
    private QuotesResponse quoteResponse;
    private TextView titleLine;
    
    public CoverageDetailsView(Context context) {
        super(context);
        init();
    }
    
    public CoverageDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public CoverageDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setOrientation(VERTICAL);
        setBackgroundColor(0xFF333333); // Brighter gray background, contrasting with black titleLine
        int padding = DpPxUtils.dp(23);
        setPadding(padding, padding, padding, padding);
        
        // Create title row
        titleLine = new TextView(getContext());
        titleLine.setText("Get a Full Refund, No Questions Asked");
        titleLine.setTextColor(0xFFFFFFFF); // White text
        titleLine.setTextSize(16);
        titleLine.setTypeface(null, android.graphics.Typeface.BOLD);
        titleLine.setMaxLines(2); // Allow multi-line display
        titleLine.setEllipsize(android.text.TextUtils.TruncateAt.END); // Use ellipsis for overflow

        addView(titleLine);
    }
    
    /**
     * Update views
     */
    public void updateViews() {
        java.util.List<String> coverageTexts = quoteResponse != null && quoteResponse.getExtraInfo() != null 
            ? quoteResponse.getExtraInfo().getCoverageDetailsText() 
            : null;
        
        // If no data, hide view
        if (coverageTexts == null || coverageTexts.isEmpty()) {
            setVisibility(GONE);
            return;
        }
        
        setVisibility(VISIBLE);
        
        // Remove old detail rows (keep title row)
        while (getChildCount() > 1) {
            removeViewAt(1);
        }

        // Add detail rows
        for (String text : coverageTexts) {
            CoverageLineView lineView = new CoverageLineView(getContext());
            lineView.setIconImage(R.mipmap.icon_bg_select);
            lineView.setContent(text);
            lineView.setContentColor(getResources().getColor(android.R.color.white));
            lineView.updateViews();

            // Set LineView spacing to 8dp
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lineParams.setMargins(0, DpPxUtils.dp(12), 0, 0);
            lineView.setLayoutParams(lineParams);

            addView(lineView);
        }

        // Force relayout
        titleLine.requestLayout();
        invalidate();
    }
    
    public void setQuoteResponse(QuotesResponse quoteResponse) {
        this.quoteResponse = quoteResponse;
    }
}




