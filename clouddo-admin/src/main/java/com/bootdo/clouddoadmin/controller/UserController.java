package com.bootdo.clouddoadmin.controller;

import com.bootdo.clouddoadmin.domain.UserDO;
import com.bootdo.clouddoadmin.dto.UserDTO;
import com.bootdo.clouddoadmin.dto.do2dto.UserConvert;
import com.bootdo.clouddoadmin.enums.ErrorCode;
import com.bootdo.clouddoadmin.service.RoleService;
import com.bootdo.clouddoadmin.service.UserService;
import com.bootdo.clouddoadmin.utils.BeanUtils;
import com.bootdo.clouddoadmin.utils.MD5Utils;
import com.bootdo.clouddocommon.annotation.Log;
import com.bootdo.clouddocommon.context.FilterContextHandler;
import com.bootdo.clouddocommon.dto.LoginUserDTO;
import com.bootdo.clouddocommon.request.UserRequest;
import com.bootdo.clouddocommon.utils.PageUtils;
import com.bootdo.clouddocommon.utils.Query;
import com.bootdo.clouddocommon.utils.R;
import com.bootdo.clouddocommon.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户信息
 * @author bootdo
 */
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


	/**
	 * 登录的当前用户，前台需要验证用户登录的页面可以调用此方法
	 * @return
	 */
    @GetMapping("/currentUser")
	LoginUserDTO currentUser(){
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setUserId(FilterContextHandler.getUserID());
		loginUserDTO.setUsername(FilterContextHandler.getUsername());
		loginUserDTO.setName(FilterContextHandler.getName());
		return loginUserDTO;
	}

	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
    @GetMapping("{id}")
	R get(@PathVariable("id") Long id ){
		UserDTO userDTO = UserConvert.MAPPER.do2dto(userService.get(id));
    	return R.ok().put("data",userDTO);
	}

	/**
	 * 分页查询用户
	 * @param params
	 * @return
	 */
	@Log("获取用户列表")
    @GetMapping()
    R listByPage(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<UserDTO> userDTOS = UserConvert.MAPPER.dos2dtos((userService.list(query)));
        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(userDTOS, total);
        return R.ok().put("page",pageUtil);
    }

	/**
	 * 增加用户
	 * @param request
	 * @return
	 */
	@PostMapping("save")
    R save(@RequestBody UserRequest request) {
		UserDO user = BeanUtils.copyProperties(request, UserDO.class);
		user.setPassword(MD5Utils.encrypt(request.getUserName(), request.getPassword()));
		Response<Integer> saveResponse = userService.save(user);
		if(!saveResponse.isSuccess()) {
			return R.error(saveResponse.getCode(), saveResponse.getMsg());
		}
		return R.data(saveResponse.getData());
	}

	/**
	 * 修改用户
	 * @param request
	 * @return
	 */
	@PostMapping("update")
	R update(@RequestBody UserRequest request) {
		if(request.getUserId() == null || request.getUserId() <= 0) {
			logger.error("userId is null");
			return R.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
		}
		UserDO user = BeanUtils.copyProperties(request, UserDO.class);
		Response<Integer> updateResponse = userService.update(user);
		if(!updateResponse.isSuccess() || updateResponse.getData() < 0) {
			return R.error(updateResponse.getCode(), updateResponse.getMsg());
		}
		return R.operate(Boolean.TRUE);
	}


	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@PostMapping("delete")
	R remove( Long id) {
		if(id == null || id < 0) {
			return R.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
		}
		Response<Integer> delResponse = userService.remove(id);
		if(!delResponse.isSuccess()) {
			return R.error(delResponse.getCode(), delResponse.getMsg());
		}
		return R.operate (Boolean.TRUE);
	}

	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exits")
	@ResponseBody
	boolean exits(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exits(params);
	}
}
