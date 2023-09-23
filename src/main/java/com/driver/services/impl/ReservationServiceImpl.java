package com.driver.services.impl;

import com.driver.exception.CantmadeException;
import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        User user=userRepository3.findById(userId).get();

        if(user==null){
            throw new CantmadeException("Cannot make reservation");
        }

        Optional<ParkingLot> parkingLot=parkingLotRepository3.findById(parkingLotId);

        if(!parkingLot.isPresent()){
            throw new CantmadeException("Cannot make reservation");
        }

        Spot newspot=null;
        int newPrice=Integer.MAX_VALUE;

        List<Spot>spotList=parkingLot.get().getSpotList();
        for(Spot spot:spotList){
            if(!spot.getOccupied()){

                if(numberOfWheels<=2 && newPrice>spot.getPricePerHour()){
                    newPrice= spot.getPricePerHour();
                    newspot=spot;
                }
                else if(numberOfWheels<=4 && newPrice>spot.getPricePerHour()){
                    newPrice= spot.getPricePerHour();
                    newspot=spot;
                }
                else{
                    newPrice= spot.getPricePerHour();
                    newspot=spot;
                }
            }

        }

        if(newspot==null){
            throw new CantmadeException("Cannot make reservation");
        }

        Reservation reservation=new Reservation();
        reservation.setNoOfHours(timeInHours);
        reservation.setSpot(newspot);
        reservation.setUser(user);

      //  reservationRepository3.save(reservation);

        List<Reservation> userReservation=user.getReservationList();
        List<Reservation> spotReservation=newspot.getReservationList();

        userReservation.add(reservation);
        spotReservation.add(reservation);
        newspot.setOccupied(true);

        user.setReservationList(userReservation);
        newspot.setReservationList(spotReservation);

        userRepository3.save(user);
        spotRepository3.save(newspot);

        return reservation;







    }
}
