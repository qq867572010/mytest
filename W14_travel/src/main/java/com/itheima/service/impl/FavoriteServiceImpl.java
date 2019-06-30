package com.itheima.service.impl;

import com.itheima.dao.FavoriteDao;
import com.itheima.dao.impl.FavoriteDaoImpl;
import com.itheima.domain.Favorite;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.FavoriteService;
import com.itheima.util.JdbcUtils;
import com.itheima.util.PageUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao dao = new FavoriteDaoImpl();

    /*判断路线是否被收藏*/
    @Override
    public boolean isFavorite(String rid, User user) {
        Favorite favorite = dao.isFavorite(rid, user.getUid());
        return favorite != null;
    }

    /*添加收藏*/
    @Override
    public boolean addFavorite(String rid, User user) throws SQLException {
        //把收藏的数据封装成一个Favorite对象
        Favorite favorite = new Favorite();
        favorite.setUser(user);

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式yyyy-MM-dd
        String format = date.format(new Date());//将当前日期设置成yyyy-MM-dd形式的字符串
        favorite.setDate(format);

        Route route = new Route();
        route.setRid(Integer.parseInt(rid));//将字符串类型的rid转换成Integer类型存入route对象中
        favorite.setRoute(route);

        //1. 获取一个连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //2. 创建JDBCTemplate对象
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //3. 开启线程标志：事务管理状态标记
        TransactionSynchronizationManager.initSynchronization();
        //4. 从连接池里获取一个连接
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            //5. 开启事务
            connection.setAutoCommit(false);
            //6. 调用dao，添加收藏数据
            dao.addFavorite(favorite, jdbcTemplate);
            //7. 调用dao，修改收藏数量
            dao.updateFavoriteCount(rid, jdbcTemplate);
            //8. 关闭事务：提交
            connection.commit();
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            //8. 关闭事务：回滚
            connection.rollback();
        } finally {
            //9. 把连接对象的autoCommit恢复成默认值
            connection.setAutoCommit(true);
            //10. 清理线程标志
            TransactionSynchronizationManager.clearSynchronization();
        }
        return false;
    }

    /*查看我的收藏*/
    @Override
    public PageBean<Route> myFavorite(User user, int pageNumber, int pageSize) {
        PageBean<Route> pageBean = new PageBean<>();

        /*当前页码 */
        pageBean.setPageNumber(pageNumber);
        /*每页多少条*/
        pageBean.setPageSize(pageSize);
        /*总共多少数据*/
        int totalCount = dao.myFavorite(user.getUid());
        pageBean.setTotalCount(totalCount);
        /*分了多少页*/
        int pageCount = PageUtils.calcPageCount(totalCount, pageSize);
        pageBean.setPageCount(pageCount);
        /*页码条从几开始显示*/
        int[] pagination = PageUtils.pagination(pageNumber, pageCount);
        pageBean.setStart(pagination[0]);
        /*页码条显示到几结束*/
        pageBean.setEnd(pagination[1]);
        /*当前页的数据集合*/
        int index = PageUtils.calcSqlLimitIndex(pageNumber,pageSize);
        List<Route> data = dao.myAllFavorite(user.getUid(),index,pageSize);
        pageBean.setData(data);

        return pageBean;
    }


}
