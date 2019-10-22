package com.resto.brand.test.service;


import javax.annotation.Resource;

import org.junit.Test;

import com.resto.brand.core.enums.MenuType;
import com.resto.brand.core.feature.test.TestSupport;
import com.resto.brand.web.model.Permission;
import com.resto.brand.web.service.PermissionService;

public class PermissionServiceTest extends TestSupport{

	@Resource
	PermissionService permissonService;
	
	@Test
	public void addPermission() {
		String []  classPrimay = new String []{
				"OtherConfig",
		};
		
		String [] permissionList = new String[]{"add","delete","edit"};
		Permission rootPermission = new Permission();
		rootPermission.setIsMenu(true);
		rootPermission.setMenuType(MenuType.AJAX);
		rootPermission.setPermissionName("Auto Genic Manager");
		rootPermission.setPermissionSign("auto genic"+System.currentTimeMillis());
		permissonService.insert(rootPermission);
		Long rootId = rootPermission.getId();
		for (String cp: classPrimay) {
			String modelName = cp;
			String modelNameLower = modelName.toLowerCase();
			Permission p = new Permission();
			p.setIsMenu(true);
			p.setMenuType(MenuType.AJAX);
			p.setPermissionName(modelName+" Manager");
			p.setPermissionSign(modelNameLower+"/list");
			p.setParentId(rootId);
			permissonService.insert(p);
			
			Long pid = p.getId();
			for(String childPermission:permissionList){
				Permission child = new Permission();
				child.setIsMenu(false);
				child.setMenuType(MenuType.BUTTON);
				child.setPermissionName(modelNameLower+":"+childPermission);
				child.setPermissionSign(modelNameLower+"/"+childPermission);
				child.setParentId(pid);
				permissonService.insert(child);
			}
		}
	}

}
