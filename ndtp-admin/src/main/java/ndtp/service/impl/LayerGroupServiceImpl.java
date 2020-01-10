package ndtp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ndtp.domain.Depth;
import ndtp.domain.Layer;
import ndtp.domain.LayerGroup;
import ndtp.domain.Move;
import ndtp.persistence.LayerGroupMapper;
import ndtp.service.LayerGroupService;
import ndtp.service.LayerService;

@Slf4j
@Service
public class LayerGroupServiceImpl implements LayerGroupService {
	
	@Autowired
	private LayerService layerService;
	
	@Autowired
	private LayerGroupMapper layerGroupMapper;
	
//	@Autowired
//	private LayerMapper layerMapper;

	/** 
	 * 레이어 그룹 목록 조회한다.
	 */
	@Transactional(readOnly = true)
	public List<LayerGroup> getListLayerGroup() {
		return layerGroupMapper.getListLayerGroup();
	}
	
	/**
     * 데이터 정보 조회
     * @param layerGroupId
     * @return
     */
	@Transactional(readOnly = true)
    public LayerGroup getLayerGroup(Integer layerGroupId) {
		return layerGroupMapper.getLayerGroup(layerGroupId);
	}
	
	/**
	 * 레이어 그룹 목록 및 하위 레이어를 조회
     * @return
     */
	@Transactional(readOnly = true)
	public List<LayerGroup> getListLayerGroupAndLayer() {
		List<LayerGroup> layerGroupList = layerGroupMapper.getListLayerGroup();
		for(LayerGroup layerGroup : layerGroupList) {
			Layer layer = new Layer();
			layer.setLayerGroupId(layerGroup.getLayerGroupId());
			layerGroup.setLayerList(layerService.getListLayer(layer));
		}
		
		return layerGroupList;
	}
	
	/**
	 * 데이터 그룹 표시 순서 수정. UP, DOWN
	 * @param layerGroup
	 * @return
	 */
    @Transactional
	public int updateLayerGroupViewOrder(LayerGroup layerGroup) {
    	
    	LayerGroup dbLayerGroup = layerGroupMapper.getLayerGroup(layerGroup.getLayerGroupId());
    	dbLayerGroup.setUpdateType(layerGroup.getUpdateType());
    	
    	Integer modifyViewOrder = dbLayerGroup.getViewOrder();
    	LayerGroup searchLayerGroup = new LayerGroup();
    	searchLayerGroup.setUpdateType(dbLayerGroup.getUpdateType());
    	searchLayerGroup.setParent(dbLayerGroup.getParent());
    	
    	if(Move.UP == Move.valueOf(dbLayerGroup.getUpdateType())) {
    		// 바로 위 메뉴의 view_order 를 +1
    		searchLayerGroup.setViewOrder(dbLayerGroup.getViewOrder());
    		searchLayerGroup = getDataLayerByParentAndViewOrder(searchLayerGroup);
    		
    		if(searchLayerGroup == null) return 0;
    		
	    	dbLayerGroup.setViewOrder(searchLayerGroup.getViewOrder());
	    	searchLayerGroup.setViewOrder(modifyViewOrder);
    	} else {
    		// 바로 아래 메뉴의 view_order 를 -1 함
    		searchLayerGroup.setViewOrder(dbLayerGroup.getViewOrder());
    		searchLayerGroup = getDataLayerByParentAndViewOrder(searchLayerGroup);
    		
    		if(searchLayerGroup == null) return 0;
    		
    		dbLayerGroup.setViewOrder(searchLayerGroup.getViewOrder());
    		searchLayerGroup.setViewOrder(modifyViewOrder);
    	}
    	
    	updateViewOrderLayerGroup(searchLayerGroup);
		return updateViewOrderLayerGroup(dbLayerGroup);
    }
    
    /** 
	 * 레이어 그룹 등록
	 */
	@Transactional
	public int insertLayerGroup(LayerGroup layerGroup) {
		// TODO 자식 존재 유무 부분은 나중에 추가 하자.
		return layerGroupMapper.insertLayerGroup(layerGroup);
	}
	
	/**
	 * 데이터 그룹 수정
	 * @param dataGroup
	 * @return
	 */
    @Transactional
	public int updateLayerGroup(LayerGroup layerGroup) {
    	return layerGroupMapper.updateLayerGroup(layerGroup);
    }
    
    /**
	 * 데이터 그룹 삭제
	 * @param layerGroup
	 * @return
	 */
    @Transactional
	public int deleteLayerGroup(Integer layerGroupId) {
    	// 삭제하고, children update
    	
    	LayerGroup layerGroup = layerGroupMapper.getLayerGroup(layerGroupId);
    	log.info("--- 111111111 delete dataGroup = {}", layerGroup);
    	
    	int result = 0;
    	if(Depth.ONE == Depth.findBy(layerGroup.getDepth())) {
    		log.info("--- one ================");
    		result = layerGroupMapper.deleteLayerGroupByAncestor(layerGroup);
    	} else if(Depth.TWO == Depth.findBy(layerGroup.getDepth())) {
    		log.info("--- two ================");
    		result = layerGroupMapper.deleteLayerGroupByParent(layerGroup);
    		
    		LayerGroup ancestorLayerGroup = new LayerGroup();
    		ancestorLayerGroup.setLayerGroupId(layerGroup.getAncestor());
    		ancestorLayerGroup = layerGroupMapper.getLayerGroup(ancestorLayerGroup.getLayerGroupId());
    		ancestorLayerGroup.setChildren(ancestorLayerGroup.getChildren() + 1);
	    	
    		log.info("--- delete ancestorDataGroup = {}", ancestorLayerGroup);
    		
	    	layerGroupMapper.updateLayerGroup(ancestorLayerGroup);
    		// ancestor - 1
    	} else if(Depth.THREE == Depth.findBy(layerGroup.getDepth())) {
    		log.info("--- three ================");
    		result = layerGroupMapper.deleteLayerGroup(layerGroup);
    		log.info("--- dataGroup ================ {}", layerGroup);
    		
    		LayerGroup parentDataGroup = new LayerGroup();
	    	parentDataGroup.setLayerGroupId(layerGroup.getParent());
	    	parentDataGroup = layerGroupMapper.getLayerGroup(parentDataGroup.getLayerGroupId());
	    	log.info("--- parentDataGroup ================ {}", parentDataGroup);
	    	parentDataGroup.setChildren(parentDataGroup.getChildren() - 1);
	    	log.info("--- parentDataGroup children ================ {}", parentDataGroup);
	    	layerGroupMapper.updateLayerGroup(parentDataGroup);
    	} else {
    		
    	}
    	
    	return result;
    }
    
    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param layerGroup
     * @return
     */
    private LayerGroup getDataLayerByParentAndViewOrder(LayerGroup layerGroup) {
    	return layerGroupMapper.getLayerGroupByParentAndViewOrder(layerGroup);
    }
    
    /**
	 * 
	 * @param layerGroup
	 * @return
	 */
	private int updateViewOrderLayerGroup(LayerGroup layerGroup) {
		return layerGroupMapper.updateLayerGroupViewOrder(layerGroup);
	}

}