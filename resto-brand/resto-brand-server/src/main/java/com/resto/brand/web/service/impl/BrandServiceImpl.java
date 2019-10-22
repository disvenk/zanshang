package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.core.util.ApplicationUtils;
import com.resto.brand.web.dao.BrandMapper;
import com.resto.brand.web.dao.DatabaseConfigMapper;
import com.resto.brand.web.dao.ShopDetailMapper;
import com.resto.brand.web.dao.WechatConfigMapper;
import com.resto.brand.web.dto.BrandDataDto;
import com.resto.brand.web.dto.Names;
import com.resto.brand.web.model.Brand;
import com.resto.brand.web.model.DatabaseConfig;
import com.resto.brand.web.model.ShopDetail;
import com.resto.brand.web.model.WechatConfig;
import com.resto.brand.web.service.BrandService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Component
@Service
public class BrandServiceImpl extends GenericServiceImpl<Brand, Long> implements BrandService {

	@Resource
	private DatabaseConfigMapper databaseConfigMapper;
	@Resource
	private WechatConfigMapper wechatConfigMapper;
	@Resource
	private ShopDetailMapper shopDetailMapper;
    @Resource
    private BrandMapper brandMapper;
    @Resource
	private DatabaseConfigMapper databaseconfigMapper;

    static final String GET_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";

    static final String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Override
    public GenericDao<Brand, Long> getDao() {
        return brandMapper;
    }

	/**
	 * 根据BrandId 查询品牌信息
	 * @param brandId
	 * @return
	 */
	public Brand selectByPrimaryKey(Long brandId){
    	return brandMapper.selectByPrimaryKey(brandId);
	}

    /**
     * 查询 品牌状态为1的品牌详细信息，包含该品牌的数据库配置和微信配置
     */
    @Override
	public List<Brand> selectBrandDetailInfo() {
		return brandMapper.selectBrandDetailInfo();
	}

    /**
     * 添加 品牌 的信息（包括关联的表）
     */
	@Override
	public void insertInfo(Brand brand) {
		//生成 DatabaseConfig 表的 ID 值
		//String wechatConfigId = ApplicationUtils.randomUUID();
		String databaseConfigId = ApplicationUtils.randomUUID();
		Date date = new Date();
		//赋值
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setId(databaseConfigId);
		databaseConfig.setName(brand.getBrandSign());
		databaseConfig.setUrl("jdbc:mysql://rm-uf634nf5s2ds41beoko.mysql.rds.aliyuncs.com:3306/"+brand.getBrandSign()+"?useUnicode=true&characterEncoding=utf8");
		databaseConfig.setCreateTime(date);
		//添加数据
		databaseConfigMapper.insertSelective(databaseConfig);
		//关联 Brand 表的 外键
		brand.setDatabaseConfigId(databaseConfigId);
		List<WechatConfig> wechatConfig = wechatConfigMapper.selectList();
		brand.setWechatConfigId(wechatConfig.get(0).getId());
		brand.setCreateTime(date);
		brandMapper.insertSelective(brand);

	}

	/**
	 * 查询出 品牌 的信息（包括关联的表）
	 */
	@Override
	public Brand selectById(Long id) {
		Brand brand = brandMapper.selectByPrimaryKey(id);
		WechatConfig wechatConfig = wechatConfigMapper.selectByPrimaryKey(brand.getWechatConfigId());
		DatabaseConfig databaseConfig = databaseConfigMapper.selectByPrimaryKey(brand.getDatabaseConfigId());
		//将查询出的关联内容 保存在实体中
		brand.setWechatConfig(wechatConfig);
		brand.setDatabaseConfig(databaseConfig);
		return super.selectById(id);
	}

	/**
	 * 修改 品牌 信息（包括关联的表）
	 * @param brand
	 */
	@Override
	public void updateInfo(Brand brand) {
		//验证 品牌标识 是否重复
		Brand b=brandMapper.selectByPrimaryKey(brand.getId());
		wechatConfigMapper.updateByPrimaryKey(brand.getWechatConfig());
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setId(b.getDatabaseConfigId());
		databaseConfig.setName(brand.getBrandSign());
		databaseConfig.setUrl("jdbc:mysql://rm-uf634nf5s2ds41beoko.mysql.rds.aliyuncs.com:3306/"+brand.getBrandSign()+"?useUnicode=true&characterEncoding=utf8");
		databaseConfig.setUpdateTime(new Date());
		databaseConfigMapper.updateByPrimaryKeySelective(databaseConfig);
		brandMapper.updateByPrimaryKeySelective(brand);
	}


	/**
	 * 查询 品牌 信息 和 该品牌旗下的店铺
	 * @return
	 */
	@Override
	public List<Brand> queryBrandAndShop() {
		List<Brand> brands = brandMapper.selectIdAndName();
		for(Brand brand : brands){
			List<ShopDetail> shopDetails = shopDetailMapper.selectIdAndName(brand.getId());
			brand.setShopDetail(shopDetails);
		}
		return brands;
	}

	@Override
	public List<Brand> selectByBrandSign(String brandSign) {
		List<Brand> brands = brandMapper.selectByBrandSign(brandSign);
		return brands;
	}

	@Override
	public List<BrandDataDto> listBrandDataDto() {
		return brandMapper.listBrandDataDto();
	}

	@Override
	public Names getNames(Long shopId) {
		return brandMapper.selectBrandNameAndShopName(shopId);
	}

	/**
	 * 返回 true 表示验证通过
	 * @param brandSign
	 * @param brandId
	 * @return
	 */
	@Override
	public boolean validataBrandInfo(String brandSign,Long brandId) {
//		boolean flag = false;
//		if(StringUtils.isBlank(brandId.toString())){
//			int brandSignCount = brandMapper.validataParam("brand_sign", brandSign);
//			if(brandSignCount <= 0){
//				flag = true;
//			}
//		}else{
//			Brand brand = brandMapper.selectByPrimaryKey(brandId);
//			if(brand.getBrandSign().equals(brandSign)){
//				flag = true;
//			}else{
//				int brandSignCount = brandMapper.validataParam("brand_sign", brandSign);
//				if(brandSignCount <= 0){
//					flag = true;
//				}
//			}
//		}
		return true;
	}

	@Override
	public void deleteInfo(Long brandId) {
		Brand brand = brandMapper.selectByPrimaryKey(brandId);
		brandMapper.deleteByPrimaryKey(brandId);
		/*wechatConfigMapper.deleteByPrimaryKey(brand.getWechatConfigId());*/
		databaseConfigMapper.deleteByPrimaryKey(brand.getDatabaseConfigId());
	}

}
