package com.android.tourguide;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private LinearLayout layoutPassenger,tripSelector;
	private RelativeLayout layoutShuffle;
	private TextView totalPassengerTv, tripSelectorTv,mTo,mFrom;
	private BottomSheetHelper bottomSheetHelper;
	private int adultCount = 1, childrenCount = 0, infantsCount = 0, totalPassenger;
	private ImageView shuffleIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BottomSheetHelper.init(MainActivity.this);
		bottomSheetHelper = BottomSheetHelper.getInstance();


		layoutPassenger = findViewById(R.id.layout_passenger);
		tripSelector = findViewById(R.id.tripSelector);

		mFrom = findViewById(R.id.starting_from);
		mTo = findViewById(R.id.going_to);


		totalPassengerTv = findViewById(R.id.number_passenger);
		tripSelectorTv = findViewById(R.id.way_tv);


		layoutPassenger.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//getTotalPassenger();
				bottomSheetHelper.setTotalPassenger(v, totalPassengerTv);
			}
		});

		tripSelector.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bottomSheetHelper.setTrip(v, tripSelectorTv);
			}
		});


		layoutShuffle = findViewById(R.id.shuffle);
		shuffleIcon = findViewById(R.id.shuffle_icon);
		layoutShuffle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				shuffleIcon.animate().rotationBy(180).setDuration(300).start();
				final String tempTO = mFrom.getText().toString();
				final String tempFROM = mTo.getText().toString();

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mFrom.setText(tempFROM);
						mTo.setText(tempTO);
					}
				},200);
			}
		});
	}

	private void setPassengers() {
		try {
			RelativeLayout viewGroup = findViewById(R.id.content_passenger);
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = layoutInflater.inflate(R.layout.content_passengers, viewGroup);

			// Creating the PopupWindow
			PopupWindow popup = new PopupWindow();
			popup.setContentView(layout);
			popup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
			popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
			popup.setFocusable(true);
			popup.setTouchable(true);

			popup.setBackgroundDrawable(new BitmapDrawable());
			popup.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*private void changeStatusBarColor() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		}
	}*/
}
