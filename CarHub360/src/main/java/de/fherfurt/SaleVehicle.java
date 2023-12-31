package de.fherfurt;

public class SaleVehicle extends Vehicle
{
    private int VehicleId;
    private float SalePrice;
    private boolean IsNew;

    public SaleVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type,
                       float salePrice, boolean isNew)
    {
        super(vehicleId, name, brand, kilometerCount, constructionYear, type);
        this.SalePrice = salePrice;
        this.IsNew = isNew;
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

    public String getSaleVehicleDetails()  // ergänze! <---
    {
        return null;
    }
}
