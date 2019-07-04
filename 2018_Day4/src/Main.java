import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String args[]){

        List<String> input = readFile("Day4");
        PriorityQueue<String> order = new PriorityQueue<>();

        for (String s : input){
            order.add(s);
        }

        List<String> actions = new ArrayList<>();
        List<List<String>> days = new ArrayList<>();

        //split up into days and add all actions for that day into a list
        //then that list into a list so list of days -> list of actions

        while(!order.isEmpty()){

            String current = order.poll();
            if (current.split("] ")[1].charAt(0) == 'G') {
                if (actions.size() != 0)    //safeguarding the first day
                    days.add(actions);
                System.out.println(actions.size());
                actions = new ArrayList<>();
            }
            actions.add(stripTimeAction(current));    //hh:mm action
        }


        HashMap<String, int[]> guardsToTimes = new HashMap<>(); // maps a guard id to their minute tracker
        List<String> guards = new ArrayList<>();                // keeps track of guards for debugging and later

        // count the sleeping hours for each guard / day

        for (List<String> d : days){
            System.out.println("Actions: ");
            for (String s : d){
                System.out.println(s);
            }

            Iterator<String> i = d.iterator();
            String firstAction = i.next();
            String guard = firstAction.split(" ")[2];  //hh:mm Guard #xxx <- this one

            int[] times; //60 ints representing the amount of times a guard was asleep in each minute

            //get or create the minute tracker for the guard on duty
            if (guardsToTimes.containsKey(guard)){
                times = guardsToTimes.get(guard);
            }
            else {
                guards.add(guard);
                times = new int[60];
            }

            //taking in groupt of 2 (fall asleep - wake up)
            //increment the minute tracker for the minutes asleep

            while(i.hasNext()){
                int start = Integer.parseInt(getTimeFromActions(i.next())[1]);
                int finish = Integer.parseInt(getTimeFromActions(i.next())[1]);
                for (int j = start; j < finish; ++j){
                    times[j]++;
                }
            }
            guardsToTimes.put(guard, times);
        }

        //find the guard with the greatest total minuets asleep

        String sleepiestGuard = "";     //this is his ID
        int mostSleep = 0;              // this is the minutes total slept

        //pretty much just max finding
        for (String guard : guards){
            int[] times = guardsToTimes.get(guard);
            int sleepTime = 0;
            for (int i = 0; i < times.length; i++){
                sleepTime += times[i];
            }
            if (sleepTime > mostSleep){
                mostSleep = sleepTime;
                sleepiestGuard = guard;
            }
        }

        //part 1

        int[] sleepiestGuardTime = guardsToTimes.get(sleepiestGuard);
        int sleepiestMinute = 0;
        int maxSleepsInMinute = 0;
        for (int i = 0; i < sleepiestGuardTime.length; ++i){
            if (sleepiestGuardTime[i] > maxSleepsInMinute){
                maxSleepsInMinute = sleepiestGuardTime[i];
                sleepiestMinute = i;
            }
        }
        int guardID = Integer.parseInt(sleepiestGuard.substring(1));
        System.out.println("part1: "+guardID+" "+sleepiestMinute+" "+guardID*sleepiestMinute);

        //part 2

        sleepiestMinute = -1;
        int minuteFrequency = 0;
        for (String guard : guards){
            int[] times = guardsToTimes.get(guard);
            for (int i = 0; i < times.length; ++i){
                if (times[i] > minuteFrequency){
                    minuteFrequency = times[i];
                    sleepiestMinute = i;
                    sleepiestGuard = guard;
                }
            }
        }
        guardID = Integer.parseInt(sleepiestGuard.substring(1));
        System.out.println("part2: "+guardID+" "+sleepiestMinute+" "+minuteFrequency+" "+guardID*sleepiestMinute);
    }

    public static List<String> readFile(String fileName){

        List<String> output = new ArrayList<>();

        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while (s.hasNextLine()){
                output.add(s.nextLine());
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        return output;
    }


    // returns "hh:mm action goes here"
    public static String stripTimeAction(String s){
        return s.split("-[\\d]{2} ")[1].replace("]", "");
    }

    // returns { hour, minute }
    public static String[] getTimeFromActions(String s){
        return s.split(" ")[0].split(":");
    }

}
