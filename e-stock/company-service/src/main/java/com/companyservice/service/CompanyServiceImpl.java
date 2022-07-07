package com.companyservice.service;

import com.companyservice.dto.CompanyDto;
import com.companyservice.entity.Company;
import com.companyservice.repository.CompanyRepository;
import com.companyservice.ui.CompanyResponseModel;
import com.companyservice.exception.CompanyException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * company method implementation
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    /**
     * company repository for db operations
     */
    private final CompanyRepository companyRepository;

    /**
     * model mapper for mapping
     */
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository,ModelMapper modelMapper){
        this.companyRepository=companyRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * register new company
     * @param companyDto
     * @return company response
     */
    @Override
    public CompanyResponseModel registerCompany(CompanyDto companyDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if(companyDto.getCompanyTurnover() <= 100_000_000){
            throw  new CompanyException("Company Turnover is low");
        }

        CompanyResponseModel company = getCompanyDetail(companyDto.getCompanyCode(),true);

        if(company != null){
            throw new CompanyException("Company already Register.");
        }
        Company companyResponse =  companyRepository.save(modelMapper.map(companyDto,Company.class));
        return modelMapper.map(companyResponse,CompanyResponseModel.class);

    }

    /**
     * company detail of one company
     * @param companyCode
     * @param isLocal
     * @return company response object
     */
    @Override
    public CompanyResponseModel getCompanyDetail(final String companyCode,final Boolean isLocal) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final Company company = companyRepository.getByCompanyCode(companyCode);
        CompanyResponseModel companyResponseModel = null;

        if(company == null && !isLocal){
            throw new CompanyException("Company not Register in our database.");
        }
        if(company != null){
             companyResponseModel = modelMapper.map(company,CompanyResponseModel.class);
        }
        return companyResponseModel;
    }

    /**
     * delete a company with the help of company code
     * @param companyCode
     * @return message
     */
    @Override
    public String deleteCompany(final String companyCode) {
        Company company = companyRepository.getByCompanyCode(companyCode);

        if(company == null){
            throw new CompanyException("Company not found of this company code");
        }else {
            companyRepository.delete(company);
            return "Company delete successfully";
        }
    }

    /**
     * get all companies
     * @return list of companies
     */
    @Override
    public List<CompanyResponseModel> getAll() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final List<Company> companies =  companyRepository.findAll();

        return companies.stream()
                .map(company -> modelMapper.map(company,CompanyResponseModel.class)).collect(Collectors.toList());

    }
}
