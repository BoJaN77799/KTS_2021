package com.app.RestaurantApp.mail;

import java.util.ArrayList;
import java.util.List;

public class EmailContent {

    private String subject;
    private List<String> recipients;
    private String body;

    public EmailContent(String subject, List<String> recipients, String body) {
        this.subject = subject;
        this.recipients = recipients;
        this.body = body;
    }

    public EmailContent(String subject, String body) {
        this.subject = subject;
        this.recipients = new ArrayList<String>();
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getRecipients() {
        if(recipients == null) {
            this.recipients = new ArrayList<>();
        }
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        if(recipients == null) {
            this.recipients = new ArrayList<>();
        }
        this.recipients = recipients;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addRecipient(String recipient) {
        if(recipients == null) {
            this.recipients = new ArrayList<>();
        }
        if(!recipients.contains(recipient)) {
            this.recipients.add(recipient);
        }
    }

    public void removeRecipient(String recipient) {
        if(recipients.contains(recipient)) {
            this.recipients.remove(recipient);
        }
    }
}
