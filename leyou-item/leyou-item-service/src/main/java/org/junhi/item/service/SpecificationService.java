package org.junhi.item.service;

import org.junhi.item.mapper.SpecGroupMapper;
import org.junhi.item.mapper.SpecParamMapper;
import org.junhi.item.pojo.SpecGroup;
import org.junhi.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SpecGroup和SpecParam共同使用的service
 * @author junhi
 * @date 2019/7/25 19:43
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsById(Long cid) {
        SpecGroup specGroup = SpecGroup.builder().cid(cid).build();
        return this.specGroupMapper.select(specGroup);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {

        SpecParam specParam = SpecParam.builder()
                .groupId(gid)
                .cid(cid)
                .generic(generic)
                .searching(searching)
                .build();
        return this.specParamMapper.select(specParam);

    }

    /**
     * 新增一个品牌中的参数
     * @param specParam
     */
    public void saveParam(SpecParam specParam) {
        this.specParamMapper.insert(specParam);
    }

    /**
     * 根据pid删除一个参数
     * @param id
     */
    public void deleteParamByPid(Long id) {
        SpecParam specParam = SpecParam.builder().id(id).build();
        this.specParamMapper.delete(specParam);
    }

    /**
     * 更新一个参数
     * @param specParam
     */
    public void updateParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 根据cid查询组下面的参数
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsWithParam(Long cid) {
        List<SpecGroup> groups = this.queryGroupsById(cid);
        groups.forEach(group -> {
            List<SpecParam> params = this.queryParams(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;
    }
}
