package edu.uci.ics.vegao1.service.billing.records.customer;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.customer.CustomerModel;
import edu.uci.ics.vegao1.service.billing.models.customer.CustomerRetrieveRequestModel;
import edu.uci.ics.vegao1.service.billing.models.customer.CustomerRetrieveResponseModel;
import edu.uci.ics.vegao1.service.billing.util.Db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRecords {
    private static final String INSERT_CUSTOMER_STATEMENT = "INSERT IGNORE INTO customers (firstName, lastName, ccid, address, email) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER_STATEMENT = "UPDATE customers SET  firstName = ?, lastName = ?, ccid = ?, address = ? WHERE email = ?";
    private static final String RETRIEVE_CUSTOMER_STATEMENT = "SELECT * FROM customers WHERE email = ?";


    public static ResponseModel insertCustomer(CustomerModel customer) throws SQLException {
        ServiceLogger.LOGGER.info("preparing statement to insert customer");
        boolean customerInserted = Db.executeStatement(INSERT_CUSTOMER_STATEMENT, customer.getFirstName(), customer.getLastName(), customer.getCcId(), customer.getAddress(), customer.getEmail());
        if (customerInserted) {
            return ResponseModel.CUSTOMER_INSERT_SUCCESSFUL;
        }
        return ResponseModel.CUSTOMER_DUPLICATE_INSERTION;
    }

    public static ResponseModel updateCustomer(CustomerModel customer) throws SQLException {
        boolean customerUpdated = Db.executeStatement(UPDATE_CUSTOMER_STATEMENT, customer.getFirstName(), customer.getLastName(), customer.getCcId(), customer.getAddress(), customer.getEmail());
        if (customerUpdated) {
            return ResponseModel.CUSTOMER_UPDATE_SUCCESSFUL;
        }
        return ResponseModel.CUSTOMER_DOES_NOT_EXIST;
    }

    public static CustomerRetrieveResponseModel retrieveCustomer(CustomerRetrieveRequestModel customerRetrieveRequest) throws SQLException {
        ServiceLogger.LOGGER.info("preparing statement to retrieve credit card");
        ResultSet resultSet = Db.executeStatementForResult(RETRIEVE_CUSTOMER_STATEMENT, customerRetrieveRequest.getEmail());
        if (resultSet.next()) {
            CustomerModel customer = CustomerModel.fromResultSet(resultSet);
            return new CustomerRetrieveResponseModel(ResponseModel.CUSTOMER_RETRIEVE_SUCCESSFUL, customer);
        }
        return CustomerRetrieveResponseModel.fromResponseModel(ResponseModel.CUSTOMER_DOES_NOT_EXIST);
    }
}
