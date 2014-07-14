package com.example.maptest.json;

public class GeneralRequest {
	private Auth auth;
	private Data data;

	public GeneralRequest(Auth auth, Data data) {
		super();
		this.auth = auth;
		this.data = data;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
