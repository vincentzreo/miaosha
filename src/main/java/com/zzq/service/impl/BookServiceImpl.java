package com.zzq.service.impl;

import com.zzq.dao.BookDoMapper;
import com.zzq.dao.BookStockDoMapper;
import com.zzq.dataobject.BookDo;
import com.zzq.dataobject.BookStockDo;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.service.BookService;
import com.zzq.service.model.BookModel;
import com.zzq.validator.ValidationResult;
import com.zzq.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private BookDoMapper bookDoMapper;

    @Autowired
    private BookStockDoMapper bookStockDoMapper;

    private BookDo convertBookDoFromBookModel(BookModel bookModel){
        if (bookModel == null){
            return null;
        }
        BookDo bookDo = new BookDo();
        BeanUtils.copyProperties(bookModel,bookDo);
        bookDo.setPrice(bookModel.getPrice().doubleValue());
        return bookDo;
    }
    private BookStockDo convertBookStockDoFromBookModel(BookModel bookModel){
        if (bookModel == null){
            return null;
        }
        BookStockDo bookStockDo = new BookStockDo();
        bookStockDo.setBookId(bookModel.getId());
        bookStockDo.setStock(bookModel.getStock());
        return bookStockDo;
    }

    @Override
    @Transactional
    public BookModel createBook(BookModel bookModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(bookModel);
        if (result.isHsaErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //转化bookModel -> dataobject
        BookDo bookDo = this.convertBookDoFromBookModel(bookModel);
        //写入数据库
        bookDoMapper.insertSelective(bookDo);
        bookModel.setId(bookDo.getId());

        BookStockDo bookStockDo = this.convertBookStockDoFromBookModel(bookModel);
        bookStockDoMapper.insertSelective(bookStockDo);
        //返回创建完成的对象

        return this.getBookById(bookModel.getId());
    }

    @Override
    public List<BookModel> listBook() {
        List<BookDo> bookDoList = bookDoMapper.listBook();
        List<BookModel> bookModelList = bookDoList.stream().map(bookDo -> {
            BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
            BookModel bookModel = this.convertModelFromDataObject(bookDo,bookStockDo);
            return bookModel;
        }).collect(Collectors.toList());
        return bookModelList;
    }

    @Override
    public BookModel getBookById(Integer id) {
        BookDo bookDo = bookDoMapper.selectByPrimaryKey(id);
        if (bookDo == null){
            return null;
        }
        //操作获得库存数量
        BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());

        //将dataobject -> model

        BookModel bookModel = this.convertModelFromDataObject(bookDo,bookStockDo);
        return bookModel;
    }
    private BookModel convertModelFromDataObject(BookDo bookDo,BookStockDo bookStockDo){
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookDo,bookModel);
        bookModel.setPrice(new BigDecimal(bookDo.getPrice()));
        bookModel.setStock(bookStockDo.getStock());
        return bookModel;
    }
}
