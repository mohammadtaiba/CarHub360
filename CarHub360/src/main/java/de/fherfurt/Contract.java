package de.fherfurt;

import java.util.Date;

public class Contract
{
    /* constructor */
    public Contract(int m_ContractId,Customer m_Customer,SaleVehicle m_SaleVehicle,RentVehicle m_RentVehicle,boolean m_IsRentalContract)
    {
        this.m_ContractId	     = m_ContractId;
        this.m_Customer 	     = m_Customer;
        this.m_SaleVehicle       = m_SaleVehicle;
        this.m_RentVehicle       = m_RentVehicle;
        this.m_IsRentalContract  = m_IsRentalContract;
        this.m_ContractDate	     = new Date();
    }

    /* class-Methods */
    public boolean createPurchaseContract(int m_ContractId,Customer m_Customer,SaleVehicle m_SaleVehicle)
    {
        validateContractId(contractID);
        validateCustomer(customer);
        validateSaleVehicle(saleVehicle);
        setContractId(contractID);

        this.m_ContractId	    = m_ContractId;
        this.m_Customer         = m_Customer;
        this.m_SaleVehicle      = m_SaleVehicle;
        this.m_IsRentalContract = false;
        this.m_ContractDate     = new Date();
        return true;
    }
    public boolean createRentalContract(int m_ContractId,Customer m_Customer,RentVehicle m_RentVehicle, Date m_RentalStartdate , Date m_RentalEnddate )
    {
        if (validateContractId(m_ContractId) && validateCustomer(m_Customer) &&
                validateRentVehicle(m_RentVehicle) && validateRentalPeriod(m_RentalStartdate , m_RentalEnddate ))
        {
            this.m_ContractId	    = m_ContractId;
            this.m_Customer         = m_Customer;
            this.m_RentVehicle      = m_RentVehicle;
            this.m_IsRentalContract = true;
            this.m_ContractDate     = new Date();
            this.m_RentalStartDate  = m_RentalStartdate ;
            this.m_RentalEndDate    = m_RentalEnddate ;
            return true;
        }
        return false;
    }

    public void terminateRentalContract(int ContractID, RentVehicle rentVehicle)
    {
        if ( ContractID  <= 0 || !isRentalContract() )
        {
            throw new IllegalArgumentException("Mietvertrag nicht gefunden oder ungültig.");
        }
        this.m_RentVehicle = rentVehicle;

        m_RentVehicle.setEndDate(new Date());
        m_RentVehicle.setAvailable(true);
    }

    public void renewRentalContract(int ContractID, Date NewRentalEnddate)
    {
        // Logic to renew a rental contract
    }

    public void getRentalContractDetails(int ContractID)
    {
        // Logic to get rental contract details
    }

    public void getPurchaseContractDetails(int ContractID)
    {
        // Logic to get purchase contract details
    }

    /* Validation methods */
    private boolean validateContractId(int contractID)
    {
        if (contractID <= 0)
        {
            System.out.println("Vertrags-ID muss positiv sein.");
            return false;
        }
        return true;
    }

    private boolean validateCustomer(Customer customer)
    {
        if (customer == null)
        {
            System.out.println("Kundenobjekt darf nicht null sein.");
            return false;
        }
        return true;
    }

    private boolean validateSaleVehicle(SaleVehicle saleVehicle)
    {
        if (saleVehicle == null)
        {
            System.out.println("SaleVehicle-Objekt darf nicht null sein.");
            return true;
        }
        return true;
    }

    private boolean validateRentVehicle(RentVehicle rentVehicle)
    {
        if (rentVehicle == null)
        {
            System.out.println("RentVehicle-Objekt darf nicht null sein.");
            return false;
        }
        return true;
    }

    private boolean validateRentalPeriod(Date startDate, Date endDate)
    {
        if (startDate == null || endDate == null) {
            System.out.println("Start- und Enddatum dürfen nicht null sein.");
            return false;
        }
        if (startDate.after(endDate)) {
            System.out.println("Das Startdatum muss vor dem Enddatum liegen.");
            return false;
        }

        Date today = new Date();
        if (startDate.before(today)) {
            System.out.println("Das Startdatum darf nicht in der Vergangenheit liegen.");
            return false;
        }
        return true;
    }


    /* Attributes */
    private Customer    m_Customer;
    private SaleVehicle m_SaleVehicle;
    private RentVehicle m_RentVehicle;
    private int         m_ContractId;
    private boolean     m_IsRentalContract;
    private Date        m_ContractDate;
    private Date        m_RentalStartDate;
    private Date        m_RentalEndDate;


    /* Set & Get Methods */
    public int getM_ContractId() {
        return m_ContractId;
    }

    public void setContractId(int m_ContractId) {
        this.m_ContractId = m_ContractId;
    }

    public boolean isRentalContract() {
        return m_IsRentalContract;
    }

    public void setRentalContract(boolean IsRentalContract) {
        m_IsRentalContract = IsRentalContract;
    }

    public Date getContractDate() {
        return m_ContractDate;
    }

    public void setContractDate() {
        m_ContractDate = new Date(); // sets at the current date
    }




}
