package com.example.interview.component;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Tools {


    public static int[] decodePointsPerMonth(String pointsPerMonth){
        return Arrays.stream(pointsPerMonth.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public static String encodePointsPerMonth(int[] pointsPerMonth){
        return IntStream.of(pointsPerMonth).mapToObj(Integer::toString).collect(Collectors.joining(", "));
    }
}
