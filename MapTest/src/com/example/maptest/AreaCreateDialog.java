package com.example.maptest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AreaCreateDialog extends DialogFragment implements
		OnEditorActionListener {

	private EditText areaSize;

	public interface EditAreaDialogListener {
		void onFinishAreaDialog(double inputText);
	}

	public AreaCreateDialog() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.area_dialog, container);
		areaSize = (EditText) view.findViewById(R.id.areaSize);

		getDialog().setTitle("Add New Area");

		areaSize.requestFocus();
		areaSize.setOnEditorActionListener(this);

		return view;

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (EditorInfo.IME_ACTION_DONE == actionId) {
			EditAreaDialogListener activity = (EditAreaDialogListener) getActivity();
			activity.onFinishAreaDialog(Integer.parseInt(areaSize.getText()
					.toString()));
			final MainActivity mainActivityContext = (MainActivity) getActivity();
			mainActivityContext.areaSize = areaSize.getText().toString();
			this.dismiss();

			return true;
		}
		return false;
	}

}
