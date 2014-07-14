package com.example.maptest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.maptest.json.Area;
import com.example.maptest.json.Auth;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegisterDialog extends DialogFragment implements
		OnEditorActionListener {

	Button saveCircles;
	Button deleteCircles;
	Button synch;
	EditText userId;
	SendPostRequest request = new SendPostRequest();

	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}

	public RegisterDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.register_dialog, container);
		userId = (EditText) view.findViewById(R.id.username);
		saveCircles = (Button) view.findViewById(R.id.saveCircles);
		deleteCircles = (Button) view.findViewById(R.id.deleteCircles);
		synch = (Button) view.findViewById(R.id.synch);
		getDialog().setTitle("Account Settings");
		final MainActivity mainActivityContext = (MainActivity) getActivity();
		// Show soft keyboard automatically
		userId.requestFocus();
		userId.setOnEditorActionListener(this);

		if (mainActivityContext.userId != null) {
			userId.setText(mainActivityContext.userId);
		} else {
			userId.setText("");
		}
		saveCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				request.sendMessage(mainActivityContext.generateSavePayload(
						mainActivityContext.circlesToArea(),
						mainActivityContext.userId), "save");

			}
		});

		deleteCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				request.sendMessage(mainActivityContext
						.generateSavePayload(mainActivityContext.userId),
						"save");

				for (int i = 0; i < mainActivityContext.circleList.size(); i++)
					mainActivityContext.circleList.get(i).remove();

			}
		});

		synch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Gson gson = new GsonBuilder().create();

				Auth myAuth = new Auth(mainActivityContext.userId);

				for (int i = 0; i < mainActivityContext.circleList.size(); i++)
					mainActivityContext.circleList.get(i).remove();

				Area[] og = gson.fromJson(request.sendMessage(myAuth, "synch"),
						Area[].class);

				for (int i = 0; i < og.length; i++) {

					LatLng point = new LatLng(og[i].getLatitude(), og[i]
							.getLongitude());

					CircleOptions circleOptions = new CircleOptions().center(
							point).radius(og[i].getRadius());

					Circle circle = mainActivityContext.map
							.addCircle(circleOptions);

					mainActivityContext.circleList.add(circle);

				}

			}
		});

		return view;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (EditorInfo.IME_ACTION_DONE == actionId) {
			EditNameDialogListener activity = (EditNameDialogListener) getActivity();
			activity.onFinishEditDialog(userId.getText().toString());
			final MainActivity xxx = (MainActivity) getActivity();
			xxx.userId = userId.getText().toString();
			this.dismiss();

			return true;
		}
		return false;
	}

}
