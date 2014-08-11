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

		// String userIdString = userId.getText().toString();
		// String userPassWordString = userPassword.getText().toString();
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

				// class SendSaveRequest extends AsyncTask<String, Void, String>
				// {
				//
				// // private Gson gson = new GsonBuilder().create();
				// // String data = gson.toJson(message);
				//
				// private String sendMessage(String message, String address) {
				// String url = "http://192.168.1.7:8080/MSS/" + address;
				//
				// HttpPost post = new HttpPost(url);
				//
				// List<NameValuePair> nameValuePairs = new
				// ArrayList<NameValuePair>(
				// 1);
				// nameValuePairs.add(new BasicNameValuePair("report",
				// message));
				//
				// try {
				// post.setEntity(new UrlEncodedFormEntity(
				// nameValuePairs));
				// } catch (UnsupportedEncodingException e) {
				// Log.i("client", "Your url encoding is shiat fail");
				// }
				//
				// HttpClient client = new DefaultHttpClient();
				// HttpResponse response = null;
				// try {
				// Log.i("client", "Trying to execute");
				// response = client.execute(post);
				// Log.i("client", "Execution success");
				// } catch (ClientProtocolException e) {
				// Log.i("client",
				// "Problems with execute post, ClientProtocolException");
				// e.printStackTrace();
				// } catch (IOException e) {
				// Log.i("client",
				// "Problems with execute post, IOException");
				// e.printStackTrace();
				// }
				//
				// HttpEntity entity = response.getEntity();
				//
				// String responseText = "";
				// try {
				// responseText = EntityUtils.toString(entity);
				// } catch (ParseException e) {
				// Log.i("request", "ParseException found");
				// e.printStackTrace();
				// } catch (IOException e) {
				// Log.i("request", "IOException found");
				// e.printStackTrace();
				// }
				// return responseText;
				//
				// }
				//
				// @Override
				// protected String doInBackground(String... params) {
				// return sendMessage(params[0], params[1]);
				// }
				//
				// @Override
				// protected void onPostExecute(String result) {
				// Toast.makeText(mainActivityContext, result,
				// Toast.LENGTH_LONG).show();
				// }
				//
				// }
				//
				mainActivityContext.dbController
						.newAreas(mainActivityContext.areaList);

			}
		});

		deleteCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// class SendDeleteRequest extends AsyncTask<String, Void,
				// String> {
				//
				// // private Gson gson = new GsonBuilder().create();
				// // String data = gson.toJson(message);
				//
				// private String sendMessage(String message, String address) {
				// String url = "http://192.168.1.7:8080/MSS/" + address;
				//
				// HttpPost post = new HttpPost(url);
				//
				// List<NameValuePair> nameValuePairs = new
				// ArrayList<NameValuePair>(
				// 1);
				// nameValuePairs.add(new BasicNameValuePair("report",
				// message));
				//
				// try {
				// post.setEntity(new UrlEncodedFormEntity(
				// nameValuePairs));
				// } catch (UnsupportedEncodingException e) {
				// System.out
				// .println("Your url encoding is shiat fail");
				// e.printStackTrace();
				// }
				//
				// HttpClient client = new DefaultHttpClient();
				// HttpResponse response = null;
				// try {
				// response = client.execute(post);
				// } catch (ClientProtocolException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// HttpEntity entity = response.getEntity();
				//
				// String responseText = "";
				// try {
				// responseText = EntityUtils.toString(entity);
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// return responseText;
				//
				// }
				//
				// @Override
				// protected String doInBackground(String... params) {
				// return sendMessage(params[0], params[1]);
				// }
				//
				// @Override
				// protected void onPostExecute(String result) {
				// Toast.makeText(mainActivityContext, result,
				// Toast.LENGTH_LONG).show();
				// }
				//
				// }
				//

				mainActivityContext.dbController.deleteAllAreas();

			}
		});

		loadCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// class SendSynchRequest extends AsyncTask<String, Void,
				// String> {
				//
				// // private Gson gson = new GsonBuilder().create();
				// // String data = gson.toJson(message);
				//
				// private String sendMessage(String message, String address) {
				// String url = "http://192.168.1.7:8080/MSS/" + address;
				//
				// HttpPost post = new HttpPost(url);
				//
				// List<NameValuePair> nameValuePairs = new
				// ArrayList<NameValuePair>(
				// 1);
				// nameValuePairs.add(new BasicNameValuePair("report",
				// message));
				//
				// try {
				// post.setEntity(new UrlEncodedFormEntity(
				// nameValuePairs));
				// } catch (UnsupportedEncodingException e) {
				// System.out
				// .println("Your url encoding is shiat fail");
				// e.printStackTrace();
				// }
				//
				// HttpClient client = new DefaultHttpClient();
				// HttpResponse response = null;
				// try {
				// response = client.execute(post);
				// } catch (ClientProtocolException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// HttpEntity entity = response.getEntity();
				//
				// String responseText = "";
				// try {
				// responseText = EntityUtils.toString(entity);
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// return responseText;
				//
				// }
				//
				// @Override
				// protected String doInBackground(String... params) {
				// return sendMessage(params[0], params[1]);
				// }
				//
				// @Override
				// protected void onPostExecute(String result) {

				mainActivityContext.areaList = mainActivityContext.dbController
						.getAreas();

				// Area[] og = null;
				// Object x = request.execute(msg, "synch");

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

				// Toast.makeText(mainActivityContext, result,
				// Toast.LENGTH_LONG)
				// .show();
				// }
				//
				// }

			}
		});

		return view;
	}

}
