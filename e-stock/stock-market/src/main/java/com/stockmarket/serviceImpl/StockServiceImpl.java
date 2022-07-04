package com.stockmarket.serviceImpl;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.exception.StockException;
import com.stockmarket.repository.StockRepository;
import com.stockmarket.service.StockService;
import com.stockmarket.ui.StockResponseModel;
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
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;

    public StockServiceImpl(StockRepository stockRepository,ModelMapper modelMapper) {
        this.stockRepository= stockRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * create new stock of company
     * @param stockDto
     * @return saved stock
     */
    public Stock createStock(StockDto stockDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
        String createdDate = formatter.format(LocalDateTime.now());
        Stock stock = modelMapper.map(stockDto,Stock.class);
        stock.setCreatedDate(createdDate);

        Stock response =  stockRepository.save(stock);

       if(response != null){
           return response;
       }else {
           throw new NullPointerException("failed to add the stock");
       }

    }

    /**
     * get all stock as per based on variables
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return list of stock
     */
    @Override
    public List<Stock> getAll(String companyCode, String startDate, String endDate) {
        return stockRepository.findByCriteria(companyCode,startDate,endDate);
    }

    /**
     * delete all stock of company
     * @param companyCode
     * @return
     */
    @Override
    public String deleteAllCompanyStock(String companyCode) {
        List<Long> stockIds = stockRepository.findAllByCompanyCode(companyCode)
                .stream().map(stock -> stock.getId()).collect(Collectors.toList());

        if(stockIds == null){
            throw  new StockException("no any stock available ");
        }
        stockRepository.deleteAllById(stockIds);
        return "stock deleted successfully";
    }

    /**
     * fetch company stock based on company code
     * @param companyCode
     * @return list of company stock
     */
    @Override
    public List<StockResponseModel> getCompanyStock(String companyCode) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Stock> stocks;
        if(companyCode != null && companyCode.trim().length() >0){
            stocks = stockRepository.findLatestRecord(companyCode);
        }else {
            stocks = stockRepository.findAll();
        }

        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        if(stocks.size() > 0){
            stockResponseModelList = stocks.stream()
                    .map(stock -> modelMapper.map(stock,StockResponseModel.class)).collect(Collectors.toList());
        }

        return stockResponseModelList;
    }

    /**
     * fetch all stock
     * @return list of all stock
     */
    public List<StockResponseModel> getAllStock() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        if(stocks.size() > 0){
            stockResponseModelList = mapObject(stocks);
        }

        return stockResponseModelList;
    }

    /**
     * fetch all stock based on muliptle company codes
     * @param companyCodes
     * @return
     */
    @Override
    public List<StockResponseModel> getAllStock(List<String> companyCodes) {
        List<Stock> stocks = stockRepository.findAllByCompanyCode(companyCodes);

        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        if(stocks.size() > 0){
            stockResponseModelList = mapObject(stocks);
        }

        return stockResponseModelList;
    }

    /**
     * convert the pojo object to user response model
     * @param stocks
     * @return list of converted response model stock objects
     */
    private List<StockResponseModel> mapObject(List<Stock> stocks){
        List<StockResponseModel> stockResponseModelList = stocks.stream()
                .map(stock -> modelMapper.map(stock,StockResponseModel.class)).collect(Collectors.toList());
        return stockResponseModelList;
    }


}
