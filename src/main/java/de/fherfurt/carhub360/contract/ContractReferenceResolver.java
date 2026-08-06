package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleRepository;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ContractReferenceResolver {

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private SaleVehicleRepository saleVehicleRepository;

    @Inject
    private RentVehicleRepository rentVehicleRepository;

    public ContractReferences resolve(int customerId, Integer saleVehicleId, Integer rentVehicleId) {
        Customer customer = customerRepository.findById(customerId);
        SaleVehicle saleVehicle = saleVehicleId == null ? null : saleVehicleRepository.findById(saleVehicleId);
        RentVehicle rentVehicle = rentVehicleId == null ? null : rentVehicleRepository.findById(rentVehicleId);
        return new ContractReferences(customer, saleVehicle, rentVehicle);
    }
}
