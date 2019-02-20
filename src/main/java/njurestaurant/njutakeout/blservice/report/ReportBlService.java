package njurestaurant.njutakeout.blservice.report;

import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.response.report.*;

import java.util.Date;
import java.util.List;

public interface ReportBlService {
    List<MerchantReportResponse> getReportOfMerchant(Date startDate, Date endDate)throws WrongInputException;;

    List<AgentReportResponse> getReportOfAgent(Date startDate, Date endDate) throws WrongInputException;

    List<ReceiptCodeReportResponse> getReportOfReceiptCode(Date startDate, Date endDate) throws WrongInputException;

    List<FundingReportResponse> getReportOfFunding(Date startDate, Date endDate) throws WrongInputException;

    List<SupplierReportResponse> getReportOfSupplier(Date startDate, Date endDate) throws WrongInputException;
}
