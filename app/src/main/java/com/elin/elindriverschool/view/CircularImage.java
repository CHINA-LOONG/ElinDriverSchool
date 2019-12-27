package com.elin.elindriverschool.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * @author: KingLone
 * @类   说   明:	
 * @version 1.0
 * @创建时间：2015-4-1 上午11:03:38
 * 
 */
public class CircularImage extends MaskedImage{

	public CircularImage(Context context) {
		super(context);
		
	}
	public CircularImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}
	public CircularImage(Context paramContext, AttributeSet paramAttributeSet,
						 int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}
	@Override
	public Bitmap creatMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint(1);
		localPaint.setColor(-16777216);
		float f1 = getWidth();
		float f2 = getHeight();
		RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
		localCanvas.drawOval(localRectF, localPaint);
		return localBitmap;
	}
	

}
