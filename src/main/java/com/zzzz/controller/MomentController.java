package com.zzzz.controller;

import com.zzzz.dto.MomentCommentParam;
import com.zzzz.dto.MomentImgParam;
import com.zzzz.dto.MomentLikeParam;
import com.zzzz.dto.MomentParam;
import com.zzzz.po.Moment;
import com.zzzz.po.MomentImg;
import com.zzzz.service.*;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.MomentCommentDetail;
import com.zzzz.vo.MomentLikeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MomentController {
    private final MomentService momentService;
    private final MomentCommentService momentCommentService;
    private final MomentLikeService momentLikeService;
    private final MomentImgService momentImgService;

    @Autowired
    public MomentController(MomentService momentService, MomentCommentService momentCommentService,
                            MomentLikeService momentLikeService, MomentImgService momentImgService) {
        this.momentService = momentService;
        this.momentCommentService = momentCommentService;
        this.momentLikeService = momentLikeService;
        this.momentImgService = momentImgService;
    }

    /**
     * Create a moment.
     * @param enterpriseId The ID of the enterprise to which the moment belongs
     * @param param content
     * @return Success: Last moment ID; Bad request: 400; Enterprise not found: 404; Internal: 500
     */
    @PostMapping("/enterprise/{id}/moment")
    public ResponseEntity<Long> createMoment(@PathVariable("id") String enterpriseId,
                                             @RequestBody MomentParam param) throws MomentServiceException, SQLException {
        long lastId = this.momentService.insert(enterpriseId, param.getContent(), new Date());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Update a moment. Only the content is open for modification.
     * The content is left unchanged if the new content is null.
     * @param momentId Moment ID
     * @param param content
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/moment/{id}")
    public ResponseEntity updateMoment(@PathVariable("id") String momentId,
                                       @RequestBody MomentParam param) throws MomentServiceException, SQLException {
        this.momentService.update(momentId, param.getContent());
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a moment.
     * @param momentId Moment ID
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping("/moment/{id}")
    public ResponseEntity deleteMoment(@PathVariable("id") String momentId) throws MomentServiceException, SQLException {
        momentService.delete(momentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list of moments of an enterprise.
     * @param enterpriseId Enterprise ID
     * @param usePagination Use pagination or not (`false` by default): boolean
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @return Success: Moment list result; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/moment/list")
    public ResponseEntity<ListResult<Moment>> listMoments(@PathVariable("id") String enterpriseId,
                                                          String usePagination, String targetPage, String pageSize) throws MomentServiceException, SQLException {
        return ResponseEntity.ok(momentService.list(usePagination, targetPage, pageSize, enterpriseId));
    }

    /**
     * Like a moment.
     * @param momentId Moment ID
     * @param param userId
     * @return Success: 204; Bad request: 400; Not found: 400; Internal: 500
     */
    @PostMapping("/moment/{id}/like")
    public ResponseEntity<Long> like(@PathVariable("id") String momentId,
                                     @RequestBody MomentLikeParam param) throws MomentLikeServiceException, SQLException {
        momentLikeService.insert(momentId, param.getUserId(), new Date());
        return ResponseEntity.noContent().build();
    }

    /**
     * Unlike a moment.
     * @param momentId Moment ID
     * @param userId User ID
     * @return Success: 204; Bad request: 400; Not found: 400; Internal: 500
     */
    @DeleteMapping("/moment/{id}/like")
    public ResponseEntity unlike(@PathVariable("id") String momentId,
                                 String userId) throws MomentLikeServiceException, SQLException {
        momentLikeService.delete(momentId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if a user has liked a moment.
     * @param momentId Moment ID
     * @param userId User ID
     * @return `true` if the user has liked it or `false` otherwise
     */
    @GetMapping("/moment/{id}/like")
    public ResponseEntity<Boolean> hasLiked(@PathVariable("id") String momentId,
                                            String userId) throws MomentLikeServiceException, SQLException {
        boolean hasLiked = momentLikeService.hasLiked(momentId, userId);
        return ResponseEntity.ok(hasLiked);
    }

    /**
     * Get the total number of likes of a moment.
     * @param momentId Moment ID
     * @return Success: total; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/moment/{id}/like/total")
    public ResponseEntity<Long> countTotalLikes(@PathVariable("id") String momentId) throws MomentLikeServiceException, SQLException {
        long total = momentLikeService.countTotal(momentId);
        return ResponseEntity.ok(total);
    }

    /**
     * Get a list of the latest N likes of a moment.
     * @param momentId Moment ID
     * @param n The number of likes to be listed. The actual result can be less than N items.
     * @return Success: Latest N likes; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/moment/{id}/like/latest")
    public ResponseEntity listLatestLikes(@PathVariable("id") String momentId,
                                          String n) throws MomentLikeServiceException, SQLException {
        List<MomentLikeDetail> result = momentLikeService.listLatest(momentId, n);
        return ResponseEntity.ok(result);
    }

    /**
     * Create a moment comment.
     * @param momentId Moment ID
     * @param param userId, content
     * @return Success: new moment comment ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/moment/{id}/comment")
    public ResponseEntity<Long> createComment(@PathVariable("id") String momentId,
                                              @RequestBody MomentCommentParam param) throws SQLException, MomentCommentServiceException {
        long lastId = momentCommentService.insert(momentId, param.getUserId(), param.getContent(), new Date());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Delete a moment comment.
     * @param momentCommentId Moment comment ID
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @DeleteMapping("/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") String momentCommentId) throws SQLException, MomentCommentServiceException {
        momentCommentService.delete(momentCommentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update a moment comment. Only the content is open for modification.
     * The content will be left unchanged if the new content is null.
     * @param momentCommentId Moment comment ID
     * @param param content
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/comment/{id}")
    public ResponseEntity updateComment(@PathVariable("id") String momentCommentId,
                                        @RequestBody MomentCommentParam param) throws SQLException, MomentCommentServiceException {
        momentCommentService.update(momentCommentId, param.getContent());
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list of comments of a moment.
     * @param momentId Moment ID
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @return Success: Moment comment list result; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/moment/{id}/comment/list")
    public ResponseEntity<ListResult<MomentCommentDetail>> listComments(@PathVariable("id") String momentId,
                                                                        String usePagination,
                                                                        String targetPage, String pageSize) throws SQLException, MomentCommentServiceException {
        ListResult<MomentCommentDetail> result = momentCommentService.list(usePagination, targetPage, pageSize, momentId);
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list of images of a moment. URLs inside.
     * @param momentId Moment ID
     * @return Success: Image URLs of a moment (ordered); Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/moment/{id}/img/list")
    public ResponseEntity<List<MomentImg>> listImgs(@PathVariable("id") String momentId) throws SQLException, MomentImgServiceException {
        List<MomentImg> result = momentImgService.list(momentId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update images URLs of a moment.
     * @param momentId Moment ID
     * @param param imgUrls: Image URLs (9 URLs at most, ordered in the list)
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/moment/{id}/img")
    public ResponseEntity updateImgs(@PathVariable("id") String momentId,
                                     @RequestBody MomentImgParam param) throws SQLException, MomentImgServiceException {
        momentImgService.updateImgUrls(momentId, param.getImgUrls());
        return ResponseEntity.noContent().build();
    }
}
