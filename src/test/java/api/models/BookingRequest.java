package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingRequest {
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("userId")
    private Integer userId;
    
    public BookingRequest() {
    }
    
    public BookingRequest(String title, String body, Integer userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "BookingRequest{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }
}
