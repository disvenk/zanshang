
package com.resto.brand.web.service;

import com.github.pagehelper.PageInfo;
import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.dto.RankingsDto;

import java.math.BigDecimal;
import java.util.List;
public interface RankingsService extends GenericService<RankingsDto, String> {
    public RankingsDto selectByBrandId(Long brandId,Long currentShopId,String startDate, String endDate);

    public RankingsDto selectByBrandIdAndShopId(Long brandId,Long currentShopId);

    public List<com.resto.brand.web.dto.RankingsDto> selectAllOrderByGrade(Integer choose,Long brandId,String formats);

    public RankingsDto selectByDateAndBrandIdAndShopId(String date, String brandId, Long shopId);


/**
     * 根据当前分数查询该分数在当天的总榜的排名
     * @param score
     * @param date
     * @return
     */

    public Integer selectRankingByScore(BigDecimal score, String date);


/**
     * 根据当前排名去修改被顶掉的排名
     * @param orderGrade
     */

    void updateOrderGradeByNow(Integer orderGrade);



/**
     * 删除未满足最新设置的记入总榜条件的总榜信息
     * @param commentCount
     */

    void deleteRankings(Integer commentCount, String day);



    /**
     * 查询历史总榜
     * @param choose
     * @param brandId
     * @param formats
     * @return
     */
    List<RankingsDto> selectListRankings(String date,Integer choose, Long brandId, Integer isToDay, String formats);

   public List<RankingsDto> getListRankings(String startDate, String endDate);

   public int insertSelective(RankingsDto rankingsDto);

   public PageInfo<RankingsDto> selectAll(Boolean idDownLoad,Integer start, Integer length, Integer choose, Long brandId,Long shopId , String startDate, String endDate);

    public RankingsDto selectByShopId(Long shopId,String startDate,String endDate);

    public int updateRankings(String appraiseId);
}

