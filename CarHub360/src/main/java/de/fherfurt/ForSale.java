package de.fherfurt;

public class ForSale
{
    private int VehicleId;
    private float SalePrice;
    private boolean IsNew;

    public ForSale(int vehicleId, float salePrice, boolean isNew) {
        VehicleId = vehicleId;
        SalePrice = salePrice;
        IsNew = isNew;
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
