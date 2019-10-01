package com.kieranjohnmoore.endora.model;

public class WeekWorkouts {
    public class Day {
        public String name;
        public boolean completed = false;

        Day(String name, boolean completed) {
            this.name = name;
            this.completed = completed;
        }
    }
    public Day[] days = new Day[7];

    public WeekWorkouts() {
        for (int i = 0; i < days.length; i++) {
            days[i] = new Day("", false);
        }
        days[0].name = "Mon"; days[0].completed = false;
        days[1].name = "Tues"; days[1].completed = true;
        days[2].name = "Wed"; days[2].completed = false;
        days[3].name = "Thur"; days[3].completed = true;
        days[4].name = "Fri"; days[4].completed = true;
        days[5].name = "Sat"; days[5].completed = true;
        days[6].name = "Sun"; days[6].completed = false;
    }

}
