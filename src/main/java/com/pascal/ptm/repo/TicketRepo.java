package com.pascal.ptm.repo;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 27/02/2024.
 */

import com.pascal.ptm.entities.Ticket;
import com.pascal.ptm.utils.Datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo {
    private final Datasource datasource;

    public TicketRepo(Datasource datasource) {
        this.datasource = datasource;
    }

    public boolean createTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO ticket (vehicle_number, ticket_number,entry_time, created_by, phone, note) VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql)) {
            statement.setString(1, ticket.getVehicleNumber());
            statement.setString(2, ticket.getTicketNumber());
            statement.setTimestamp(3, Timestamp.valueOf(ticket.getEntryTime()));
            statement.setInt(4, ticket.getCreatedBy());
            statement.setString(5, ticket.getPhone());
            statement.setString(6, ticket.getNote());

            statement.executeUpdate();
            return true;
        }
    }


    public List<Ticket> listTicket() throws SQLException {
        String sql = "select * from ticket";
        PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql);
        List<Ticket> ticketList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ticketList.add(mapToEntity(resultSet));
        }
        return ticketList;

    }


    public Ticket getTicketByTicketNumber(String ticketNumber) throws SQLException {
        String sql = "select ticket_id, vehicle_number, ticket_number, entry_time,  exit_time, total_time, total_amount, created_by, phone, note from ticket where ticket_number = ?";
        PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql);
        statement.setString(1, ticketNumber);
        ResultSet resultSet = statement.executeQuery();
        return mapToEntity(resultSet);
    }

    public static String convertSecondsToDuration(long seconds) {
        long days = seconds / (24 * 3600);
        long hours = (seconds % (24 * 3600)) / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        StringBuilder duration = new StringBuilder();
        if (days > 0) {
            duration.append(days).append(" days, ");
        }
        if (hours > 0) {
            duration.append(hours).append(" hours, ");
        }
        if (minutes > 0) {
            duration.append(minutes).append(" minutes, ");
        }
        duration.append(remainingSeconds).append(" seconds");

        return duration.toString();
    }


    private Ticket mapToEntity(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        if (resultSet.next()) {
            ticket.setTicketId(resultSet.getInt("ticket_id"));
            ticket.setVehicleNumber(resultSet.getString("vehicle_number"));
            ticket.setTicketNumber(resultSet.getString("ticket_number"));

            ticket.setEntryTime(resultSet.getTimestamp("entry_time").toLocalDateTime());
            if (resultSet.getTimestamp("exit_time") != null) {
                ticket.setExitTime(resultSet.getTimestamp("exit_time").toLocalDateTime());
            }
            ticket.setTotalTime(resultSet.getLong("total_time"));
            ticket.setTotalAmount(resultSet.getFloat("total_amount"));
            ticket.setCreatedBy(resultSet.getInt("created_by"));
            ticket.setPhone(resultSet.getString("phone"));
            ticket.setNote(resultSet.getString("note"));
            ticket.setTotalTimeStr(convertSecondsToDuration(ticket.getTotalTime()));

        }
        return ticket;
    }


    public boolean saveTicketCheckoutDetail(int ticketId, LocalDateTime exitTime, int updatedBy, double totalAmount, long totalTimeSec) throws SQLException {
        String sql = "UPDATE ticket SET exit_time = ?, total_amount = ?, updated_by = ?,total_time=? WHERE ticket_id = ?";
        try (PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(exitTime));
            statement.setDouble(2, totalAmount);
            statement.setInt(3, updatedBy);
            statement.setLong(4, totalTimeSec);
            statement.setInt(5, ticketId);


            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
