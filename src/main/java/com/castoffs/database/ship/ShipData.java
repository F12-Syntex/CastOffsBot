package com.castoffs.database.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.castoffs.bot.Castoffs;

public class ShipData {
    
    private List<ShipEntry> shipEntries;

    public ShipData() {
        this.shipEntries = new ArrayList<>();
    }

    public List<ShipEntry> getShipEntries() {
        return this.shipEntries;
    }

    public void setShipEntries(List<ShipEntry> shipEntries) {
        this.shipEntries = shipEntries;
    }

    public void addShipEntry(ShipEntry shipEntry) {
        this.shipEntries.add(shipEntry);
        Castoffs.getInstance().getDependencies().getStorage().getInformationStorage().getShip().updateConfig();
    }

    public void removeShipEntry(ShipEntry shipEntry) {
        this.shipEntries.remove(shipEntry);
    }

    public ShipEntry getShipEntry(int index) {
        return this.shipEntries.get(index);
    }

    public boolean containsEntry(String user1, String user2){
        return this.shipEntries.stream()
            .anyMatch(entry -> entry.getUser1().equals(user1) && entry.getUser2().equals(user2) ||
                               entry.getUser1().equals(user2) && entry.getUser2().equals(user1));
    }

    public Optional<ShipEntry> getEntry(String user1, String user2){

        for(ShipEntry entry : this.shipEntries){
            if(entry.getUser1().equals(user1) && entry.getUser2().equals(user2) ||
               entry.getUser1().equals(user2) && entry.getUser2().equals(user1)){
                return Optional.of(entry);
            }
        }

        return Optional.empty();
    }

}
