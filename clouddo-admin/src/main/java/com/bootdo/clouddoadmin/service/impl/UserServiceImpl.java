package com.bootdo.clouddoadmin.service.impl;




import com.alibaba.fastjson.JSONObject;
import com.bootdo.clouddoadmin.dao.DeptDao;
import com.bootdo.clouddoadmin.dao.UserDao;
import com.bootdo.clouddoadmin.dao.UserRoleDao;
import com.bootdo.clouddoadmin.domain.DeptDO;
import com.bootdo.clouddoadmin.domain.Tree;
import com.bootdo.clouddoadmin.domain.UserDO;
import com.bootdo.clouddoadmin.domain.UserRoleDO;
import com.bootdo.clouddoadmin.enums.ErrorCode;
import com.bootdo.clouddoadmin.service.UserService;
import com.bootdo.clouddoadmin.utils.BuildTree;
import com.bootdo.clouddoadmin.utils.MD5Utils;
import com.bootdo.clouddoadmin.vo.UserVO;
import com.bootdo.clouddocommon.utils.Response;
import com.bootdo.clouddocommon.utils.Responses;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Author bootdo 1992lcg@163.com
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userMapper;
	@Autowired
	UserRoleDao userRoleMapper;
	@Autowired
	DeptDao deptMapper;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDO get(Long id) {
		List<Long> roleIds = userRoleMapper.listRoleId(id);
		UserDO user = userMapper.get(id);
		user.setDeptName(deptMapper.get(user.getDeptId()).getName());
		user.setroleIds(roleIds);
		return user;
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		return userMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}

	@Override
	public Response<Integer> save(UserDO user) {
		//验证用户是否已存在
		Response<UserDO> existUserResponse =  queryUserByMobile(user.getMobile());
		if(!existUserResponse.isSuccess()) {
			logger.error("query user by mobile failed when save user , mobile: {}", user.getMobile());
			return Responses.fail(ErrorCode.USER_QUERY_ERROR.getCode(), ErrorCode.USER_QUERY_ERROR.getMsg());
		}
		if(existUserResponse.getData() != null) {
			logger.error("user has exist, mobile : {}", user.getMobile());
			return Responses.fail(ErrorCode.USER_EXIST_ERROR.getCode(), ErrorCode.USER_EXIST_ERROR.getMsg());
		}
		int count = userMapper.save(user);
		if(count == 0) {
			logger.error("save user failed, user : {}", JSONObject.toJSON(user));
			return Responses.fail(ErrorCode.USER_SAVE_ERROR.getCode(), ErrorCode.USER_SAVE_ERROR.getMsg());
		}
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		userRoleMapper.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return Responses.of(count);
	}

	@Override
	public Response<Integer> update(UserDO user) {
		int r = userMapper.update(user);
		if(r == 0) {
			logger.error("update user failed, user : {}", JSONObject.toJSON(user));
			return Responses.fail(ErrorCode.USER_SAVE_ERROR.getCode(), ErrorCode.USER_SAVE_ERROR.getMsg());
		}
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		if(null!=roles){
			userRoleMapper.removeByUserId(userId);
			List<UserRoleDO> list = new ArrayList<>();
			for (Long roleId : roles) {
				UserRoleDO ur = new UserRoleDO();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				userRoleMapper.batchSave(list);
			}
		}
		return Responses.of(r);
	}

	@Override
	public Response<Integer> remove(Long userId) {
		userRoleMapper.removeByUserId(userId);
		int delNum = userMapper.remove(userId);
		return Responses.of(delNum);
	}

	@Override
	public boolean exits(Map<String, Object> params) {
		boolean exits = userMapper.list(params).size() > 0;
		return exits;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public int resetPwd(UserVO userVO, UserDO userDO) throws Exception {
		if(Objects.equals(userVO.getUserDO().getUserId(),userDO.getUserId())){
			if(Objects.equals(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdOld()),userDO.getPassword())){
				userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdNew()));
				return userMapper.update(userDO);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else{
			throw new Exception("你修改的不是你登录的账号！");
		}
	}
	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		UserDO userDO =get(userVO.getUserDO().getUserId());
		if("admin".equals(userDO.getUsername())){
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
		return userMapper.update(userDO);


	}

	@Transactional
	@Override
	public int batchremove(Long[] userIds) {
		int count = userMapper.batchRemove(userIds);
		userRoleMapper.batchRemoveByUserId(userIds);
		return count;
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
		Long[] pDepts = deptMapper.listParentDept();
		Long[] uDepts = userMapper.listAllDept();
		Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
		for (DeptDO dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
				continue;
			}
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
		for (UserDO user : users) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public int updatePersonal(UserDO userDO) {
		return userMapper.update(userDO);
	}

	@Override
	public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
		return null;
	}

	@Override
	public Response<UserDO> queryUserByMobile(String mobile) {
		UserDO userDO = userMapper.getUserByMobile(mobile);
		return Responses.of(userDO);
	}


}
