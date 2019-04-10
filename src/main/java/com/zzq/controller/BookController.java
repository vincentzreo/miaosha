package com.zzq.controller;

import com.zzq.controller.viewobject.BookVO;
import com.zzq.error.BusinessException;
import com.zzq.response.CommonReturnType;
import com.zzq.service.BookService;
import com.zzq.service.model.BookModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/book")
@RequestMapping("/book")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class BookController extends BaseController {
    @Autowired
    private BookService bookService;
    //创建图书的controller
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType createBook(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "auther")String auther,
                                       @RequestParam(name = "press")String press,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        //封装service请求用来创建图书
        BookModel bookModel = new BookModel();
        bookModel.setTitle(title);
        bookModel.setAuther(auther);
        bookModel.setPress(press);
        bookModel.setPrice(price);
        bookModel.setDescription(description);
        bookModel.setStock(stock);
        bookModel.setImgUrl(imgUrl);
        BookModel bookModelForReturn = bookService.createBook(bookModel);
        BookVO bookVO = convertVOFromModel(bookModelForReturn);
        return CommonReturnType.create(bookVO);
    }
    //详情页浏览
    @RequestMapping(value = "/get",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType getBook(@RequestParam(name = "id")Integer id){
        BookModel bookModel = bookService.getBookById(id);
        BookVO bookVO = convertVOFromModel(bookModel);
        return CommonReturnType.create(bookVO);
    }
    //图书列表页面浏览
    @RequestMapping(value = "/list",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType listBook(){
        List<BookModel> bookModelList = bookService.listBook();
        //使用stream api 将list内的bookModel转化为BOOKVO;
        List<BookVO> bookVOList = bookModelList.stream().map(bookModel -> {
            BookVO bookVO = this.convertVOFromModel(bookModel);
            return bookVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(bookVOList);
    }
    private BookVO convertVOFromModel(BookModel bookModel){
        if (bookModel == null){
            return null;
        }
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookModel,bookVO);
        return bookVO;
    }
}
