package com.hospital.enums;

public enum PriorityStatus {
    
    EMERGENCY(1, "Acil Durum"),
    DISABLED(2, "Engelli"),
    PREGNANT(3, "Hamile"),
    ELDERLY(4, "65 Yas Ustu"), 
    STANDARD(6, "Standart Hasta"); 

    
    private final int level;
    private final String description;

    
    PriorityStatus(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }
    
    public String getDescription() {
        return description;
    }
}