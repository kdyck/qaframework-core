package com.qarepo.queue;

public class QueueResponse {
    private String responseUrl;

    public QueueResponse(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }
}
