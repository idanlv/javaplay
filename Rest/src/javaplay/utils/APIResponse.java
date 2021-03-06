package javaplay.utils;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents an API response
 */
public class APIResponse {
	/**
	 * Constants 
	 */
	public static final int SUCCESS = 200;
	public static final int INTERNAL_SERVER_ERROR = 500;
	public static final int BAD_REQUEST = 400;
	
	/**
	 * Members
	 */
    @SerializedName("status_code")
	private int mStatusCode;
        
    @SerializedName("verbose")
    private String mVerbose;
    
    @SerializedName("response")
	private Object mResponse;

    /**
     * Constructor 
     * @param statusCode request status code
     * @param verbose description of response
     * @param response response object
     */
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
	 * Getter
	 * @param mStatusCode the mStatusCode to set
	 */
	public void setStatusCode(int mStatusCode) {
		this.mStatusCode = mStatusCode;
	}

	/**
	 * Getter
	 * @return the mVerbose
	 */
	public String getVerbose() {
		return mVerbose;
	}

	/**
	 * Getter
	 * @param mVerbose the mVerbose to set
	 */
	public void setVerbose(String mVerbose) {
		this.mVerbose = mVerbose;
	}

	/**
	 * Getter
	 * @return the mResponse
	 */
	public Object getResponse() {
		return mResponse;
	}

	/**
	 * Setter
	 * @param mResponse the mResponse to set
	 */
	public void setResponse(Object mResponse) {
		this.mResponse = mResponse;
	}
}
