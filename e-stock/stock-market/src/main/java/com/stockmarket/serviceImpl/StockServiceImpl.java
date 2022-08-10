package com.stockmarket.serviceImpl;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.proxy.CommonProxy;
import com.stockmarket.repository.StockRepository;
import com.stockmarket.service.StockService;
import com.stockmarket.ui.CompanyResponseModel;
import com.stockmarket.ui.StockResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * implentation of stock operations
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;
    private final CommonProxy commonProxy;
    private static final String DATE_TIME_FORMAT ="yyyy-MM-dd:HH:mm:ss";

    public StockServiceImpl(final StockRepository stockRepository,
                            final ModelMapper modelMapper,
                            final CommonProxy commonProxy) {
        this.stockRepository= stockRepository;
        this.modelMapper = modelMapper;
        this.commonProxy =commonProxy;
    }

    /**
     * create new stock of company
     * @param stockDto-> user request object
     * @return saved stock
     */
    @Override
    public Stock createStock(final StockDto stockDto) {
        log.info("createStock method called");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final CompanyResponseModel companyresponse = commonProxy.getCompanyDetail(stockDto.getCompanyCode(),false);
        final Stock stock = modelMapper.map(stockDto,Stock.class);
        stock.setCreatedDate(getCurrentDate());
        stock.setCompanyName(companyresponse.getCompanyName());
        return stockRepository.save(stock);
    }

    /**
     * get all stock as per based on variables
     * @param companyCode-> unique code of company
     * @param startDate -> search startDate
     * @param endDate-> search endDate
     * @return list of stock
     */
    @Override
    public List<Stock> getAll(final String companyCode, final String startDate,final String endDate) {
        log.info("getAll called to get all stock of company with startDate {} and endDate {}",startDate,endDate);
        return stockRepository.findByCriteria(companyCode,startDate,endDate);
    }

    /**
     * delete all stock of company
     * @param companyCode-> unique code of company
     * @return success message
     */
    @Override
    public String deleteAllCompanyStock(final String companyCode) {
        log.info("deleteAllCompanyStock called to delete all stock of companyCode {}",companyCode);
        final List<Long> stockIds = stockRepository.findAllByCompanyCode(companyCode)
                .stream().map(Stock::getId).collect(Collectors.toList());

        if(stockIds.isEmpty()){
            return "No stock Available of this company";
        }

        stockRepository.deleteAllById(stockIds);
        return "Stock deleted related to this company";
    }

    /**
     * fetch company stock based on company code
     * @param companyCode> unique code of company
     * @return list of company stock
     */
    @Override
    public List<StockResponseModel> getCompanyLatestStock(final String companyCode) {
        log.info("getCompanyStock called to get stock of companyCode {}",companyCode);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Stock> stocks;
        if(companyCode != null && companyCode.trim().length() >0){
            stocks = stockRepository.findLatestRecord(companyCode);
        }else {
            stocks = stockRepository.findAll();
        }

        List<StockResponseModel> stockList = new ArrayList<>();
        if(!stocks.isEmpty()){
            stockList = stocks.stream()
                    .map(stock -> modelMapper.map(stock,StockResponseModel.class)).collect(Collectors.toList());
        }

        return stockList;
    }

    /**
     * fetch all stock
     * @return list of all stock
     */
    @Override
    public List<StockResponseModel> getAllStock() {
        log.info("getAllStock of whole companies");
        final List<Stock> stocks = stockRepository.findAll();
        List<StockResponseModel> stockList = new ArrayList<>();
        if(!stocks.isEmpty()){
            stockList = mapObject(stocks);
        }

        return stockList;
    }

    /**
     * fetch all stock of company by company code
     * @param companyCode-> unique code of company
     * @return list of stock of companies
     */
    @Override
    public List<StockResponseModel> getAllStockOfCompany(final String companyCode) {
        log.info("getAllStock of companies by compancode");
        final List<Stock> stocks = stockRepository.findAllByCompanyCode(companyCode);

        List<StockResponseModel> stockList = new ArrayList<>();
        if(!stocks.isEmpty()){
            stockList = mapObject(stocks);
        }

        return stockList;
    }

    /**
     * fetch all stock based on muliptle company codes
     * @param companyCodes-> unique code of company
     * @return list of stock of companies
     */
    @Override
    public List<StockResponseModel> getLatestStockOfCompanies(final List<String> companyCodes) {
        log.info("getAllStock of companies by compancode");
        final List<Stock> stocks = stockRepository.findAllByCompanyCodes(companyCodes);

        List<StockResponseModel> stockList = new ArrayList<>();
        if(!stocks.isEmpty()){
            stockList = mapObject(stocks);
        }

        return stockList;
    }

    /**
     * convert the pojo object to user response model
     * @param stocks list of stock
     * @return list of converted response model stock objects
     */
    private List<StockResponseModel> mapObject(final List<Stock> stocks){
        log.info("convert the stock object to response object");
        return stocks.stream()
                .map(stock -> modelMapper.map(stock,StockResponseModel.class)).collect(Collectors.toList());
    }

    private String getCurrentDate(){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StockServiceImpl.DATE_TIME_FORMAT);
        return formatter.format(LocalDateTime.now());
    }


}
