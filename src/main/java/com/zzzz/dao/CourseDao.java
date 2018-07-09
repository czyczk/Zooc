package com.zzzz.dao;

import com.zzzz.po.Course;
import com.zzzz.po.CourseStatusEnum;
import com.zzzz.vo.CourseDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CourseDao {
    int insert(Course course) throws SQLException;
    boolean checkExistenceById(long courseId) throws SQLException;
    Course getById(long courseId) throws SQLException;
    CourseDetail getVoById(long courseId) throws SQLException;
    int update(Course course) throws SQLException;

    /**
     * Count the number of items meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the course belongs
     * @param courseId Course ID (optional)
     * @param nameContaining Name containing (optional)
     * @param categoryId Category ID (optional)
     * @param priceMin Lower bound of price range (optional)
     * @param priceMax Upper bound of price range (optional)
     * @param status Status (optional)
     * @return The number of items meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    long countTotal(@Param("enterpriseId") long enterpriseId,
                    @Param("courseId") Long courseId,
                    @Param("nameContaining") String nameContaining,
                    @Param("categoryId") Long categoryId,
                    @Param("priceMin") BigDecimal priceMin,
                    @Param("priceMax") BigDecimal priceMax,
                    @Param("status") CourseStatusEnum status) throws SQLException;

    /**
     * Query a list containing all items meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param enterpriseId The ID of the enterprise to which the courses belong
     * @param courseId Course ID (optional)
     * @param nameContaining Name containing (optional)
     * @param categoryId Category ID (optional)
     * @param priceMin Lower bound of price range (optional)
     * @param priceMax Upper bound of price range (optional)
     * @param status Status (optional)
     * @return A list containing all items meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<CourseDetail> list(@Param("usePagination") boolean usePagination,
                            @Param("starting") Long starting,
                            @Param("pageSize") Long pageSize,
                            @Param("enterpriseId") long enterpriseId,
                            @Param("courseId") Long courseId,
                            @Param("nameContaining") String nameContaining,
                            @Param("categoryId") Long categoryId,
                            @Param("priceMin") BigDecimal priceMin,
                            @Param("priceMax") BigDecimal priceMax,
                            @Param("status") CourseStatusEnum status) throws SQLException;

    /**
     * Query a list containing the latest N items.
     * The actual number of items can be less than the N specified.
     * @param enterpriseId The ID of the enterprise to which the courses belong
     * @param n The number of items to list
     * @return A list containing the latest N items
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<CourseDetail> listLatest(@Param("enterpriseId") long enterpriseId,
                                  @Param("n") int n) throws SQLException;
}
