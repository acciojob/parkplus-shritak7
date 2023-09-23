package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setAddress(address);
        parkingLot.setName(name);

        ParkingLot savedParkinglot=parkingLotRepository1.save(parkingLot);
        return savedParkinglot;


    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();

        //creating new spot

        Spot spot=new Spot();

        if(numberOfWheels<=2)spot.setSpotType(SpotType.TWO_WHEELER);
        if(numberOfWheels==3 || numberOfWheels==4)spot.setSpotType(SpotType.FOUR_WHEELER);
        else{
            spot.setSpotType(SpotType.OTHERS);
        }

        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        Spot savedpot=spotRepository1.save(spot);

        parkingLot.getSpotList().add(savedpot);

        parkingLotRepository1.save(parkingLot);

        return savedpot;


    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        Spot newspot=null;

        List<Spot>spotList=parkingLot.getSpotList();

        for(Spot spot:spotList){
            if(spot.getId()==spotId){

                newspot=spot;


            }
        }
        newspot.setPricePerHour(pricePerHour);

        Spot savedSpot=spotRepository1.save(newspot);
        return  savedSpot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);

        

    }
}
