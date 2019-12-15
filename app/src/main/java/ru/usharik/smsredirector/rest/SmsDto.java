package ru.usharik.smsredirector.rest;

public class SmsDto {

    private String from;

    private String messageBody;

    private Long timestamp;

    public SmsDto() {
    }

    public SmsDto(String from, String messageBody, Long timestamp) {
        this.from = from;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
