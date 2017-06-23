package com.orion;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int amount;
    private int userId;
    private String reason;
    private String action;
    private Date timeStamp;
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getAmount() {return amount;}
    public void setAmount(int amount) {this.amount = amount;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public String getReason() {return reason;}
    public void setReason(String reason) {this.reason = reason;}
    public String getAction() {return action;}
    public void setAction(String action) {this.action = action;}
    public Date getTimeStamp() {return timeStamp;}
    public void setTimeStamp(Date timeStamp) {this.timeStamp = timeStamp;}
}