package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.entity.DataVailedException;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.core.util.ApplicationUtils;
import com.resto.brand.web.dao.BrandUserMapper;
import com.resto.brand.web.model.BrandUser;
import com.resto.brand.web.service.BrandUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Component
@Service
public class BrandUserServiceImpl extends GenericServiceImpl<BrandUser, String> implements BrandUserService {

    @Resource
    private BrandUserMapper branduserMapper;

    @Override
    public GenericDao<BrandUser, String> getDao() {
        return branduserMapper;
    }

	@Override
	public BrandUser selectByUsername(String username) {
		return branduserMapper.selectByUsername(username);
	}

	@Override
	public BrandUser authentication(BrandUser brandUser) {
		BrandUser user = branduserMapper.authentication(brandUser);
		//判断是否为空	如果不为空则修改登入时间
		if(user!=null){
			user.setLastLoginTime(new Date());
			update(user);
		}
		return user;
	}
	
	//添加 商家用户信息
	@Override
	public void creatBrandUser(BrandUser brandUser) {
		checkBrandUserNameExits(brandUser);
		brandUser.setId(ApplicationUtils.randomUUID());
		brandUser.setCreateTime(new Date());
		brandUser.setPassword(ApplicationUtils.pwd(brandUser.getPassword()));
		branduserMapper.insert(brandUser);
	}
	
	//验证 商家用户名是否已经存在
	private void checkBrandUserNameExits(BrandUser brandUser) {
		BrandUser oldUser = selectByUsername(brandUser.getUsername());
		if(oldUser!=null){
			throw new DataVailedException("用户名已存在");
		}
	}

	@Override
	public void updatePwd(String id, String password) {
		password = ApplicationUtils.pwd(password);
		branduserMapper.updatePwd(id, password);
	}

	public List<BrandUser> selectListBybrandId(Long currentBrandId) {
		return branduserMapper.selectListBybrandId(currentBrandId);

	}

    @Override
    public BrandUser selectOneByBrandId(Long brandId) {
        return branduserMapper.selectOneByBrandId(brandId);
    }



	@Override
	public BrandUser selectUserInfoByBrandIdAndRole(Long BrandId, int roleId) {
		return branduserMapper.selectUserInfoByBrandIdAndRole(BrandId,roleId);
	}

    @Override
    public Void deleteBrandUser(String id) {
        //根据id查询品牌用户
        BrandUser brandUser = branduserMapper.selectByPrimaryKey(id);
        if(brandUser!=null){
            brandUser.setState(0);
            branduserMapper.updateByPrimaryKeySelective(brandUser);
        }
        return null;
    }

	@Override
	public BrandUser loginByWaitModel(String username, String password) {
		return branduserMapper.loginByWaitModel(username, password);
	}

	@Override
	public void updateSuperPwd(String id, String password) {
		password = ApplicationUtils.pwd(password);
		branduserMapper.updateSuperPwd(id, password);
	}

    @Override
    public List<BrandUser> selectByShopId(Long shopId) {
        return branduserMapper.selectByShopId(shopId);
    }

    @Override
    public BrandUser selectByPhone(String phone) {
        return branduserMapper.selectByPhone(phone);
    }

	@Override
	public List<BrandUser> checkoutUsernameAndEmail(String username, String email) {
		return branduserMapper.checkoutUsernameAndEmail(username,email);
	}
}
