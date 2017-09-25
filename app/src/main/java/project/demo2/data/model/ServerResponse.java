package project.demo2.data.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by hassan on 9/24/2017.
 * response when deleting or adding an item if success or failed
 */

public class ServerResponse {

    @SerializedName("response")
    private String response;

    @SerializedName("success")
    private boolean success;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



}
