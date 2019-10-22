package com.resto.brand.web.dao;

import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.web.dto.RankingsDto;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RankingsMapper  extends GenericDao<RankingsDto,String> {

    RankingsDto selectByBrandIdAndShopId(@Param("brandId") Long brandId, @Param("shopId") Long shopId,@Param("startDate")String startDate,@Param("endDate")String endDate);

    RankingsDto queryByBrandIdAndShopId(@Param("brandId") Long brandId, @Param("shopId") Long shopId);
    //查询排名
    List<RankingsDto> selectAllOrderByGrade(@Param("brandId")Long brandId,@Param("formats")String formats);

    RankingsDto selectByDateAndBrandIdAndShopId(@Param("date") String date, @Param("brandId") String brandId, @Param("shopId") Long shopId);

    Integer selectRankingByScore(@Param("score") BigDecimal score, @Param("date") String date);

    void updateOrderGradeByNow(@Param("orderGrade") Integer orderGrade);

    void deleteRankings(@Param("commentCount") Integer commentCount, @Param("day") String day);

    List<RankingsDto> selectListRankings(@Param("brandId")Long brandId, @Param("isToDay") Integer isToDay, @Param("formats")String formats);

    List<RankingsDto> selectListRankingsByDate(@Param("date")String date,@Param("brandId")Long brandId, @Param("isToDay") Integer isToDay, @Param("formats")String formats);

    List<RankingsDto> selectListRankingsByStartAndEnd(@Param("startDate")String startDate, @Param("endDate")String endDate);

    int insertSelective(RankingsDto rankingsDto);

    List<RankingsDto> selectAll(@Param("brandId")Long brandId,@Param("formatsId")Integer formatsId,@Param("startDate")String startDate,@Param("endDate")String endDate);

    RankingsDto selectByShopId(@Param("shopId")Long shopId,@Param("startDate")String startDate, @Param("endDate")String endDate);

    int updateStatusByAppraiseId(@Param("appraiseId")String appraiseId);
}
