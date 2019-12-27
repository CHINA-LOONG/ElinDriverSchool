package org.chenglei.widget.datepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

public class DatePickerForMinute extends LinearLayout implements NumberPicker.OnValueChangeListener {

	private NumberPicker mYearPicker;
	private NumberPicker mMonthPicker;
	private NumberPicker mDayOfMonthPicker;
	private NumberPicker mHourPicker;
	private NumberPicker mMinutePicker;

	private Calendar mCalendar;

	private OnDateChangedListener mOnDateChangedListener;

	private LayoutInflater mLayoutInflater;

	public DatePickerForMinute(Context context) {
		this(context, null);
	}

	public DatePickerForMinute(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		init();
	}

	private void init() {
		mLayoutInflater.inflate(R.layout.date_picker_layout_for_minute, this, true);
		mYearPicker = (NumberPicker) findViewById(R.id.year_picker_for_minute);
		mMonthPicker = (NumberPicker) findViewById(R.id.month_picker_for_minute);
		mDayOfMonthPicker = (NumberPicker) findViewById(R.id.day_picker_for_minute);
		mHourPicker = (NumberPicker) findViewById(R.id.hour_picker_for_minute);
		mMinutePicker = (NumberPicker) findViewById(R.id.minute_picker_for_minute);

		mYearPicker.setOnValueChangeListener(this);
		mMonthPicker.setOnValueChangeListener(this);
		mDayOfMonthPicker.setOnValueChangeListener(this);
		mHourPicker.setOnValueChangeListener(this);
		mMinutePicker.setOnValueChangeListener(this);

		if (!getResources().getConfiguration().locale.getCountry().equals("CN")
				&& !getResources().getConfiguration().locale.getCountry().equals("TW")) {

			String[] monthNames = getResources().getStringArray(R.array.month_name);
			mMonthPicker.setCustomTextArray(monthNames);

		}

		mCalendar = Calendar.getInstance();
		setDate(mCalendar.getTime());
	}

	public DatePickerForMinute setDate(Date date) {
		mCalendar.setTime(date);
		mDayOfMonthPicker.setEndNumber(mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		mYearPicker.setCurrentNumber(mCalendar.get(Calendar.YEAR));
		mMonthPicker.setCurrentNumber(mCalendar.get(Calendar.MONTH) + 1);
		mDayOfMonthPicker.setCurrentNumber(mCalendar.get(Calendar.DAY_OF_MONTH));
		mHourPicker.setCurrentNumber(mCalendar.get(Calendar.HOUR_OF_DAY));
		mMinutePicker.setCurrentNumber(mCalendar.get(Calendar.MINUTE));

		return this;
	}

	@Override
	public void onValueChange(final NumberPicker picker, final int oldVal, final int newVal) {

		if (picker == mYearPicker) {
            int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
			mCalendar.set(newVal, mCalendar.get(Calendar.MONTH), 1);
            int lastDayOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (dayOfMonth > lastDayOfMonth) {
                dayOfMonth = lastDayOfMonth;
            }
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			mDayOfMonthPicker.setEndNumber(lastDayOfMonth);
		} else if (picker == mMonthPicker) {
            int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
            mCalendar.set(mCalendar.get(Calendar.YEAR), newVal - 1, 1);
            int lastDayOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (dayOfMonth > lastDayOfMonth) {
                dayOfMonth = lastDayOfMonth;
            }
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mDayOfMonthPicker.setEndNumber(lastDayOfMonth);
		} else if (picker == mDayOfMonthPicker) {
			mCalendar.set(Calendar.DAY_OF_MONTH, newVal);
		}else if (picker==mHourPicker){
			mCalendar.set(Calendar.HOUR_OF_DAY,newVal);
		}else if (picker==mMinutePicker){
			mCalendar.set(Calendar.MINUTE,newVal);
		}


		notifyDateChanged();
	}

	/**
     * The callback used to indicate the user changes\d the date.
     */
    public interface OnDateChangedListener {

        /**
         * Called upon a date change.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *            with {@link Calendar}.
         * @param dayOfMonth The day of the month that was set.
         */
        void onDateChanged(DatePickerForMinute view, int year, int monthOfYear, int dayOfMonth);
    }
    
    public DatePickerForMinute setOnDateChangedListener(OnDateChangedListener l) {
    	mOnDateChangedListener = l;
    	return this;
    }
    
    private void notifyDateChanged() {
    	if (mOnDateChangedListener != null) {
    		mOnDateChangedListener.onDateChanged(this, getYear(), getMonth(), getDayOfMonth());
    	}
    }
    
    public int getYear() {
    	return mCalendar.get(Calendar.YEAR);
    }
    
    public int getMonth() {
    	return mCalendar.get(Calendar.MONTH) + 1;
    }
    
    public int getDayOfMonth() {
    	return mCalendar.get(Calendar.DAY_OF_MONTH);
    }
	public int getHourOfDay(){
		return mCalendar.get(Calendar.HOUR_OF_DAY);
	}
	public int getMinuteOfHour(){
		return mCalendar.get(Calendar.MINUTE);
	}
    
    public DatePickerForMinute setSoundEffect(Sound sound) {
    	mYearPicker.setSoundEffect(sound);
    	mMonthPicker.setSoundEffect(sound);
    	mDayOfMonthPicker.setSoundEffect(sound);
		mHourPicker.setSoundEffect(sound);
		mMinutePicker.setSoundEffect(sound);
    	return this;
    }
    
    @Override
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
    	super.setSoundEffectsEnabled(soundEffectsEnabled);
    	mYearPicker.setSoundEffectsEnabled(soundEffectsEnabled);
    	mMonthPicker.setSoundEffectsEnabled(soundEffectsEnabled);
    	mDayOfMonthPicker.setSoundEffectsEnabled(soundEffectsEnabled);
		mHourPicker.setSoundEffectsEnabled(soundEffectsEnabled);
		mMinutePicker.setSoundEffectsEnabled(soundEffectsEnabled);
    }
    
    public DatePickerForMinute setRowNumber(int rowNumber) {
    	mYearPicker.setRowNumber(rowNumber);
    	mMonthPicker.setRowNumber(rowNumber);
    	mDayOfMonthPicker.setRowNumber(rowNumber);
    	mHourPicker.setRowNumber(rowNumber);
    	mMinutePicker.setRowNumber(rowNumber);
    	return this;
    }
    
    public DatePickerForMinute setTextSize(float textSize) {
    	mYearPicker.setTextSize(textSize);
    	mMonthPicker.setTextSize(textSize);
    	mDayOfMonthPicker.setTextSize(textSize);
    	mHourPicker.setTextSize(textSize);
    	mMinutePicker.setTextSize(textSize);
    	return this;
    }
    
    public DatePickerForMinute setFlagTextSize(float textSize) {
    	mYearPicker.setFlagTextSize(textSize);
    	mMonthPicker.setFlagTextSize(textSize);
    	mDayOfMonthPicker.setFlagTextSize(textSize);
    	mHourPicker.setFlagTextSize(textSize);
    	mMinutePicker.setFlagTextSize(textSize);
    	return this;
    }
    
    public DatePickerForMinute setTextColor(int color) {
    	mYearPicker.setTextColor(color);
    	mMonthPicker.setTextColor(color);
    	mDayOfMonthPicker.setTextColor(color);
    	mHourPicker.setTextColor(color);
    	mMinutePicker.setTextColor(color);
    	return this;
    }
    
    public DatePickerForMinute setFlagTextColor(int color) {
    	mYearPicker.setFlagTextColor(color);
    	mMonthPicker.setFlagTextColor(color);
    	mDayOfMonthPicker.setFlagTextColor(color);
    	mHourPicker.setFlagTextColor(color);
    	mMinutePicker.setFlagTextColor(color);
    	return this;
    }
    
    public DatePickerForMinute setBackground(int color) {
    	super.setBackgroundColor(color);
    	mYearPicker.setBackground(color);
    	mMonthPicker.setBackground(color);
    	mDayOfMonthPicker.setBackground(color);
    	mHourPicker.setBackground(color);
    	mMinutePicker.setBackground(color);
    	return this;
    }

}
