package com.example.maptest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maptest.json.Area;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegisterDialog extends DialogFragment {

	Button registerUser;
	Button saveCircles;
	Button deleteCircles;
	Button loadCircles;

	Gson gson = new GsonBuilder().create();

	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputTextId, String inTextPassword);
	}

	public RegisterDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.register_dialog, container);

		registerUser = (Button) view.findViewById(R.id.register);
		saveCircles = (Button) view.findViewById(R.id.saveCircles);
		deleteCircles = (Button) view.findViewById(R.id.deleteCircles);
		loadCircles = (Button) view.findViewById(R.id.load);
		getDialog().setTitle("Account Settings");
		final MainActivity mainActivityContext = (MainActivity) getActivity();
		// Show soft keyboard automatically

		saveCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				mainActivityContext.dbController
						.newAreas(mainActivityContext.areaList);

			}
		});

		deleteCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				mainActivityContext.dbController.deleteAllAreas();
				while (mainActivityContext.circleList.size() != 0) {
					mainActivityContext.circleList.get(0).remove();
					mainActivityContext.circleList.remove(0);
					mainActivityContext.areaList.remove(0);
				}
			}
		});

		loadCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int areaListSize = mainActivityContext.areaList.size();

				for (int i = 0; i < areaListSize; i++) {

					Area tempArea = mainActivityContext.areaList.get(i);

					LatLng point = new LatLng(tempArea.getLatitude(), tempArea
							.getLongitude());

					CircleOptions circleOptions = new CircleOptions().center(
							point).radius(tempArea.getRadius());

					Circle circle = mainActivityContext.map
							.addCircle(circleOptions);

					mainActivityContext.circleList.add(circle);

				}

			}
		});

		return view;
	}
}
