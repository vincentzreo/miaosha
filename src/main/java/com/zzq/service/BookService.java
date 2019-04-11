package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.BookModel;

import java.util.List;

public interface BookService {
    //创建
    BookModel createBook(BookModel bookModel) throws BusinessException;
    //列表浏览
    List<BookModel> listBook();
    //详情浏览
    BookModel getBookById(Integer id);
    //库存扣减
    boolean decreaseStock(Integer bookId,Integer amount) throws BusinessException;
    //销量增加
    void increaseSales(Integer bookId,Integer amount) throws BusinessException;
}
