package com.pascal.oms.entities;

import java.time.LocalDateTime;

public class OrganMatch {
    private String matchId;
    private String organId;
    private String donorId;
    private String recipientId;
    private LocalDateTime matchDate;
    private String status;
    private String donorName;
    private String recipientName;
    private String organName;

    public OrganMatch() {}

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getOrganId() { return organId; }
    public void setOrganId(String organId) { this.organId = organId; }

    public String getDonorId() { return donorId; }
    public void setDonorId(String donorId) { this.donorId = donorId; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public LocalDateTime getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getOrganName() { return organName; }
    public void setOrganName(String organName) { this.organName = organName; }
}
