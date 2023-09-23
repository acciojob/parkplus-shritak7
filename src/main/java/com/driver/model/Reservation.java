package com.driver.model;

import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int noOfHours;

    @ManyToOne
    @JoinColumn
    Spot spot;

    @ManyToOne
    @JoinColumn
    User user;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    Payment payment;

    public Reservation() {
    }

    public Reservation(int id, int noOfHours, Spot spot, User user, Payment payment) {
        this.id = id;
        this.noOfHours = noOfHours;
        this.spot = spot;
        this.user = user;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(int noOfHours) {
        this.noOfHours = noOfHours;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
