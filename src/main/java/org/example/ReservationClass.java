package org.example;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationClass {
    private static Integer idNumber;
    private static String name;
    private static LocalDate startDay;
    private static LocalDate endDay;
    private static Integer price;

    public ReservationClass(Integer id, String n, LocalDate start, LocalDate end) {
        idNumber = id;
        name = n;
        startDay = start;
        endDay = end;
    }

    public ReservationClass(Integer id, String n) {
        idNumber = id;
        name = n;
    }

    public static LocalDate getStartDay() {
        return startDay;
    }

    public static void setStartDay(LocalDate startDay) {
        ReservationClass.startDay = startDay;
    }

    public static LocalDate getEndDay() {
        return endDay;
    }

    public static void setEndDay(LocalDate endDay) {
        ReservationClass.endDay = endDay;
    }

    public static Integer getPrice() {
        return price;
    }

    public static void setPrice(Integer price) {
        ReservationClass.price = price;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReservationClass) {
            return ((ReservationClass) obj).getIdNumber().equals(idNumber) && ((ReservationClass) obj).getName().equals(name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, idNumber, endDay, startDay);
    }
}


