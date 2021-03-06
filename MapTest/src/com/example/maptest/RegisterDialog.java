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

public class RegisterDialog extends DialogFragment {

	private MainActivity mainActivityContext = (MainActivity) getActivity();
	Button registerUser;
	Button saveCircles;
	Button deleteCircles;
	Button loadCircles;

	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputTextId, String inTextPassword);
	}

	public RegisterDialog() {
		// Empty constructor required for DialogFragment
	}

	private void softDeleteCircles() {
		while (mainActivityContext.circleList.size() != 0) {
			mainActivityContext.circleList.get(0).remove();
			mainActivityContext.circleList.remove(0);
			mainActivityContext.areaList.remove(0);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.register_dialog, container);
		mainActivityContext = (MainActivity) getActivity();
		registerUser = (Button) view.findViewById(R.id.register);
		saveCircles = (Button) view.findViewById(R.id.saveCircles);
		deleteCircles = (Button) view.findViewById(R.id.deleteCircles);
		loadCircles = (Button) view.findViewById(R.id.load);
		getDialog().setTitle("Account Settings");
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
				softDeleteCircles();
			}
		});

		loadCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				softDeleteCircles();
				mainActivityContext.areaList = mainActivityContext.dbController
						.getAreas();

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
