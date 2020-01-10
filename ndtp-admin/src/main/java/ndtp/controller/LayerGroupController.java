package ndtp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import ndtp.domain.Key;
import ndtp.domain.LayerGroup;
import ndtp.domain.Policy;
import ndtp.domain.UserSession;
import ndtp.service.LayerGroupService;
import ndtp.service.PolicyService;

@Slf4j
@Controller
@RequestMapping("/layer/")
public class LayerGroupController {
	
	@Autowired
	private LayerGroupService layerGroupService;

	@Autowired
	private PolicyService policyService;
	
	/**
	 * 레이어 그룹 관리
	 */
	@GetMapping(value = "list-group")
	public String list(HttpServletRequest request, @ModelAttribute LayerGroup layerGroup, Model model) {
		List<LayerGroup> layerGroupList = layerGroupService.getListLayerGroupAndLayer();
		//List<LayerGroup> layerGroupList = layerGroupService.getListLayerGroup();
		
		model.addAttribute("layerGroupList", layerGroupList);
		
		return "/layer/list-group";
	}
	
	/**
	 * 사용자 그룹 트리 순서 수정, up, down
	 * @param model
	 * @return
	 */
	@PostMapping(value = "view-order-group/{layerGroupId}")
	@ResponseBody
	public Map<String, Object> moveLayerGroup(HttpServletRequest request, @PathVariable Integer layerGroupId, @ModelAttribute LayerGroup layerGroup) {
		log.info("@@ dataGroup = {}", layerGroup);
		
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		try {
			layerGroup.setLayerGroupId(layerGroupId);
			
			int updateCount = layerGroupService.updateLayerGroupViewOrder(layerGroup);
			if(updateCount == 0) {
				statusCode = HttpStatus.BAD_REQUEST.value();
				errorCode = "data.group.view-order.invalid";
			}
		} catch(Exception e) {
			e.printStackTrace();
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            errorCode = "db.exception";
            message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	/**
	 * 레이어 그룹 등록
	 */
	@GetMapping(value = "input-group")
	public String input(Model model) {
		Policy policy = policyService.getPolicy();
		
		List<LayerGroup> layerGroupList = layerGroupService.getListLayerGroup();
		
		LayerGroup layerGroup = new LayerGroup();
		layerGroup.setParentName(policy.getContentLayerGroupRoot());
		layerGroup.setParent(0);
		
		model.addAttribute("policy", policy);
		model.addAttribute("layerGroup", layerGroup);
		model.addAttribute("layerGroupList", layerGroupList);
		
		return "/layer/input-group";
	}
	
	/**
	 * 레이어 그룹을 등록한다.
	 */
	@PostMapping(value = "insert-group")
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request, @Valid @ModelAttribute LayerGroup layerGroup, BindingResult bindingResult) {
		
		log.info("@@@@@ insert layerGroup = {}", layerGroup);
		
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		
		try {
			UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
			
			if(bindingResult.hasErrors()) {
				message = bindingResult.getAllErrors().get(0).getDefaultMessage();
				log.info("@@@@@ message = {}", message);
				result.put("statusCode", HttpStatus.BAD_REQUEST.value());
				result.put("errorCode", errorCode);
				result.put("message", message);
	            return result;
			}
			
			layerGroup.setUserId(userSession.getUserId());
			
			layerGroupService.insertLayerGroup(layerGroup);
		} catch (Exception e) {
			e.printStackTrace();
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            errorCode = "db.exception";
            message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	
	/**
	 * 레이어 그룹을 수정하기 위한 페이지로 이동한다.
	 */
	@GetMapping(value = "modify-group")
	public String modify(HttpServletRequest request, @RequestParam Integer layerGroupId, Model model) {
		LayerGroup layerGroup = layerGroupService.getLayerGroup(layerGroupId);
		Policy policy = policyService.getPolicy();
		
		model.addAttribute("policy", policy);
		model.addAttribute("layerGroup", layerGroup);
		
		return "/layer/modify-group";
	}
	
	/**
	 * 레이어 그룹 정보 수정
	 * @param layerGroup
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "update-group")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, @Valid LayerGroup layerGroup, BindingResult bindingResult) {
		log.info("@@ layerGroup = {}", layerGroup);
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		
		try {
			if(bindingResult.hasErrors()) {
				message = bindingResult.getAllErrors().get(0).getDefaultMessage();
				log.info("@@@@@ message = {}", message);
				result.put("statusCode", HttpStatus.BAD_REQUEST.value());
				result.put("errorCode", errorCode);
				result.put("message", message);
	            return result;
			}
		
			layerGroupService.updateLayerGroup(layerGroup);
		} catch (Exception e) {
			e.printStackTrace();
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            errorCode = "db.exception";
            message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	/**
	 * 레이어 그룹 정보 삭제
	 * @param layerGroup
	 * @param bindingResult
	 * @return
	 */
	@DeleteMapping(value = "delete-group/{layerGroupId}")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, @PathVariable Integer layerGroupId) {
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		
		try {
					
			layerGroupService.deleteLayerGroup(layerGroupId);
		} catch (Exception e) {
			e.printStackTrace();
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            errorCode = "db.exception";
            message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
}