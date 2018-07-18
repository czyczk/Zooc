package com.zzzz.dao;

import com.zzzz.po.MomentImg;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface MomentImgDao {
    int insert(MomentImg momentImg) throws SQLException;
    int delete(@Param("momentImgIndex") short momentImgIndex,
               @Param("momentId") long momentId) throws SQLException;

    /**
     * Update a moment image item. Only `img_url` is open for modification.
     * @param momentImg Moment image item
     * @return Number of rows affected
     */
    int update(MomentImg momentImg) throws SQLException;

    /**
     * Check existence of an image item by its primary keys.
     * @param momentImgIndex Moment image item index
     * @param momentId Moment ID
     * @return `true` if it exists or `false` if not
     */
    boolean checkExistenceByPk(@Param("momentImgIndex") short momentImgIndex,
                               @Param("momentId") long momentId) throws SQLException;

    /**
     * Get a moment image item by its primary keys.
     * @param momentImgIndex Moment image item index
     * @param momentId Moment ID
     * @return Moment image item
     */
    MomentImg getByPk(@Param("momentImgIndex") short momentImgIndex,
                      @Param("momentId") long momentId) throws SQLException;

    /**
     * Get the number of images of a moment.
     * @param momentId Moment ID
     * @return The number of images of a moment
     */
    short countTotal(long momentId) throws SQLException;

    /**
     * Get a list of image items of a moment.
     * @param momentId Moment ID
     * @return A list of image items of a moment
     */
    List<MomentImg> list(long momentId) throws SQLException;
}
