package vn.techmaster.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.bookstore.model.Book;
import vn.techmaster.bookstore.repository.Dao;

@Controller
@RequestMapping("/book")
public class RESTController {
  @Autowired
  private Dao<Book> bookDao;

  @GetMapping
 public ResponseEntity<List<Book>> getListBook(){
   List<Book> listBook = bookDao.getAll();
//    return ResponseEntity.status(HttpStatus.OK).body(listBook);
    return ResponseEntity.ok().body(listBook);
 }

 @GetMapping("/{id}")
 public ResponseEntity<Book> getBookById(@PathVariable int id){
    Optional<Book> bookOptional = bookDao.get(id);
    Book book = null;
    if (bookOptional.isPresent()) {
        book = bookOptional.get();
    }
    return ResponseEntity.ok().body(book);
 }

 @PostMapping("/add")
 public ResponseEntity<List<Book>> createBook(@RequestParam int id, @RequestParam String title, @RequestParam String description){
   Book newBook = new Book(id, title, description);
   bookDao.add(newBook);
   List<Book> listBook = bookDao.getAll();
   return ResponseEntity.status(HttpStatus.CREATED).body(listBook);
 }


 @PutMapping("/update")
 public ResponseEntity<List<Book>> updateBook(@RequestParam int id, @RequestParam String title, @RequestParam String description){
  Book newBook = new Book(id, title, description);
  bookDao.update(newBook);
  List<Book> listBook = bookDao.getAll();
  return ResponseEntity.status(HttpStatus.OK).body(listBook);
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<List<Book>> deleteBook(@PathVariable int id){
   bookDao.deleteByID(id);
   List<Book> listBook = bookDao.getAll();
   return ResponseEntity.status(HttpStatus.OK).body(listBook);
 }
}