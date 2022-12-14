package com.example.red.book.manager;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.mapper.NoteMapper;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.vo.NoteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class NoteManager extends ServiceImpl<NoteMapper, Note> {

    private static final String indexName = NoteConstant.INDEX;

    @Autowired
    private ElasticsearchClient esClient;

    public Note getNote(Long userId, String title) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getUserId, userId)
                .eq(Note::getTitle, title));
    }


    public Page<Note> getNotePage(NoteQueryForm noteQueryForm) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>()
                .eq(Note::getIsPublic, true);
        if (noteQueryForm.getUserId() != null) {
            wrapper.eq(Note::getUserId, noteQueryForm.getUserId());
        }
        Page<Note> page = new Page<>(noteQueryForm.getCurrentPage(), noteQueryForm.getSize());
        return this.baseMapper.selectPage(page, wrapper);
    }

    public Page<NoteVO> convertNoteVOPage(NoteQueryForm noteQueryForm, Long selfId) {
        Page<NoteVO> page = new Page<>(noteQueryForm.getCurrentPage(), noteQueryForm.getSize());
        Page<NoteVO> noteVOPage = this.baseMapper.selectNoteList(page, noteQueryForm.getUserId(), true, selfId);
        for (NoteVO noteVO : noteVOPage.getRecords()) {
            noteVO.setImageList(noteVO.getImages() == null ? new ArrayList<>() : Arrays.asList((noteVO.getImages().split(","))));
            noteVO.setImages(null);
        }
        return noteVOPage;
    }


    public Page<NoteVO> getLikedNoteVOPage(NoteQueryForm noteQueryForm, Long selfId) {
        Page<NoteVO> page = new Page<>(noteQueryForm.getCurrentPage(), noteQueryForm.getSize());
        //??????????????????????????????
        Page<NoteVO> noteVOPage = this.baseMapper.selectLikedNoteList(page, noteQueryForm.getUserId());
        return convertNoteVOPage(selfId, noteVOPage);
    }

    public ElasticSearchResult<NoteDoc> getNoteByEs(NoteSearchForm noteSearchForm) {
        BoolQuery boolQuery = new BoolQuery.Builder()
                .mustNot(new TermQuery.Builder().field("isPublic").value("false").build()._toQuery())
                .build();

        MatchQuery matchQuery = new MatchQuery.Builder()
                .query(noteSearchForm.getKeyword())
                .field("title")
                .field("content")
                .build();

        SortOptions sortOptions = new SortOptions.Builder()
                .field(f -> f.field("createdTime").order(SortOrder.Asc))
                .build();

        int start = (noteSearchForm.getCurrentPage() - 1) * noteSearchForm.getSize();

        SearchRequest request = new SearchRequest.Builder()
                .from(start)
                .size(noteSearchForm.getSize())
                .sort(sortOptions)
                .index(indexName)
                .query(boolQuery._toQuery())
                .query(matchQuery._toQuery())
                .build();

        ElasticSearchResult<NoteDoc> result;
        try {
            SearchResponse<NoteDoc> search = esClient.search(request, NoteDoc.class);
            result = new ElasticSearchResult<>(search, noteSearchForm.getCurrentPage(), noteSearchForm.getSize());
        } catch (Exception e) {
            log.error("???????????????{}", noteSearchForm.getKeyword());
            log.error("????????????????????????: {} ", e.getMessage(), e);
            throw GlobalException.from(ResultCode.QueryError);
        }
        return result;
    }

    public Page<NoteVO> getFavoriteNoteVOPage(NoteQueryForm noteQueryForm, Long selfId) {
        Page<NoteVO> page = new Page<>(noteQueryForm.getCurrentPage(), noteQueryForm.getSize());
        //??????????????????????????????
        Page<NoteVO> noteVOPage = this.baseMapper.selectFavoriteNoteList(page, noteQueryForm.getUserId());
        return convertNoteVOPage(selfId, noteVOPage);
    }

    /**
     * ????????????????????????
     *
     * @param selfId
     * @param noteVOPage
     * @return
     */
    private Page<NoteVO> convertNoteVOPage(Long selfId, Page<NoteVO> noteVOPage) {
        List<NoteVO> records = noteVOPage.getRecords();
        List<NoteVO> noteVOList = new ArrayList<>();
        records.forEach(noteVO -> {
            //???????????????????????????????????????????????????
            NoteVO note = this.baseMapper.selectNote(noteVO.getId(), selfId);
            note.setImageList(noteVO.getImages() == null ? new ArrayList<>() : Arrays.asList((noteVO.getImages().split(","))));
            note.setImages(null);
            noteVOList.add(note);
        });
        noteVOPage.setRecords(noteVOList);
        return noteVOPage;
    }
}
