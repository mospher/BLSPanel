package com.linln.admin.system.domain;

import com.linln.common.utils.StatusUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author 岳天一
 * @date 2024/01/12
 */
@Data
@Entity
@Table(name="shallow_info")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class ShallowInfo implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String systemId;
    private String browserId;
    private String publicIp;
    private String osType;
    private String osRelease;
    private String osDistribution;
    private String assetType;
    private String manufactory;
    private String model;
    // 创建时间
    @CreatedDate
    private Date createDate;
}