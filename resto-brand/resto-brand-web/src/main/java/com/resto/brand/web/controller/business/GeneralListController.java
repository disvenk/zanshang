
package com.resto.brand.web.controller.business;


import com.github.pagehelper.PageInfo;
import com.resto.brand.core.entity.JSONResult;
import com.resto.brand.core.entity.Result;
import com.resto.brand.web.controller.GenericController;
import com.resto.brand.web.dto.RankingsDto;
import com.resto.brand.web.model.Rankings;
import com.resto.brand.web.model.SmsLog;
import com.resto.brand.web.service.RankingsService;

import com.resto.brand.web.util.DataTablesOutput;
import com.resto.brand.web.util.ExcelUtilZs;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/general")
public class GeneralListController  extends GenericController {

    @Autowired
    private RankingsService rankingsService;

    @RequestMapping("/list")
    public String list(Boolean isBrand){
        return "general/list";
    }

    @RequestMapping("/historyList")
    public String historyList(){
        return "general/historyList";
    }

    @RequestMapping("/info")
    public String info(String brandId, Long shopDetailId, String brandName, String shopName, Integer isToDay, ModelMap modelMap){
        modelMap.addAttribute("brandId",brandId);
        modelMap.addAttribute("shopId",shopDetailId);
        modelMap.addAttribute("brandName",brandName);
        modelMap.addAttribute("shopName",shopName);
        modelMap.addAttribute("isToDay", isToDay);
        return "general/info";
    }

    @RequestMapping("selectAllOrderByGrade")
    @ResponseBody
    public Result selectAllOrderByGrade(Integer choose){
        List<com.resto.brand.web.dto.RankingsDto> rankings = rankingsService.selectAllOrderByGrade(choose, getCurrentBrandId(), null);
        return new JSONResult<>(rankings);
    }

    /**
     * 查询历史总榜
     * @param choose
     * @return
     */

    @RequestMapping("selectListRankings")
    @ResponseBody
    public Result selectListRankings(Integer choose){
        return getSuccessResult(getListRankings(choose));
    }

    /**
    *@Description:查询历史榜单区间
    *@Author:disvenk.dai
    *@Date:13:56 2018/6/21 0021
    */

    @RequestMapping("selectRankingsByStartAndEnd")
    @ResponseBody
    public List<RankingsDto> getListRankingsByStartAndEnd(String startDate,String endDate){
        List<RankingsDto> list = null;

        if(startDate.equals(endDate)){
            list = getListRankingss(startDate,4);
        }else {
            AtomicReference<Integer> order = new AtomicReference<>(0);
            list = rankingsService.getListRankings(startDate,endDate);
            list.forEach(n->{
              order.updateAndGet(v -> v + 1);
                n.setFoatingNumber(0);
                n.setOrderGrade(order.get());
            });
        }

        return list;
    }


/**
     * 查询历史榜单某一天
     * @param choose
     * @return
     */

    private List<RankingsDto> getListRankingss(String date,Integer choose){
        //查询出今日历史榜单
        List<RankingsDto> toDayRankingsList = rankingsService.selectListRankings(date,choose, getCurrentBrandId(), 1, null);
        //计算今日排名
        computingRankings(toDayRankingsList);
        //查询出昨日的历史榜单
        List<RankingsDto> lastDayRankingsList = rankingsService.selectListRankings(date,choose, getCurrentBrandId(), 0, null);
        if (lastDayRankingsList != null && lastDayRankingsList.size() > 0){
            computingRankings(lastDayRankingsList);
            for (RankingsDto rankings : toDayRankingsList){
                //标识在昨日是否存在
                boolean isSelect = false;
                for (RankingsDto lastRankings : lastDayRankingsList){
                    if (rankings.getShopDetailId().equals(lastRankings.getShopDetailId())){
                        isSelect = true;
                        Integer difference = rankings.getOrderGrade() - lastRankings.getOrderGrade();
                        if (difference > 0) {
                            //相比于昨天排名下降
                            rankings.setType(false);
                            rankings.setFoatingNumber(difference);
                        } else {
                            //相比于昨天排名上升或相等
                            rankings.setType(true);
                            rankings.setFoatingNumber(Math.abs(difference));
                        }
                    }
                }
                //如果不存在
                if (!isSelect){
                    rankings.setType(true);
                    rankings.setFoatingNumber(toDayRankingsList.size() - rankings.getOrderGrade());
                }
            }
        }else{
            //昨日不存在总榜排名
            for (RankingsDto rankings : toDayRankingsList){
                rankings.setType(true);
                rankings.setFoatingNumber(toDayRankingsList.size() - rankings.getOrderGrade());
            }
        }
        return toDayRankingsList;
    }



/**
     * 查询历史榜单
     * @param choose
     * @return
     */

    private List<RankingsDto> getListRankings(Integer choose){
        //查询出今日历史榜单
        List<RankingsDto> toDayRankingsList = rankingsService.selectListRankings("",choose, getCurrentBrandId(), 1, null);
        //计算今日排名
        computingRankings(toDayRankingsList);
        //查询出昨日的历史榜单
        List<RankingsDto> lastDayRankingsList = rankingsService.selectListRankings("",choose, getCurrentBrandId(), 0, null);
        if (lastDayRankingsList != null && lastDayRankingsList.size() > 0){
            computingRankings(lastDayRankingsList);
            for (RankingsDto rankings : toDayRankingsList){
                //标识在昨日是否存在
                boolean isSelect = false;
                for (RankingsDto lastRankings : lastDayRankingsList){
                    if (rankings.getShopDetailId().equals(lastRankings.getShopDetailId())){
                        isSelect = true;
                        Integer difference = rankings.getOrderGrade() - lastRankings.getOrderGrade();
                        if (difference > 0) {
                            //相比于昨天排名下降
                            rankings.setType(false);
                            rankings.setFoatingNumber(difference);
                        } else {
                            //相比于昨天排名上升或相等
                            rankings.setType(true);
                            rankings.setFoatingNumber(Math.abs(difference));
                        }
                    }
                }
                //如果不存在
                if (!isSelect){
                    rankings.setType(true);
                    rankings.setFoatingNumber(toDayRankingsList.size() - rankings.getOrderGrade());
                }
            }
        }else{
            //昨日不存在总榜排名
            for (RankingsDto rankings : toDayRankingsList){
                rankings.setType(true);
                rankings.setFoatingNumber(toDayRankingsList.size() - rankings.getOrderGrade());
            }
        }
        return toDayRankingsList;
    }


/**
     * 计算排名
     * @param rankingsList
     */

    private void computingRankings(List<RankingsDto> rankingsList){
        //将当前list以totalScore从大大小排序
        //以水杯的角色用以替换元素
        RankingsDto ranking;
        for (int i = 1; i < rankingsList.size(); i++) {
            for (int j = 0; j < rankingsList.size() - i; j++) {
                if (rankingsList.get(j).getTotalScore().compareTo(rankingsList.get(j + 1).getTotalScore()) < 0){
                    ranking = rankingsList.get(j);
                    rankingsList.set(j, rankingsList.get(j + 1));
                    rankingsList.set(j + 1, ranking);
                }
            }
        }
        //记录当前排名
        int orderGrade = 0;
        //记录当前循环次数
        int frequency = 1;
        BigDecimal totalScore = BigDecimal.ZERO;
        for (RankingsDto rankings : rankingsList){
            if (rankings.getTotalScore().compareTo(totalScore) != 0){
                orderGrade = frequency;
            }
            rankings.setOrderGrade(orderGrade);
            //用以比较前后名次是否相同
            totalScore = rankings.getTotalScore();
            //插入数据存在时则更新
            frequency++;
        }
    }


/**
    *@Description:导出报表
    *@Author:disvenk.dai
    *@Date:13:50 2018/5/26 0026
    */

    @RequestMapping("exportExcel")
    @ResponseBody
    public Result exportExcel(String startDate,String endDate,Integer isHistory, HttpServletRequest request, HttpServletResponse response){

       // List<RankingsDto> rankings;
       /* if (isHistory != null){
            rankings = getListRankings(1);
        } else{
            rankings = rankingsService.selectAllOrderByGrade(1, null, null);
        }*/

        String str;
        Map<String, String> map = new HashMap<>();
        map.put("num", "16");// 显示的位置
        map.put("reportTitle", "好餐厅口碑榜");// 表的名字


        List<RankingsDto> rankings = null;

        if(startDate.equals(endDate)){
             str =  startDate+"好餐厅口碑榜.xls";
            map.put("date",startDate);
            rankings = getListRankingss(startDate,4);
        }else {
             str =  startDate+"至"+endDate+"好餐厅口碑榜.xls";
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            AtomicReference<Integer> order = new AtomicReference<>(0);
            rankings = rankingsService.getListRankings(startDate,endDate);
            rankings.forEach(n->{
                order.updateAndGet(v -> v + 1);
                n.setFoatingNumber(0);
                n.setOrderGrade(order.get());
            });
        }



        String[][] headers = {
                {"总排名", "15"},
                {"品牌", "20"},
                {"店铺名称", "16"},
                {"评论数量", "16"},
                {"总评分", "19"},
                {"服务", "19"},
                {"出品", "19"},
                {"氛围", "19"},
                {"环境", "19"},
                {"性价比", "19"}
        };
        String[] columns = {
                "orderGrade",
                "brandName",
                "shopName",
                "appraiseCount",
                "totalScore",
                "serviceScore",
                "produceScore",
                "atmosphereScore",
                "environmentalScore",
                "costPerformanceScore"
        };
        HSSFWorkbook workbook1 = new HSSFWorkbook();

        ExcelUtilZs<RankingsDto> excelUtil = new ExcelUtilZs<>();

        try {
            excelUtil.exportExcel(str,request,response,workbook1,headers, columns,rankings , map);

            return Result.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getError("下载报表出错");
    }

    @RequestMapping(value = "selectRankings",method = RequestMethod.GET)
    @ResponseBody
    public Result queryRankings(Long currentBrandId,Long currentShopId,String startDate,String endDate) {
        try {
            if (currentBrandId != null & currentShopId != null){
                RankingsDto rankingsList = rankingsService.selectByBrandId(currentBrandId, currentShopId, startDate, endDate);
                if(rankingsList!=null){
                    return new JSONResult(rankingsList,"成功",true) ;
                }
            }
            return Result.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getError("失败");
        }
    }

    /**
    *@Description:start
    *@Author:disvenk.dai
    *@Date:11:02 2018/7/19 0019
    */
    @RequestMapping("selectAll")
    @ResponseBody
    public DataTablesOutput<RankingsDto> selectAll(Integer draw,Integer start, Integer length, String startDate, String endDate){

        DataTablesOutput<RankingsDto> dtable=new DataTablesOutput<RankingsDto>();
        dtable.setDraw(draw);
        PageInfo<RankingsDto> pageInfo = rankingsService.selectAll(false,start,length,1,null,null,startDate,endDate);
        dtable.setRecordsTotal(pageInfo.getTotal());
        dtable.setRecordsFiltered(pageInfo.getTotal());
        dtable.setData(pageInfo.getList());
        return dtable;
    }

    /**
     *@Description:导出报表
     *@Author:disvenk.dai
     *@Date:13:50 2018/5/26 0026
     */

    @RequestMapping("exportExcelNew")
    @ResponseBody
    public Result exportExcelNew(String startDate,String endDate, HttpServletRequest request, HttpServletResponse response){

        // List<RankingsDto> rankings;
       /* if (isHistory != null){
            rankings = getListRankings(1);
        } else{
            rankings = rankingsService.selectAllOrderByGrade(1, null, null);
        }*/

        String str;
        Map<String, String> map = new HashMap<>();
        map.put("num", "16");// 显示的位置
        map.put("reportTitle", "好餐厅口碑榜");// 表的名字


        List<RankingsDto> rankings = null;

        if(startDate.equals(endDate)){
            str =  (startDate+"好餐厅口碑榜.xls").replace(" ","日");
            map.put("date",startDate);
            rankings = rankingsService.selectAll(true,0,0,1,null,null,startDate,endDate).getList();
        }else {
            str =  (startDate+"至"+endDate+"好餐厅口碑榜.xls").replace(" ","日").replace(":","-");
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            rankings = rankingsService.selectAll(true,0,0,1,null,null,startDate,endDate).getList();
        }

        String[][] headers = {
                {"品牌", "20"},
                {"店铺名称", "16"},
                {"评论数量", "16"},
                {"平均分", "19"},
                {"服务", "19"},
                {"出品", "19"},
                {"氛围", "19"},
                {"环境", "19"},
                {"性价比", "19"}
        };
        String[] columns = {
                "brandName",
                "shopName",
                "appraiseCount",
                "totalScore",
                "serviceScore",
                "produceScore",
                "atmosphereScore",
                "environmentalScore",
                "costPerformanceScore"
        };
        HSSFWorkbook workbook1 = new HSSFWorkbook();

        ExcelUtilZs<RankingsDto> excelUtil = new ExcelUtilZs<>();

        try {
            excelUtil.exportExcel(str,request,response,workbook1,headers, columns,rankings , map);

            return Result.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getError("下载报表出错");
    }
}

