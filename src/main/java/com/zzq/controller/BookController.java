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
                                       @RequestParam(name = "category")String category,
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
        bookModel.setCategory(category);
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
    //通过书名搜索图书
    @RequestMapping(value = "/getbytitle",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType getBookByTitle(@RequestParam(name = "title")String title){
        BookModel bookModel = bookService.getBookByTitle(title);
        BookVO bookVO = convertVOFromModel(bookModel);
        return CommonReturnType.create(bookVO);
    }
    //根据书名或作者来搜索图书
    @RequestMapping(value = "/getbykeyword",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType getBookByKeyWord(@RequestParam(name = "keyword")String keyword){
        List<BookModel> bookModelList = bookService.getBookByKeyword(keyword);
        //使用stream api 将list内的bookModel转化为BOOKVO;
        List<BookVO> bookVOList = bookModelList.stream().map(bookModel -> {
            BookVO bookVO = this.convertVOFromModel(bookModel);
            return bookVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(bookVOList);
    }

    //通过作者来搜索图书
    @RequestMapping(value = "/getbyauther",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType getBookByAuther(@RequestParam(name = "auther") String auther){
        List<BookModel> bookModelList = bookService.getBookByAuther(auther);
        //使用stream api 将list内的bookModel转化为BOOKVO;
        List<BookVO> bookVOList = bookModelList.stream().map(bookModel -> {
            BookVO bookVO = this.convertVOFromModel(bookModel);
            return bookVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(bookVOList);
    }
    //通过图书分类来搜索图书
    @RequestMapping(value = "/listbycate",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType listBookBycate(@RequestParam(name = "category") String category){
        List<BookModel> bookModelList = bookService.getBookByCategory(category);
        List<BookVO> bookVOList = bookModelList.stream().map(bookModel -> {
            BookVO bookVO = this.convertVOFromModel(bookModel);
            return bookVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(bookVOList);
    }
    //管理员删除图书
    @RequestMapping(value = "/delete",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType delete(@RequestParam(name = "id")Integer id){
        bookService.deleteBook(id);
        return CommonReturnType.create(null);

    }
    //管理员修改图书信息
    @RequestMapping(value = "/update",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType update(@RequestParam(name = "title")String title,
                                   @RequestParam(name = "auther")String auther,
                                   @RequestParam(name = "press")String press,
                                   @RequestParam(name = "price") BigDecimal price,
                                   @RequestParam(name = "description")String description,
                                   @RequestParam(name = "stock")Integer stock,
                                   @RequestParam(name = "category")String category,
                                   @RequestParam(name = "id")Integer id,
                                   @RequestParam(name = "sales")Integer sales
                                   ){
        BookModel bookModel = new BookModel();
        bookModel.setId(id);
        bookModel.setCategory(category);
        bookModel.setStock(stock);
        bookModel.setDescription(description);
        bookModel.setPrice(price);
        bookModel.setPress(press);
        bookModel.setAuther(auther);
        bookModel.setTitle(title);
        bookModel.setSales(sales);
        BookModel bookModel1 = bookService.updateBook(bookModel);
        return CommonReturnType.create(bookModel1);
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
    //图书类别浏览
    @RequestMapping(value = "/catelist",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType listcate(){
        List<String> cateList = bookService.getcategory();
        return CommonReturnType.create(cateList);
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
