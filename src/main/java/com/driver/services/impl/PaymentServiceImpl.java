package com.driver.services.impl;

import com.driver.exception.CantmadeException;
import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation=reservationRepository2.findById(reservationId).get();
        int time=reservation.getNoOfHours();

        Spot spot=reservation.getSpot();


        Payment payment=reservation.getPayment();
        int requiredamnt=time*reservation.getSpot().getPricePerHour();

        String str=mode.toUpperCase();
        if(str.equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(str.equals("CARD")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else if(str.equals("UPI")){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        else{
            throw new CantmadeException("Payment mode not detected");
        }

        if(requiredamnt>amountSent){
            throw new CantmadeException("Insufficient Amount");
        }

        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        spot.setOccupied(false);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;



    }
}
