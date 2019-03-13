/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author BuckingB
 */
public class Gene {
    //represents an scheduled course, this is, a single gene by its own, represents a solution a single course schedule:
    //in this first version the assumtion here is that a course cannot have more than 4h of workload
    //also remeber that a course:name1-lab0 is different from a course:name1-lab1
    
    //representation => Gene with Id=id contains {R,T,R,T}. Where R is a roomId and T a timeSlotId
    //two sesions of two hours
        
    int roomId1; //roomId1
    int timeSlotId1;//timeSlotId1
    int roomId2;
    int timeSlotId2;
    
    public Gene(){}
    
    public Gene(int r1, int ts1, int r2, int ts2){        
        this.roomId1 = r1;
        this.timeSlotId1 = ts1;
        this.roomId2 = r2;
        this.timeSlotId2 = ts2;
    }
    
    public void setAllElementsRandom(int numberOfRooms, int numberOfTimeSlots){
        this.roomId1 = ThreadLocalRandom.current().nextInt(0,numberOfRooms);
        this.timeSlotId1 = ThreadLocalRandom.current().nextInt(0,numberOfTimeSlots);
        this.roomId2 = ThreadLocalRandom.current().nextInt(0,numberOfRooms);
        this.timeSlotId2 = ThreadLocalRandom.current().nextInt(0,numberOfTimeSlots);
    }
    
    public void setRoom1Random(int numberOfRooms){
    this.roomId1 = ThreadLocalRandom.current().nextInt(0,numberOfRooms);
    }
    public void setRoom2Random(int numberOfRooms){
    this.roomId2 = ThreadLocalRandom.current().nextInt(0,numberOfRooms);
    }
    public void setTimeSlot1Random(int numberOfTimeSlots){
    this.timeSlotId1 = ThreadLocalRandom.current().nextInt(0,numberOfTimeSlots);
    }
    public void setTimeSlot2Random(int numberOfTimeSlots){
    this.timeSlotId2 = ThreadLocalRandom.current().nextInt(0,numberOfTimeSlots);
    }
        
    public void setRoom1(int room){
        this.roomId1 = room;
    }
    public void setRoom2(int room){
        this.roomId2 = room;
    }
    public void setTimeSlot1(int timeslot){
        this.timeSlotId1 = timeslot;
    }
    public void setTimeSlot2(int timeslot){
        this.timeSlotId2 = timeslot;
    }
    
    public int getRoomId1(){
        return this.roomId1;
    }
    public int getRoomId2(){
        return this.roomId2;
    }
    public int getTimeSlotId1(){
        return this.timeSlotId1;
    }
    public int getTimeSlotId2(){
        return this.timeSlotId2;
    }
    
    @Override
    public String toString(){
        String out = String.format("%d,%d;%d,%d", roomId1,timeSlotId1,roomId2,timeSlotId2);
        return out;
    }

}
