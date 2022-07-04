package com.stockmarket.repository;

import com.stockmarket.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {

    /**
     * find stock based on variables
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return list of stock
     */
    @Query(value = "SELECT * FROM stock u WHERE "
            + "u.company_code = :companyCode AND DATE(u.created_date) BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Stock> findByCriteria(@Param("companyCode") String companyCode,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * fetch all by company code
     * @param companyCode
     * @return
     */
    List<Stock> findAllByCompanyCode(String companyCode);

    /**
     * fetch latest stock of each company based on company code
     * @param companyCodes
     * @return list of latest stock
     */
    @Query(value ="select * from stock inner join (select max(created_date) as MaxDate from stock group by company_code) tm on company_code " +
            "in (:companyCodes) and created_date = tm.MaxDate",nativeQuery = true)
    List<Stock> findAllByCompanyCode(@Param("companyCodes") List<String> companyCodes);

    /**
     * fetch one latest record
     * @param companyCode
     * @return list one lastest record
     */
    @Query(value = "SELECT * FROM stock where company_code = :companyCode ORDER by created_date DESC LIMIT 1", nativeQuery = true)
    List<Stock> findLatestRecord(@Param("companyCode")String companyCode);
}
