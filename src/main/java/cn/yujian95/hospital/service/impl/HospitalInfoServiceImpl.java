package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.HospitalInfoParam;
import cn.yujian95.hospital.entity.HospitalInfo;
import cn.yujian95.hospital.entity.HospitalInfoExample;
import cn.yujian95.hospital.mapper.HospitalInfoMapper;
import cn.yujian95.hospital.service.IHospitalInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

@Service
public class HospitalInfoServiceImpl implements IHospitalInfoService {

    @Resource
    private HospitalInfoMapper infoMapper;

    /**
     * 添加医院信息
     *
     * @param param 医院信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalInfoParam param) {
        HospitalInfo info = new HospitalInfo();

        BeanUtils.copyProperties(param, info);

        info.setGmtCreate(new Date());
        info.setGmtModified(new Date());

        return infoMapper.insertSelective(info) > 0;
    }

    /**
     * 更新医院信息
     *
     * @param id    医院编号
     * @param param 医院信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, HospitalInfoParam param) {
        HospitalInfo info = new HospitalInfo();

        BeanUtils.copyProperties(param, info);

        info.setId(id);
        info.setGmtModified(new Date());

        return infoMapper.updateByPrimaryKeySelective(info) > 0;
    }

    /**
     * 获取医院信息
     *
     * @param id 医院编号
     * @return 医院信息
     */
    @Override
    public Optional<HospitalInfo> getOptional(Long id) {

        return Optional.ofNullable(infoMapper.selectByPrimaryKey(id));
    }

    /**
     * 删除医院信息
     *
     * @param id 医院编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return infoMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 判断电话是否存在
     *
     * @param phone 电话
     * @return 是否存在
     */
    @Override
    public boolean count(String phone) {
        HospitalInfoExample example = new HospitalInfoExample();

        example.createCriteria()
                .andPhoneEqualTo(phone);

        return infoMapper.countByExample(example) > 0;
    }

    /**
     * 判断医院信息是否存在
     *
     * @param id 医院编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        HospitalInfoExample example = new HospitalInfoExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return infoMapper.countByExample(example) > 0;
    }

    /**
     * 查找医院列表
     *
     * @param name     医院名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 医院列表
     */
    @Override
    public List<HospitalInfo> list(String name, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalInfoExample example = new HospitalInfoExample();

        if (!StringUtils.isEmpty(name)) {
            example.createCriteria()
                    .andNameLike("%" + name + "%");

        }

        return infoMapper.selectByExample(example);
    }
}