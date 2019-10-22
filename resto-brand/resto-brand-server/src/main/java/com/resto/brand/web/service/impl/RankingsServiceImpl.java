
package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.web.dao.BrandMapper;
import com.resto.brand.web.dao.RankingsMapper;
import com.resto.brand.web.dao.ShopDetailMapper;
import com.resto.brand.web.dto.Names;
import com.resto.brand.web.dto.RankingsDto;
import com.resto.brand.web.model.ShopDetail;
import com.resto.brand.web.model.SmsLog;
import com.resto.brand.web.service.RankingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 *
 */

@Component
@Service
public class RankingsServiceImpl extends GenericServiceImpl<RankingsDto, String> implements RankingsService {

    @Resource
    private RankingsMapper rankingsMapper;

    @Autowired
    private ShopDetailMapper shopDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public GenericDao<RankingsDto, String> getDao() {
        return rankingsMapper;
    }

    @Override
    public RankingsDto selectByBrandIdAndShopId(Long brandId, Long currentShopId) {
        return rankingsMapper.queryByBrandIdAndShopId(brandId,currentShopId);
    }

    @Override
    public RankingsDto selectByBrandId(Long brandId, Long currentShopId, String startDate, String endDate) {
        RankingsDto n = rankingsMapper.selectByBrandIdAndShopId(brandId, currentShopId, startDate, endDate);
        if(n!=null){
            Names names = brandMapper.selectBrandNameAndShopName(currentShopId);
            n.setTotalScore(n.getTotalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setServiceScore(n.getServiceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setEnvironmentalScore(n.getEnvironmentalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setCostPerformanceScore(n.getCostPerformanceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setAtmosphereScore(n.getAtmosphereScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setProduceScore(n.getProduceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setBrandName(names.getBrandName());
            n.setShopName(names.getShopName());
        }
        return n;
    }


/**
     * @Description:查询所有的并排序
     * @Author:disvenk.dai
     * @Date:16:08 2018/5/25 0025
     */

    @Override
    public List<RankingsDto> selectAllOrderByGrade(Integer choose, Long brandId, String formats) {
        List<RankingsDto> rankings = null;
        switch (choose) {
            case 1:
                rankings = rankingsMapper.selectAllOrderByGrade(null, null);
                break;
            case 2:
                rankings = rankingsMapper.selectAllOrderByGrade(brandId, null);
                break;
            case 3:
                rankings = rankingsMapper.selectAllOrderByGrade(null, formats);
                break;
            default:
                break;
        }
        return rankings;
    }


    @Override
    public RankingsDto selectByDateAndBrandIdAndShopId(String date, String brandId, Long shopId) {
        return rankingsMapper.selectByDateAndBrandIdAndShopId(date, brandId, shopId);
    }

    @Override
    public Integer selectRankingByScore(BigDecimal score, String date) {
        return rankingsMapper.selectRankingByScore(score, date);
    }

    @Override
    public void updateOrderGradeByNow(Integer orderGrade) {
        rankingsMapper.updateOrderGradeByNow(orderGrade);
    }

    @Override
    public void deleteRankings(Integer commentCount, String day) {
        rankingsMapper.deleteRankings(commentCount, day);
    }

    @Override
    public List<RankingsDto> selectListRankings(String date,Integer choose, Long brandId, Integer isToDay, String formats) {
        List<RankingsDto> rankings = null;
        switch (choose){
            case 1:
                rankings = rankingsMapper.selectListRankings(null, isToDay, null);
                break;
            case 2:
                rankings = rankingsMapper.selectListRankings(brandId, isToDay, null);
                break;
            case 3:
                rankings = rankingsMapper.selectListRankings(null, isToDay, formats);
                break;
            case 4:
                rankings = rankingsMapper.selectListRankingsByDate(date,null, isToDay, null);
                break;
            default:
                break;
        }
        return rankings;
    }


/**
    *@Description:查询历史总榜区间
    *@Author:disvenk.dai
    *@Date:14:09 2018/6/21 0021
    */

    @Override
    public List<RankingsDto> getListRankings(String startDate, String endDate) {
        List<RankingsDto> rankings = rankingsMapper.selectListRankingsByStartAndEnd(startDate, endDate);
        return  rankings;
    }

    @Override
    public int insertSelective(RankingsDto rankingsDto) {
        return rankingsMapper.insertSelective(rankingsDto);
    }

    /**
    *@Description:取消排名新版需求开始方法
    *@Author:disvenk.dai
    *@Date:11:00 2018/7/19 0019
    */
    @Override
    public PageInfo<RankingsDto> selectAll(Boolean idDownLoad,Integer start,Integer length,Integer choose,Long brandId,Long shopId ,String startDate, String endDate) {
        List<RankingsDto> rankings = null;
        switch (choose){
            case 1:

                if(!idDownLoad){
                    PageHelper.startPage((start/length)+1,length);
                }
                rankings = rankingsMapper.selectAll(null, null, startDate,endDate);

                rankings.forEach(n->{
                    Names names = brandMapper.selectBrandNameAndShopName(n.getShopDetailId());
                    n.setBrandName(names.getBrandName());
                    n.setShopName(names.getShopName());
                    n.setTotalScore(n.getTotalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setServiceScore(n.getServiceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setEnvironmentalScore(n.getEnvironmentalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setCostPerformanceScore(n.getCostPerformanceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setAtmosphereScore(n.getAtmosphereScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setProduceScore(n.getProduceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                });
                break;
            case 2:
                if(!idDownLoad){
                    PageHelper.startPage((start/length)+1,length);
                }
                rankings = rankingsMapper.selectAll(brandId, null, startDate,endDate);
                rankings.forEach(n->{
                    Names names = brandMapper.selectBrandNameAndShopName(n.getShopDetailId());
                    n.setBrandName(names.getBrandName());
                    n.setShopName(names.getShopName());
                    n.setTotalScore(n.getTotalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setServiceScore(n.getServiceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setEnvironmentalScore(n.getEnvironmentalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setCostPerformanceScore(n.getCostPerformanceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setAtmosphereScore(n.getAtmosphereScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setProduceScore(n.getProduceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                });
                break;
            case 3:

                ShopDetail shopDetail = shopDetailMapper.selectByPrimaryKey(shopId);
                if(!idDownLoad){
                    PageHelper.startPage((start/length)+1,length);
                }
                rankings = rankingsMapper.selectAll(null, shopDetail.getBusinessFormatId(), startDate,endDate);
                rankings.forEach(n->{
                    Names names = brandMapper.selectBrandNameAndShopName(n.getShopDetailId());
                    n.setBrandName(names.getBrandName());
                    n.setShopName(names.getShopName());
                    n.setTotalScore(n.getTotalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setServiceScore(n.getServiceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setEnvironmentalScore(n.getEnvironmentalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setCostPerformanceScore(n.getCostPerformanceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setAtmosphereScore(n.getAtmosphereScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                    n.setProduceScore(n.getProduceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
                });
                break;
            default:
                break;
        }
        PageInfo<RankingsDto> pageInfo = new PageInfo<>(rankings);
        return pageInfo;
    }

    @Override
    public RankingsDto selectByShopId(Long shopId, String startDate, String endDate) {
        RankingsDto n = rankingsMapper.selectByShopId(shopId, startDate, endDate);
        if(n!=null){
            Names names = brandMapper.selectBrandNameAndShopName(n.getShopDetailId());
            n.setTotalScore(n.getTotalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setServiceScore(n.getServiceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setEnvironmentalScore(n.getEnvironmentalScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setCostPerformanceScore(n.getCostPerformanceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setAtmosphereScore(n.getAtmosphereScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setProduceScore(n.getProduceScore().divide(BigDecimal.valueOf(n.getAppraiseCount()), RoundingMode.HALF_UP));
            n.setBrandName(names.getBrandName());
            n.setShopName(names.getShopName());
        }

        return n;
    }

    @Override
    public int updateRankings(String appraiseId) {
        return rankingsMapper.updateStatusByAppraiseId(appraiseId);
    }

}

