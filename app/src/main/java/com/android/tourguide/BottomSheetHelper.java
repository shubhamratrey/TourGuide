package com.android.tourguide;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BottomSheetHelper implements View.OnClickListener {

	private static BottomSheetHelper instance;

	private Context mContext;



	private int adultCount = 1, childrenCount = 0, infantsCount = 0, totalPassenger;
	private ImageView adultPlus, adultMinus, childrenPlus, childrenMinus, infantsPlus, infantsMinus, oneWayIv, roundTripIv;
	private TextView adultCountTv, childrenTv, infantsTv, oneWayTv, roundTripTv, tempTrip;
	private RelativeLayout onewayRL, roundtripRL;
	private BottomSheetDialog dialog;

	private BottomSheetHelper() {
	}

	public static synchronized void init(Context context) {
		instance = new BottomSheetHelper();
		instance.mContext = context;
	}

	public static synchronized BottomSheetHelper getInstance() {
		return instance;
	}

	public void setTotalPassenger(View v, final TextView totalPassengerTv) {
		RelativeLayout viewGroup = v.findViewById(R.id.content_passenger);
		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.content_passengers, viewGroup);

		final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
		dialog.setContentView(view);
		dialog.show();

		adultCountTv = view.findViewById(R.id.adult_no);
		childrenTv = view.findViewById(R.id.children_no);
		infantsTv = view.findViewById(R.id.infants_no);
		adultPlus = view.findViewById(R.id.adult_plus);
		adultMinus = view.findViewById(R.id.adult_minus);
		childrenPlus = view.findViewById(R.id.children_plus);
		childrenMinus = view.findViewById(R.id.children_minus);
		infantsPlus = view.findViewById(R.id.infants_plus);
		infantsMinus = view.findViewById(R.id.infants_minus);

		adultCountTv.setText(String.valueOf(adultCount));
		childrenTv.setText(String.valueOf(childrenCount));
		infantsTv.setText(String.valueOf(infantsCount));


		adultPlus.setOnClickListener(this);
		adultMinus.setOnClickListener(this);
		childrenPlus.setOnClickListener(this);
		childrenMinus.setOnClickListener(this);
		infantsPlus.setOnClickListener(this);
		infantsMinus.setOnClickListener(this);

		int tempChildCount = Integer.parseInt(childrenTv.getText().toString());
		int tempInfantsCount = Integer.parseInt(infantsTv.getText().toString());

		if (tempChildCount > 0) {
			childrenMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
		}
		if (tempInfantsCount > 0) {
			infantsMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
		}

		TextView submit = view.findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				totalPassenger = adultCount + childrenCount + infantsCount;
				totalPassengerTv.setText(String.valueOf(totalPassenger));
				dialog.dismiss();
			}
		});
	}

	public void setTrip(View v, TextView textView) {
		RelativeLayout viewGroup = v.findViewById(R.id.content_roundtrip);
		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.content_roundtrip, viewGroup);

		dialog = new BottomSheetDialog(mContext);
		dialog.setContentView(view);
		dialog.show();

		onewayRL = view.findViewById(R.id.onewayRL);
		oneWayIv = view.findViewById(R.id.onewayIv);
		oneWayTv = view.findViewById(R.id.onewayTv);
		roundtripRL = view.findViewById(R.id.roundtripRL);
		roundTripIv = view.findViewById(R.id.roundtripIv);
		roundTripTv = view.findViewById(R.id.roundtripTv);

		onewayRL.setOnClickListener(this);
		roundtripRL.setOnClickListener(this);


		if (textView.getText().toString().contains(oneWayTv.getText().toString())) {
			toggleTripTexts(true);
		} else {
			toggleTripTexts(false);
		}
		tempTrip = textView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.adult_plus:
				adultCount++;
				adultCountTv.setText(String.valueOf(adultCount));
				break;
			case R.id.adult_minus:
				if (adultCount > 1) {
					adultCount--;
					adultCountTv.setText(String.valueOf(adultCount));
				}
				break;
			case R.id.children_plus:
				childrenCount++;
				childrenTv.setText(String.valueOf(childrenCount));
				childrenMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
				break;
			case R.id.children_minus:
				if (childrenCount > 0) {
					childrenCount--;
					childrenTv.setText(String.valueOf(childrenCount));
					childrenMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
					if (childrenCount == 0) {
						childrenMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus_disabled));
					}
				}
				break;
			case R.id.infants_plus:
				infantsCount++;
				infantsTv.setText(String.valueOf(infantsCount));
				infantsMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
				break;
			case R.id.infants_minus:
				if (infantsCount > 0) {
					infantsCount--;
					infantsTv.setText(String.valueOf(infantsCount));
					infantsMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
					if (infantsCount == 0) {
						infantsMinus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus_disabled));
					}
				}
				break;
			case R.id.onewayRL:
				toggleTripTexts(true);

				tempTrip.setText(oneWayTv.getText().toString());
				dialog.dismiss();
				break;
			case R.id.roundtripRL:
				toggleTripTexts(false);

				tempTrip.setText(roundTripTv.getText().toString());
				dialog.dismiss();
				break;
		}
	}

	private void toggleTripTexts(Boolean oneway) {
		if (oneway) {
			oneWayIv.setVisibility(View.VISIBLE);
			roundTripIv.setVisibility(View.GONE);

			oneWayTv.setTypeface(Typeface.DEFAULT_BOLD);
			roundTripTv.setTypeface(Typeface.DEFAULT);
		} else {
			oneWayIv.setVisibility(View.GONE);
			roundTripIv.setVisibility(View.VISIBLE);

			oneWayTv.setTypeface(Typeface.DEFAULT);
			roundTripTv.setTypeface(Typeface.DEFAULT_BOLD);
		}
	}
}
