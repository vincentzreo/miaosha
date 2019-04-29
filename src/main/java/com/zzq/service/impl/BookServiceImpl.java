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
import java.util.ArrayList;
import java.util.HashSet;
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
    public List<String> getcategory() {
        List<BookDo> bookDoList = bookDoMapper.listBook();
        List<String> categorylist = new ArrayList<>();
        for (BookDo bookDo : bookDoList){
            categorylist.add(bookDo.getCategory());
        }
        return removeDuplicate(categorylist);
    }
    private static List removeDuplicate(List list){
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
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



    @Override
    public BookModel getBookByTitle(String title) {
        BookDo bookDo = bookDoMapper.selectByTitle(title);
        if (bookDo == null){
            return null;
        }
        BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
        BookModel bookModel = this.convertModelFromDataObject(bookDo,bookStockDo);
        return bookModel;
    }

    @Override
    public List<BookModel> getBookByAuther(String auther) {
        List<BookDo> bookDoList = bookDoMapper.selectByAuther(auther);
        List<BookModel> bookModelList = bookDoList.stream().map(bookDo -> {
            BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
            BookModel bookModel = this.convertModelFromDataObject(bookDo,bookStockDo);
            return bookModel;
        }).collect(Collectors.toList());
        return bookModelList;
    }

    @Override
    public List<BookModel> getBookByCategory(String category) {
        List<BookDo> bookDoList = bookDoMapper.selectByCategory(category);
        List<BookModel> bookModelList = bookDoList.stream().map(bookDo -> {
            BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
            BookModel bookModel = this.convertModelFromDataObject(bookDo,bookStockDo);
            return bookModel;
        }).collect(Collectors.toList());
        return bookModelList;
    }

    @Override
    public List<BookModel> getBookByKeyword(String keyword) {
        List<BookDo> bookDoList = bookDoMapper.selectByKeyword(keyword);
        List<BookModel> bookModelList = bookDoList.stream().map(bookDo -> {
            BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
            BookModel bookModel = this.convertModelFromDataObject(bookDo,bookStockDo);
            return bookModel;
        }).collect(Collectors.toList());
        return bookModelList;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer bookId, Integer amount) throws BusinessException {
        int affectedRow = bookStockDoMapper.decreaseStock(bookId,amount);
        if (affectedRow > 0){
            //跟新库存成功
            return true;
        }else {
            //跟新库存失败
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer bookId, Integer amount) throws BusinessException {
        bookDoMapper.increaseSales(bookId,amount);
    }

    @Override
    public void deleteBook(Integer id) {
        BookDo bookDo = bookDoMapper.selectByPrimaryKey(id);
        if (bookDo == null){
            return;
        }
        BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
        bookDoMapper.deleteByPrimaryKey(bookDo.getId());
        bookStockDoMapper.deleteByPrimaryKey(bookStockDo.getId());
    }

    @Override
    public BookModel updateBook(BookModel bookModel) {
        if (bookModel == null){
            return null;
        }
        BookDo bookDo = this.convertBookDoFromBookModel(bookModel);
        BookStockDo bookStockDo = bookStockDoMapper.selectByBookId(bookDo.getId());
        bookStockDo.setStock(bookModel.getStock());
       /* bookStockDo = this.convertBookStockDoFromBookModel(bookModel);*/
        bookDoMapper.updateByPrimaryKeySelective(bookDo);
        bookStockDoMapper.updateByPrimaryKeySelective(bookStockDo);
        BookDo bookDo1 = bookDoMapper.selectByPrimaryKey(bookModel.getId());
        BookStockDo bookStockDo1 = bookStockDoMapper.selectByBookId(bookDo1.getId());
        BookModel bookModel1 = this.convertModelFromDataObject(bookDo1,bookStockDo1);
        return bookModel1;
    }

    private BookModel convertModelFromDataObject(BookDo bookDo,BookStockDo bookStockDo){
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookDo,bookModel);
        bookModel.setPrice(new BigDecimal(bookDo.getPrice()));
        bookModel.setStock(bookStockDo.getStock());
        return bookModel;
    }
}
