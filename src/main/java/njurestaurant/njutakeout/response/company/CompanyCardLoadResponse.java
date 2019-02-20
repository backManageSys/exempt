package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class CompanyCardLoadResponse extends Response {
    private List<CompanyCard> companyCardList;

    public CompanyCardLoadResponse(List<CompanyCard> companyCardList) {
        this.companyCardList = companyCardList;
    }

    public List<CompanyCard> getCompanyCardList() {
        return companyCardList;
    }

    public void setCompanyCardList(List<CompanyCard> companyCardList) {
        this.companyCardList = companyCardList;
    }
}
