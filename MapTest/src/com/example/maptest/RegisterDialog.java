package com.example.maptest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
	EditText userPassword; // make the interface GUI gurl

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
		userId = (EditText) view.findViewById(R.id.username);
		userPassword = (EditText) view.findViewById(R.id.password);
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

				class SendSaveRequest extends AsyncTask<String, Void, String> {

					// private Gson gson = new GsonBuilder().create();
					// String data = gson.toJson(message);

					private String sendMessage(String message, String address) {
						String url = "http://192.168.87.108:8080/MSS/"
								+ address;

						HttpPost post = new HttpPost(url);

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								1);
						nameValuePairs.add(new BasicNameValuePair("report",
								message));

						try {
							post.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
						} catch (UnsupportedEncodingException e) {					
							Log.i("client", "Your url encoding is shiat fail");
						}

						HttpClient client = new DefaultHttpClient();
						HttpResponse response = null;
						try {
							Log.i("client", "Trying to execute");
							response = client.execute(post);
							Log.i("client", "Execution success");							
						} catch (ClientProtocolException e) {
							Log.i("client", "Problems with execute post, ClientProtocolException");
							e.printStackTrace();
						} catch (IOException e) {
							Log.i("client", "Problems with execute post, IOException");
							e.printStackTrace();
						}
						
						HttpEntity entity = response.getEntity();

						String responseText = "";
						try {
							responseText = EntityUtils.toString(entity);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return responseText;

					}

					@Override
					protected String doInBackground(String... params) {
						return sendMessage(params[0], params[1]);
					}

					@Override
					protected void onPostExecute(String result) {
						Toast.makeText(mainActivityContext, result,
								Toast.LENGTH_LONG).show();
					}

				}

				String msg = gson.toJson(mainActivityContext
						.generateSavePayload(
								mainActivityContext.circlesToArea(),
								mainActivityContext.userId,
								mainActivityContext.userPassword));

				new SendSaveRequest().execute(msg, "save");
			}
		});

		deleteCircles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				class SendDeleteRequest extends AsyncTask<String, Void, String> {

					// private Gson gson = new GsonBuilder().create();
					// String data = gson.toJson(message);

					private String sendMessage(String message, String address) {
						String url = "http://192.168.87.108:8080/MSS/"
								+ address;

						HttpPost post = new HttpPost(url);

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								1);
						nameValuePairs.add(new BasicNameValuePair("report",
								message));

						try {
							post.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
						} catch (UnsupportedEncodingException e) {
							System.out
									.println("Your url encoding is shiat fail");
							e.printStackTrace();
						}

						HttpClient client = new DefaultHttpClient();
						HttpResponse response = null;
						try {
							response = client.execute(post);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						HttpEntity entity = response.getEntity();

						String responseText = "";
						try {
							responseText = EntityUtils.toString(entity);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return responseText;

					}

					@Override
					protected String doInBackground(String... params) {
						return sendMessage(params[0], params[1]);
					}

					@Override
					protected void onPostExecute(String result) {
						Toast.makeText(mainActivityContext, result,
								Toast.LENGTH_LONG).show();
					}

				}

				String msg = gson.toJson(mainActivityContext
						.generateSavePayload(mainActivityContext.userId,
								mainActivityContext.userPassword));

				new SendDeleteRequest().execute(msg, "save");
				for (int i = 0; i < mainActivityContext.circleList.size(); i++)
					mainActivityContext.circleList.get(i).remove();

			}
		});

		synch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				class SendSynchRequest extends AsyncTask<String, Void, String> {

					// private Gson gson = new GsonBuilder().create();
					// String data = gson.toJson(message);

					private String sendMessage(String message, String address) {
						String url = "http://192.168.87.108:8080/MSS/"
								+ address;

						HttpPost post = new HttpPost(url);

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								1);
						nameValuePairs.add(new BasicNameValuePair("report",
								message));

						try {
							post.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
						} catch (UnsupportedEncodingException e) {
							System.out
									.println("Your url encoding is shiat fail");
							e.printStackTrace();
						}

						HttpClient client = new DefaultHttpClient();
						HttpResponse response = null;
						try {
							response = client.execute(post);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						HttpEntity entity = response.getEntity();

						String responseText = "";
						try {
							responseText = EntityUtils.toString(entity);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return responseText;

					}

					@Override
					protected String doInBackground(String... params) {
						return sendMessage(params[0], params[1]);
					}

					@Override
					protected void onPostExecute(String result) {
						Auth myAuth = new Auth(mainActivityContext.userId, null);

						String msg = gson.toJson(myAuth);

						for (int i = 0; i < mainActivityContext.circleList
								.size(); i++)
							mainActivityContext.circleList.get(i).remove();

						Area[] og = gson.fromJson(result, Area[].class);

						// Area[] og = null;
						// Object x = request.execute(msg, "synch");

						for (int i = 0; i < og.length; i++) {

							LatLng point = new LatLng(og[i].getLatitude(),
									og[i].getLongitude());

							CircleOptions circleOptions = new CircleOptions()
									.center(point).radius(og[i].getRadius());

							Circle circle = mainActivityContext.map
									.addCircle(circleOptions);

							mainActivityContext.circleList.add(circle);

						}

						Toast.makeText(mainActivityContext, result,
								Toast.LENGTH_LONG).show();
					}

				}

			}
		});

		return view;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (EditorInfo.IME_ACTION_DONE == actionId) {
			EditNameDialogListener activity = (EditNameDialogListener) getActivity();
			activity.onFinishEditDialog(userId.getText().toString(),
					userPassword.getText().toString()); // C:

			final MainActivity xxx = (MainActivity) getActivity();
			xxx.userId = userId.getText().toString();
			this.dismiss();

			return true;
		}
		return false;
	}

}
