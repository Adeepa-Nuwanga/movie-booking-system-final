package com.moviebooking.model;

import java.util.ArrayList;
import java.util.List;

public class SeatMap {
    private final Seat[][] seats;

    public SeatMap(int rows, int columns) {
        seats = new Seat[rows][columns];
        initializeSeats(rows, columns);
    }

    public synchronized boolean isAvailable(String seatCode) {
        Seat seat = findSeat(seatCode);
        return seat != null && seat.getStatus() == SeatStatus.AVAILABLE;
    }

    public synchronized boolean holdSeats(List<String> selectedSeats) {
        if (selectedSeats == null || selectedSeats.isEmpty()) {
            return false;
        }

        for (String seatCode : selectedSeats) {
            if (!isAvailable(seatCode)) {
                return false;
            }
        }

        for (String seatCode : selectedSeats) {
            setSeatStatus(seatCode, SeatStatus.HELD);
        }

        return true;
    }

    public synchronized void releaseSeats(List<String> selectedSeats) {
        if (selectedSeats == null) {
            return;
        }

        for (String seatCode : selectedSeats) {
            if (getSeatStatus(seatCode) == SeatStatus.HELD) {
                setSeatStatus(seatCode, SeatStatus.AVAILABLE);
            }
        }
    }

    public synchronized boolean bookSeats(List<String> selectedSeats) {
        if (selectedSeats == null || selectedSeats.isEmpty()) {
            return false;
        }

        for (String seatCode : selectedSeats) {
            SeatStatus status = getSeatStatus(seatCode);
            if (status != SeatStatus.AVAILABLE && status != SeatStatus.HELD) {
                return false;
            }
        }

        for (String seatCode : selectedSeats) {
            setSeatStatus(seatCode, SeatStatus.BOOKED);
        }

        return true;
    }

    public synchronized SeatStatus getSeatStatus(String seatCode) {
        Seat seat = findSeat(seatCode);
        if (seat == null) {
            return null;
        }

        return seat.getStatus();
    }

    public synchronized List<Seat> getAllSeats() {
        List<Seat> allSeats = new ArrayList<>();
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                allSeats.add(seat);
            }
        }

        return allSeats;
    }

    private void initializeSeats(int rows, int columns) {
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            String rowName = String.valueOf((char) ('A' + rowIndex));
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                seats[rowIndex][columnIndex] = new Seat(rowName, columnIndex + 1, SeatStatus.AVAILABLE);
            }
        }
    }

    private Seat findSeat(String seatCode) {
        if (seatCode == null) {
            return null;
        }

        for (Seat[] row : seats) {
            for (Seat seat : row) {
                if (seat.getSeatCode().equalsIgnoreCase(seatCode.trim())) {
                    return seat;
                }
            }
        }

        return null;
    }

    private void setSeatStatus(String seatCode, SeatStatus status) {
        Seat seat = findSeat(seatCode);
        if (seat != null) {
            seat.setStatus(status);
        }
    }
}
