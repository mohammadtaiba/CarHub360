package de.fherfurt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Contract
{
    /* class-Methods */
    public boolean createPurchaseContract(int mContractId,Customer mCustomer,SaleVehicle mSaleVehicle)
    {
        if (validateContractId(mContractId) && validateCustomer(mCustomer) && validateSaleVehicle(mSaleVehicle))
        {
            this.m_ContractId = mContractId;
            this.m_Customer = mCustomer;
            this.m_SaleVehicle = mSaleVehicle;
            this.m_IsRentalContract = false;
            this.m_ContractDate = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean createRentalContract(int mContractId,Customer mCustomer,RentVehicle mRentVehicle,
                                        LocalDate mRentalStartdate, LocalDate mRentalEnddate )
    {
        if (validateContractId(mContractId) && validateCustomer(mCustomer) &&
                validateRentVehicle(mRentVehicle) && validateRentalPeriod(mRentalStartdate , mRentalEnddate ))
        {
            this.m_ContractId	    = mContractId;
            this.m_Customer         = mCustomer;
            this.m_RentVehicle      = mRentVehicle;
            this.m_IsRentalContract = true;
            this.m_ContractDate     = LocalDate.now();
            this.m_RentalStartDate  = mRentalStartdate ;
            this.m_RentalEndDate    = mRentalEnddate ;
            return true;
        }
        return false;
    }

    public boolean terminateRentalContract(int mContractID, RentVehicle mRentVehicle)
    {
        if (validateContractId(mContractID) && isRentalContract())
        {
            setRentalEndDate(LocalDate.now());
            mRentVehicle.setAvailable(true);
            return true;
        }
        System.out.println("Ungültige eingegebene Vertrag!");
        return false;
    }

    public boolean renewRentalContract(int mContractID, RentVehicle mRentVehicle, LocalDate NewRentalEnddate)
    {
        if (validateContractId(mContractID) && isRentalContract() && validateRentalPeriod(getRentalStartDate() , NewRentalEnddate) )
        {
            setRentalEndDate(NewRentalEnddate);
            mRentVehicle.setAvailable(false);
            return true;
        }
        System.out.println("Ungültige eingegebene Vertrag!");
        return false;
    }

    public double getTotalPrice()
    {
        long   daysRented = ChronoUnit.DAYS.between(getRentalStartDate(), getRentalEndDate());
        return daysRented * m_RentVehicle.getDailyPrice();
    }

    public boolean getRentalContractDetails(int mContractID)
    {
        if (validateContractId(mContractID) && this.m_IsRentalContract)
        {
            // System.out.println("Vertrags-ID: "   + mContractID);
            // System.out.println("Kosten: "        + getTotalPrice() + " €");
            // System.out.println("Mietbeginn: "    + getRentalStartDate());
            // System.out.println("Mietende: "      + getRentalEndDate());
            // System.out.println("Vertragsdatum: " + getContractDate());
            // m_RentVehicle.getRentVehicleDetails();
            // m_Customer.GetCustomerDetails(m_Customer.getCustomerId());
            return true;
        }
        System.out.println("Kein Mietvertrag mit dieser ID gefunden.");
        return false;
    }

    public boolean getPurchaseContractDetails(int mContractID)
    {
        if (validateContractId(mContractID) && !this.m_IsRentalContract)
        {
            // System.out.println("Vertrags-ID: "   + mContractID);
            // System.out.println("Vertragsdatum: " + getContractDate());
            // m_SaleVehicle.getSaleVehicleDetails();
            // m_Customer.GetCustomerDetails(m_Customer.getCustomerId());
            return true;
        }
        System.out.println("Kein Kaufvertrag mit dieser ID gefunden.");
        return false;
    }

    /* Validation methods */
    public boolean validateContractId(int contractID)
    {
        if (contractID <= 0)
        {
            System.out.println("Vertrags-ID muss positiv sein.");
            return false;
        }
        return true;
    }

    public boolean validateCustomer(Customer customer)
    {
        if (customer == null)
        {
            System.out.println("Kundenobjekt darf nicht null sein.");
            return false;
        }
        return true;
    }

    public boolean validateSaleVehicle(SaleVehicle saleVehicle)
    {
        if (saleVehicle == null)
        {
            System.out.println("SaleVehicle-Objekt darf nicht null sein.");
            return false;
        }
        return true;
    }

    public boolean validateRentVehicle(RentVehicle rentVehicle)
    {
        if (rentVehicle == null)
        {
            System.out.println("RentVehicle-Objekt darf nicht null sein.");
            return false;
        }
        return true;
    }

    public boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate)
    {
        if (startDate == null || endDate == null)
        {
            System.out.println("Start- und Enddatum dürfen nicht null sein.");
            return false;
        }
        if (startDate.isAfter(endDate))
        {
            System.out.println("Das Startdatum muss vor dem Enddatum liegen.");
            return false;
        }

        if (startDate.isBefore(LocalDate.now()))
        {
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
    private LocalDate   m_ContractDate;
    private LocalDate   m_RentalStartDate;
    private LocalDate   m_RentalEndDate;

    /* Set & Get Methods */
    public int getContractId()
    {
        return m_ContractId;
    }

    public void setContractId(int mContractId)
    {
        this.m_ContractId = mContractId;
    }

    public boolean isRentalContract()
    {
        return m_IsRentalContract;
    }

    public void setRentalContract(boolean mIsRentalContract)
    {
        this.m_IsRentalContract = mIsRentalContract;
    }

    public LocalDate getContractDate()
    {
        return m_ContractDate;
    }

    public void setContractDate(LocalDate mContractDate)
    {
        this.m_ContractDate = mContractDate;
    }

    public LocalDate getRentalStartDate()
    {
        return m_RentalStartDate;
    }

    public void setRentalStartDate(LocalDate mRentalStartDate)
    {
        this.m_RentalStartDate = mRentalStartDate;
    }

    public LocalDate getRentalEndDate()
    {
        return m_RentalEndDate;
    }

    public void setRentalEndDate(LocalDate mRentalEndDate)
    {
        this.m_RentalEndDate = mRentalEndDate;
    }

    public Customer getCustomer()
    {
        return m_Customer;
    }

    public void setCustomer(Customer mCustomer)
    {
        this.m_Customer = mCustomer;
    }

    public SaleVehicle getSaleVehicle()
    {
        return m_SaleVehicle;
    }

    public void setSaleVehicle(SaleVehicle mSaleVehicle)
    {
        this.m_SaleVehicle = mSaleVehicle;
    }

    public RentVehicle getRentVehicle()
    {
        return m_RentVehicle;
    }

    public void setRentVehicle(RentVehicle mRentVehicle)
    {
        this.m_RentVehicle = m_RentVehicle;
    }
}
