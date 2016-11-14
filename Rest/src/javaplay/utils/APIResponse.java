package javaplay.utils;

import com.google.gson.annotations.SerializedName;

public class APIResponse {
	public static final int SUCCESS = 200;
	public static final int INTERNAL_SERVER_ERROR = 500;
	public static final int BAD_REQUEST = 400;
	
    @SerializedName("status_code")
	private int mStatusCode;
        
    @SerializedName("verbose")
    private String mVerbose;
    
    @SerializedName("response")
	private Object mResponse;
    
    public APIResponse(int statusCode, String verbose, Object response) {
    	mStatusCode = statusCode;
    	mVerbose = verbose;
    	mResponse = response;
    }

	/**
	 * @return the mStatusCode
	 */
	public int getStatusCode() {
		return mStatusCode;
	}

	/**
	 * @param mStatusCode the mStatusCode to set
	 */
	public void setStatusCode(int mStatusCode) {
		this.mStatusCode = mStatusCode;
	}

	/**
	 * @return the mVerbose
	 */
	public String getVerbose() {
		return mVerbose;
	}

	/**
	 * @param mVerbose the mVerbose to set
	 */
	public void setVerbose(String mVerbose) {
		this.mVerbose = mVerbose;
	}

	/**
	 * @return the mResponse
	 */
	public Object getResponse() {
		return mResponse;
	}

	/**
	 * @param mResponse the mResponse to set
	 */
	public void setResponse(Object mResponse) {
		this.mResponse = mResponse;
	}
}
