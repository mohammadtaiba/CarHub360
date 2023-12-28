package de.fherfurt;

public class SaleVehicle extends Vehicle
{
    private int VehicleId;
    private float SalePrice;
    private boolean IsNew;

    public SaleVehicle(int vehicleId, float salePrice, boolean isNew) {
        super(); // // Ruft den Konstruktor der Basisklasse auf, ergänze!
        VehicleId = vehicleId;
        SalePrice = salePrice;
        IsNew = isNew;
    }

    // ergänze!
    public void GetSaleVehicleVehicleDetails (int VehicleId) {

    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public float getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(float salePrice) {
        SalePrice = salePrice;
    }

    public boolean isNew() {
        return IsNew;
    }

    public void setNew(boolean aNew) {
        IsNew = aNew;
    }
}
