package com.android.commonlibrary.util;

import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.android.commonlibrary.app.LibraryConfig;

/***
 * 设置文本工具类
 *
 *  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE: (start，end) --- 前后下标都不包括
 *  Spanned.SPAN_EXCLUSIVE_INCLUSIVE: (start，end] --- 前面不包括,后面包括
 *  Spanned.SPAN_INCLUSIVE_EXCLUSIVE: [start，end) --- 前面包括,后面不包括
 *  Spanned.SPAN_INCLUSIVE_INCLUSIVE: [start，end] --- 前后都包括
 */
public class SpannableStringUtil {

    private static final String DEFAULT_LETTER="A";

	/**
	 * 设置字体大小
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param scale  表示为默认字体大小的多少倍，为 float
	 * @return
	 */
	public static SpannableString setTextSize(SpannableString source,int index1, int index2,float scale){
		if(source!=null) {
			source.setSpan(new RelativeSizeSpan(scale), index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置字体大小
	 *
	 * @param source 操作源，为String
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param scale  表示为默认字体大小的多少倍，为 float
	 * @return
	 */
	public static SpannableString setTextSize(String source,int index1, int index2,float scale){
		SpannableString result=getSpannableString(source);
		return setTextSize(result,index1,index2,scale);
	}

	/**
	 * 设置字体大小
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 要改变的部分
	 * @param scale 表示为默认字体大小的多少倍，为 float
	 * @return
	 */
	public static SpannableString setTextSize(SpannableString source,String changeStr, float scale){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			return setTextSize(source,startIndex,endIndex,scale);
		}
		return null;
	}

	/**
	 * 设置字体大小
	 *
	 * @param source 操作源，为String
	 * @param changeStr 要改变的部分
	 * @param scale 表示为默认字体大小的多少倍，为 float
	 * @return
	 */
	public static SpannableString setTextSize(String source,String changeStr, float scale){
		SpannableString spannableString=getSpannableString(source);
		return setTextSize(spannableString,changeStr,scale);
	}

	/**
	 * 设置文字前景色
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param color  设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextFrontColor(SpannableString source,int index1, int index2,int color){
		if(source!=null) {
			int colorVaule = ContextCompat.getColor(LibraryConfig.getInstance().getApplication(), color);
			ForegroundColorSpan fcs=new ForegroundColorSpan(colorVaule);
			source.setSpan(fcs,index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置文字前景色
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param color  设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextFrontColor(String source,int index1, int index2,int color){
		SpannableString result=getSpannableString(source);
		return setTextFrontColor(result,index1,index2,color);
	}

	/**
	 * 设置文字前景色
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 要操作的内容
	 * @param color 设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextFrontColor(SpannableString source,String changeStr, int color){
       if(source!=null){
		   String temp=source.toString();
		   int startIndex = temp.indexOf(changeStr);
		   if (startIndex == -1) {
			   return source;
		   }
		   int endIndex=startIndex+changeStr.length();
		   return setTextFrontColor(source,startIndex,endIndex,color);
	   }
	   return null;
	}

	/**
	 * 设置文字前景色
	 *
	 * @param source 操作源，为String
	 * @param changeStr 要操作的内容
	 * @param color 设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextFrontColor(String source, String changeStr, int color){
		SpannableString result=getSpannableString(source);
		return setTextFrontColor(result,changeStr,color);
	}

	/**
	 * 设置文字背景色
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param color  设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextBackgroundColor(SpannableString source,int index1,int index2, int color){
		if(source!=null){
			int colorVaule = ContextCompat.getColor(LibraryConfig.getInstance().getApplication(), color);
			BackgroundColorSpan bcs=new BackgroundColorSpan(colorVaule);
			source.setSpan(bcs,index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置文字背景色
	 *
	 * @param source 操作源，为String
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param color  设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextBackgroundColor(String source,int index1,int index2, int color){
		SpannableString result=getSpannableString(source);
		return setTextBackgroundColor(result,index1,index2,color);
	}

	/**
	 * 设置文字背景色
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 要操作的内容
	 * @param color 设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextBackgroundColor(SpannableString source, String changeStr, int color){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			return setTextBackgroundColor(source,startIndex,endIndex,color);
		}
		return null;
	}

	/**
	 * 设置文字背景色
	 *
	 * @param source 操作源，为String
	 * @param changeStr 要操作的内容
	 * @param color 设置的颜色,如：R.color.red
	 * @return
	 */
	public static SpannableString setTextBackgroundColor(String source, String changeStr, int color){
		SpannableString result=getSpannableString(source);
		return setTextBackgroundColor(result,changeStr,color);
	}

	/**
	 * 设置字体样式
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param textStyle 字体样式: Typeface.NORMAL：普通
	 *                           Typeface.BORD：粗体
	 *                           Typeface.ITALIC：斜体
	 *                           Typeface.BORD_ITALIC：粗斜体
	 * @return
	 */
	public static SpannableString setTextStyle(SpannableString source, int index1,int index2, int textStyle){
        if(source!=null){
			StyleSpan styleSpan=new StyleSpan(textStyle);
			source.setSpan(styleSpan,index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置字体样式
	 *
	 * @param source 操作源，为String
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param textStyle 字体样式: Typeface.NORMAL：普通
	 *                           Typeface.BORD：粗体
	 *                           Typeface.ITALIC：斜体
	 *                           Typeface.BORD_ITALIC：粗斜体
	 * @return
	 */
	public static SpannableString setTextStyle(String source, int index1,int index2, int textStyle){
		SpannableString result=getSpannableString(source);
		return setTextStyle(result,index1,index2,textStyle);
	}

	/**
	 * 设置字体样式
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 要操作的内容
	 * @param textStyle 字体样式: Typeface.NORMAL：普通
	 *                           Typeface.BORD：粗体
	 *                           Typeface.ITALIC：斜体
	 *                           Typeface.BORD_ITALIC：粗斜体
	 * @return
	 */
	public static SpannableString setTextStyle(SpannableString source, String changeStr, int textStyle){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			return setTextStyle(source,startIndex,endIndex,textStyle);
		}
		return null;
	}

	/**
	 * 设置字体样式
	 *
	 * @param source 操作源，为String
	 * @param changeStr 要操作的内容
	 * @param textStyle 字体样式: Typeface.NORMAL：普通
	 *                           Typeface.BORD：粗体
	 *                           Typeface.ITALIC：斜体
	 *                           Typeface.BORD_ITALIC：粗斜体
	 * @return
	 */
	public static SpannableString setTextStyle(String source, String changeStr, int textStyle){
		SpannableString result=getSpannableString(source);
		return setTextStyle(result,changeStr,textStyle);
	}

	/**
	 * 设置文字划线 (中划线 或 下划线)
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param isCenter boolean,true时是中划线,false时是下划线
	 * @return
	 */
	public static SpannableString setTextLine(SpannableString source, int index1,int index2, boolean isCenter){
		if (source != null) {
			CharacterStyle cts=null;
			if(isCenter){
				cts=new StrikethroughSpan();
			}else{
				cts=new UnderlineSpan();
			}
			if(cts!=null) {
				source.setSpan(cts, index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				return source;
			}
		}
		return null;
	}

	/**
	 * 设置文字划线 (中划线 或 下划线)
	 *
	 * @param source 操作源，为String
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param isCenter boolean,true时是中划线,false时是下划线
	 * @return
	 */
	public static SpannableString setTextLine(String source, int index1,int index2, boolean isCenter){
		SpannableString result=getSpannableString(source);
		return setTextLine(result,index1,index2,isCenter);
	}

	/**
	 * 设置文字划线 (中划线 或 下划线)
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 要操作的内容
	 * @param isCenter boolean,true时是中划线,false时是下划线
	 * @return
	 */
	public static SpannableString setTextLine(SpannableString source,String changeStr, boolean isCenter){
        if(source!=null){
        	String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			return setTextLine(source,startIndex,endIndex,isCenter);
		}
		return null;
	}

	/**
	 * 设置文字划线 (中划线 或 下划线)
	 *
	 * @param source 操作源，为String
	 * @param changeStr 要操作的内容
	 * @param isCenter boolean,true时是中划线,false时是下划线
	 * @return
	 */
	public static SpannableString setTextLine(String source,String changeStr, boolean isCenter){
		SpannableString result=getSpannableString(source);
		return setTextLine(result,changeStr,isCenter);
	}

	/**
	 * 设置内容上角标，下角标
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param isTop  boolean,为true时是上角标,为false时是下角标
	 * @return
	 */
	public static SpannableString setTextMark(SpannableString source, int index1,int index2, boolean isTop){
		if(source!=null){
			MetricAffectingSpan mas=null;
			if(isTop){
				//上角标
				mas=new SuperscriptSpan();
			}else{
				//下角标
				mas=new SubscriptSpan();
			}
			source.setSpan(mas, index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置内容上角标，下角标
	 *
	 * @param source 操作源，为String
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param isTop  boolean,为true时是上角标,为false时是下角标
	 * @return
	 */
	public static SpannableString setTextMark(String source, int index1,int index2, boolean isTop){
		SpannableString result=getSpannableString(source);
		return setTextMark(result,index1,index2,isTop);
	}

	/**
	 * 设置内容上角标，下角标
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param isTop boolean,为true时是上角标,为false时是下角标
	 * @return
	 */
	public static SpannableString setTextMark(SpannableString source, String changeStr, boolean isTop){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			return setTextMark(source,startIndex,endIndex,isTop);
		}
		return null;
	}

	/**
	 * 设置内容上角标，下角标
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param isTop boolean,为true时是上角标,为false时是下角标
	 * @return
	 */
	public static SpannableString setTextMark(String source, String changeStr, boolean isTop){
		SpannableString result=getSpannableString(source);
		return setTextMark(result,changeStr,isTop);
	}

	/**
	 * 设置内容沿x方向拉伸
	 *
	 * @param source 操作源，为SpannableString
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param xScale 缩放比例
	 * @return
	 */
	public static SpannableString setTextScaleX(SpannableString source, int index1,int index2, float xScale){
		if(source!=null){
			ScaleXSpan scaleXSpan=new ScaleXSpan(xScale);
			source.setSpan(scaleXSpan,index1,index2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置内容沿x方向拉伸
	 *
	 * @param source 操作源，为String
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @param xScale 缩放比例
	 * @return
	 */
	public static SpannableString setTextScaleX(String source, int index1,int index2, float xScale){
		SpannableString result=getSpannableString(source);
		return setTextScaleX(result,index1,index2,xScale);
	}

	/**
	 * 设置内容沿x方向拉伸
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param xScale 缩放比例
	 * @return
	 */
	public static SpannableString setTextScaleX(SpannableString source, String changeStr, float xScale){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			return setTextScaleX(source,startIndex,endIndex,xScale);
		}
		return null;
	}

	/**
	 * 设置内容沿x方向拉伸
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param xScale 缩放比例
	 * @return
	 */
	public static SpannableString setTextScaleX(String source, String changeStr, float xScale){
		SpannableString result=getSpannableString(source);
		return setTextScaleX(result,changeStr,xScale);
	}

	/**
	 * changeStr左侧插入图片
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static SpannableString setTextLeftImage(SpannableString source, String changeStr, int drawableId) {
		if (source != null) {
			String temp = source.toString();
			int index = temp.indexOf(changeStr);
			if (index == -1) {
				return source;
			}
			Drawable drawable = LibraryConfig.getInstance().getApplication().getResources().getDrawable(drawableId,null);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() , drawable.getIntrinsicHeight());
			ImageSpan imageSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);

			SpannableStringBuilder builder=new SpannableStringBuilder(source);
			builder.insert(index,DEFAULT_LETTER);
			source=SpannableString.valueOf(builder);
			if(index==0){
				source.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}else if(index>0){
				source.setSpan(imageSpan, index, index+1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			return source;
		}
		return null;
	}

	/**
	 * changeStr左侧插入图片
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static SpannableString setTextLeftImage(String source, String changeStr, int drawableId) {
		SpannableString result=getSpannableString(source);
		return setTextLeftImage(result,changeStr,drawableId);
	}

	/**
	 * changeStr右侧插入图片
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static SpannableString setTextRightImage(SpannableString source, String changeStr, int drawableId) {
		if(source!=null){
			String temp = source.toString();
			int index = temp.indexOf(changeStr);
			if (index == -1) {
				return source;
			}
			index=index+changeStr.length();
			Drawable drawable = LibraryConfig.getInstance().getApplication().getResources().getDrawable(drawableId,null);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() , drawable.getIntrinsicHeight());
			ImageSpan imageSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
			SpannableStringBuilder builder=new SpannableStringBuilder(source);
			builder.insert(index,DEFAULT_LETTER);
			source=SpannableString.valueOf(builder);
			source.setSpan(imageSpan, index, index+1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * changeStr右侧插入图片
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static SpannableString setTextRightImage(String source, String changeStr, int drawableId) {
		SpannableString result=getSpannableString(source);
		return setTextRightImage(result,changeStr,drawableId);
	}

	/**
	 * 将changeStr替换成图片
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static SpannableString replaceTextByImage(SpannableString source, String changeStr, int drawableId) {
		if(source!=null){
			String temp = source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();

			Drawable drawable=LibraryConfig.getInstance().getApplication().getDrawable(drawableId);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth() , drawable.getIntrinsicHeight());
			ImageSpan imageSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
			source.setSpan(imageSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 将changeStr替换成图片
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static SpannableString replaceTextByImage(String source, String changeStr, int drawableId) {
		SpannableString result=getSpannableString(source);
		return replaceTextByImage(result,changeStr,drawableId);
	}

	/**
	 * changeStr左侧插入图片,基于底部
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
    public static SpannableString setTextLeftDrawable(SpannableString source, String changeStr, final int drawableId){
		if(source!=null) {
			String temp = source.toString();
			int index = temp.indexOf(changeStr);
			if (index == -1) {
				return source;
			}
			DynamicDrawableSpan dds = new DynamicDrawableSpan() {
				@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
				@Override
				public Drawable getDrawable() {
					Drawable drawable=LibraryConfig.getInstance().getApplication().getDrawable(drawableId);
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
					return drawable;
				}
			};
			if(index==0){
				SpannableStringBuilder builder=new SpannableStringBuilder(source);
				builder.insert(index,DEFAULT_LETTER);
				source=SpannableString.valueOf(builder);
				source.setSpan(dds, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}else if(index>0){
				source.setSpan(dds, index-1, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			return source;
		}
		return null;
	}

	/**
	 * changeStr左侧插入图片,基于底部
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	public static SpannableString setTextLeftDrawable(String source, String changeStr,int drawableId){
		SpannableString result=getSpannableString(source);
		return setTextLeftDrawable(result,changeStr,drawableId);
	}

	/**
	 * changeStr右侧插入图片,基于底部
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	public static SpannableString setTextRightDrawable(SpannableString source, String changeStr, final int drawableId) {
		if(source!=null){
			String temp = source.toString();
			int index = temp.indexOf(changeStr);
			if (index == -1) {
				return source;
			}
			index=index+changeStr.length();
			DynamicDrawableSpan dds = new DynamicDrawableSpan() {
				@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
				@Override
				public Drawable getDrawable() {
					Drawable drawable = LibraryConfig.getInstance().getApplication().getDrawable(drawableId);
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
					return drawable;
				}
			};
			SpannableStringBuilder builder=new SpannableStringBuilder(source);
			builder.insert(index,DEFAULT_LETTER);
			source=SpannableString.valueOf(builder);
			source.setSpan(dds, index, index+1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * changeStr右侧插入图片,基于底部
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	public static SpannableString setTextRightDrawable(String source, String changeStr, int drawableId) {
		SpannableString result=getSpannableString(source);
		return setTextRightDrawable(result,changeStr,drawableId);
	}

	/**
	 * 将changeStr替换成图片,基于底部
	 *
	 * @param source 操作源，为SpannableString
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	public static SpannableString replaceTextByDrawable(SpannableString source, String changeStr, final int drawableId) {
		if(source!=null){
			String temp = source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex=startIndex+changeStr.length();
			DynamicDrawableSpan dds = new DynamicDrawableSpan() {
				@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
				@Override
				public Drawable getDrawable() {
					Drawable drawable =LibraryConfig.getInstance().getApplication().getDrawable(drawableId);
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
					return drawable;
				}
			};
			source.setSpan(dds, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 将changeStr替换成图片,基于底部
	 *
	 * @param source 操作源，为String
	 * @param changeStr 操作内容
	 * @param drawableId 图片id,如:R.drawable.ic
	 * @return
	 */
	public static SpannableString replaceTextByDrawable(String source, String changeStr, int drawableId) {
		SpannableString result=getSpannableString(source);
		return replaceTextByDrawable(result,changeStr,drawableId);
	}

    /**
     * 设置可点击
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为SpannableString
     * @param index1 起始下标
     * @param index2 终止下标
     * @param colorId 可点击部分颜色资源id,如:R.color.red
     * @param isUnderLine 可点击部分是否添加下划线, true时添加,false时不添加
     * @param listener 监听类
     * @return
     */
    public static SpannableString setClickText(SpannableString source, int index1, int index2, final int colorId, final boolean isUnderLine, final View.OnClickListener listener) {
        if (source != null) {
            source.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(ContextCompat.getColor(LibraryConfig.getInstance().getApplication(),colorId));//设置颜色
                    ds.setUnderlineText(isUnderLine);//false时表示去掉下划线
                }
            }, index1, index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return source;
        }
        return null;
    }

    /**
     * 设置可点击
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为String
     * @param index1 起始下标
     * @param index2 终止下标
     * @param colorId 可点击部分颜色资源id,如:R.color.red
     * @param isUnderLine 可点击部分是否添加下划线, true时添加,false时不添加
     * @param listener 监听类
     * @return
     */
    public static SpannableString setClickText(String source, int index1, int index2, int colorId,boolean isUnderLine,View.OnClickListener listener) {
        SpannableString result=getSpannableString(source);
        return setClickText(result,index1,index2,colorId,isUnderLine,listener);
    }

    /**
     * 设置可点击
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为SpannableString
     * @param changeStr 操作内容
     * @param colorId 可点击部分颜色资源id,如:R.color.red
     * @param isUnderLine 可点击部分是否添加下划线, true时添加,false时不添加
     * @param listener 监听类
     * @return
     */
    public static SpannableString setClickText(SpannableString source, String changeStr, int colorId,boolean isUnderLine,View.OnClickListener listener){
        if(source!=null){
            String temp=source.toString();
            int startIndex = temp.indexOf(changeStr);
            if (startIndex == -1) {
                return source;
            }
            int endIndex=startIndex+changeStr.length();
            return setClickText(source,startIndex,endIndex,colorId,isUnderLine,listener);
        }
        return null;
    }

    /**
     * 设置可点击
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为String
     * @param changeStr 操作内容
     * @param colorId 可点击部分颜色资源id,如:R.color.red
     * @param isUnderLine 可点击部分是否添加下划线, true时添加,false时不添加
     * @param listener 监听类
     * @return
     */
    public static SpannableString setClickText(String source, String changeStr, int colorId,boolean isUnderLine,View.OnClickListener listener){
        SpannableString result=getSpannableString(source);
        return setClickText(result,changeStr,colorId,isUnderLine,listener);
    }

    /**
     * 打电话
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为SpannableString
     * @param phoneNum 电话号码
     * @param index1 起始下标
     * @param index2 终止下标
     * @return
     */
    public static SpannableString callUp(SpannableString source, String phoneNum,int index1, int index2){
        return setTextURL(source,"tel:"+phoneNum,index1,index2);
    }

    /**
     * 打电话
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为String
     * @param phoneNum 电话号码
     * @param index1 起始下标
     * @param index2 终止下标
     * @return
     */
    public static SpannableString callUp(String source, String phoneNum,int index1, int index2){
        return setTextURL(source,"tel:"+phoneNum,index1,index2);
    }

    /***
     * 打电话
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为SpannableString
     * @param phoneNum 电话号码
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString callUp(SpannableString source, String phoneNum,String changeStr){
        return setTextURL(source,"tel:"+phoneNum,changeStr);
    }

    /***
     * 打电话
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为String
     * @param phoneNum 电话号码
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString callUp(String source, String phoneNum,String changeStr){
        return setTextURL(source,"tel:"+phoneNum,changeStr);
    }


    /**
     * 发邮件
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为SpannableString
     * @param email 邮箱地址,如：125489713@qq.com
     * @param index1 起始下标
     * @param index2 终止下标
     * @return
     */
    public static SpannableString sendEmail(SpannableString source, String email,int index1, int index2){
        return setTextURL(source,"mailto:"+email,index1,index2);
    }

    /**
     * 发邮件
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为String
     * @param email 邮箱地址,如：125489713@qq.com
     * @param index1 起始下标
     * @param index2 终止下标
     * @return
     */
    public static SpannableString sendEmail(String source, String email,int index1, int index2){
        return setTextURL(source,"mailto:"+email,index1,index2);
    }

    /***
     * 发邮件
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为SpannableString
     * @param email 邮箱地址,如：125489713@qq.com
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString sendEmail(SpannableString source, String email,String changeStr){
        return setTextURL(source,"mailto:"+email,changeStr);
    }

    /***
     * 发邮件
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为String
     * @param email 邮箱地址,如：125489713@qq.com
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString sendEmail(String source, String email,String changeStr){
        return setTextURL(source,"mailto:"+email,changeStr);
    }

    /**
     * 发短信
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为SpannableString
     * @param sms 短信电话号码,如：13595247280
     * @param index1 起始下标
     * @param index2 终止下标
     * @return
     */
    public static SpannableString sendSMS(SpannableString source, String sms,int index1, int index2){
        return setTextURL(source,"sms:"+sms,index1,index2);
    }

    /**
     * 发短信
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为String
     * @param sms 短信电话号码,如：13595247280
     * @param index1 起始下标
     * @param index2 终止下标
     * @return
     */
    public static SpannableString sendSMS(String source, String sms,int index1, int index2){
        return setTextURL(source,"sms:"+sms,index1,index2);
    }

    /***
     * 发短信
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为SpannableString
     * @param sms 短信电话号码,如：13595247280
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString sendSMS(SpannableString source, String sms,String changeStr){
        return setTextURL(source,"sms:"+sms,changeStr);
    }

    /***
     * 发短信
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为String
     * @param sms 短信电话号码,如：13595247280
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString sendSMS(String source, String sms,String changeStr){
        return setTextURL(source,"sms:"+sms,changeStr);
    }

	/**
	 * 发彩信
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源，为SpannableString
	 * @param mms 发彩信电话号码,如：13595247280
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString sendMMS(SpannableString source, String mms,int index1, int index2){
		return setTextURL(source,"mms:"+mms,index1,index2);
	}

	/**
	 * 发彩信
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源，为String
	 * @param mms 发彩信电话号码,如：13595247280
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString sendMMS(String source, String mms,int index1, int index2){
		return setTextURL(source,"mms:"+mms,index1,index2);
	}

	/***
	 * 发彩信
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源,为SpannableString
	 * @param mms 发彩信电话号码,如：13595247280
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString sendMMS(SpannableString source, String mms,String changeStr){
		return setTextURL(source,"mms:"+mms,changeStr);
	}

	/***
	 * 发彩信
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源,为String
	 * @param mms 发彩信电话号码,如：13595247280
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString sendMMS(String source, String mms,String changeStr){
		return setTextURL(source,"mms:"+mms,changeStr);
	}

	/**
	 * 地图
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源，为SpannableString
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString sendGEO(SpannableString source,String longitude,String latitude,int index1, int index2){
		return setTextURL(source,"geo:"+latitude+","+longitude,index1,index2);
	}

	/**
	 * 地图
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源，为String
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString sendGEO(String source, String longitude,String latitude,int index1, int index2){
		return setTextURL(source,"geo:"+latitude+","+longitude,index1,index2);
	}

	/***
	 * 地图
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源,为SpannableString
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString sendGEO(SpannableString source,  String longitude,String latitude,String changeStr){
		return setTextURL(source,"geo:"+latitude+","+longitude,changeStr);
	}

	/***
	 * 地图
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源,为String
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString sendGEO(String source,  String longitude,String latitude,String changeStr){
		return setTextURL(source,"geo:"+latitude+","+longitude,changeStr);
	}

	/**
	 * 打开网址
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源，为SpannableString
	 * @param url 网址,如：http://www.baidu.com
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString openWeb(SpannableString source, String url,int index1, int index2){
		return setTextURL(source,url,index1,index2);
	}

	/**
	 * 打开网址
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源，为String
	 * @param url 网址,如：http://www.baidu.com
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString openWeb(String source, String url,int index1, int index2){
		return setTextURL(source,url,index1,index2);
	}

	/***
	 * 打开网址
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源,为SpannableString
	 * @param url 网址,如：http://www.baidu.com
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString openWeb(SpannableString source, String url,String changeStr){
		return setTextURL(source,url,changeStr);
	}

	/***
	 * 打开网址
	 *
	 * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
	 *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
	 *
	 * @param source 操作源,为String
	 * @param url 网址,如：http://www.baidu.com
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString openWeb(String source, String url,String changeStr){
		return setTextURL(source,url,changeStr);
	}

	/**
	 * 设置项目符号
	 *
	 * @param source 操作源,为SpannableString
	 * @param itemWidth 项目符号宽度
	 * @param colorId 项目符号颜色,如:R.color.red
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString setTextItem(SpannableString source, int itemWidth,int colorId,int index1, int index2){
		if(source!=null){
			BulletSpan bulletSpan=new BulletSpan(itemWidth, ContextCompat.getColor(LibraryConfig.getInstance().getApplication(),colorId));
			source.setSpan(bulletSpan, index1, index2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置项目符号
	 *
	 * @param source 操作源,为String
	 * @param itemWidth 项目符号宽度
	 * @param colorId 项目符号颜色,如:R.color.red
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString setTextItem(String source, int itemWidth,int colorId,int index1, int index2){
		SpannableString result=getSpannableString(source);
		return setTextItem(result,itemWidth,colorId,index1,index2);
	}

	/**
	 * 设置项目符号
	 *
	 * @param source 操作源,为SpannableString
	 * @param itemWidth 项目符号宽度
	 * @param colorId 项目符号颜色,如:R.color.red
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString setTextItem(SpannableString source,int itemWidth,int colorId,String changeStr){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex = startIndex + changeStr.length();
			return setTextItem(source,itemWidth,colorId,startIndex,endIndex);
		}
		return null;
	}

	/**
	 * 设置项目符号
	 *
	 * @param source 操作源,为String
	 * @param itemWidth 项目符号宽度
	 * @param colorId 项目符号颜色,如:R.color.red
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString setTextItem(String source,int itemWidth,int colorId,String changeStr) {
		SpannableString result = getSpannableString(source);
		return setTextItem(result,itemWidth,colorId,changeStr);
	}

	/**
	 * 设置模糊
	 *
	 * @param source 操作源,为SpannableString
	 * @param radius 模糊半径
	 * @param style 风格: BlurMaskFilter.Blur.NORMAL 默认类型，模糊内外边界
	 *                   BlurMaskFilter.Blur.INNER 内部模糊
	 *                   BlurMaskFilter.Blur.OUTER 外部模糊
	 *                   BlurMaskFilter.Blur.SOLID 在边界内绘制固体，模糊
	 *
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString setTextFuzzy(SpannableString source, float radius, BlurMaskFilter.Blur style, int index1, int index2){
		if(source!=null){
			BlurMaskFilter filter=new BlurMaskFilter(radius, style);
			MaskFilterSpan mfs=new MaskFilterSpan(filter);
			source.setSpan(mfs, index1, index2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置模糊
	 *
	 * @param source 操作源,为String
	 * @param radius 模糊半径
	 * @param style 风格: BlurMaskFilter.Blur.NORMAL 默认类型，模糊内外边界
	 *                   BlurMaskFilter.Blur.INNER 内部模糊
	 *                   BlurMaskFilter.Blur.OUTER 外部模糊
	 *                   BlurMaskFilter.Blur.SOLID 在边界内绘制固体，模糊
	 *
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString setTextFuzzy(String source, float radius, BlurMaskFilter.Blur style, int index1, int index2){
		SpannableString result=getSpannableString(source);
		return setTextFuzzy(result,radius,style,index1,index2);
	}

	/**
	 * 设置模糊
	 *
	 * @param source 操作源,为SpannableString
	 * @param radius 模糊半径
	 * @param style 风格: BlurMaskFilter.Blur.NORMAL 默认类型，模糊内外边界
	 *                   BlurMaskFilter.Blur.INNER 内部模糊
	 *                   BlurMaskFilter.Blur.OUTER 外部模糊
	 *                   BlurMaskFilter.Blur.SOLID 在边界内绘制固体，模糊
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString setTextFuzzy(SpannableString source,float radius, BlurMaskFilter.Blur style,String changeStr){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex = startIndex + changeStr.length();
			return setTextFuzzy(source,radius,style,startIndex,endIndex);
		}
		return null;
	}

	/**
	 * 设置模糊
	 *
	 * @param source 操作源,为String
	 * @param radius 模糊半径
	 * @param style 风格: BlurMaskFilter.Blur.NORMAL 默认类型，模糊内外边界
	 *                   BlurMaskFilter.Blur.INNER 内部模糊
	 *                   BlurMaskFilter.Blur.OUTER 外部模糊
	 *                   BlurMaskFilter.Blur.SOLID 在边界内绘制固体，模糊
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString setTextFuzzy(String source,float radius, BlurMaskFilter.Blur style,String changeStr){
		SpannableString result=getSpannableString(source);
		return setTextFuzzy(result,radius,style,changeStr);
	}

	/**
	 * 设置浮雕效果
	 *
	 * @param source 操作源,为SpannableString
	 * @param direction 是float数组，定义长度为3的数组标量[x,y,z]，来指定光源的方向
	 * @param ambient 取值在0到1之间，定义背景光 或者说是周围光
	 * @param specular 定义镜面反射系数
	 * @param blurRadius 模糊半径
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString setTextRelief(SpannableString source, float direction[],float ambient, float specular, float blurRadius, int index1, int index2){
		if(source!=null){
			//EmbossMaskFilter filter=new EmbossMaskFilter(new float[]{10, 10, 10}, 0.5f, 1f, 1f);
			EmbossMaskFilter filter=new EmbossMaskFilter(direction,ambient,specular,blurRadius);
			MaskFilterSpan mfs=new MaskFilterSpan(filter);
			source.setSpan(mfs, index1, index2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			return source;
		}
		return null;
	}

	/**
	 * 设置浮雕效果
	 *
	 * @param source 操作源,为String
	 * @param direction 是float数组，定义长度为3的数组标量[x,y,z]，来指定光源的方向
	 * @param ambient 取值在0到1之间，定义背景光 或者说是周围光
	 * @param specular 定义镜面反射系数
	 * @param blurRadius 模糊半径
	 * @param index1 起始下标
	 * @param index2 终止下标
	 * @return
	 */
	public static SpannableString setTextRelief(String source, float direction[],float ambient, float specular, float blurRadius, int index1, int index2){
		SpannableString result=getSpannableString(source);
		return setTextRelief(result,direction,ambient,specular,blurRadius,index1,index2);
	}

	/**
	 * 设置浮雕效果
	 *
	 * @param source 操作源,为SpannableString
	 * @param direction 是float数组，定义长度为3的数组标量[x,y,z]，来指定光源的方向
	 * @param ambient 取值在0到1之间，定义背景光 或者说是周围光
	 * @param specular 定义镜面反射系数
	 * @param blurRadius 模糊半径
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString setTextRelief(SpannableString source,float direction[],float ambient, float specular, float blurRadius,String changeStr){
		if(source!=null){
			String temp=source.toString();
			int startIndex = temp.indexOf(changeStr);
			if (startIndex == -1) {
				return source;
			}
			int endIndex = startIndex + changeStr.length();
			return setTextRelief(source,direction,ambient,specular,blurRadius,startIndex,endIndex);
		}
		return null;
	}

	/**
	 * 设置浮雕效果
	 *
	 * @param source 操作源,为String
	 * @param direction 是float数组，定义长度为3的数组标量[x,y,z]，来指定光源的方向
	 * @param ambient 取值在0到1之间，定义背景光 或者说是周围光
	 * @param specular 定义镜面反射系数
	 * @param blurRadius 模糊半径
	 * @param changeStr 操作内容
	 * @return
	 */
	public static SpannableString setTextRelief(String source,float direction[],float ambient, float specular, float blurRadius,String changeStr){
		SpannableString result=getSpannableString(source);
		return setTextRelief(result,direction,ambient,specular,blurRadius,changeStr);
	}

    /**
     * URL的使用
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为SpannableString
     * @param parameter 参数
     * @param index1 起始坐标
     * @param index2 终止坐标
     * @return
     */
	public static SpannableString setTextURL(SpannableString source, String parameter,int index1, int index2){
        if(source!=null&& StringUtil.isNotEmpty(parameter)){
            URLSpan urlSpan=new URLSpan(parameter);
            source.setSpan(urlSpan, index1, index2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return source;
        }
        return null;
    }

    /**
     * URL的使用
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源，为String
     * @param parameter 参数
     * @param index1 起始坐标
     * @param index2 终止坐标
     * @return
     */
	public static SpannableString setTextURL(String source, String parameter,int index1, int index2){
        SpannableString result=getSpannableString(source);
        return setTextURL(result,parameter,index1,index2);
    }

    /***
     * URL的使用
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为SpannableString
     * @param parameter 参数
     * @param changeStr 操作内容
     * @return
     */
    public static SpannableString setTextURL(SpannableString source, String parameter,String changeStr){
        if(source!=null&& StringUtil.isNotEmpty(parameter)){
            String temp=source.toString();
            int startIndex = temp.indexOf(changeStr);
            if (startIndex == -1) {
                return source;
            }
            int endIndex = startIndex + changeStr.length();
            return setTextURL(source,parameter,startIndex,endIndex);
        }
        return null;
    }

    /***
     * URL的使用
     *
     * 注：textView在使用的时候，在textView.setText(object obj)之后需要添加以下代码：
     *    textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
     *
     * @param source 操作源,为String
     * @param parameter 参数
     * @param changeStr 操作内容
     * @return
     */
    private static SpannableString setTextURL(String source, String parameter,String changeStr){
        SpannableString result=getSpannableString(source);
        return setTextURL(result,parameter,changeStr);
    }

	private static SpannableString getSpannableString(String source){
		SpannableString spannableString=null;
		if(StringUtil.isNotEmpty(source)){
			spannableString=new SpannableString(source);
		}
		return spannableString;
	}

}