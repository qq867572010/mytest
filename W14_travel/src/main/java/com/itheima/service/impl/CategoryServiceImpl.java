package com.itheima.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.util.JedisUtils;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    /*导航页上分类*/
    @Override
    public String queryAll() throws JsonProcessingException {
        //先从缓存中查找
        String categories = JedisUtils.getCache("categories");
        //判断,如果缓存里没有就加载
        if (categories == null || "".equals(categories)) {
            List<Category> categoryList = dao.queryAll();
            //转换成json格式
            categories = new ObjectMapper().writeValueAsString(categoryList);
            //将String类型的json存入缓存中
            JedisUtils.setCache("categories", categories);
        }
        return categories;
    }
}

