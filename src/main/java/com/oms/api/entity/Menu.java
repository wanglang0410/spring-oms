package com.oms.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.io.Serializable;

@TableName(value = "menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {
    private String name;
    private String path;
    private String icon;
    private Integer hide_in_menu;
    private Integer parent_id;
    private Integer type_id;
    private Integer order_no;
}
