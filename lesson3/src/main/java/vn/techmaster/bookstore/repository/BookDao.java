package vn.techmaster.bookstore.repository;

import java.util.List;
import java.util.Optional;
import vn.techmaster.bookstore.model.Book;

public class BookDao extends Dao<Book> {

  public BookDao() {
    collections.add(new Book(1, "7 viên ngọc rồng", "Hàng trình tìm kiếm 7 viên ngọc cũng Songoku"));
    collections.add(new Book(2, "Cuốn theo chiều gió", "Nội chiến Hoa kỳ, cuộc tình giữa Red Butler và Ohara"));
    collections.add(new Book(3, "Cô bé bán diêm", "Cô bé cô đơn giữa đêm giáng sinh"));
    collections.add(new Book(5, "Tấm Cám", "Bống bống bang bang"));
  }

  @Override
  public List<Book> getAll() {
    return collections;
  }

  @Override
  public Optional<Book> get(int id) {
    // TODO Auto-generated method stub
    for (Book book : collections) {
      if (book.getId() == id) {
        return Optional.of(book);
      }
    }
    //return collections.stream().filter(p -> (p.getId() == id)).findFirst();
   return null; 
  }

  @Override
  public void add(Book t) {
    String s = "Trùng mã ID";
    for (int i = 0; i < collections.size(); i++) {
      if (collections.get(i).getId() == t.getId()){
        t = new Book(t.getId(),s,s);
      }
      }
      collections.add(t);
  }

  @Override
  public void update(Book t) {
    // // TODO Auto-generated method stub
    Optional<Book> findBook = collections.stream().filter(p -> (p.getId() == t.getId())).findFirst();
    if (findBook.isPresent()) {
      Book book = findBook.get();
      int index = collections.indexOf(book);
      collections.set(index, t);
    } else {
      collections.add(t);
    }
   
  }

  @Override
  public void deleteByID(int id) {
    for (int i = collections.size()-1; i >= 0; i--) {
      if(collections.get(i).getId() == id){
        collections.remove(collections.get(i));
        break;
      }
    }
  }

  
}