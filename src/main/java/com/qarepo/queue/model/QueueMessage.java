package com.qarepo.queue.model;

public class QueueMessage {
    private String jobNumber;
    private String jobName;
    private String previewUrl;

    public QueueMessage() {
    }

    public QueueMessage(String jobNumber, String jobName, String previewUrl) {
        this.jobNumber = jobNumber;
        this.jobName = jobName;
        this.previewUrl = previewUrl;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return "QueueMessage{" +
                "jobNumber='" + jobNumber + '\'' +
                ", jobName='" + jobName + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }
}
