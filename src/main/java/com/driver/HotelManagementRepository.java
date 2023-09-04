package com.driver;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    HashMap<String,Hotel>hotels=new HashMap<>();
    HashMap<String,Booking>bookings=new HashMap<>();
    List<User>users=new ArrayList<>();
    public String addHotel(Hotel hotel) {
        if(hotel.getHotelName()==null) return "FAILURE";
        if(hotels.containsKey(hotel.getHotelName())) return "FAILURE";
        hotels.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        users.add(user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        int cnt=0; Hotel hotel=null;
//        if(hotels.size()==0) return "";
        for (Hotel hot:hotels.values()){
            if(hot.getFacilities().size()>cnt){
                hotel=hot;
                cnt=hot.getFacilities().size();
            } else if (hot.getFacilities().size()==cnt && hotel!=null) {
                if(hot.getHotelName().compareTo(hotel.getHotelName())<0){
                    hotel=hot;
                }
            }
        }
        if(cnt==0) return "";
        return hotel.getHotelName();
    }

    public int bookRoom(Booking booking) {
        Hotel hotel=hotels.get(booking.getHotelName());
        if(hotel.getAvailableRooms()<booking.getNoOfRooms()) return -1;
        booking.setBookingId(String.valueOf(UUID.randomUUID()));
        booking.setAmountToBePaid(booking.getNoOfRooms()*hotel.getPricePerNight());
        bookings.put(booking.getBookingId(),booking);
        return booking.getAmountToBePaid();
    }

    public int getBookings(Integer aadharCard) {
        int cnt=0;
        if(aadharCard==null)return 0;
        for (Booking booking:bookings.values()){
            if(booking.getBookingAadharCard()==aadharCard) cnt++;
        }
        return cnt;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel=hotels.get(hotelName);
        for(Facility facility:newFacilities){
            if(hotel.getFacilities().contains(facility)==false){
                hotel.getFacilities().add(facility);
            }
        }
        return hotel;
    }
}
