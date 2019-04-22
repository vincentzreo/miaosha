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
    //根据书名获取图书
    BookModel getBookByTitle(String title);
    //根据作者搜索图书
    List<BookModel> getBookByAuther(String auther);
    //根据分类获取图书
    List<BookModel> getBookByCategory(String category);
    //显示分类
    List<String> getcategory();
    //库存扣减
    boolean decreaseStock(Integer bookId,Integer amount) throws BusinessException;
    //销量增加
    void increaseSales(Integer bookId,Integer amount) throws BusinessException;
}
