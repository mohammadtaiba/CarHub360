package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;

public record ContractReferences(Customer customer, SaleVehicle saleVehicle, RentVehicle rentVehicle) {
}
