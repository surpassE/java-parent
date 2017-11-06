package com.sirding.javaeight;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * Created by dzc on 17.9.14.
 */
public class Streams {
    private enum Status {
        OPEN, CLOSED
    };

    private static final class Task {
        private final Status status;
        private final Integer points;


        Task(final Status status, final Integer points ) {
            this.status = status;
            this.points = points;
        }

        public Integer getPoints() {
            return points;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return String.format( "[%s, %d]", status, points );
        }
    }

    public static void main(String[] args){
        final Collection<Task> tasks = Arrays.asList(
                new Task(Status.OPEN,5),
                new Task(Status.OPEN, 13),
                new Task(Status.CLOSED, 8)
        );
        IntStream intStream = tasks.stream().filter(task -> task.getStatus() == Status.OPEN).mapToInt(Task::getPoints);
        final long tpot = intStream.sum();
        System.out.println("count:" + tpot);


        final Map<Status, List<Task>> map = tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
        System.out.println(map);
        
    }

}
