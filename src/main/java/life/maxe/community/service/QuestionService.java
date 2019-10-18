package life.maxe.community.service;

import life.maxe.community.dto.PaginationDTO;
import life.maxe.community.dto.QuestionDTO;
import life.maxe.community.mapper.QuestionMapper;
import life.maxe.community.mapper.UserMapper;
import life.maxe.community.model.Question;
import life.maxe.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount == 0){
            totalPage = 1;
        }else if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page<1) {
            page=1;
        }
        if (page>totalPage) {
            page = totalPage;
        }
        paginationDTO.setPageination(totalPage,page);

        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount==0){
            totalPage = 1;
        }else if (totalCount % size == 0) {
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        if (page<1) {
            page=1;
        }
        if (page>totalPage) {
            page = totalPage;
        }
        paginationDTO.setPageination(totalPage,page);
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            questionMapper.create(question);
        }else{
            //更新
        }
    }
}
