package org.example;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class MeetingTime {
    Date startTime;
    Date endTime;

    public MeetingTime(String time) throws ParseException {
        String[] times = time.split("-");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        this.startTime= dateFormat.parse(times[0]);
        this.endTime= dateFormat.parse(times[1]);
    }
}

public class FindMeetingRooms {

    public static boolean isOverlapping(List<MeetingTime> existingMeetingList, MeetingTime newTime) {
        boolean overlap = false;
        for (MeetingTime existingTime : existingMeetingList) {
            int timeDiff = newTime.startTime.compareTo(existingTime.endTime);
            if(timeDiff >= 0) {
                overlap = false;
            } else {
                timeDiff = newTime.startTime.compareTo(existingTime.startTime);
                if(timeDiff >= 0) {
                    overlap = true;
                    break;
                } else {
                    timeDiff = newTime.endTime.compareTo(existingTime.startTime);
                    if(timeDiff > 0) {
                        overlap = true;
                        break;
                    } else {
                        overlap = false;
                    }
                }
            }
        }
        return overlap;
    }

    public static void main(String[] args) throws IOException, ParseException {

        List<List<MeetingTime>> meetingRooms = new ArrayList<>();

        File file = new File("C:\\Users\\Dineshkumar.Jayakuma\\Desktop\\input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            MeetingTime newMeetingTime = new MeetingTime(st);
            if(meetingRooms.size() == 0) {
                List<MeetingTime> meetingList = new ArrayList<>();
                meetingList.add(newMeetingTime);
                meetingRooms.add(meetingList);
            } else {
                for (int i=0; i<meetingRooms.size(); i++) {
                    List<MeetingTime> existingMeetingList = meetingRooms.get(i);
                    if(!isOverlapping(existingMeetingList, newMeetingTime)) {
                        existingMeetingList.add(newMeetingTime);
                        meetingRooms.set(i, existingMeetingList);
                        break;
                    } else if( i == meetingRooms.size()-1){
                        List<MeetingTime> newMeetingList = new ArrayList<>();
                        newMeetingList.add(newMeetingTime);
                        meetingRooms.add(newMeetingList);
                        break;
                    }
                }
            }
        }
        System.out.println(meetingRooms.size());
    }
}