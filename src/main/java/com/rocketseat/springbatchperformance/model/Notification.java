package com.rocketseat.springbatchperformance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Notification {

    @Id
    private Integer id;
    private String name;
    private String surname;
    private String dept;
    private String phone;
    private String email;
    private Integer tracking;
    private String messageTracking;
    private Date time;
    private Integer orderNumber;

    public Notification(Integer id, String name, String dept, String phone, String email, Integer tracking, Date time) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.phone = phone;
        this.email = email;
        this.tracking = tracking;
        this.time = time;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMessageTracking() {
        return messageTracking;
    }

    public void setMessageTracking(String messageTracking) {
        this.messageTracking = messageTracking;
    }

    public Notification() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getTracking() {
        return tracking;
    }

    public void setTracking(Integer tracking) {
        this.tracking = tracking;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dept='" + dept + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", tracking=" + tracking +
                ", messageTracking='" + messageTracking + '\'' +
                ", time=" + time +
                ", orderNumber=" + orderNumber +
                '}';
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
